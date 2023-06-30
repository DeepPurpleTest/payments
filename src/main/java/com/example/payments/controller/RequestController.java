package com.example.payments.controller;

import com.example.payments.configuration.securityconfig.PersonDetails;
import com.example.payments.dto.CardDto;
import com.example.payments.dto.RequestDto;
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
    private final GenericMapper<Request, RequestDto> requestMapper;

    // admin/client
    @GetMapping("/{id}")
    public RequestView findOne(@PathVariable("id") Long id) {
        return requestService.findById(id);
    }

    // admin
    @GetMapping("/all")
    public List<RequestView> findAll() {
        return requestService.findAll();
    }

    // client
    @GetMapping("/user")
    public List<RequestView> findByUser(@AuthenticationPrincipal PersonDetails personDetails) {
        return requestService.findByUser(personDetails.getUser());
    }

    // admin
    @GetMapping("/phone_number")// todo validation for phone number
    public List<RequestView> findByUserNumber(@RequestParam(value = "param", required = true) String phoneNUmber) {
        return requestService.findByPhoneNumber(phoneNUmber);
    }

    // client
    @PostMapping("/create")
    public Request create(@RequestBody @Valid CardDto cardDto,
                          BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new ValidationException();
        }

        return requestService.create(cardMapper.toEntity(cardDto));
    }

    // admin
    @PatchMapping("/update")
    public RequestDto update(@RequestBody @Valid CardDto cardDto,
                             BindingResult bindingResult,
                             @AuthenticationPrincipal PersonDetails personDetails) {
        if(bindingResult.hasErrors()) {
            throw new ValidationException();
        }

        Request request = requestService.update(personDetails.getUser(), cardMapper.toEntity(cardDto));
        return requestMapper.toDto(request);
    }

    // admin
    @DeleteMapping("/{id}")
    public RequestDto delete(@PathVariable("id") Long id) {
        return requestMapper.toDto(requestService.delete(id));
    }
}
