package com.example.payments.controller;

import com.example.payments.dto.RegistrationDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test-env")
@Transactional
class UserAuthControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testRegisterUser() throws Exception {
        RegistrationDto dto = RegistrationDto.builder()
                .phoneNumber("+380977777777")
                .password("password")
                .name("Vodem")
                .surname("Storozhuk")
                .build();

        String userToCreate = objectMapper.writeValueAsString(dto);
        mockMvc.perform(post("/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userToCreate))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("name").exists())
                .andExpect(jsonPath("surname").exists())
                .andExpect(jsonPath("phoneNumber").exists());
    }
}
