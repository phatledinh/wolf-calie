package com.phatle.wolf_calie.feature.user.dto;

import com.phatle.wolf_calie.feature.user.Gender;

import jakarta.validation.constraints.NotBlank;
import java.time.LocalDate;

public record UpdateUserRequest(
        @NotBlank(message = "Name is required")
        String name,
        
        String phone,
        
        Gender gender,
        
        LocalDate birthday,
        
        String avatar
) {
}
