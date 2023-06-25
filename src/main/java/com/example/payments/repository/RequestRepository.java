package com.example.payments.repository;

import com.example.payments.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RequestRepository extends JpaRepository<Request, Long> {
    <T> Optional<T> findById(Long id, Class<T> type);
    <T> List<T> findAllBy(Class<T> type);
    <T> List<T> findAllByUser(User user, Class<T> type);
    @Query("select r.id as id, r.card as card, r.user as user, r.admin as admin, r.status as status" +
           " from Request r where r.card = :card and r.status = 'PROCESSING' or r.status = 'UNTREATED'")
    <T> Optional<T> findByCardAndStatus(Card card, Class<T> type);
}
