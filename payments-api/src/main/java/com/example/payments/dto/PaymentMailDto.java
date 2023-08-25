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
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@Jacksonized
public class PaymentMailDto {
    @NotNull
    private CardDto sender;
    @NotNull
    private CardDto receiver;
    @NotNull
    private UserDto userSender;
    @NotNull
    private UserDto userReceiver;
    @NotNull
    private BigDecimal amount;
    @NotNull
    private PaymentStatus status;
    @NotEmpty
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm")
    private LocalDateTime date;
}
