package com.example.payments.util.mapper;

import com.example.payments.dto.RegistrationDto;
import com.example.payments.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RegistrationDtoMapper extends GenericMapper<User, RegistrationDto>{
}
