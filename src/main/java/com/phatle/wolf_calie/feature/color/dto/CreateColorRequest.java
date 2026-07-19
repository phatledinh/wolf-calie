package com.phatle.wolf_calie.feature.color.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateColorRequest(
        @NotBlank(message = "Name must not be blank")
        @Size(max = 100, message = "Name must not exceed 100 characters")
        String name,

        @NotBlank(message = "Hex code must not be blank")
        @Size(max = 10, message = "Hex code must not exceed 10 characters")
        String hexCode,

        Integer sortOrder,

        Boolean isActive
) {
}
