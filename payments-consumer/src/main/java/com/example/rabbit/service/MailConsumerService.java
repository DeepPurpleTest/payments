package com.example.rabbit.service;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class MailConsumerService {
    @RabbitListener(queues = "${alert-queue.name.sender-payment-queue}")
    public void consumeSenderMessage(String message) {
        System.out.println("Message is received -> " + message);
    }

    @RabbitListener(queues = "${alert-queue.name.receiver-payment-queue}")
    public void consumeReceiverMessage(String message) {
        System.out.println("Message is received -> " + message);
    }
}
