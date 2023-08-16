package com.example.payments.service;

import com.example.payments.entity.*;
import com.example.payments.repository.CardRepository;
import com.example.payments.repository.PaymentRepository;
import com.example.payments.util.exception.EntityNotFoundException;
import com.example.payments.util.exception.TransactionException;
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
    private final PaymentMailService paymentMailService;
    private final PaymentRepository paymentRepository;
    private final CardRepository cardRepository;

    @Transactional
    public Payment findById(Long id) {
        Optional<Payment> byId = paymentRepository.findById(id);
        return byId.orElseThrow(() -> new EntityNotFoundException("Payment is not found"));
    }

    @Transactional(readOnly = true)
    public List<Payment> findByCurrentUser(User user) {
        return paymentRepository.findAllByUserId(user.getId(), Payment.class);
    }

    @Transactional(readOnly = true)
    public List<Payment> findByCardNumber(Card card) {
        Optional<Card> byId = cardRepository.findByCardNumber(card.getCardNumber(), Card.class);
        if (byId.isEmpty()) {
            throw new EntityNotFoundException(String.format("Card with number %s is not found", card.getCardNumber()));
        }
        return paymentRepository.findBySenderOrReceiver(card, card, Payment.class);
    }

    @Transactional
    public Payment create(Payment payment, User currentUser) {
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

        validateTransaction(cardSender, cardReceiver, payment.getAmount(), currentUser);

        payment.setSender(cardSender);
        payment.setReceiver(cardReceiver);
        payment.setStatus(PaymentStatus.PROCESSED);
        payment.setDate(LocalDateTime.now());

        Payment createdPayment = paymentRepository.save(payment);
        cardSender.setBalance(cardSender.getBalance().subtract(payment.getAmount()));
        cardReceiver.setBalance(cardReceiver.getBalance().add(payment.getAmount()));

        createdPayment.setSenderBalanceAfterPayment(cardSender.getBalance());
        createdPayment.setReceiverBalanceAfterPayment(cardReceiver.getBalance());
        createdPayment.setStatus(PaymentStatus.SENT);

        paymentMailService.sendReceipt(createdPayment);

        return createdPayment;
    }

    private void validateTransaction(Card cardSender, Card cardReceiver, BigDecimal amount, User userSender) {
        if (!cardSender.getUser().getId().equals(userSender.getId())) {
            throw new TransactionException(String.format("Card with number %s is not found", cardSender.getCardNumber()));
        }
        if (cardSender.getStatus().equals(Status.BLOCKED)) {
            throw new TransactionException(String.format("Card with number %s is blocked", cardSender.getCardNumber()));
        }
        if (cardReceiver.getStatus().equals(Status.BLOCKED)) {
            throw new TransactionException(String.format("Card with number %s is blocked", cardReceiver.getCardNumber()));
        }
        if ((cardSender.getBalance().compareTo(amount)) < 0) {
            throw new TransactionException("Sender has not enough money for transaction");
        }
    }
}
