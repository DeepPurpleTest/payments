package com.example.payments.controller.client;

import com.example.payments.dto.UserDto;
import com.example.payments.entity.User;
import com.example.payments.service.UserService;
import com.example.payments.util.exception.EntityValidationException;
import com.example.payments.util.mapper.UserDtoMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/client/user")
@RequiredArgsConstructor
public class ClientUserController {
    private final UserService userService;
    private final UserDtoMapper mapper;

    @PatchMapping("/update")
    public UserDto update(@RequestBody @Valid UserDto userDto,
                          BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new EntityValidationException("Incorrect data for update", bindingResult);
        }
        User updatedUser = userService.update(mapper.toEntity(userDto));
        return mapper.toDto(updatedUser);
    }
}
