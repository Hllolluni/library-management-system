package com.hllolluni.securityservice.repositories;

import com.hllolluni.securityservice.entities.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TokenRepository extends JpaRepository<Token, Long> {

    @Query("""
        select t from Token t inner join UserInfo u on t.user.id = u.id
        where t.user.id = :userId and t.loggedOut = false
        """)
    List<Token> findAllAccessTokensByUser(Long userId);
}
