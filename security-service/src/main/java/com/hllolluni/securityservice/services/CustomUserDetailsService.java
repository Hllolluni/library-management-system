package com.hllolluni.securityservice.services;

import com.hllolluni.securityservice.entities.UserInfo;
import com.hllolluni.securityservice.models.CustomUserDetails;
import com.hllolluni.securityservice.repositories.UserInfoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserInfoRepository repository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<UserInfo> credential = repository.findUserInfoByEmail(email);
        if (!credential.isPresent()){
            throw new UsernameNotFoundException("User not found!");
        }
        UserInfo user = credential.get();
        return CustomUserDetails.buildUserDetails(user);
    }

}
