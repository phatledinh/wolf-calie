package com.phatle.wolf_calie.feature.permission.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UpdatePermissionRequest(
        @NotBlank(message = "Name is required")
        @Size(max = 255, message = "Name must not exceed 255 characters")
        String name,

        @NotBlank(message = "API path is required")
        @Size(max = 255, message = "API path must not exceed 255 characters")
        String apiPath,

        @NotBlank(message = "Method is required")
        @Size(max = 50, message = "Method must not exceed 50 characters")
        String method,

        @NotBlank(message = "Module is required")
        @Size(max = 100, message = "Module must not exceed 100 characters")
        String module
) {
}
