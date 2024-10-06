package com.hllolluni.mail.controllers;

import com.hllolluni.common_module.kafka.EmailModel;
import com.hllolluni.mail.services.SimpleEmailSenderServiceImp;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/mail")
public class EmailSenderController {

    private final SimpleEmailSenderServiceImp simpleEmailSenderServiceImp;

    @PostMapping("/send")
    public void sendEmail(EmailModel emailModel) {
        simpleEmailSenderServiceImp.sendEmail(emailModel);
    }
}
