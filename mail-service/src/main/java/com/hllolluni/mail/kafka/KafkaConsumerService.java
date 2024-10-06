package com.hllolluni.mail.kafka;

import com.hllolluni.common_module.kafka.EmailModel;
import com.hllolluni.mail.services.SimpleEmailSenderServiceImp;
import lombok.AllArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class KafkaConsumerService {
    private final SimpleEmailSenderServiceImp emailSenderService;

    @KafkaListener(topics = "send-message", groupId = "my-group", containerFactory = "factory")
    public void consume(EmailModel message) {
        emailSenderService.sendEmail(message);
    }
}
