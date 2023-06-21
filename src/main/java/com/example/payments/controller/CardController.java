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
    public List<CardDto> findAll(@AuthenticationPrincipal PersonDetails personDetails) {
        return cardService.findAll(personDetails.getUser());
    }

    @GetMapping("/find")
    public CardDto findOne(@RequestBody @Valid CardDto dto) {
        return cardService.find(mapper.toEntity(dto));
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
