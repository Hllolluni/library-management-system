package com.hllolluni.mail.services;


import com.hllolluni.common_module.kafka.EmailModel;

public interface EmailSenderService {

    void sendEmail(EmailModel email);
}
