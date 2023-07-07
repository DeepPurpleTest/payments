package com.example.payments.dto;

import com.example.payments.entity.PaymentStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
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
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm")
    String date;
}
