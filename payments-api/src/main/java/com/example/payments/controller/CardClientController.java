package com.example.payments.controller;

import com.example.payments.configuration.securityconfig.PersonDetails;
import com.example.payments.dto.CardDto;
import com.example.payments.entity.Card;
import com.example.payments.entity.CardType;
import com.example.payments.service.CardService;
import com.example.payments.util.mapper.GenericMapper;
import jakarta.validation.Valid;
import jakarta.validation.ValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/client/card")
@RequiredArgsConstructor
public class CardClientController {
    private final GenericMapper<Card, CardDto> mapper;
    private final CardService cardService;

    // for admin/user
    @GetMapping("/{id}")
    public CardDto findById(@PathVariable("id") Long id) {
        return cardService.findById(id);
    }

    // for user
    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public CardDto create(@AuthenticationPrincipal PersonDetails personDetails, @RequestParam("cardType") CardType cardType) {
        Card createdCard = cardService.createCard(personDetails.getUser(), cardType);
        return mapper.toDto(createdCard);
    }

    // for user/admin
    @PatchMapping("/block")
    public CardDto block(@RequestBody @Valid CardDto cardDto,
                         BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            throw new ValidationException();
        }
        Card card = cardService.blockCard(mapper.toEntity(cardDto));
        return mapper.toDto(card);
    }

    // for user
    @DeleteMapping
    public CardDto delete(@AuthenticationPrincipal PersonDetails personDetails,
                          @RequestBody @Valid CardDto dto) {
        Card card = cardService.delete(personDetails.getUser(), mapper.toEntity(dto));
        return mapper.toDto(card);
    }
}
