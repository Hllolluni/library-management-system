package com.hllolluni.securityservice.controllers;

import com.hllolluni.securityservice.entities.Role;
import com.hllolluni.securityservice.entities.UserInfo;
import com.hllolluni.securityservice.models.AuthResponse;
import com.hllolluni.securityservice.models.LoginRequest;
import com.hllolluni.securityservice.models.RoleUserForm;
import com.hllolluni.securityservice.models.UserInfoTransfer;
import com.hllolluni.securityservice.services.AuthService;
import com.hllolluni.securityservice.utils.JwtUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final JwtUtils jwtUtils;

    @PostMapping("/register")
    public UserInfo addNewUser(@RequestBody UserInfoTransfer userInfo) {
        return authService.saveUser(userInfo);
    }

    @PostMapping("/role/save")
    public Role addNewRole(@RequestBody Role role) {
        return authService.saveRole(role);
    }

    @PostMapping("/role/addToUser")
    public void addNewRoleToUser(@RequestBody RoleUserForm roleUserForm) throws Exception {
        authService.addRoleToUser(roleUserForm);
    }

    @PostMapping("/refresh_token")
    public AuthResponse refreshToken(HttpServletRequest request, HttpServletResponse response) throws Exception {
        return authService.refreshToken(request, response);
    }

    @PostMapping("/login")
    public AuthResponse loginUser(@RequestBody LoginRequest authRequest) {
        return authService.login(authRequest);
    }

    @PostMapping("/validate-token")
    public void validateToken(@RequestParam String token) {
        jwtUtils.validateToken(token);
    }
}
