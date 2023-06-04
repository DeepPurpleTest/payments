package com.example.payments.repository;

import com.example.payments.entity.Card;
import com.example.payments.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CardRepository extends JpaRepository<Card, Long> {
    <T> List<T> findAllByUser(User user, Class<T> type);
}
