package com.example.payments.view;

import com.example.payments.entity.Status;

import java.math.BigDecimal;

public interface CardView {
    Long getId();
    String getCardNumber();
    BigDecimal getBalance();
    UserView getUser();
    Status getStatus();
}
