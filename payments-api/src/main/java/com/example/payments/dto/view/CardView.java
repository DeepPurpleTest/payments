package com.example.payments.dto.view;

import com.example.payments.dto.identifiable.AbstractCardIdentifiable;
import com.example.payments.entity.Status;

import java.math.BigDecimal;

public interface CardView extends AbstractCardIdentifiable {
    Long getId();
    String getCardNumber();
    BigDecimal getBalance();
    UserView getUser();
    Status getStatus();
}
