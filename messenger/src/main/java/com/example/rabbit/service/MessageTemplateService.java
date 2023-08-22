package com.example.rabbit.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

@Service
public class MessageTemplateService {
    @Value(value = "${spring.mail.username}")
    private String mailFrom;

    public SimpleMailMessage getTemplate() {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(mailFrom);
        message.setSubject("Payment receipt");
        message.setText("""
                        Sender card: %s
                        Receiver card: %s
                        Amount: %.2f UAH
                        Date: %s
                        Your balance: %.2f UAH
                        """);
        return message;
    }
}
