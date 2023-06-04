package com.example.payments.controller;

import com.example.payments.configuration.securityconfig.PersonDetails;
import com.example.payments.dto.AuthCredentialsDto;
import com.example.payments.entity.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import jakarta.validation.ValidationException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class UserAuthController {
    @PostMapping("/login")
    public void authenticate(@Valid @RequestBody AuthCredentialsDto credentialsDto, BindingResult bindingResult,
                             HttpServletRequest request, HttpServletResponse response) {
        if(bindingResult.hasErrors()) {
            throw new ValidationException("Invalid login or password");
        }

        String username = credentialsDto.getUsername();
        String password = credentialsDto.getPassword();

        var encoder = new BCryptPasswordEncoder();
        var bCryptPass = encoder.encode(password);
        System.out.println("{bcrypt}" + bCryptPass);

        try {
            request.login(username, password);
        } catch (ServletException e) {
            throw new RuntimeException(e);
        }
        response.setStatus(HttpServletResponse.SC_OK);
    }

    @GetMapping("/account")
    public User current(@AuthenticationPrincipal PersonDetails personDetails) {
        User user = personDetails.getUser();
        return User.builder().id(user.getId()).name(user.getName()).build();
    }
}
