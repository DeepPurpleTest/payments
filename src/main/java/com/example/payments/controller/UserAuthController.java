package com.example.payments.controller;

import com.example.payments.configuration.securityconfig.PersonDetails;
import com.example.payments.dto.AuthCredentialsDto;
import com.example.payments.dto.RegistrationDto;
import com.example.payments.dto.UserDto;
import com.example.payments.entity.User;
import com.example.payments.service.UserService;
import com.example.payments.util.mapper.GenericMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import jakarta.validation.ValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class UserAuthController {
    private final GenericMapper<User, RegistrationDto> registrationMapper;
    private final GenericMapper<User, UserDto> userMapper;
    private final UserService userService;

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public void authenticate(@RequestBody @Valid AuthCredentialsDto credentialsDto, BindingResult bindingResult,
                             HttpServletRequest request) {
        if(bindingResult.hasErrors()) {
            throw new ValidationException("Invalid login or password");
        }

        String phoneNumber = credentialsDto.getPhoneNumber();
        String password = credentialsDto.getPassword();

        try {
            request.login(phoneNumber, password);
        } catch (ServletException e) {
            throw new RuntimeException(e); // todo exception
        }
    }

    @PostMapping("register")
    @ResponseStatus(HttpStatus.CREATED)
    public RegistrationDto register(@RequestBody @Valid RegistrationDto registrationDto,
                                       BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            return null; // todo exception
        }
        User userToCreate = registrationMapper.toEntity(registrationDto);
        User createdUser = userService.create(userToCreate);
        return registrationMapper.toDto(createdUser);
    }

    @GetMapping("/account")
    public UserDto current(@AuthenticationPrincipal PersonDetails personDetails) {
        User user = personDetails.getUser();
        return userMapper.toDto(user);
    }
}
