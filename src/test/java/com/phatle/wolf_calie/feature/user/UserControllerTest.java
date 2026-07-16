package com.phatle.wolf_calie.feature.user;

import tools.jackson.databind.ObjectMapper;
import com.phatle.wolf_calie.feature.user.dto.CreateUserRequest;
import com.phatle.wolf_calie.feature.user.dto.UpdateUserRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        userRepository.deleteAll();
    }

    @Test
    @DisplayName("Should create user and return 201 Created")
    void createUser_validRequest_returnsCreated() throws Exception {
        CreateUserRequest request = new CreateUserRequest("Integration Test", "integration@test.com", "password123", "0123456789");

        mockMvc.perform(post("/api/v1/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.statusCode").value(201))
                .andExpect(jsonPath("$.data.email").value("integration@test.com"))
                .andExpect(jsonPath("$.data.name").value("Integration Test"));
    }

    @Test
    @DisplayName("Should return 400 when request is invalid")
    void createUser_invalidRequest_returnsBadRequest() throws Exception {
        CreateUserRequest request = new CreateUserRequest("", "invalid-email", "short", null);

        mockMvc.perform(post("/api/v1/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.statusCode").value(400));
    }

    @Test
    @DisplayName("Should return 409 when email already exists")
    void createUser_duplicateEmail_returnsConflict() throws Exception {
        User existingUser = new User();
        existingUser.setName("Existing");
        existingUser.setEmail("duplicate@test.com");
        existingUser.setPassword("password");
        userRepository.save(existingUser);

        CreateUserRequest request = new CreateUserRequest("New User", "duplicate@test.com", "password123", "0123456789");

        mockMvc.perform(post("/api/v1/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.statusCode").value(409));
    }

    @Test
    @DisplayName("Should get user by id")
    @WithMockUser
    void getUser_existingId_returnsUser() throws Exception {
        User savedUser = new User();
        savedUser.setName("Get Test");
        savedUser.setEmail("get@test.com");
        savedUser.setPassword("password");
        savedUser = userRepository.save(savedUser);

        mockMvc.perform(get("/api/v1/users/" + savedUser.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.statusCode").value(200))
                .andExpect(jsonPath("$.data.email").value("get@test.com"));
    }

    @Test
    @DisplayName("Should return 404 when user not found")
    @WithMockUser
    void getUser_nonExistingId_returnsNotFound() throws Exception {
        mockMvc.perform(get("/api/v1/users/999999"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.statusCode").value(404));
    }

    @Test
    @DisplayName("Should return 401 when unauthenticated access to secured endpoint")
    void getUser_unauthenticated_returnsUnauthorized() throws Exception {
        mockMvc.perform(get("/api/v1/users/1"))
                .andExpect(status().isUnauthorized()); // In Spring Security, this might redirect or return 401 depending on config. But for API it should be 401.
    }

    @Test
    @DisplayName("Should update user successfully")
    @WithMockUser
    void updateUser_validRequest_returnsOk() throws Exception {
        User savedUser = new User();
        savedUser.setName("Update Test");
        savedUser.setEmail("update@test.com");
        savedUser.setPassword("password");
        savedUser = userRepository.save(savedUser);

        UpdateUserRequest request = new UpdateUserRequest("Updated Name", "0987654321", Gender.MALE, null, null);

        mockMvc.perform(put("/api/v1/users/" + savedUser.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.name").value("Updated Name"))
                .andExpect(jsonPath("$.data.phone").value("0987654321"));
    }

    @Test
    @DisplayName("Should soft delete user successfully")
    @WithMockUser
    void deleteUser_existingId_returnsOk() throws Exception {
        User savedUser = new User();
        savedUser.setName("Delete Test");
        savedUser.setEmail("delete@test.com");
        savedUser.setPassword("password");
        savedUser = userRepository.save(savedUser);

        mockMvc.perform(delete("/api/v1/users/" + savedUser.getId()))
                .andExpect(status().isOk());

        mockMvc.perform(get("/api/v1/users/" + savedUser.getId()))
                .andExpect(status().isNotFound());
    }
}
