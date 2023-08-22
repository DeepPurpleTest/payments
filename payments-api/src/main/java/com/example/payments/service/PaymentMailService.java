package com.example.payments.service;

import com.example.payments.dto.PaymentMailDto;
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

    @Value("${alert-queue.routing-key}")
    private String routingKey;
    private final RabbitTemplate rabbitTemplate;
    private final GenericMapper<Payment, PaymentMailDto> mapper;

    public void sendReceipt(Payment payment) {
        rabbitTemplate.convertAndSend(exchange, routingKey, mapper.toDto(payment));
    }
}
