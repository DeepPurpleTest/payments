package com.example.payments.dto;

import com.example.payments.entity.PaymentStatus;
import com.example.payments.view.identifiable.AbstractOutPaymentIdentifiable;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;

import java.math.BigDecimal;

@Getter
@Builder
@Jacksonized
public class OutSenderPaymentDto implements AbstractOutPaymentIdentifiable {
    @NotNull
    CardDto sender;
    @NotEmpty
    String receiver;
    @NotNull
    BigDecimal amount;
    @NotNull
    PaymentStatus status;
    @NotEmpty
    String date;
}