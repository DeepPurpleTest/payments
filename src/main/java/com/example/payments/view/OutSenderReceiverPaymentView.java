package com.example.payments.view;

import com.example.payments.entity.PaymentStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public interface OutSenderReceiverPaymentView extends AbstractOutPaymentIdentifiable {
    Long getId();
    CardView getSender();
    CardView getReceiver();
    BigDecimal getAmount();
    PaymentStatus getStatus();
    LocalDateTime getDate();
}
