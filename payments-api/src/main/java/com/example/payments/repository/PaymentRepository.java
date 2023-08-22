package com.example.payments.repository;

import com.example.payments.entity.Card;
import com.example.payments.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
    <T> List<T> findBySenderOrReceiver(Card sender, Card receiver, Class<T> type);
    @Query("select distinct p " +
           "from Payment p " +
           "left join Card c on c.cardNumber = p.sender or c.cardNumber = p.receiver " +
           "where c.user.id = :userId")
    <T> List<T> findAllByUserId(@Param("userId") Long id, Class<T> type);
}
