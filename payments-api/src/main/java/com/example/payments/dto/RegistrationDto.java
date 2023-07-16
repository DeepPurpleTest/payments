package com.example.payments.dto;

import com.example.payments.util.ApplicationConstants;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;
import org.hibernate.validator.constraints.Length;
import org.springframework.lang.Nullable;

@Getter
@Builder
@Jacksonized
public class RegistrationDto {
    @Nullable
    @Length(min = ApplicationConstants.Validation.MIN_NAME_LENGTH,
            max = ApplicationConstants.Validation.MAX_NAME_LENGTH,
            message = ApplicationConstants.Validation.NAME_MESSAGE)
    private String name;
    @Nullable
    @Length(min = ApplicationConstants.Validation.MIN_SURNAME_LENGTH,
            max = ApplicationConstants.Validation.MAX_SURNAME_LENGTH,
            message = ApplicationConstants.Validation.SURNAME_MESSAGE)
    private String surname;
    @Nullable
    @Length(min = ApplicationConstants.Validation.MIN_MIDDLE_NAME_LENGTH,
            max = ApplicationConstants.Validation.MAX_MIDDLE_NAME_LENGTH,
            message = ApplicationConstants.Validation.MIDDLE_NAME_MESSAGE)
    private String middleName;
    @Nullable
    @Length() // todo complete dto
    private String phoneNumber;
    @Nullable
    @Length() // todo complete dto
    private String password;
    @Nullable
    @Length() // todo complete dto
    private String email;
}
