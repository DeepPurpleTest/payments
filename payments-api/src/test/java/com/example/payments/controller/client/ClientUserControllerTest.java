package com.example.payments.controller.client;

import com.example.payments.entity.Role;
import com.example.payments.entity.User;
import com.example.payments.repository.UserRepository;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test-env")
@Transactional
class ClientUserControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private UserRepository userRepository;

    @Test
    @WithMockUser(authorities = "CLIENT")
    void testUpdateUser() throws Exception {
        User userToUpdate = userRepository.findFirstByRole(Role.CLIENT);

        userToUpdate.setName("Vodem");
        userToUpdate.setSurname("Storozhuk");
        String updatedUser = objectMapper.writeValueAsString(userToUpdate);

        mockMvc.perform(patch("/client/user/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updatedUser))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").exists())
                .andExpect(jsonPath("name").exists())
                .andExpect(jsonPath("surname").exists())
                .andExpect(jsonPath("middleName").exists())
                .andExpect(jsonPath("phoneNumber").exists());
    }

    @Test
    @WithMockUser(authorities = "CLIENT")
    void testUpdateNonExistUser() throws Exception {
        User userToUpdate = userRepository.findFirstByRole(Role.CLIENT);

        userToUpdate.setId(-1L);
        String updatedUser = objectMapper.writeValueAsString(userToUpdate);

        mockMvc.perform(patch("/client/user/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updatedUser))
                .andExpect(status().isNotFound());
    }
}
