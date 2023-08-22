package com.example.rabbit.dto;

import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

import java.math.BigDecimal;

@Data
@Builder
@Jacksonized
public class PaymentDto {
    CardDto sender;
    CardDto receiver;
    UserDto userSender;
    UserDto userReceiver;
    BigDecimal amount;
    String date;
}
