package com.phatle.wolf_calie.feature.permission;

import com.phatle.wolf_calie.dto.ApiResponse;
import com.phatle.wolf_calie.feature.permission.dto.CreatePermissionRequest;
import com.phatle.wolf_calie.feature.permission.dto.PermissionResponse;
import com.phatle.wolf_calie.feature.permission.dto.UpdatePermissionRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/permissions")
public class PermissionController {

    private final PermissionService permissionService;

    public PermissionController(PermissionService permissionService) {
        this.permissionService = permissionService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<PermissionResponse>>> getAllPermissions() {
        return ResponseEntity.ok(ApiResponse.success(permissionService.getAllPermissions()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<PermissionResponse>> getPermissionById(@PathVariable long id) {
        return ResponseEntity.ok(ApiResponse.success(permissionService.getPermissionById(id)));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<PermissionResponse>> createPermission(
            @RequestBody @Valid CreatePermissionRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.created(permissionService.createPermission(request)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<PermissionResponse>> updatePermission(
            @PathVariable long id,
            @RequestBody @Valid UpdatePermissionRequest request) {
        return ResponseEntity.ok(ApiResponse.success(permissionService.updatePermission(id, request)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deletePermission(@PathVariable long id) {
        permissionService.deletePermission(id);
        return ResponseEntity.ok(ApiResponse.success(null));
    }
}
