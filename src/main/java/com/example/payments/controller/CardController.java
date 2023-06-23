package com.example.payments.controller;

import com.example.payments.configuration.securityconfig.PersonDetails;
import com.example.payments.dto.CardDto;
import com.example.payments.entity.Card;
import com.example.payments.entity.CardType;
import com.example.payments.service.CardService;
import com.example.payments.util.mapper.GenericMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cards")
@RequiredArgsConstructor
public class CardController {
    private final GenericMapper<Card, CardDto> mapper;
    private final CardService cardService;
    @GetMapping
    public List<CardDto> findAllByCurrentUser(@AuthenticationPrincipal PersonDetails personDetails) {
        return cardService.findAll(personDetails.getUser());
    }

    @GetMapping("/user/{id}")
    public List<CardDto> findAllByUserId(@PathVariable("id") Long id) {
        return cardService.findAll(id);
    }

    @GetMapping("/{id}")
    public CardDto findById(@PathVariable("id") Long id) {
        return cardService.findById(id);
    }

    @GetMapping("/phone_number")
    public CardDto findByPhoneNumber(@RequestBody @Valid CardDto cardDto) {
        return cardService.findByPhoneNumber(mapper.toEntity(cardDto));
    }

    @PostMapping("/create")
    public CardDto create(@AuthenticationPrincipal PersonDetails personDetails, @RequestParam("cardType") CardType cardType) {
        Card createdCard = cardService.createCard(personDetails.getUser(), cardType);
        return mapper.toDto(createdCard);
    }

    @DeleteMapping
    public CardDto delete(@AuthenticationPrincipal PersonDetails personDetails,
                          @RequestBody @Valid CardDto dto) {
        Card card = cardService.delete(personDetails.getUser(), mapper.toEntity(dto));
        return mapper.toDto(card);
    }
}
