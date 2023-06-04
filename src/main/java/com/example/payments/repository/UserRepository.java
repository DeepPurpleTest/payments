package com.example.payments.repository;

import com.example.payments.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    <T> Optional<T> findByPhoneNumber(String phoneNumber, Class<T> type);
    <T> Optional<T> findById(Long id, Class<T> type);
    <T> List<T> findAllBy(Class<T> type);
}
