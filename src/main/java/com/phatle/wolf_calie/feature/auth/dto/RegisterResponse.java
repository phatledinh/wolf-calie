package com.phatle.wolf_calie.feature.auth.dto;

import com.phatle.wolf_calie.feature.user.User;
import com.phatle.wolf_calie.feature.user.Gender;
import java.time.Instant;
import java.time.LocalDate;

public record RegisterResponse(
        long id,
        String name,
        String email,
        String phone,
        Gender gender,
        LocalDate birthday,
        Instant createdAt
) {
    public static RegisterResponse fromEntity(User user) {
        return new RegisterResponse(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getPhone(),
                user.getGender(),
                user.getBirthday(),
                user.getCreatedAt()
        );
    }
}
