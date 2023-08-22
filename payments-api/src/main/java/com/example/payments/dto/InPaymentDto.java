package com.example.payments.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.extern.jackson.Jacksonized;
import org.hibernate.validator.constraints.CreditCardNumber;
import org.springframework.lang.Nullable;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@Jacksonized
public class InPaymentDto {
    @Nullable
    private Long id;
    @CreditCardNumber
    private String sender;
    @CreditCardNumber
    private String receiver;
    @NotNull
    @Min(5)
    private BigDecimal amount;
}
