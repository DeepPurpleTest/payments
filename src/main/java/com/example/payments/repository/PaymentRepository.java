package com.example.payments.repository;

import com.example.payments.entity.Card;
import com.example.payments.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
    <T> List<T> findBySenderOrReceiver(Card sender, Card receiver, Class<T> type); //todo nested dto
}
