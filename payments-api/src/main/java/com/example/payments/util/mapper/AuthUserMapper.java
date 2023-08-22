package com.example.payments.util.mapper;

import com.example.payments.dto.AuthCredentialsDto;
import com.example.payments.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AuthUserMapper extends GenericMapper<User, AuthCredentialsDto> {
}
