package com.example.payments.util;

import lombok.experimental.UtilityClass;

@UtilityClass
public class ApplicationConstants {
    @UtilityClass
    public static final class Validation {
        public static final int MIN_NAME_LENGTH = 5;
        public static final int MAX_NAME_LENGTH = 25;
        public static final int MIN_SURNAME_LENGTH = 5;
        public static final int MAX_SURNAME_LENGTH = 25;
        public static final int MIN_MIDDLE_NAME_LENGTH = 5;
        public static final int MAX_MIDDLE_NAME_LENGTH = 25;
        public static final int MIN_PHONE_NUMBER_LENGTH = 13;
        public static final int MAX_PHONE_NUMBER_LENGTH = 13;
        public static final int MIN_PASSWORD_LENGTH = 8;
        public static final int MAX_PASSWORD_LENGTH = 32;

        public static final String NAME_MESSAGE = "Invalid name format";
        public static final String SURNAME_MESSAGE = "Invalid surname format";
        public static final String MIDDLE_NAME_MESSAGE = "Invalid middle name format";
        public static final String PHONE_NUMBER_MESSAGE = "Invalid phone number format";
        public static final String PASSWORD_MESSAGE = "Password length must be in " + MIN_PASSWORD_LENGTH +
                " to " + MAX_PASSWORD_LENGTH + "symbols";
    }
}
