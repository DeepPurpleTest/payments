package com.example.payments.service;

import com.example.payments.dto.CardDto;
import com.example.payments.entity.Card;
import com.example.payments.entity.User;
import com.example.payments.repository.CardRepository;
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

import static com.example.payments.TestConstants.CARD_DTO_WITH_BALANCE;
import static com.example.payments.TestConstants.USER_ENTITY;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test-env")
@Transactional
class CardServiceTest {
    @Autowired
    private CardService cardService;
    @MockBean
    private UserService userService;
    @MockBean
    private CardRepository cardRepository;

    @Test
    void findAllByIdShouldReturnList() {
        when(cardRepository.findByUser(USER_ENTITY, Card.class)).thenReturn(new ArrayList<>());
        when(userService.findById(1L, User.class)).thenReturn(USER_ENTITY);
        assertNotNull(cardService.findAll(1L));
    }

    @Test
    void findByIdShouldReturnEntity() {
        when(cardRepository.findById(1L, CardDto.class)).thenReturn(Optional.ofNullable(CARD_DTO_WITH_BALANCE));
        assertThat(cardService.findById(1L)).isEqualTo(CARD_DTO_WITH_BALANCE);
    }

    @Test
    void findByIdShouldThrowCardIsNotFoundException() {
        when(cardRepository.findById(1L, CardDto.class)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, ()->cardService.findById(1L));
    }
}
