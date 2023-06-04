package com.example.payments.configuration.securityconfig;

import com.example.payments.entity.User;
import com.example.payments.repository.UserRepository;
import com.example.payments.util.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PersonDetailsService implements UserDetailsService {
    private final UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> byPhoneNumber = userRepository.findByPhoneNumber(username, User.class);
        if(byPhoneNumber.isEmpty()) {
            throw new UserNotFoundException("User with this phone number is not exist");
        }

        return new PersonDetails(byPhoneNumber.get());
    }
}
