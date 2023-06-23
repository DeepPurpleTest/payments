package com.example.payments.service;

import com.example.payments.dto.CardDto;
import com.example.payments.entity.Card;
import com.example.payments.entity.CardType;
import com.example.payments.entity.Status;
import com.example.payments.entity.User;
import com.example.payments.repository.CardRepository;
import com.example.payments.util.CardNumberBuilder;
import com.example.payments.util.exception.EntityNotFoundException;
import com.example.payments.util.exception.TransactionIsNotPossibleException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CardService {
    private final CardRepository cardRepository;

    @Transactional(readOnly = true)
    public List<CardDto> findAll(User user) {
        return cardRepository.findByUserId(user.getId(), CardDto.class);
    }

    @Transactional(readOnly = true)
    public List<CardDto> findAll(Long id) {
        return cardRepository.findByUserId(id, CardDto.class);
    }

    @Transactional(readOnly = true)
    public CardDto findById(Long id) {
        Optional<CardDto> byId = cardRepository.findById(id, CardDto.class);
        return byId.orElseThrow(() -> new EntityNotFoundException("Card is not found"));
    }

    @Transactional(readOnly = true)
    public CardDto findByPhoneNumber(Card card) {
        Optional<CardDto> byId = cardRepository.findByCardNumber(card.getCardNumber(), CardDto.class);
        return byId.orElseThrow(() -> new EntityNotFoundException("Card is not found"));
    }

    @Transactional
    public Card createCard(User user, CardType type) {
        Card cardToCreate = Card.builder()
                .cardNumber(CardNumberBuilder.generateCardNumber(type.getPrefix()))
                .user(user)
                .balance(BigDecimal.valueOf(0.0))
                .status(Status.ACTIVE)
                .build();

        return cardRepository.save(cardToCreate);
    }

    @Transactional
    public Card delete(User user, Card cardToFind) {
        Optional<Card> byCardNumber = cardRepository.findByCardNumber(cardToFind.getCardNumber(), Card.class);
        if (byCardNumber.isEmpty()) {
            return null;
        }

        Card card = byCardNumber.get();
        if (card.getUser().equals(user)) {
            if (card.getBalance().compareTo(BigDecimal.valueOf(0.0)) > 0) {
                cardRepository.delete(card);
            } else {
                throw new TransactionIsNotPossibleException("Not enough money on balance");
            }
        } else {
            throw new EntityNotFoundException("Card is not found");
        }
        return card;
    }
}
