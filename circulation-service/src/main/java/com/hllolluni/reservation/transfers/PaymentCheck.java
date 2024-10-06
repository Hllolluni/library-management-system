package com.hllolluni.reservation.transfers;

import lombok.Builder;

@Builder
public record PaymentCheck(Long userId, String email){}
