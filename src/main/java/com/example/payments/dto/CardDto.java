package com.example.payments.dto;

import com.example.payments.entity.Status;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;
import org.springframework.lang.Nullable;

import java.math.BigDecimal;

@Getter
@Builder
@Jacksonized
public class CardDto {
    @Nullable
    Long id;
    @NotNull
    String cardNumber;
    @Nullable
    BigDecimal balance;
    @Nullable
    Status status;

}
