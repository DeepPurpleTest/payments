package com.example.payments.controller;

import com.example.payments.configuration.securityconfig.PersonDetails;
import com.example.payments.dto.AuthCredentialsDto;
import com.example.payments.entity.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import jakarta.validation.ValidationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class UserAuthController {
    @PostMapping("/login")
    public User authenticate(@Valid @RequestBody AuthCredentialsDto credentialsDto, BindingResult bindingResult,
                             HttpServletRequest request) {
        if(bindingResult.hasErrors()) {
            throw new ValidationException("Invalid login or password");
        }

        String username = credentialsDto.getUsername();
        String password = credentialsDto.getPassword();

//        var encoder = new BCryptPasswordEncoder();
//        var bCryptPass = encoder.encode(password);
//        System.out.println("{bcrypt}" + bCryptPass);

        try {
            request.login(username, password);
        } catch (ServletException e) {
            throw new RuntimeException(e);
        }

        Authentication auth = (Authentication) request.getUserPrincipal();
        PersonDetails person = (PersonDetails) auth.getPrincipal();
        User user = person.getUser();
        System.out.println("User logged in: " + user.getPhoneNumber());
        return User.builder().name(user.getName()).password(user.getPassword()).build();
    }

    @GetMapping("/account")
    public User current(@AuthenticationPrincipal PersonDetails personDetails) {
        User user = personDetails.getUser();
        return User.builder().id(user.getId()).name(user.getName()).build();
    }
}
