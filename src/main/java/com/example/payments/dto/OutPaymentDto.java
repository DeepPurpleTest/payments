package com.example.payments.dto;

import com.example.payments.entity.PaymentStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;

import java.math.BigDecimal;

@Getter
@Builder
@Jacksonized
public class OutPaymentDto {
    CardDto sender;
    String receiver;
    BigDecimal amount;
    PaymentStatus status;
    String date;
}
