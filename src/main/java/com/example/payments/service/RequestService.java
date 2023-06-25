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
        return requestRepository.findAllBy(RequestView.class);
    }
    @Transactional(readOnly = true)
    public List<RequestView> findByUser(User user) {
        return requestRepository.findAllByUser(user, RequestView.class);
    }

    @Transactional
    public Request create(User user, Card cardForRequest) {
        Optional<Card> byNumber = cardService.findByCardNumber(cardForRequest, Card.class);
        if(byNumber.isEmpty()) {
            System.out.println("FIRST NOT FOUND");
            throw new EntityNotFoundException(String.format("Card with number %s is not found", cardForRequest.getCardNumber()));
        }

        Card card = byNumber.get();
        // todo maybe rework unnecessary checks?
        System.out.println(card.getUser().getName());
        if(user.getRole().equals(Role.CLIENT) && !(user.equals(card.getUser()))) {
            System.out.println("SECOND NOT FOUND");
            throw new EntityNotFoundException(String.format("Card with number %s is not found", cardForRequest.getCardNumber()));
        }
        Optional<Request> request = requestRepository.findByCardAndStatus(card, Request.class);
        if(request.isPresent()) {
            System.out.println("REQUEST ALREADY EXIST");
            throw new IllegalStateException("Request already exist");
//            return null; // todo entity already exist exception
        }
        return null;
    }
}
