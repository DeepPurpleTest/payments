package com.example.payments.controller;

import com.example.payments.entity.CardType;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
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
class CardControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @WithUserDetails("+380960150636")
    void testGetAllCardsByAuthUser() throws Exception {
        mockMvc.perform(get("/card"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(authorities = "ADMIN")
    void testGetAllCardsByUserId() throws Exception {
        mockMvc.perform(get("/card/user/1"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(authorities = "ADMIN")
    void testGetAllCardsByNonExistUserId() throws Exception {
        mockMvc.perform(get("/card/user/-1"))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(authorities = "CLIENT")
    void testGetCardById() throws Exception {
        mockMvc.perform(get("/card/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").exists())
                .andExpect(jsonPath("cardNumber").exists())
                .andExpect(jsonPath("balance").exists())
                .andExpect(jsonPath("status").exists());
    }

    @Test
    @WithMockUser(authorities = "CLIENT")
    void testGetCardByNonExistId() throws Exception {
        mockMvc.perform(get("/card/-1"))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithUserDetails("+380960150636")
    void testCreateCardWithValidType() throws Exception {
        mockMvc.perform(post("/card/create" + "?cardType=" + CardType.VISA))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("id").exists())
                .andExpect(jsonPath("cardNumber").exists())
                .andExpect(jsonPath("balance").exists())
                .andExpect(jsonPath("status").exists());
    }

    @Test
    @WithUserDetails("+380960150636")
    void testCreateCardWithInvalidType() throws Exception {
        mockMvc.perform(post("/card/create" + "?cardType=" + "asd"))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(authorities = "CLIENT")
    void testBlockCard() throws Exception {
        String card = objectMapper.writeValueAsString(CARD_DTO_NULL_BALANCE);
        mockMvc.perform(patch("/card/block")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(card))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").exists())
                .andExpect(jsonPath("cardNumber").exists())
                .andExpect(jsonPath("balance").exists())
                .andExpect(jsonPath("status").exists());
    }

    @Test
    @WithMockUser(authorities = "CLIENT")
    void testBlockNonExistCard() throws Exception {
        String card = objectMapper.writeValueAsString(NON_EXIST_CARD_DTO);
        mockMvc.perform(patch("/card/block")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(card))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(authorities = "ADMIN")
    void testUnlockCard() throws Exception {
        String card = objectMapper.writeValueAsString(CARD_DTO_NULL_BALANCE);
        mockMvc.perform(patch("/card/unlock")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(card))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").exists())
                .andExpect(jsonPath("cardNumber").exists())
                .andExpect(jsonPath("balance").exists())
                .andExpect(jsonPath("status").exists());
    }

    @Test
    @WithMockUser(authorities = "ADMIN")
    void testUnlockNonExistCard() throws Exception {
        String card = objectMapper.writeValueAsString(NON_EXIST_CARD_DTO);
        mockMvc.perform(patch("/card/unlock")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(card))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithUserDetails("+380960150636")
    void testDeleteCardWithNullBalance() throws Exception {
        String card = objectMapper.writeValueAsString(CARD_DTO_NULL_BALANCE);
        mockMvc.perform(delete("/card")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(card))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").exists())
                .andExpect(jsonPath("cardNumber").exists())
                .andExpect(jsonPath("balance").exists())
                .andExpect(jsonPath("status").exists());
    }

    @Test
    @WithUserDetails("+380960150636")
    void testDeleteCardWithNonNullBalance() throws Exception {
        String card = objectMapper.writeValueAsString(CARD_DTO_WITH_BALANCE);
        mockMvc.perform(delete("/card")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(card))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").exists())
                .andExpect(jsonPath("cardNumber").exists())
                .andExpect(jsonPath("balance").exists())
                .andExpect(jsonPath("status").exists());
    }
}
