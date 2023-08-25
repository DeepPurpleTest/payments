package com.example.payments.controller.admin;

import com.example.payments.configuration.securityconfig.PersonDetails;
import com.example.payments.dto.CardDto;
import com.example.payments.dto.RequestDto;
import com.example.payments.dto.view.RequestView;
import com.example.payments.entity.Request;
import com.example.payments.service.RequestService;
import com.example.payments.util.exception.EntityValidationException;
import com.example.payments.util.mapper.CardDtoMapper;
import com.example.payments.util.mapper.RequestDtoMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/request")
@RequiredArgsConstructor
public class AdminRequestController {
    private final RequestService requestService;
    private final CardDtoMapper cardMapper;
    private final RequestDtoMapper requestMapper;

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

    // admin
    @GetMapping("/phone_number")// todo validation for phone number
    public List<RequestView> findByUserNumber(@RequestParam(value = "param") String phoneNUmber) {
        return requestService.findByPhoneNumber(phoneNUmber);
    }

    // admin
    @PatchMapping("/update")
    public RequestDto update(@RequestBody @Valid CardDto cardDto,
                             BindingResult bindingResult,
                             @AuthenticationPrincipal PersonDetails personDetails) {
        if(bindingResult.hasErrors()) {
            throw new EntityValidationException("Incorrect data for update", bindingResult);
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
