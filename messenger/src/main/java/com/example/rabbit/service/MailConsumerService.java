package com.example.rabbit.service;

import com.example.rabbit.dto.PaymentDto;
import com.example.rabbit.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class MailConsumerService {
    private final JavaMailSender mailSender;
    private final MessageTemplateService messageTemplateService;
    @RabbitListener(queues = "${alert-queue.name}")
    public void consumeSenderMessage(PaymentDto dto) {
        if(dto.getUserSender().getEmail() != null) {
            sendMessage(dto, dto.getUserSender());
        }

        if(dto.getUserReceiver().getEmail() != null) {
            sendMessage(dto, dto.getUserReceiver());
        }
    }

    private void sendMessage(PaymentDto paymentDto, UserDto userToSent) {
        SimpleMailMessage message = messageTemplateService.getTemplate();
        message.setTo(userToSent.getEmail());
        if(paymentDto.getUserSender().equals(userToSent)){
            fillMessageText(message, paymentDto, paymentDto.getSender().getBalance());
        } else {
            fillMessageText(message, paymentDto, paymentDto.getReceiver().getBalance());
        }
        mailSender.send(message);
    }

    private void fillMessageText(SimpleMailMessage message, PaymentDto paymentDto, BigDecimal balance) {
        String senderCardNumber = paymentDto.getSender().getCardNumber();
        String receiverCardNumber = paymentDto.getReceiver().getCardNumber();
        message.setText(String.format(Objects.requireNonNull(message.getText()),
                getLastFourOfCardNumber(senderCardNumber),
                getLastFourOfCardNumber(receiverCardNumber),
                paymentDto.getAmount(),
                paymentDto.getDate(),
                balance));
    }

    private String getLastFourOfCardNumber(String cardNumber) {
        return "*" + cardNumber.substring(cardNumber.length() - 4);
    }
}
