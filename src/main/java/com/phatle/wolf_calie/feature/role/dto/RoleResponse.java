package com.phatle.wolf_calie.feature.role.dto;

import com.phatle.wolf_calie.feature.permission.dto.PermissionResponse;
import com.phatle.wolf_calie.feature.role.Role;

import java.time.Instant;
import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

public record RoleResponse(
        Long id,
        String name,
        String description,
        boolean isActive,
        Set<PermissionResponse> permissions,
        Instant createdAt,
        Instant updatedAt
) {
    public static RoleResponse fromEntity(Role role) {
        Set<PermissionResponse> permResponses = role.getPermissions() != null
                ? role.getPermissions().stream()
                .map(PermissionResponse::fromEntity)
                .collect(Collectors.toSet())
                : Collections.emptySet();

        return new RoleResponse(
                role.getId(),
                role.getName(),
                role.getDescription(),
                role.isActive(),
                permResponses,
                role.getCreatedAt(),
                role.getUpdatedAt()
        );
    }
}
