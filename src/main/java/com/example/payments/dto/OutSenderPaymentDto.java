package com.example.payments.dto;

import com.example.payments.entity.PaymentStatus;
import com.example.payments.view.identifiable.AbstractOutPaymentIdentifiable;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;

import java.math.BigDecimal;

@Getter
@Builder
@Jacksonized
public class OutSenderPaymentDto implements AbstractOutPaymentIdentifiable {
    CardDto sender;
    String receiver;
    BigDecimal amount;
    PaymentStatus status;
    String date;
}
