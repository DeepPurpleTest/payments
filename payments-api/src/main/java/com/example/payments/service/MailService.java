package com.example.payments.service;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MailService {
    @Value("${alert-queue.exchange}")
    private String exchange;

    @Value("${alert-queue.routing-key}")
    private String routingKey;

    private final RabbitTemplate rabbitTemplate;

    public void sendMessage(String message) {
        System.out.println("Message sent -> " + message);
        rabbitTemplate.convertAndSend(exchange, routingKey, message);
    }
}
