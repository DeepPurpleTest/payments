package com.example.payments;

import com.google.i18n.phonenumbers.PhoneNumberUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class PaymentsApiApplication {
    public static void main(String[] args) {
        SpringApplication.run(PaymentsApiApplication.class, args);
    }

    @Bean
    public PhoneNumberUtil phoneNumberUtil() {
        return PhoneNumberUtil.getInstance();
    }
}
