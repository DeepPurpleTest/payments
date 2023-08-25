package com.example.payments.controller.admin;

import com.example.payments.dto.CardDto;
import com.example.payments.entity.Card;
import com.example.payments.service.CardService;
import com.example.payments.util.exception.EntityValidationException;
import com.example.payments.util.mapper.CardDtoMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/card")
@RequiredArgsConstructor
public class AdminCardController {
    private final CardDtoMapper mapper;
    private final CardService cardService;

    // for admin
    @GetMapping("/user/{id}")
    public List<CardDto> findAllByUserId(@PathVariable("id") Long id) {
        return cardService.findAll(id);
    }

    // for admin/user
    @GetMapping("/{id}")
    public CardDto findById(@PathVariable("id") Long id) {
        return cardService.findById(id);
    }

    // for admin
    @GetMapping("/phone_number")
    public CardDto findByPhoneNumber(@RequestBody @Valid CardDto cardDto) {
        return cardService.findByCardNumber(mapper.toEntity(cardDto));
    }

    // for user/admin
    @PatchMapping("/block")
    public CardDto block(@RequestBody @Valid CardDto cardDto,
                         BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            throw new EntityValidationException("Incorrect card data", bindingResult);
        }
        Card card = cardService.blockCard(mapper.toEntity(cardDto));
        return mapper.toDto(card);
    }

    @PatchMapping("/unlock")
    public CardDto unlock(@RequestBody @Valid CardDto cardDto,
                          BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            throw new EntityValidationException("Incorrect card data", bindingResult);
        }
        Card card = cardService.unlockCard(mapper.toEntity(cardDto));
        return mapper.toDto(card);
    }
}
