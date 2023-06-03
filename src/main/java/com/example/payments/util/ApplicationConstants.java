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

        public static final String NAME_MESSAGE = "Invalid name format";
        public static final String SURNAME_MESSAGE = "Invalid name format";
        public static final String MIDDLE_NAME_MESSAGE = "Invalid name format";
    }
}
