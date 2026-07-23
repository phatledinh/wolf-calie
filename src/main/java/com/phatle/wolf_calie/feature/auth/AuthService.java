package com.phatle.wolf_calie.feature.auth;

import com.phatle.wolf_calie.feature.auth.dto.LoginRequest;
import com.phatle.wolf_calie.feature.auth.dto.LoginResponse;
import com.phatle.wolf_calie.feature.auth.dto.RegisterRequest;
import com.phatle.wolf_calie.feature.auth.dto.RegisterResponse;
import com.phatle.wolf_calie.feature.user.dto.UserResponse;

public interface AuthService {

    LoginResponse login(LoginRequest request, String deviceInfo, String ipAddress);

    RegisterResponse register(RegisterRequest request);

    LoginResponse refresh(String rawRefreshToken);

    void logout(String rawRefreshToken);

    UserResponse getMe(String email);
}
