package com.phatle.wolf_calie.feature.permission.dto;

import com.phatle.wolf_calie.feature.permission.Permission;

public record PermissionResponse(
        long id,
        String name,
        String apiPath,
        String method,
        String module
) {
    public static PermissionResponse fromEntity(Permission permission) {
        return new PermissionResponse(
                permission.getId(),
                permission.getName(),
                permission.getApiPath(),
                permission.getMethod(),
                permission.getModule()
        );
    }
}
