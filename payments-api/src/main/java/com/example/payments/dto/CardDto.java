package com.example.payments.dto;

import com.example.payments.dto.identifiable.AbstractCardIdentifiable;
import com.example.payments.entity.Status;
import lombok.*;
import lombok.extern.jackson.Jacksonized;
import org.hibernate.validator.constraints.CreditCardNumber;
import org.springframework.lang.Nullable;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@Jacksonized
public class CardDto implements AbstractCardIdentifiable {
    @Nullable
    private Long id;
    @CreditCardNumber
    private String cardNumber;
    @Nullable
    private BigDecimal balance;
    @Nullable
    private Status status;

}
