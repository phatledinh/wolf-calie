package com.phatle.wolf_calie.feature.auth;

import tools.jackson.databind.ObjectMapper;
import com.phatle.wolf_calie.feature.auth.dto.LoginRequest;
import com.phatle.wolf_calie.feature.auth.dto.LoginResponse;
import com.phatle.wolf_calie.feature.auth.dto.RegisterRequest;
import com.phatle.wolf_calie.feature.auth.dto.RegisterResponse;
import com.phatle.wolf_calie.feature.user.Gender;
import jakarta.servlet.http.Cookie;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.time.Instant;
import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false) // disable security filters for simple controller testing
@ActiveProfiles("test")
class AuthControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private AuthService authService;

    @Test
    @DisplayName("Should login successfully and set HttpOnly refresh_token cookie")
    void login_validRequest_setsCookie() throws Exception {
        LoginRequest request = new LoginRequest("test@test.com", "password");
        LoginResponse response = new LoginResponse("mock-access-token", "mock-refresh-token");

        when(authService.login(any(LoginRequest.class), anyString(), anyString())).thenReturn(response);

        mockMvc.perform(post("/api/v1/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
                        .header("User-Agent", "Test-Agent"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.accessToken").value("mock-access-token"))
                .andExpect(cookie().exists("refresh_token"))
                .andExpect(cookie().value("refresh_token", "mock-refresh-token"))
                .andExpect(cookie().httpOnly("refresh_token", true));
    }

    @Test
    @DisplayName("Should return 400 when login with invalid request")
    void login_invalidRequest_returns400() throws Exception {
        mockMvc.perform(post("/api/v1/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"email":"invalid-email","password":""}
                                """))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.statusCode").value(400));
    }

    @Test
    @DisplayName("Should register successfully and return 201 Created")
    void register_validRequest_returns201() throws Exception {
        RegisterRequest request = new RegisterRequest("Test", "test@test.com", "password123", "0123456789", Gender.MALE, LocalDate.of(1990, 1, 1));
        RegisterResponse response = new RegisterResponse(1L, "Test", "test@test.com", "0123456789", Gender.MALE, LocalDate.of(1990, 1, 1), Instant.now());

        when(authService.register(any(RegisterRequest.class))).thenReturn(response);

        mockMvc.perform(post("/api/v1/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.data.email").value("test@test.com"))
                .andExpect(jsonPath("$.statusCode").value(201));
    }

    @Test
    @DisplayName("Should refresh token using cookie")
    void refresh_withCookie_returnsNewTokens() throws Exception {
        LoginResponse response = new LoginResponse("new-access-token", "new-refresh-token");
        when(authService.refresh(eq("old-refresh-token"))).thenReturn(response);

        Cookie cookie = new Cookie("refresh_token", "old-refresh-token");

        mockMvc.perform(post("/api/v1/auth/refresh")
                        .cookie(cookie))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.accessToken").value("new-access-token"))
                .andExpect(cookie().exists("refresh_token"))
                .andExpect(cookie().value("refresh_token", "new-refresh-token"));
    }

    @Test
    @DisplayName("Should logout and clear cookie")
    void logout_clearsCookie() throws Exception {
        Cookie cookie = new Cookie("refresh_token", "valid-refresh-token");

        mockMvc.perform(post("/api/v1/auth/logout")
                        .cookie(cookie))
                .andExpect(status().isOk())
                .andExpect(cookie().maxAge("refresh_token", 0)); // cookie cleared
        
        verify(authService).logout("valid-refresh-token");
    }
}
