package com.example.payments.configuration.securityconfig;

import com.example.payments.entity.User;
import com.example.payments.repository.UserRepository;
import com.example.payments.util.exception.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PersonDetailsService implements UserDetailsService {
    private final UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) {
        Optional<User> byPhoneNumber = userRepository.findByPhoneNumber(username, User.class);
        if(byPhoneNumber.isEmpty()) {
            throw new EntityNotFoundException(String.format("User with this phone number %s is not found", username));
        }

        return new PersonDetails(byPhoneNumber.get());
    }
}
