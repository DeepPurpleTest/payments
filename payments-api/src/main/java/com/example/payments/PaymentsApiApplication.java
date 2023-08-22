package com.example.payments;

import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.itextpdf.html2pdf.ConverterProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication(scanBasePackages = {"com.example.payments", "com.example.monitoringservice"})
public class PaymentsApiApplication {
    public static void main(String[] args) {
        SpringApplication.run(PaymentsApiApplication.class, args);
    }

    @Bean
    public PhoneNumberUtil phoneNumberUtil() {
        return PhoneNumberUtil.getInstance();
    }

    @Bean
    public ConverterProperties converterProperties() {
        return new ConverterProperties();
    }
}
