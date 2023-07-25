package com.example.payments.controller.client;

import com.example.payments.dto.CardDto;
import com.example.payments.dto.InPaymentDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static com.example.payments.TestConstants.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test-env")
@Transactional
class ClientPaymentControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private RabbitTemplate rabbitTemplate;

    @Test
    @WithUserDetails("+380960150636")
    void testGetAllPaymentsByUser() throws Exception {
        mockMvc.perform(get("/client/payment/all"))
                .andExpect(status().isOk());
    }

    @Test
    @WithUserDetails("+380960150636")
    void testGetPaymentById() throws Exception {
        String inPaymentDto = objectMapper.writeValueAsString(IN_PAYMENT_DTO);
        mockMvc.perform(post("/client/payment/_find")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(inPaymentDto))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").exists())
                .andExpect(jsonPath("sender").exists())
                .andExpect(jsonPath("receiver").exists())
                .andExpect(jsonPath("currentUserCard").exists())
                .andExpect(jsonPath("currentCardBalance").exists())
                .andExpect(jsonPath("amount").exists())
                .andExpect(jsonPath("status").exists())
                .andExpect(jsonPath("date").exists());
    }

    @Test
    @WithUserDetails("+380960150636")
    void testGetPaymentByIdThrowValidationException() throws Exception {
        String inPaymentDto = objectMapper.writeValueAsString(InPaymentDto.builder().build());
        mockMvc.perform(post("/client/payment/_find")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(inPaymentDto))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithUserDetails("+380960150636")
    void testGetAllByCardNumber() throws Exception {
        String inPaymentDto = objectMapper.writeValueAsString(CARD_DTO_WITH_BALANCE);
        mockMvc.perform(post("/client/payment/_findAll")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(inPaymentDto))
                .andExpect(status().isOk());
    }

    @Test
    @WithUserDetails("+380960150636")
    void testGetAllByCardNumberThrowValidationException() throws Exception {
        String inPaymentDto = objectMapper.writeValueAsString(CardDto.builder().build());
        mockMvc.perform(post("/client/payment/_findAll")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(inPaymentDto))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithUserDetails("+380960150636")
    void testCreateTransaction() throws Exception {
        String inPaymentDto = objectMapper.writeValueAsString(IN_PAYMENT_DTO);
        mockMvc.perform(post("/client/payment/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(inPaymentDto))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("id").exists())
                .andExpect(jsonPath("sender").exists())
                .andExpect(jsonPath("receiver").exists())
                .andExpect(jsonPath("currentUserCard").exists())
                .andExpect(jsonPath("currentCardBalance").exists())
                .andExpect(jsonPath("amount").exists())
                .andExpect(jsonPath("status").exists())
                .andExpect(jsonPath("date").exists());
    }

    @Test
    @WithUserDetails("+380960150636")
    void testCreateTransactionThrowValidationException() throws Exception {
        String inPaymentDto = objectMapper.writeValueAsString(InPaymentDto.builder().build());
        mockMvc.perform(post("/client/payment/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(inPaymentDto))
                .andExpect(status().isBadRequest());
    }

}
