package com.hllolluni.payment_service.data.repositories;

import com.hllolluni.payment_service.data.entities.Payment;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PaymentRepository extends MongoRepository<Payment, String> {
    Payment findPaymentByUserIdAndEmail(Long userId, String email);
}
