package com.example.payments.service;

import com.example.payments.dto.PaymentDto;
import com.example.payments.entity.Payment;
import com.example.payments.util.mapper.GenericMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentMailService {
    @Value("${alert-queue.exchange}")
    private String exchange;

    @Value("${alert-queue.routing-key.sender-key}")
    private String senderRoutingKey;
    @Value("${alert-queue.routing-key.receiver-key}")
    private String receiverRoutingKey;

    private final RabbitTemplate rabbitTemplate;
    private final GenericMapper<Payment, PaymentDto> mapper;

    public void sendReceiptToSender(Payment payment) {
        System.out.println("Message sent to " + payment.getSender().getUser().getPhoneNumber());
        rabbitTemplate.convertAndSend(exchange, senderRoutingKey, mapper.toDto(payment));
    }

    public void sendReceiptToReceiver(Payment payment) {
        System.out.println("Message sent to " + payment.getReceiver().getUser().getPhoneNumber());
        rabbitTemplate.convertAndSend(exchange, receiverRoutingKey, mapper.toDto(payment));
    }
}
