package com.example.payments.controller;

import com.example.payments.dto.UserDto;
import com.example.payments.entity.User;
import com.example.payments.service.UserService;
import com.example.payments.util.mapper.GenericMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor // RequiredArgsConstructor for args we need
public class UserController {
    private final GenericMapper<User, UserDto> mapper;
    private final UserService userService; // final only for @RequiredArgsConstructor

    @GetMapping("/{id}")
    public UserDto find(@PathVariable("id") Long id) {
        return userService.findById(id);
    }

    @GetMapping("/phone_number")
    public UserDto findByPhoneNumber(@RequestParam String phoneNumber) {
        System.out.println(phoneNumber);
        return userService.findByPhoneNumber(phoneNumber);
    }

    @GetMapping("/all")
    public List<UserDto> findAll() {
        return userService.findAll();
    }

    @PatchMapping("/update")
    public UserDto update(@RequestBody @Valid UserDto userDto,
                          BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return null;
        }
        User updatedUser = userService.update(mapper.toEntity(userDto));
        return mapper.toDto(updatedUser);
    }

    @DeleteMapping("{id}")
    public UserDto delete(@PathVariable("id") Long id) {
        User deletedUser = userService.delete(id);
        return mapper.toDto(deletedUser);
    }

}
