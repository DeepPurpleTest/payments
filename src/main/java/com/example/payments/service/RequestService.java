package com.example.payments.service;

import com.example.payments.entity.*;
import com.example.payments.repository.RequestRepository;
import com.example.payments.util.exception.EntityNotFoundException;
import com.example.payments.view.RequestView;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RequestService {
    private final RequestRepository requestRepository;
    private final CardService cardService;
    @Transactional(readOnly = true)
    public RequestView findById(Long id) {
        Optional<RequestView> byId = requestRepository.findById(id, RequestView.class);
        if(byId.isEmpty()) {
            throw new EntityNotFoundException(String.format("Request with id %d is not found", id));
        }
        return byId.get();
    }
    @Transactional(readOnly = true)
    public List<RequestView> findAll() {
        return requestRepository.findAllByStatus(RequestStatus.UNTREATED, RequestView.class);
    }
    @Transactional(readOnly = true)
    public List<RequestView> findByUser(User user) {
        return requestRepository.findAllByUser(user, RequestView.class);
    }

    @Transactional(readOnly = true)
    public List<RequestView> findByPhoneNumber(String phoneNumber) {
        return requestRepository.findByUserPhoneNumber(phoneNumber, RequestView.class);
    }

    @Transactional
    public Request create(Card cardForRequest) {
        Optional<Card> byNumber = cardService.findByCardNumber(cardForRequest, Card.class);
        if(byNumber.isEmpty()) {
            throw new EntityNotFoundException(String.format("Card with number %s is not found", cardForRequest.getCardNumber()));
        }
        Card card = byNumber.get();
        if (card.getStatus().equals(Status.ACTIVE)) {
            throw new IllegalStateException("Card is already active");
        }
        // todo maybe rework unnecessary checks?
        Optional<Request> request = requestRepository.findByCardAndStatus(card, Request.class);
        if(request.isPresent()) {
            throw new IllegalStateException("Request already exist"); // todo entity already exist exception
        }

        Request requestToCreate = Request.builder()
                .user(card.getUser())
                .card(card)
                .status(RequestStatus.UNTREATED)
                .build();

        return requestRepository.save(requestToCreate);
    }

    @Transactional
    public Request update(User admin, Card cardToUnlock) {
        Optional<Card> byNumber = cardService.findByCardNumber(cardToUnlock, Card.class);
        if(byNumber.isEmpty()) {
            throw new EntityNotFoundException(String.format("Card with number %s is not found", cardToUnlock.getCardNumber()));
        }

        Card card = byNumber.get();
        if(card.getStatus().equals(Status.ACTIVE)) {
            throw new IllegalStateException("Card is already active");
        }

        Optional<Request> byCard = requestRepository.findByCardAndStatus(card, Request.class);
        if(byCard.isEmpty()) {
            throw new EntityNotFoundException("Request is not found");
        }

        card.setStatus(Status.ACTIVE);
        Request request = byCard.get();
        request.setAdmin(admin);
        request.setStatus(RequestStatus.PROCESSED);
        return request;
    }

    @Transactional
    public Request delete(Long id) {
        Optional<Request> byId = requestRepository.findById(id, Request.class);
        if(byId.isEmpty()) {
            throw new EntityNotFoundException(String.format("Request with id %d is not found", id));
        }
        requestRepository.delete(byId.get());
        return byId.get();
    }
}
