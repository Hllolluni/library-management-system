package com.hllolluni.common_module.kafka;

import lombok.Builder;

@Builder
public record EmailModel(String toCustomer, String subject, String body) {
}

