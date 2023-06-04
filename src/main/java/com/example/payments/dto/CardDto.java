package com.example.payments.dto;

import com.example.payments.entity.Status;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.extern.jackson.Jacksonized;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@Jacksonized
@AllArgsConstructor
@NoArgsConstructor
public class CardDto {
    @NotNull
    private String cardNumber;
    @NotNull
    private BigDecimal balance;
    @NotNull
    private Status status;
}
