package com.hllolluni.payment_service.services;

import com.hllolluni.common_module.kafka.EmailModel;
import com.hllolluni.common_module.kafka.KafkaProducerService;
import com.stripe.Stripe;
import com.stripe.model.Charge;
import com.hllolluni.payment_service.data.entities.Payment;
import com.hllolluni.payment_service.data.repositories.PaymentRepository;
import com.hllolluni.payment_service.transfers.PaymentResponse;
import com.hllolluni.payment_service.transfers.UserInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class PaymentService {

    @Value("${stripe.api.secretKey}")
    private String stripeSecretKey;

    @Value("${stripe.api.amount}")
    private Long amount;

    private final PaymentRepository paymentRepository;
    private final KafkaProducerService kafkaProducerService;


    public PaymentResponse createPayment(String token, UserInfo user) throws Exception {
        Payment payment = paymentRepository.findPaymentByUserIdAndEmail(user.userId(), user.email());
        if (payment != null) {
            if ((payment.getCreated() - System.currentTimeMillis() / 1000L) > 31536000){
                payment.setValid(false);
                paymentRepository.save(payment);
            } else throw new Exception("Payment is done!");
        }

        Stripe.apiKey = this.stripeSecretKey;
        Map<String, Object> chargeParams = new HashMap<String, Object>();
        chargeParams.put("amount", (int)(amount * 100));
        chargeParams.put("currency", "USD");
        chargeParams.put("source", token);
        Charge charge = Charge.create(chargeParams);

        if (charge != null){
            Payment paymentToBeSaved = new Payment(
                    user.userId(),
                    user.firstName(),
                    user.email(),
                    charge.getCreated(),
                    true
            );
            this.paymentRepository.insert(paymentToBeSaved);
            this.sendPaymentConfirmationEmail(user);
        }

        PaymentResponse paymentResponse = new PaymentResponse(charge.getStatus(), charge.getId(), charge.getCreated());
        return paymentResponse;
    }

    private void sendPaymentConfirmationEmail(UserInfo user) {
        String subject = "Your Payment is done successfully!";
        String body = " Dear " + user.firstName() + ", \n"
                + " Thank you for your payment for the one-year subscription. We are excited to have you with us for the upcoming year!" +
                "\n\n" + "Best regards, " +
                "\n" + "Library management!";
        kafkaProducerService.sendMessage(new EmailModel(user.email(), subject, body));
    }

    public boolean checkPayment(Long userId, String email) throws Exception {
        Payment payment = this.paymentRepository.findPaymentByUserIdAndEmail(userId, email);

        if (payment != null){
            return payment.getValid();
        }
        return false;
    }
}
