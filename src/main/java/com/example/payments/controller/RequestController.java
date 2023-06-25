package com.example.payments.controller;

import com.example.payments.configuration.securityconfig.PersonDetails;
import com.example.payments.dto.CardDto;
import com.example.payments.entity.Card;
import com.example.payments.entity.Request;
import com.example.payments.service.RequestService;
import com.example.payments.util.mapper.GenericMapper;
import com.example.payments.view.RequestView;
import jakarta.validation.Valid;
import jakarta.validation.ValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/request")
@RequiredArgsConstructor
public class RequestController {
    private final RequestService requestService;
    private final GenericMapper<Card, CardDto> cardMapper;

    @GetMapping("/{id}")
    public RequestView findOne(@PathVariable("id") Long id) {
        return requestService.findById(id);
    }

    @GetMapping("/all")
    public List<RequestView> findAll() {
        return requestService.findAll();
    }

    @GetMapping("/user")
    public List<RequestView> findByUser(@AuthenticationPrincipal PersonDetails personDetails) {
        return requestService.findByUser(personDetails.getUser());
    }

    @PostMapping("/create")
    public Request create(@RequestBody @Valid CardDto cardDto,
                          BindingResult bindingResult,
                          @AuthenticationPrincipal PersonDetails personDetails) {
        if (bindingResult.hasErrors()) {
            throw new ValidationException();
        }

        return requestService.create(personDetails.getUser(), cardMapper.toEntity(cardDto));
    }

    @PatchMapping("/update")
    public RequestView update(@AuthenticationPrincipal PersonDetails personDetails) {
        return null;
    }

    @DeleteMapping("/{id}")
    public RequestView delete(@PathVariable("id") Long id) {
        return null;
    }
}
