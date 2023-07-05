package com.example.payments.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;

import java.math.BigDecimal;

@Getter
@Builder
@Jacksonized
public class InPaymentDto {
    @NotNull
    String sender;
    @NotNull
    String receiver;
    @NotNull
    @Min(5)
    BigDecimal amount;
}
