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
public class TransactionDto {
    @NotNull
    @Min(1)
    Long senderId;
    @NotNull
    @Min(1)
    Long receiverId;
    @NotNull
    @Min(5)
    BigDecimal amount;
}
