package com.example.payments.dto;

import com.example.payments.dto.identifiable.AbstractOutPaymentIdentifiable;
import com.example.payments.entity.PaymentStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.extern.jackson.Jacksonized;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@Jacksonized
public class OutPaymentDto implements AbstractOutPaymentIdentifiable {
    @NotNull
    private Long id;
    @NotNull
    private String sender;
    @NotNull
    private String receiver;
    @NotNull
    private String currentUserCard;
    @NotNull
    private BigDecimal currentCardBalance;
    @NotNull
    private BigDecimal amount;
    @NotNull
    private PaymentStatus status;
    @NotEmpty
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm")
    private String date;
}
