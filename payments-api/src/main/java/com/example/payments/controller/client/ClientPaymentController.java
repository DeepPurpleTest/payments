package com.example.payments.controller.client;

import com.example.payments.configuration.securityconfig.PersonDetails;
import com.example.payments.dto.CardDto;
import com.example.payments.dto.InPaymentDto;
import com.example.payments.dto.OutPaymentDto;
import com.example.payments.entity.Payment;
import com.example.payments.service.PaymentService;
import com.example.payments.util.exception.EntityValidationException;
import com.example.payments.util.mapper.CardDtoMapper;
import com.example.payments.util.mapper.InPaymentDtoMapper;
import com.example.payments.util.mapper.OutPaymentDtoMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/client/payment")
@RequiredArgsConstructor
@Slf4j
public class ClientPaymentController {
    private final PaymentService paymentService;
    private final InPaymentDtoMapper inPaymentMapper;
    private final CardDtoMapper cardMapper;
    private final OutPaymentDtoMapper outPaymentMapper;

    @GetMapping("/all")
    public List<OutPaymentDto> findAll(@AuthenticationPrincipal PersonDetails personDetails) {
        List<Payment> payments = paymentService.findByCurrentUser(personDetails.getUser());
        return payments.stream()
                .map(payment -> outPaymentMapper.toDto(payment, personDetails.getUser()))
                .toList();
    }

    @GetMapping("/find/{id}")
    public OutPaymentDto findOne(@AuthenticationPrincipal PersonDetails personDetails,
                                 @PathVariable("id") Long id) {
        Payment payment = paymentService.findById(id);
        return outPaymentMapper.toDto(payment, personDetails.getUser());
    }

    @PostMapping("/_findAll")
    public List<OutPaymentDto> findAllByCardNumber(@RequestBody @Valid CardDto dto,
                                                   BindingResult bindingResult,
                                                   @AuthenticationPrincipal PersonDetails personDetails) {
        if (bindingResult.hasErrors()) {
            throw new EntityValidationException("Card validation error", bindingResult);
        }

        List<Payment> payments = paymentService.findByCardNumber(cardMapper.toEntity(dto));
        return payments.stream()
                .map(payment -> outPaymentMapper.toDto(payment, personDetails.getUser()))
                .toList();
    }

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public OutPaymentDto createTransaction(@RequestBody @Valid InPaymentDto dto,
                                           BindingResult bindingResult,
                                           @AuthenticationPrincipal PersonDetails personDetails) {
        if (bindingResult.hasErrors()) {
            throw new EntityValidationException("Cannot create transaction", bindingResult);
        }

        Payment paymentToCreate = inPaymentMapper.toEntity(dto);
        Payment createdPayment = paymentService.createTransaction(paymentToCreate, personDetails.getUser());

        return outPaymentMapper.toDto(createdPayment, personDetails.getUser());
    }
}
