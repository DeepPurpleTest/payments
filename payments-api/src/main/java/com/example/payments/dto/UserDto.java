package com.example.payments.dto;

import com.example.payments.util.ApplicationConstants.Validation;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import lombok.extern.jackson.Jacksonized;
import org.hibernate.validator.constraints.Length;
import org.springframework.lang.Nullable;

@Getter
@Builder
@Jacksonized
public class UserDto {
    @Nullable
    private Long id;
    @NotEmpty
    @Length(min = Validation.MIN_NAME_LENGTH,
            max = Validation.MAX_NAME_LENGTH,
            message = Validation.NAME_MESSAGE)
    private String name;
    @NotEmpty
    @Length(min = Validation.MIN_SURNAME_LENGTH,
            max = Validation.MAX_SURNAME_LENGTH,
            message = Validation.SURNAME_MESSAGE)
    private String surname;
    @Nullable
    @Length(min = Validation.MIN_MIDDLE_NAME_LENGTH,
            max = Validation.MAX_MIDDLE_NAME_LENGTH,
            message = Validation.MIDDLE_NAME_MESSAGE)
    private String middleName;
    @NotEmpty
    @Length(min = Validation.MIN_PHONE_NUMBER_LENGTH,
            max = Validation.MAX_PHONE_NUMBER_LENGTH,
            message = Validation.PHONE_NUMBER_MESSAGE)
    private String phoneNumber;

    @Nullable
    @Email
    @Length() // todo complete dto
    private String email;
}
