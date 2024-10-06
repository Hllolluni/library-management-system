package com.hllolluni.book_service.security;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    ADMIN("ADMIN"), USER("USER");

    public String name;

    Role(String name) {
        this.name = name;
    }

    @Override
    public String getAuthority() {
        return name();
    }
}