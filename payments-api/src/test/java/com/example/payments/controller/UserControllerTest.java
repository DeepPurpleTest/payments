package com.example.payments.controller;

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
class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private UserRepository userRepository;

    @Test
    @WithMockUser(authorities = "ADMIN")
    void testGetUser() throws Exception {
        mockMvc.perform(get("/user/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").exists())
                .andExpect(jsonPath("name").exists())
                .andExpect(jsonPath("surname").exists())
                .andExpect(jsonPath("middleName").exists())
                .andExpect(jsonPath("phoneNumber").exists())
                .andReturn();
    }

    @Test
    @WithMockUser(authorities = "ADMIN")
    void testGetNonExistUser() throws Exception {
        mockMvc.perform(get("/user/-1"))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(authorities = "ADMIN")
    void testGetUserByPhoneNumber() throws Exception {
        String phoneNumber = "+380960150636";
        mockMvc.perform(get("/user/phone_number?phoneNumber=" + phoneNumber))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").exists())
                .andExpect(jsonPath("name").exists())
                .andExpect(jsonPath("surname").exists())
                .andExpect(jsonPath("middleName").exists())
                .andExpect(jsonPath("phoneNumber").exists());
    }

    @Test
    @WithMockUser(authorities = "ADMIN")
    void testGetUserWithInvalidPhoneNUmber() throws Exception {
        mockMvc.perform(get("/user/phone_number?phoneNumber="))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(authorities = "ADMIN")
    void testUpdateUser() throws Exception {
        User userToUpdate = userRepository.findFirstByRole(Role.CLIENT);

        userToUpdate.setName("Vodem");
        userToUpdate.setSurname("Storozhuk");
        String updatedUser = objectMapper.writeValueAsString(userToUpdate);

        mockMvc.perform(patch("/user/update")
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
    @WithMockUser(authorities = "ADMIN")
    void testUpdateNonExistUser() throws Exception {
        User userToUpdate = userRepository.findFirstByRole(Role.CLIENT);

        userToUpdate.setId(-1L);
        String updatedUser = objectMapper.writeValueAsString(userToUpdate);

        mockMvc.perform(patch("/user/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updatedUser))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(authorities = "ADMIN")
    void testDeleteUser() throws Exception {
        mockMvc.perform(delete("/user/1"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(authorities = "ADMIN")
    void testDeleteNonExistUser() throws Exception {
        mockMvc.perform(delete("/user/-1"))
                .andExpect(status().isNotFound());
    }
}
