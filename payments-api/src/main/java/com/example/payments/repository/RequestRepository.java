package com.example.payments.repository;

import com.example.payments.entity.Card;
import com.example.payments.entity.Request;
import com.example.payments.entity.RequestStatus;
import com.example.payments.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RequestRepository extends JpaRepository<Request, Long> {
    <T> Optional<T> findById(Long id, Class<T> type);
    <T> List<T> findAllByStatus(RequestStatus status, Class<T> type);
    <T> List<T> findAllByUser(User user, Class<T> type);
    <T> List<T> findByUserPhoneNumber(String phoneNumber, Class<T> type);
    @Query("select r from Request r where r.card = :card and (r.status = 'PROCESSING' or r.status = 'UNTREATED')")
    <T> Optional<T> findByCardAndStatus(Card card, Class<T> type);
    <T> Optional<T> findByCardAndStatus(Card card, RequestStatus status, Class<T> type);
}
