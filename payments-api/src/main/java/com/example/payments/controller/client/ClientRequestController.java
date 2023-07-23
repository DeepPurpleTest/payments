package com.example.payments.controller.client;

import com.example.payments.configuration.securityconfig.PersonDetails;
import com.example.payments.dto.CardDto;
import com.example.payments.entity.Card;
import com.example.payments.entity.Request;
import com.example.payments.service.RequestService;
import com.example.payments.util.exception.EntityValidationException;
import com.example.payments.util.mapper.GenericMapper;
import com.example.payments.dto.view.RequestView;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/client/request")
@RequiredArgsConstructor
public class ClientRequestController {
    private final RequestService requestService;
    private final GenericMapper<Card, CardDto> cardMapper;

    // admin/client
    @GetMapping("/{id}")
    public RequestView findOne(@PathVariable("id") Long id) {
        return requestService.findById(id);
    }

    // client
    @GetMapping("/user")
    public List<RequestView> findByUser(@AuthenticationPrincipal PersonDetails personDetails) {
        return requestService.findByUser(personDetails.getUser());
    }

    // client
    @PostMapping("/create")
    public Request create(@RequestBody @Valid CardDto cardDto,
                          BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new EntityValidationException("Invalid data", bindingResult);
        }

        return requestService.create(cardMapper.toEntity(cardDto));
    }
}
