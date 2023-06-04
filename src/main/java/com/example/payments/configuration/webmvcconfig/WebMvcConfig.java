package com.example.payments.configuration.webmvcconfig;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@EnableWebMvc
public class WebMvcConfig {
    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
