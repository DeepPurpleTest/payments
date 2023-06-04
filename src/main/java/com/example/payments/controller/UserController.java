package com.example.payments.controller;

import com.example.payments.dto.UserDto;
import com.example.payments.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor // RequiredArgsConstructor for args we need
public class UserController {
    private final ModelMapper modelMapper;
    private final UserService userService; // final only for @RequiredArgsConstructor
    @GetMapping("/{id}")
    public UserDto find(@PathVariable("id") Long id) {
        return userService.findById(id);
    }

    @GetMapping()
    public UserDto findByPhoneNumber(@RequestBody @Valid UserDto userDto,
                                     BindingResult bindingResult) {
        if(bindingResult.hasErrors()){
            return null;
        }

        return userService.findByPhoneNumber(userDto.getPhoneNumber());
    }

    @GetMapping("/all")
    public List<UserDto> findAll() {
        return userService.findAll();
    }
}
