package com.example.payments.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;
import org.hibernate.validator.constraints.CreditCardNumber;

import java.math.BigDecimal;

@Getter
@Builder
@Jacksonized
public class InPaymentDto {
    @CreditCardNumber
    String sender;
    @CreditCardNumber
    String receiver;
    @NotNull
    @Min(5)
    BigDecimal amount;
}
