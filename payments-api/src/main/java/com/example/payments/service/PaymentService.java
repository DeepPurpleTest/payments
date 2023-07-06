package com.example.payments.service;

import com.example.payments.dto.OutReceiverPaymentDto;
import com.example.payments.dto.OutSenderPaymentDto;
import com.example.payments.entity.*;
import com.example.payments.repository.CardRepository;
import com.example.payments.repository.PaymentRepository;
import com.example.payments.util.exception.EntityNotFoundException;
import com.example.payments.util.exception.TransactionIsNotPossibleException;
import com.example.payments.util.mapper.ViewToDtoMapper;
import com.example.payments.view.OutSenderReceiverPaymentView;
import com.example.payments.view.identifiable.AbstractOutPaymentIdentifiable;
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
    private final ViewToDtoMapper<OutSenderReceiverPaymentView, OutReceiverPaymentDto> viewReceiverMapper;
    private final ViewToDtoMapper<OutSenderReceiverPaymentView, OutSenderPaymentDto> viewSenderMapper;

    @Transactional(readOnly = true)
    public List<AbstractOutPaymentIdentifiable> findByCurrentUser(User user) {
        List<OutSenderReceiverPaymentView> payments = paymentRepository
                .findAllByUserId(user.getId(), OutSenderReceiverPaymentView.class);

        return outPaymentViewConvertor(payments, user);
    }

    private List<AbstractOutPaymentIdentifiable> outPaymentViewConvertor(List<? extends AbstractOutPaymentIdentifiable> payments, User user) {
        return payments.stream().map(payment -> {
            OutSenderReceiverPaymentView view = (OutSenderReceiverPaymentView) payment;
            String senderNumber = view.getSender().getUser().getPhoneNumber();
            if (senderNumber.equals(user.getPhoneNumber())) {
                return viewSenderMapper.toDto(view);
            }
            return viewReceiverMapper.toDto(view);
        }).toList();
    }

    @Transactional(readOnly = true)
    public List<AbstractOutPaymentIdentifiable> findByCardNumber(Card card, User user) {
        Optional<Card> byId = cardRepository.findByCardNumber(card.getCardNumber(), Card.class);
        if(byId.isEmpty()) {
            throw new EntityNotFoundException(String.format("Card with number %s is not found", card.getCardNumber()));
        }
        List<OutSenderReceiverPaymentView> payments = paymentRepository
                .findBySenderOrReceiver(card, card, OutSenderReceiverPaymentView.class);
        return outPaymentViewConvertor(payments, user);
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

        paymentMailService.sendReceiptToSender(createdPayment);
        paymentMailService.sendReceiptToReceiver(createdPayment);

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
