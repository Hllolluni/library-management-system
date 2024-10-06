package com.hllolluni.securityservice.models;

import lombok.Builder;

@Builder
public record LoginRequest(String email, String password) {
}
