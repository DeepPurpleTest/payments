package com.example.payments;

import com.example.payments.configuration.securityconfig.PersonDetails;
import com.example.payments.dto.UserDto;
import com.example.payments.entity.Role;
import com.example.payments.entity.Status;
import com.example.payments.entity.User;

public final class TestConstants {
    public static final User USER_CREDENTIALS = User.builder()
            .phoneNumber("+380960150636")
            .password("password")
            .build();
    public static final PersonDetails PERSON_DETAILS = new PersonDetails(USER_CREDENTIALS);
    public static final User USER_ENTITY = User.builder()
            .id(1L)
            .phoneNumber("+380960150636")
            .password("password")
            .name("Test")
            .surname("Testich")
            .middleName("Testovich")
            .role(Role.CLIENT)
            .status(Status.ACTIVE)
            .build();
    public static final User USER_ENTITY_WITH_HASH_PASS = User.builder()
            .id(1L)
            .phoneNumber("+380960150636")
            .password("$2a$10$ylN/NYL8HtWkOBIjOzHIP.vaUy5cHe7EK.0xNssSM5DKc/dZvbQm6")
            .name("Test")
            .surname("Testich")
            .middleName("Testovich")
            .role(Role.CLIENT)
            .status(Status.ACTIVE)
            .build();

    public static final User ADMIN_USER = User.builder()
            .id(2L)
            .phoneNumber("+380960150635")
            .password("password")
            .name("Moksem")
            .surname("Viskovatov")
            .role(Role.ADMIN)
            .status(Status.ACTIVE)
            .build();
    public static final UserDto USER_DTO = UserDto.builder()
            .id(USER_ENTITY.getId())
            .phoneNumber(USER_ENTITY.getPhoneNumber())
            .name(USER_ENTITY.getName())
            .surname(USER_ENTITY.getSurname())
            .middleName(USER_ENTITY.getMiddleName())
            .build();
}
