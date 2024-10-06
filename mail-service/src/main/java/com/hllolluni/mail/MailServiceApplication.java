package com.hllolluni.mail;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(
        scanBasePackages = {
                "com.hllolluni.mail",
                "com.hllolluni.common_module.kafka"
        }
)
public class MailServiceApplication
{
    public static void main(String[] args) {
        SpringApplication.run(MailServiceApplication.class, args);
    }
}
