package com.example.payments.service;

import com.example.payments.dto.OutPaymentDto;
import com.example.payments.entity.Card;
import com.example.payments.entity.Payment;
import com.example.payments.entity.PaymentStatus;
import com.example.payments.entity.Status;
import com.example.payments.repository.CardRepository;
import com.example.payments.repository.PaymentRepository;
import com.example.payments.util.exception.EntityNotFoundException;
import com.example.payments.util.exception.TransactionIsNotPossibleException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PaymentService {
    private final PaymentRepository paymentRepository;
    private final CardRepository cardRepository;

    public List<OutPaymentDto> findByCardNumber(Card card) {
        Optional<Card> byId = cardRepository.findByCardNumber(card.getCardNumber(), Card.class);
        if(byId.isEmpty()) {
            throw new EntityNotFoundException(String.format("Card with number %s is not found", card.getCardNumber()));
        }

        return paymentRepository.findBySenderOrReceiver(card, card, OutPaymentDto.class);
    }

    @Transactional
    public Payment create(Payment payment) {
        Optional<Card> senderById = cardRepository.findByCardNumber(payment.getSender().getCardNumber(), Card.class);
        Optional<Card> receiverById = cardRepository.findByCardNumber(payment.getReceiver().getCardNumber(), Card.class);

        if (senderById.isEmpty()) {
            throw new EntityNotFoundException(String.format("Card with id %s is not found", payment.getSender().getCardNumber()));
        }
        if (receiverById.isEmpty()) {
            throw new EntityNotFoundException(String.format("Card with id %s is not found", payment.getReceiver().getCardNumber()));
        }

        Card cardSender = senderById.get();
        Card cardReceiver = receiverById.get();

        validateTransaction(cardSender, cardReceiver, payment.getAmount());

        payment.setSender(cardSender);
        payment.setReceiver(cardReceiver);
        payment.setStatus(PaymentStatus.PROCESSED);
        payment.setDate(LocalDateTime.now());

        Payment createdPayment = paymentRepository.save(payment);
        cardSender.setBalance(cardSender.getBalance().subtract(payment.getAmount()));
        cardReceiver.setBalance(cardReceiver.getBalance().add(payment.getAmount()));

        createdPayment.setStatus(PaymentStatus.SENT);

        return createdPayment;
    }

    private void validateTransaction(Card cardSender, Card cardReceiver, BigDecimal amount) {
        if (cardSender.getStatus().equals(Status.BLOCKED)) {
            throw new TransactionIsNotPossibleException(String.format("Card with number %s is blocked", cardSender.getCardNumber()));
        }
        if (cardReceiver.getStatus().equals(Status.BLOCKED)) {
            throw new TransactionIsNotPossibleException(String.format("Card with number %s is blocked", cardReceiver.getCardNumber()));
        }
        if((cardSender.getBalance().compareTo(amount)) < 0) {
            throw new TransactionIsNotPossibleException("Sender has not enough money for transaction");
        }
    }
}
