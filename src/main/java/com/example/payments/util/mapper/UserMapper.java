package com.example.payments.util.mapper;

import com.example.payments.dto.UserDto;
import com.example.payments.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper extends GenericMapper<User, UserDto> {
}
