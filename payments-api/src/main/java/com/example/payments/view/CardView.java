package com.example.payments.view;

import com.example.payments.entity.Status;
import com.example.payments.view.identifiable.AbstractCardIdentifiable;

import java.math.BigDecimal;

public interface CardView extends AbstractCardIdentifiable {
    Long getId();
    String getCardNumber();
    BigDecimal getBalance();
    UserView getUser();
    Status getStatus();
}
