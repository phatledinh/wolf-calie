package com.phatle.wolf_calie.feature.user.dto;

import com.phatle.wolf_calie.feature.user.Gender;
import com.phatle.wolf_calie.feature.user.User;
import com.phatle.wolf_calie.feature.user.UserStatus;

import java.time.Instant;
import java.time.LocalDate;

public record UserResponse(
        long id,
        String name,
        String email,
        String phone,
        Gender gender,
        LocalDate birthday,
        String avatar,
        UserStatus status,
        boolean emailVerified,
        boolean phoneVerified,
        Instant lastLogin,
        Instant createdAt,
        Instant updatedAt
) {
    public static UserResponse fromEntity(User user) {
        return new UserResponse(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getPhone(),
                user.getGender(),
                user.getBirthday(),
                user.getAvatar(),
                user.getStatus(),
                user.isEmailVerified(),
                user.isPhoneVerified(),
                user.getLastLogin(),
                user.getCreatedAt(),
                user.getUpdatedAt()
        );
    }
}
