package com.example.payments.dto;

import com.example.payments.entity.Status;
import com.example.payments.view.identifiable.AbstractCardIdentifiable;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;
import org.hibernate.validator.constraints.CreditCardNumber;
import org.springframework.lang.Nullable;

import java.math.BigDecimal;

@Getter
@Builder
@Jacksonized
public class CardDto implements AbstractCardIdentifiable {
    @Nullable
    Long id;
    @CreditCardNumber
    String cardNumber;
    @Nullable
    BigDecimal balance;
    @Nullable
    Status status;

}
