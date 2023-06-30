package com.example.payments.controller;

import com.example.payments.configuration.securityconfig.PersonDetails;
import com.example.payments.dto.CardDto;
import com.example.payments.dto.InPaymentDto;
import com.example.payments.dto.OutSenderPaymentDto;
import com.example.payments.entity.Card;
import com.example.payments.entity.Payment;
import com.example.payments.service.PaymentService;
import com.example.payments.util.mapper.GenericMapper;
import com.example.payments.view.identifiable.AbstractOutPaymentIdentifiable;
import jakarta.validation.Valid;
import jakarta.validation.ValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/payment")
@RequiredArgsConstructor
public class PaymentController {
    private final PaymentService paymentService;
    private final GenericMapper<Payment, InPaymentDto> inPaymentMapper;
    private final GenericMapper<Payment, OutSenderPaymentDto> outPaymentMapper;
    private final GenericMapper<Card, CardDto> cardMapper;

    @GetMapping
    public List<AbstractOutPaymentIdentifiable> findAll(@AuthenticationPrincipal PersonDetails personDetails) {
        return paymentService.findByCurrentUser(personDetails.getUser());
    }

    @PostMapping("/_findAll")
    public List<AbstractOutPaymentIdentifiable> findAllByCardNumber(@RequestBody @Valid CardDto dto,
                                                                    BindingResult bindingResult,
                                                                    @AuthenticationPrincipal PersonDetails personDetails) {
        if(bindingResult.hasErrors()) {
            throw new ValidationException("Card validation error"); //todo how to do better?
        }

        return paymentService.findByCardNumber(cardMapper.toEntity(dto), personDetails.getUser());
    }
    @PostMapping("/create")
    public OutSenderPaymentDto createTransaction(@RequestBody @Valid InPaymentDto dto,
                                                 BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            throw new ValidationException("Cannot create transaction");
        }
        Payment paymentToCreate = inPaymentMapper.toEntity(dto);
        return outPaymentMapper.toDto(paymentService.create(paymentToCreate));
    }
}
