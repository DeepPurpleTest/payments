package com.example.payments.controller.admin;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static com.example.payments.TestConstants.CARD_DTO_NULL_BALANCE;
import static com.example.payments.TestConstants.NON_EXIST_CARD_DTO;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test-env")
@Transactional
class AdminCardControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    // admin
    @Test
    @WithMockUser(authorities = "ADMIN")
    void testGetAllCardsByUserId() throws Exception {
        mockMvc.perform(get("/admin/card/1"))
                .andExpect(status().isOk());
    }

    // admin
    @Test
    @WithMockUser(authorities = "ADMIN")
    void testGetAllCardsByNonExistUserId() throws Exception {
        mockMvc.perform(get("/admin/card/-1"))
                .andExpect(status().isNotFound());
    }

    // client/admin
    @Test
    @WithMockUser(authorities = "ADMIN")
    void testGetCardById() throws Exception {
        mockMvc.perform(get("/admin/card/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").exists())
                .andExpect(jsonPath("cardNumber").exists())
                .andExpect(jsonPath("balance").exists())
                .andExpect(jsonPath("status").exists());
    }

    // client/admin
    @Test
    @WithMockUser(authorities = "ADMIN")
    void testGetCardByNonExistId() throws Exception {
        mockMvc.perform(get("/admin/card/-1"))
                .andExpect(status().isNotFound());
    }

    // client/admin
    @Test
    @WithMockUser(authorities = "ADMIN")
    void testBlockCard() throws Exception {
        String card = objectMapper.writeValueAsString(CARD_DTO_NULL_BALANCE);
        mockMvc.perform(patch("/admin/card/block")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(card))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").exists())
                .andExpect(jsonPath("cardNumber").exists())
                .andExpect(jsonPath("balance").exists())
                .andExpect(jsonPath("status").exists());
    }

    // client/admin
    @Test
    @WithMockUser(authorities = "ADMIN")
    void testBlockNonExistCard() throws Exception {
        String card = objectMapper.writeValueAsString(NON_EXIST_CARD_DTO);
        mockMvc.perform(patch("/admin/card/block")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(card))
                .andExpect(status().isNotFound());
    }

    // admin
    @Test
    @WithMockUser(authorities = "ADMIN")
    void testUnlockCard() throws Exception {
        String card = objectMapper.writeValueAsString(CARD_DTO_NULL_BALANCE);
        mockMvc.perform(patch("/admin/card/unlock")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(card))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").exists())
                .andExpect(jsonPath("cardNumber").exists())
                .andExpect(jsonPath("balance").exists())
                .andExpect(jsonPath("status").exists());
    }

    //admin
    @Test
    @WithMockUser(authorities = "ADMIN")
    void testUnlockNonExistCard() throws Exception {
        String card = objectMapper.writeValueAsString(NON_EXIST_CARD_DTO);
        mockMvc.perform(patch("/admin/card/unlock")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(card))
                .andExpect(status().isNotFound());
    }
}
