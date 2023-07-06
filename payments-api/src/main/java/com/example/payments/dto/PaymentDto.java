package com.example.payments.dto;

import com.example.payments.entity.PaymentStatus;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.jackson.Jacksonized;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@Jacksonized
public class PaymentDto {
    @NotNull
    CardDto sender;
    @NotNull
    CardDto receiver;
    @NotNull
    UserDto userSender;
    @NotNull
    UserDto userReceiver;
    @NotNull
    BigDecimal amount;
    @NotNull
    PaymentStatus status;
    @NotEmpty
    String date;
}
