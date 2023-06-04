package com.example.payments.service;

import com.example.payments.dto.UserDto;
import com.example.payments.repository.UserRepository;
import com.example.payments.util.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository; // final only
    public UserDto findById(Long id) {
        Optional<UserDto> byId = userRepository.findById(id, UserDto.class);
        return byId.orElseThrow(() -> new UserNotFoundException("User with this id is not found"));
    }

    public UserDto findByPhoneNumber(String phoneNumber) {
        Optional<UserDto> byPhoneNumber = userRepository.findByPhoneNumber(phoneNumber, UserDto.class);
        return byPhoneNumber.orElse(null);

    }

    public List<UserDto> findAll() {
        return userRepository.findAllBy(UserDto.class);
    }
}
