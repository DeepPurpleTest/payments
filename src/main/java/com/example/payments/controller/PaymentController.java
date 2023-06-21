package com.example.payments.controller;

import com.example.payments.dto.CardDto;
import com.example.payments.dto.InPaymentDto;
import com.example.payments.dto.OutPaymentDto;
import com.example.payments.entity.Card;
import com.example.payments.entity.Payment;
import com.example.payments.service.PaymentService;
import com.example.payments.util.mapper.GenericMapper;
import jakarta.validation.Valid;
import jakarta.validation.ValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/payments")
@RequiredArgsConstructor
public class PaymentController {
    private final PaymentService paymentService;
    private final GenericMapper<Payment, InPaymentDto> inPaymentMapper;
    private final GenericMapper<Payment, OutPaymentDto> outPaymentMapper;
    private final GenericMapper<Card, CardDto> cardMapper;

    @PostMapping("/_findAll")
    public List<OutPaymentDto> findAllByCardNumber(@RequestBody @Valid CardDto dto, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            throw new ValidationException("Card validation error"); //todo how to do better?
        }

        return paymentService.findByCardNumber(cardMapper.toEntity(dto));
    }
    @PostMapping("/create")
    public OutPaymentDto createTransaction(@RequestBody @Valid InPaymentDto dto, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            throw new ValidationException("Cannot create transaction");
        }
        Payment paymentToCreate = inPaymentMapper.toEntity(dto);
        return outPaymentMapper.toDto(paymentService.create(paymentToCreate));
    }
}
