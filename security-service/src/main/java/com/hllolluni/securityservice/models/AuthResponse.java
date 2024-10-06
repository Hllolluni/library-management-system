package com.hllolluni.securityservice.models;

import lombok.Builder;

@Builder
public record AuthResponse(Long id, String firstName, String email, String role, String accessToken, String refreshToken) {
}
