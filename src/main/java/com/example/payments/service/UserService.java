package com.example.payments.service;

import com.example.payments.entity.User;
import com.example.payments.repository.UserRepository;
import com.example.payments.util.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository; // final only
    public User getUserById(Long id) {
        Optional<User> byId = userRepository.findById(id);
        if (byId.isEmpty())
            throw new UserNotFoundException("User with this id is not found");

        return byId.get();
    }
}
