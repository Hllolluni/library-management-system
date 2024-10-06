package com.hllolluni.mail.services;

import com.hllolluni.common_module.kafka.EmailModel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class SimpleEmailSenderServiceImp implements EmailSenderService{

    private final JavaMailSender javaMailSender;

    public void sendEmail(EmailModel email) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("email@email.com");
        message.setTo(email.toCustomer());
        message.setText(email.body());
        message.setSubject(email.subject());

        try {
            javaMailSender.send(message);
        } catch (MailException mailException) {
            log.error("Problems to send email!");
            mailException.printStackTrace();
        }
    }
}
