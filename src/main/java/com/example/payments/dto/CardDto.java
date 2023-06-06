package com.example.payments.dto;

import com.example.payments.entity.Status;
import jakarta.annotation.Nullable;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;

import java.math.BigDecimal;

@Getter
@Builder
@Jacksonized
public class CardDto {
    @Nullable
    private String cardNumber;
    @Nullable
    private BigDecimal balance;
    @Nullable
    private Status status;
}
