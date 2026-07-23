package com.phatle.wolf_calie.feature.auth;

import com.phatle.wolf_calie.config.JwtProperties;
import com.phatle.wolf_calie.exception.DuplicateResourceException;
import com.phatle.wolf_calie.feature.auth.dto.LoginRequest;
import com.phatle.wolf_calie.feature.auth.dto.LoginResponse;
import com.phatle.wolf_calie.feature.auth.dto.RegisterRequest;
import com.phatle.wolf_calie.feature.auth.dto.RegisterResponse;
import com.phatle.wolf_calie.feature.user.User;
import com.phatle.wolf_calie.feature.user.UserRepository;
import com.phatle.wolf_calie.feature.user.dto.UserResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import java.time.Instant;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthServiceImplTest {

    @Mock
    private AuthenticationManager authenticationManager;
    @Mock
    private JwtEncoder jwtEncoder;
    @Mock
    private JwtProperties jwtProperties;
    @Mock
    private UserRepository userRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private RefreshTokenRepository refreshTokenRepository;

    @InjectMocks
    private AuthServiceImpl authService;

    @Test
    @DisplayName("Should successfully login and return tokens")
    void login_validCredentials_returnsTokens() {
        LoginRequest request = new LoginRequest("test@test.com", "password");
        User user = new User();
        user.setId(1L);
        user.setEmail("test@test.com");

        Authentication authentication = mock(Authentication.class);
        when(authentication.getName()).thenReturn("test@test.com");

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(authentication);
        when(userRepository.findByEmail("test@test.com")).thenReturn(Optional.of(user));

        when(jwtProperties.accessTokenExpiration()).thenReturn(900000L);
        when(jwtProperties.refreshTokenExpiration()).thenReturn(604800000L);

        Jwt mockJwt = mock(Jwt.class);
        when(mockJwt.getTokenValue()).thenReturn("mock-jwt-token");
        when(jwtEncoder.encode(any(JwtEncoderParameters.class))).thenReturn(mockJwt);

        LoginResponse response = authService.login(request, "127.0.0.1", "user-agent");

        assertNotNull(response);
        assertEquals("mock-jwt-token", response.accessToken());
        assertEquals("mock-jwt-token", response.refreshToken());
        verify(refreshTokenRepository).save(any(RefreshToken.class));
    }

    @Test
    @DisplayName("Should throw exception on register if email exists")
    void register_emailExists_throwsException() {
        RegisterRequest request = new RegisterRequest("Test", "test@test.com", "password", null, null, null);
        when(userRepository.existsByEmail("test@test.com")).thenReturn(true);

        assertThrows(DuplicateResourceException.class, () -> authService.register(request));
    }

    @Test
    @DisplayName("Should successfully register and return user info")
    void register_validRequest_returnsUserInfo() {
        RegisterRequest request = new RegisterRequest("Test", "test@test.com", "password", null, null, null);
        when(userRepository.existsByEmail("test@test.com")).thenReturn(false);
        when(passwordEncoder.encode("password")).thenReturn("encodedPassword");

        User savedUser = new User();
        savedUser.setId(1L);
        savedUser.setEmail("test@test.com");
        when(userRepository.save(any(User.class))).thenReturn(savedUser);

        RegisterResponse response = authService.register(request);

        assertNotNull(response);
        assertEquals(1L, response.id());
        assertEquals("test@test.com", response.email());
        verify(userRepository).save(any(User.class));
    }

    @Test
    @DisplayName("Should logout and revoke token")
    void logout_validToken_revokesToken() {
        RefreshToken storedToken = new RefreshToken();
        storedToken.setRevoked(false);
        User user = new User();
        user.setEmail("test@test.com");
        storedToken.setUser(user);

        when(refreshTokenRepository.findByToken(anyString())).thenReturn(Optional.of(storedToken));

        authService.logout("raw-refresh-token");

        assertTrue(storedToken.isRevoked());
        verify(refreshTokenRepository).save(storedToken);
    }

    @Test
    @DisplayName("Should get me by email")
    void getMe_validEmail_returnsUserResponse() {
        User user = new User();
        user.setId(1L);
        user.setEmail("test@test.com");

        when(userRepository.findByEmail("test@test.com")).thenReturn(Optional.of(user));

        UserResponse response = authService.getMe("test@test.com");

        assertNotNull(response);
        assertEquals(1L, response.id());
        assertEquals("test@test.com", response.email());
    }

    @Test
    @DisplayName("Should refresh token successfully")
    void refresh_validToken_returnsNewTokens() {
        RefreshToken storedToken = new RefreshToken();
        storedToken.setRevoked(false);
        storedToken.setExpiresAt(Instant.now().plusSeconds(3600));
        storedToken.setDeviceInfo("device");
        storedToken.setIpAddress("ip");
        User user = new User();
        user.setId(1L);
        user.setEmail("test@test.com");
        storedToken.setUser(user);
        
        when(refreshTokenRepository.findByToken(anyString())).thenReturn(Optional.of(storedToken));
        
        when(jwtProperties.accessTokenExpiration()).thenReturn(900000L);
        when(jwtProperties.refreshTokenExpiration()).thenReturn(604800000L);
        
        Jwt mockJwt = mock(Jwt.class);
        when(mockJwt.getTokenValue()).thenReturn("mock-jwt-token");
        when(jwtEncoder.encode(any(JwtEncoderParameters.class))).thenReturn(mockJwt);
        
        LoginResponse response = authService.refresh("raw-refresh-token");
        
        assertNotNull(response);
        assertEquals("mock-jwt-token", response.accessToken());
        assertEquals("mock-jwt-token", response.refreshToken());
        assertTrue(storedToken.isRevoked());
        verify(refreshTokenRepository, times(2)).save(any(RefreshToken.class));
    }
}
