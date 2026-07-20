package com.phatle.wolf_calie.feature.role.dto;

import jakarta.validation.constraints.NotBlank;
import java.util.Set;

public record UpdateRoleRequest(
        @NotBlank(message = "Role name cannot be empty")
        String name,

        String description,

        Boolean isActive,

        Set<Long> permissionIds
) {
}
