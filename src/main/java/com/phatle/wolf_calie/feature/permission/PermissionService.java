package com.phatle.wolf_calie.feature.permission;

import com.phatle.wolf_calie.feature.permission.dto.CreatePermissionRequest;
import com.phatle.wolf_calie.feature.permission.dto.PermissionResponse;
import com.phatle.wolf_calie.feature.permission.dto.UpdatePermissionRequest;

import java.util.List;

public interface PermissionService {

    PermissionResponse createPermission(CreatePermissionRequest request);

    PermissionResponse updatePermission(long id, UpdatePermissionRequest request);

    PermissionResponse getPermissionById(long id);

    List<PermissionResponse> getAllPermissions();

    void deletePermission(long id);
}
