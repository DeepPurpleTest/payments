package com.example.payments.util.validation;

import com.example.payments.util.validation.annotation.ContactNumberConstraint;
import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Configurable;

@Configurable
@RequiredArgsConstructor
public class ContactNumberValidator implements ConstraintValidator<ContactNumberConstraint, String> {
    private final PhoneNumberUtil phoneNumberUtil;
    @Override
    public void initialize(ContactNumberConstraint constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String contactField, ConstraintValidatorContext constraintValidatorContext) {
        try {
            Phonenumber.PhoneNumber phoneNumber = phoneNumberUtil.parse(contactField, null);
            return phoneNumberUtil.isValidNumber(phoneNumber);
        } catch (NumberParseException e) {
            return false;
        }
    }
}
