package com.example.payments.controller;

import com.example.payments.configuration.securityconfig.PersonDetails;
import com.example.payments.dto.AuthCredentialsDto;
import com.example.payments.dto.RegistrationDto;
import com.example.payments.dto.UserDto;
import com.example.payments.entity.User;
import com.example.payments.service.UserService;
import com.example.payments.util.exception.EntityValidationException;
import com.example.payments.util.mapper.GenericMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
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

    @PostMapping("/_login")
    @ResponseStatus(HttpStatus.OK)
    public UserDto authenticate(@RequestBody @Valid AuthCredentialsDto credentialsDto, BindingResult bindingResult,
                                HttpServletRequest request) throws ServletException {
        if (bindingResult.hasErrors()) {
            throw new EntityValidationException("Invalid data format", bindingResult);
        }

        request.login(credentialsDto.getPhoneNumber(), credentialsDto.getPassword());

        Authentication auth = (Authentication) request.getUserPrincipal();
        PersonDetails user = (PersonDetails) auth.getPrincipal();
        User authUser = user.getUser();
        return userMapper.toDto(authUser);
    }

    @PostMapping("/logout")
    @ResponseStatus(HttpStatus.OK)
    public void logout(HttpServletRequest request) throws ServletException {
        request.logout();
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public RegistrationDto register(@RequestBody @Valid RegistrationDto registrationDto,
                                    BindingResult bindingResult,
                                    HttpServletRequest request) {
        if (bindingResult.hasErrors()) {
            throw new EntityValidationException("Incorrect data for registration", bindingResult);
        }
        User userToCreate = registrationMapper.toEntity(registrationDto);
        User createdUser = userService.create(userToCreate);
//        request.login(createdUser.getPhoneNumber(), createdUser.getPassword());
        return registrationMapper.toDto(createdUser);
    }

    @GetMapping("/account")
    public UserDto current(@AuthenticationPrincipal PersonDetails personDetails) {
        User user = personDetails.getUser();
        return userMapper.toDto(user);
    }
}
