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

import java.util.ArrayList;
import java.util.Optional;

import static com.example.payments.TestConstants.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@ActiveProfiles("test-env")
class UserServiceTest {
    @Autowired
    private UserService userService;
    @MockBean
    private UserRepository userRepository;

    @Test
    void findByIdShouldReturnEntity() {
        when(userRepository.findById(1L, UserDto.class)).thenReturn(Optional.of(USER_DTO));
        assertThat(userService.findById(1L, UserDto.class)).isEqualTo(USER_DTO);
    }

    @Test
    void findByIdShouldThrowEntityNotFoundException() {
        when(userRepository.findById(1L, UserDto.class)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, ()->userService.findById(1L, UserDto.class));
    }

    @Test
    void findByPhoneNumberShouldReturnEntity() {
        when(userRepository.findByPhoneNumber("+380777777777", UserDto.class)).thenReturn(Optional.of(USER_DTO));
        assertThat(userService.findByPhoneNumber("+380777777777")).isEqualTo(USER_DTO);
    }

    @Test
    void findByPhoneNumberShouldThrowEntityNotFoundException() {
        when(userRepository.findByPhoneNumber("+380777777777", UserDto.class)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, ()->userService.findByPhoneNumber("+380777777777"));
    }

    @Test
    void findAllShouldReturnList() {
        when(userRepository.findAllBy(UserDto.class)).thenReturn(new ArrayList<>());
        assertThat(userService.findAll()).isNotNull();
    }

    @Test
    void updateShouldReturnRightEntity() {
        when(userRepository.findById(USER_ENTITY.getId())).thenReturn(Optional.of(USER_ENTITY));
        assertThat(userService.update(USER_ENTITY)).isEqualTo(USER_ENTITY);
    }

    @Test
    void updateShouldThrowEntityNotFoundException() {
        when(userRepository.findById(USER_ENTITY.getId())).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, ()->userService.update(USER_ENTITY));
    }

    @Test
    void createShouldReturnRightEntity() {
        when(userRepository.save(USER_ENTITY)).thenReturn(USER_ENTITY_WITH_HASH_PASS);
        assertThat(userService.create(USER_ENTITY)).isEqualTo(USER_ENTITY_WITH_HASH_PASS);
    }

    @Test
    void deleteShouldRemoveEntity() {
        when(userRepository.findById(1L)).thenReturn(Optional.ofNullable(USER_ENTITY));
        assertThat(userService.delete(1L)).isEqualTo(USER_ENTITY);
    }

    @Test
    void deleteShouldThrowEntityNotFoundException() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, ()-> userService.delete(1L));
    }
}
