package com.hllolluni.securityservice.repositories;

import com.hllolluni.securityservice.entities.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserInfoRepository extends JpaRepository<UserInfo, Long> {
    Optional<UserInfo> findUserInfoByEmail(String email);
}
