package com.example.payments.service;

import com.example.payments.configuration.securityconfig.PersonDetailsService;
import com.example.payments.entity.User;
import com.example.payments.repository.UserRepository;
import com.example.payments.util.exception.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static com.example.payments.TestConstants.PERSON_DETAILS;
import static com.example.payments.TestConstants.USER_CREDENTIALS;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@ActiveProfiles("test")
class PersonDetailsServiceTest {
    @Autowired
    private PersonDetailsService personDetailsService;
    @MockBean
    private UserRepository userRepository;

    @Test
    void loadByUserNameShouldReturnUserDetails() {
        when(userRepository.findByPhoneNumber(USER_CREDENTIALS.getPhoneNumber(), User.class))
                .thenReturn(Optional.of(USER_CREDENTIALS));
        UserDetails userDetails = personDetailsService.loadUserByUsername(USER_CREDENTIALS.getPhoneNumber());
        assertThat(userDetails).isNotNull();
        assertThat(userDetails.getUsername()).isEqualTo(PERSON_DETAILS.getUser().getPhoneNumber());
        assertThat(userDetails.getPassword()).isEqualTo(PERSON_DETAILS.getUser().getPassword());
    }
    @Test
    void loadByUserNameShouldThrowException() {
        when(userRepository.findByPhoneNumber("", User.class))
                .thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, ()->personDetailsService.loadUserByUsername(""));
    }
}
