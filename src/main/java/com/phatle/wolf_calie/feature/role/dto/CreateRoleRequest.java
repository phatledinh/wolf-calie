package com.phatle.wolf_calie.feature.role.dto;

import jakarta.validation.constraints.NotBlank;
import java.util.Set;

public record CreateRoleRequest(
        @NotBlank(message = "Role name cannot be empty")
        String name,

        String description,

        Set<Long> permissionIds
) {
}
