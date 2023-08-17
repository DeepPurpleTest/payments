package com.example.payments.dto;

import com.example.payments.dto.identifiable.AbstractOutPaymentIdentifiable;
import com.example.payments.entity.PaymentStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
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
    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.ARRAY)
    private LocalDateTime date;
}
