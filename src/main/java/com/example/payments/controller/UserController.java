package com.example.payments.controller;

import com.example.payments.entity.User;
import com.example.payments.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor // RequiredArgsConstructor for args we need
public class UserController {
    private final UserService userService; // final only for @RequiredArgsConstructor
    @GetMapping("/{id}")
    public User find(@PathVariable("id") Long id) {
        return userService.getUserById(id);
    }
}
