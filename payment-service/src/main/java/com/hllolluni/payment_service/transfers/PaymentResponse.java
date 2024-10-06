package com.hllolluni.payment_service.transfers;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PaymentResponse {
    public String status;
    public String id;
    public Long created;
}