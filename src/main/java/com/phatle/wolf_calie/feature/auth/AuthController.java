package com.phatle.wolf_calie.feature.auth;

import com.phatle.wolf_calie.feature.auth.dto.LoginRequest;
import com.phatle.wolf_calie.feature.auth.dto.LoginResponse;
import com.phatle.wolf_calie.security.JwtService;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Value("${jwt.expiration}")
    private long jwtExpiration;

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public AuthController(AuthenticationManager authenticationManager, JwtService jwtService) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest loginRequest) {
        // Trình quản lý xác thực sẽ sử dụng CustomUserDetailsService để xác thực email
        // & password
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

        // Sinh token từ thông tin Authentication
        String accessToken = jwtService.generateToken(authentication);
        return ResponseEntity.ok(new LoginResponse(accessToken, "Bearer", jwtExpiration));
    }
}
