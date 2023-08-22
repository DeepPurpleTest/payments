package com.example.payments.service;

import com.example.payments.dto.UserDto;
import com.example.payments.entity.User;
import com.example.payments.repository.UserRepository;
import com.example.payments.util.exception.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository; // final only
    @Transactional(readOnly = true)
    public <T> T findById(Long id, Class<T> type) {
        Optional<T> byId = userRepository.findById(id, type);
        return byId.orElseThrow(() -> new EntityNotFoundException(String.format("User with id: %d is not found", id)));
    }

    @Transactional(readOnly = true)
    public UserDto findByPhoneNumber(String phoneNumber) {
        Optional<UserDto> byPhoneNumber = userRepository.findByPhoneNumber(phoneNumber, UserDto.class);
        return byPhoneNumber.orElseThrow(() -> new EntityNotFoundException(String.format("User with phone number %s is not found", phoneNumber)));
    }

    @Transactional(readOnly = true)
    public List<UserDto> findAll() {
        return userRepository.findAllBy(UserDto.class);
    }

    @Transactional
    public User update(User userToUpdate) {
        Optional<User> byId = userRepository.findById(userToUpdate.getId());
        if(byId.isEmpty()) {
            throw new EntityNotFoundException(String.format("User with id %d is not found", userToUpdate.getId()));
        }

        User user = byId.get();
        fillUserToUpdate(user, userToUpdate);
        return user;
    }

    public void fillUserToUpdate(User user, User userToUpdate) {
        user.setName(userToUpdate.getName());
        user.setSurname(userToUpdate.getSurname());
        user.setMiddleName(userToUpdate.getSurname() == null? user.getMiddleName() : userToUpdate.getMiddleName());
        user.setPassword(userToUpdate.getPassword());
        user.setPhoneNumber(userToUpdate.getPhoneNumber());
    }

    @Transactional
    public User create(User userToCreate) {
        String bCryptPass = passwordEncoder.encode(userToCreate.getPassword());
        userToCreate.setPassword(bCryptPass);
        return userRepository.save(userToCreate);
    }

    @Transactional
    public User delete(Long id) {
        Optional<User> byId = userRepository.findById(id);
        if(byId.isEmpty()) {
            throw new EntityNotFoundException(String.format("User with id %s is not found", id));
        }
        User userToDelete = byId.get();
        userRepository.delete(userToDelete);
        return userToDelete;
    }
}
