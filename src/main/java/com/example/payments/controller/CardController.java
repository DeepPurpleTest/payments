package com.example.payments.controller;

import com.example.payments.configuration.securityconfig.PersonDetails;
import com.example.payments.dto.CardDto;
import com.example.payments.entity.Card;
import com.example.payments.entity.CardType;
import com.example.payments.service.CardService;
import com.example.payments.util.mapper.GenericMapper;
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
        System.out.println(personDetails.getUser().getId());
        System.out.println(personDetails.getUser().getPhoneNumber());
        System.out.println(personDetails.getUser().getName());
        return cardService.findAll(personDetails.getUser());
    }

    @GetMapping("/{id}")
    public CardDto findOne(@PathVariable("id") Long id) {
        return cardService.find(id);
    }

    @PostMapping("/create")
    public CardDto create(@AuthenticationPrincipal PersonDetails personDetails, @RequestParam("cardType") CardType cardType) {
        Card createdCard = cardService.createCard(personDetails.getUser(), cardType);
        return mapper.toDto(createdCard);
    }

    @DeleteMapping("/{id}")
    public CardDto delete(@AuthenticationPrincipal PersonDetails personDetails,
                          @PathVariable("id") Long id) {
        Card card = cardService.delete(personDetails.getUser(), id);
        return mapper.toDto(card);
    }
}
