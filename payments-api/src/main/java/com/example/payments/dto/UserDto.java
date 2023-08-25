package com.example.payments.dto;

import com.example.payments.dto.identifiable.AbstractUserIdentifiable;
import com.example.payments.entity.Role;
import com.example.payments.util.ApplicationConstants;
import com.example.payments.util.validation.annotation.ContactNumberConstraint;
import jakarta.validation.constraints.Email;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
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
    @Nullable
    @Length(min = ApplicationConstants.Validation.MIN_NAME_LENGTH,
            max = ApplicationConstants.Validation.MAX_NAME_LENGTH,
            message = "{validation.name.error}")
    private String name;
    @Nullable
    @Length(min = ApplicationConstants.Validation.MIN_SURNAME_LENGTH,
            max = ApplicationConstants.Validation.MAX_SURNAME_LENGTH,
            message = "{validation.surname.error}")
    private String surname;
    @Nullable
    @Length(min = ApplicationConstants.Validation.MIN_MIDDLE_NAME_LENGTH,
            max = ApplicationConstants.Validation.MAX_MIDDLE_NAME_LENGTH,
            message = "{validation.middle_name.error}")
    private String middleName;
    @Nullable
    @ContactNumberConstraint(message = "{validation.phone_number.error}")
    private String phoneNumber;

    @Nullable
    @Email(message = "{validation.email.error}")
    private String email;

    @Nullable
    private Role role;
}
