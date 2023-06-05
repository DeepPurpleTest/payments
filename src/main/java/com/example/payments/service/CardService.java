package com.example.payments.service;

import com.example.payments.dto.CardDto;
import com.example.payments.entity.Card;
import com.example.payments.entity.CardType;
import com.example.payments.entity.Status;
import com.example.payments.entity.User;
import com.example.payments.repository.CardRepository;
import com.example.payments.util.CardNumberBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CardService {
    private final CardRepository cardRepository;
    public List<CardDto> findAllCards(User user) {
        return cardRepository.findAllByUser(user, CardDto.class);
    }

    public Card createCard(User user, CardType type) {
        Card cardToCreate = Card.builder()
                .cardNumber(CardNumberBuilder.generateCardNumber(type.getPrefix()))
                .user(user)
                .balance(BigDecimal.valueOf(0.0))
                .status(Status.ACTIVE)
                .build();

        return cardRepository.save(cardToCreate);
    }
}
