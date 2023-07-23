package com.example.payments.dto;

import com.example.payments.dto.identifiable.AbstractUserIdentifiable;
import com.example.payments.util.ApplicationConstants.Validation;
import com.example.payments.util.validation.annotation.ContactNumberConstraint;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import lombok.extern.jackson.Jacksonized;
import org.hibernate.validator.constraints.Length;
import org.springframework.lang.Nullable;

@Getter
@Setter
@Builder
@Jacksonized
public class UserDto implements AbstractUserIdentifiable {
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
    @ContactNumberConstraint
    private String phoneNumber;

    @Nullable
    @Email
    private String email;
}
