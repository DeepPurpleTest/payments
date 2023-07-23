package com.example.payments.controller.client;

import com.example.payments.configuration.securityconfig.PersonDetails;
import com.example.payments.dto.CardDto;
import com.example.payments.dto.InPaymentDto;
import com.example.payments.dto.OutPaymentDto;
import com.example.payments.entity.Card;
import com.example.payments.entity.Payment;
import com.example.payments.service.PaymentService;
import com.example.payments.util.exception.EntityValidationException;
import com.example.payments.util.mapper.GenericMapper;
import com.example.payments.util.mapper.PaymentOutPaymentDtoMapper;
import jakarta.validation.Valid;
import jakarta.validation.ValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/client/payment")
@RequiredArgsConstructor
public class ClientPaymentController {
    private final PaymentService paymentService;
    private final GenericMapper<Payment, InPaymentDto> inPaymentMapper;
    private final GenericMapper<Card, CardDto> cardMapper;
    private final PaymentOutPaymentDtoMapper outPaymentMapper;

    @GetMapping("/all")
    public List<OutPaymentDto> findAll(@AuthenticationPrincipal PersonDetails personDetails) {
        List<Payment> payments = paymentService.findByCurrentUser(personDetails.getUser());
        return payments.stream()
                .map(payment -> outPaymentMapper.toDto(payment, personDetails.getUser()))
                .toList();
    }

    @PostMapping("/_find")
    public OutPaymentDto findOne(@RequestBody @Valid InPaymentDto dto,
                                 BindingResult bindingResult,
                                 @AuthenticationPrincipal PersonDetails personDetails) {
        if(bindingResult.hasErrors()) {
            throw new EntityValidationException("Invalid entity payment", bindingResult);
        }
        Payment payment = paymentService.findById(inPaymentMapper.toEntity(dto));
        return outPaymentMapper.toDto(payment, personDetails.getUser());
    }

    @PostMapping("/_findAll")
    public List<OutPaymentDto> findAllByCardNumber(@RequestBody @Valid CardDto dto,
                                              BindingResult bindingResult,
                                              @AuthenticationPrincipal PersonDetails personDetails) {
        if(bindingResult.hasErrors()) {
            throw new EntityValidationException("Card validation error", bindingResult);
        }

        List<Payment> payments = paymentService.findByCardNumber(cardMapper.toEntity(dto));
        return payments.stream()
                .map(payment -> outPaymentMapper.toDto(payment, personDetails.getUser()))
                .toList();
    }
    @PostMapping("/create")
    public OutPaymentDto createTransaction(@RequestBody @Valid InPaymentDto dto,
                                                 BindingResult bindingResult,
                                                 @AuthenticationPrincipal PersonDetails personDetails) {
        if(bindingResult.hasErrors()) {
            throw new ValidationException("Cannot create transaction");
        }
        Payment paymentToCreate = inPaymentMapper.toEntity(dto);
        Payment createdPayment = paymentService.create(paymentToCreate, personDetails.getUser());
        return outPaymentMapper.toDto(createdPayment, personDetails.getUser());
    }
}
