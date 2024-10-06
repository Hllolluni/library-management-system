package com.hllolluni.securityservice.services;

import com.hllolluni.common_module.ReaderStatus;
import com.hllolluni.common_module.ReaderTransfer;
import com.hllolluni.securityservice.entities.Role;
import com.hllolluni.securityservice.entities.Token;
import com.hllolluni.securityservice.entities.UserInfo;
import com.hllolluni.securityservice.exceptions.AppException;
import com.hllolluni.securityservice.mappers.UserInfoConverter;
import com.hllolluni.securityservice.models.AuthResponse;
import com.hllolluni.securityservice.models.CustomUserDetails;
import com.hllolluni.securityservice.models.LoginRequest;
import com.hllolluni.securityservice.models.RoleUserForm;
import com.hllolluni.securityservice.models.UserInfoTransfer;
import com.hllolluni.securityservice.repositories.RoleRepository;
import com.hllolluni.securityservice.repositories.TokenRepository;
import com.hllolluni.securityservice.repositories.UserInfoRepository;
import com.hllolluni.securityservice.utils.JwtUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class AuthService {

    private final UserInfoRepository userInfoRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private final UserInfoConverter userInfoConverter;
    private final TokenRepository tokenRepository;
    private final RestTemplate restTemplate;

    public UserInfo saveUser(UserInfoTransfer credential) {
        Optional<UserInfo> userInfoOptional = userInfoRepository.findUserInfoByEmail(credential.getEmail());
        if (userInfoOptional.isPresent()) {
            throw new AppException("User already exists", HttpStatus.BAD_REQUEST);
        }
        UserInfo userInfo = userInfoConverter.convertToUserInfo(credential);
        Role role = roleRepository.findByName("USER");
        userInfo.setRole(role);
        userInfo.setPassword(passwordEncoder.encode(credential.getPassword()));

        ReaderTransfer readerTransfer = new ReaderTransfer(userInfo.getFirstName(), userInfo.getLastName(), userInfo.getEmail(), ReaderStatus.AVAILABLE);

        restTemplate.postForObject("http://localhost:8082/api/reader", readerTransfer, ReaderTransfer.class);
        return userInfoRepository.save(userInfo);
    }

    public AuthResponse login(LoginRequest authRequest) {
        try {
            Authentication authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(authRequest.email(), authRequest.password()));

            SecurityContextHolder.getContext().setAuthentication(authentication);
            UserInfo userInfo = this.userInfoRepository.findUserInfoByEmail(authentication.getName()).get();
            String jwt = jwtUtils.generateAccessTokenForUser(authentication);
            String refreshToken = jwtUtils.createRefreshToken(authentication);
            CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
            this.revokeAllTokenByUser(userInfo);
            this.saveUserToken(jwt, refreshToken, userInfo);
            return AuthResponse.builder()
                    .id(userDetails.getId())
                    .firstName(userDetails.getFirstName())
                    .email(userDetails.getEmail())
                    .role(new ArrayList<>(userInfo.getRoles()).get(0).getName())
                    .accessToken(jwt)
                    .refreshToken(refreshToken)
                    .build();
        } catch (AuthenticationException e) {
            throw new AppException(e.getMessage(), HttpStatus.UNAUTHORIZED);
        }
    }

    private void revokeAllTokenByUser(UserInfo user) {
        List<Token> validTokens = tokenRepository.findAllAccessTokensByUser(user.getId());
        if(validTokens.isEmpty()) {
            return;
        }

        validTokens.forEach(t-> {
            t.setLoggedOut(true);
        });

        tokenRepository.saveAll(validTokens);
    }

    public Role saveRole(Role role) {
        return this.roleRepository.save(role);
    }

    public void addRoleToUser(RoleUserForm roleUserForm) throws Exception {
        Optional<UserInfo> userInfoOptional = userInfoRepository.findUserInfoByEmail(roleUserForm.email());
        if (!userInfoOptional.isPresent()) {
            throw new Exception();
        }
        UserInfo userInfo = userInfoOptional.get();
        Role role = roleRepository.findByName(roleUserForm.role());
        userInfo.getRoles().add(role);
    }

    private void saveUserToken(String accessToken, String refreshToken, UserInfo user) {
        Token token = new Token();
        token.setAccessToken(accessToken);
        token.setRefreshToken(refreshToken);
        token.setLoggedOut(false);
        token.setUser(user);
        tokenRepository.save(token);
    }

    public AuthResponse refreshToken(HttpServletRequest request, HttpServletResponse response) {
        String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        if(authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new AppException("UNAUTHORIZED", HttpStatus.UNAUTHORIZED);
        }
        String token = authHeader.substring(7);

        if (jwtUtils.validateToken(token)) {
            String email = jwtUtils.getUsernameFromToken(token);
            UserInfo user = this.userInfoRepository.findUserInfoByEmail(email).orElseThrow(() -> new UsernameNotFoundException("No user found!"));

            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            String accessToken = jwtUtils.generateAccessTokenForUser(authentication);
            String refreshToken = jwtUtils.createRefreshToken(authentication);
            revokeAllTokenByUser(user);
            saveUserToken(accessToken, refreshToken, user);
            return new AuthResponse(user.getId(), user.getEmail(), email, new ArrayList<>(user.getRoles()).get(0).getName(), accessToken, refreshToken);
        }
        throw new AppException("Unauthorized!", HttpStatus.UNAUTHORIZED);
    }
}
