package com.example.payments.service;

import com.example.payments.dto.UserDto;
import com.example.payments.repository.UserRepository;
import com.example.payments.util.exception.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static com.example.payments.TestConstants.USER_DTO;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@ActiveProfiles("test")
class UserServiceTest {
    @Autowired
    private UserService userService;
    @MockBean
    private UserRepository userRepository;

    @Test
    void findByIdShouldReturnEntity() {
        when(userRepository.findById(1L, UserDto.class)).thenReturn(Optional.of(USER_DTO));
        assertThat(userService.findById(1L)).isEqualTo(USER_DTO);
    }

    @Test
    void findByIdShouldThrowEntityNotFoundException() {
        when(userRepository.findById(1L, UserDto.class)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, ()->userService.findById(1L));
    }
}
