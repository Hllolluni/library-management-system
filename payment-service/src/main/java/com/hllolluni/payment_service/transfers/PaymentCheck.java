package com.hllolluni.payment_service.transfers;

public record PaymentCheck(Long userId, String firstName, String lastName, String email, boolean paid){}
