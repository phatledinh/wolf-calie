package com.phatle.wolf_calie.feature.size.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateSizeRequest(
        @NotBlank(message = "Name must not be blank")
        @Size(max = 50, message = "Name must not exceed 50 characters")
        String name,

        Integer sortOrder
) {
}
