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
    <T> List<T> findBySenderOrReceiver(Card sender, Card receiver, Class<T> type); //todo nested dto
    @Query("select distinct p.id as id, p.sender as sender, p.receiver as receiver," +
           " p.amount as amount, p.status as status, p.date as date " +
           "from Payment p " +
           "left join Card c on c.cardNumber = p.sender or c.cardNumber = p.receiver " +
           "where c.user.id = :userId")
    <T> List<T> findAllByUserId(@Param("userId") Long id, Class<T> type);
}
