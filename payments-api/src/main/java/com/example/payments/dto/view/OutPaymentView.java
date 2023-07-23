package com.example.payments.dto.view;

import com.example.payments.entity.PaymentStatus;
import com.example.payments.dto.identifiable.AbstractOutPaymentIdentifiable;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public interface OutPaymentView extends AbstractOutPaymentIdentifiable {
    Long getId();
    CardView getSender();
    CardView getReceiver();
    BigDecimal getSenderBalanceAfterPayment();
    BigDecimal getReceiverBalanceAfterPayment();
    BigDecimal getAmount();
    PaymentStatus getStatus();
    LocalDateTime getDate();
}
