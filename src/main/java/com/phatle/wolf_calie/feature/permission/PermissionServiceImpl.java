package com.phatle.wolf_calie.feature.permission;

import com.phatle.wolf_calie.exception.InvalidRequestException;
import com.phatle.wolf_calie.exception.ResourceNotFoundException;
import com.phatle.wolf_calie.feature.permission.dto.CreatePermissionRequest;
import com.phatle.wolf_calie.feature.permission.dto.PermissionResponse;
import com.phatle.wolf_calie.feature.permission.dto.UpdatePermissionRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PermissionServiceImpl implements PermissionService {

    private final PermissionRepository permissionRepository;

    public PermissionServiceImpl(PermissionRepository permissionRepository) {
        this.permissionRepository = permissionRepository;
    }

    @Override
    @Transactional
    public PermissionResponse createPermission(CreatePermissionRequest request) {
        if (permissionRepository.existsByName(request.name())) {
            throw new InvalidRequestException("Permission name already exists");
        }
        if (permissionRepository.existsByApiPathAndMethod(request.apiPath(), request.method())) {
            throw new InvalidRequestException("Permission with this API path and method already exists");
        }

        Permission permission = new Permission();
        permission.setName(request.name());
        permission.setApiPath(request.apiPath());
        permission.setMethod(request.method());
        permission.setModule(request.module());

        return PermissionResponse.fromEntity(permissionRepository.save(permission));
    }

    @Override
    @Transactional
    public PermissionResponse updatePermission(long id, UpdatePermissionRequest request) {
        Permission permission = permissionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Permission", "id", id));

        if (!permission.getName().equals(request.name()) && permissionRepository.existsByName(request.name())) {
            throw new InvalidRequestException("Permission name already exists");
        }

        if ((!permission.getApiPath().equals(request.apiPath()) || !permission.getMethod().equals(request.method()))
                && permissionRepository.existsByApiPathAndMethod(request.apiPath(), request.method())) {
            throw new InvalidRequestException("Permission with this API path and method already exists");
        }

        permission.setName(request.name());
        permission.setApiPath(request.apiPath());
        permission.setMethod(request.method());
        permission.setModule(request.module());

        return PermissionResponse.fromEntity(permissionRepository.save(permission));
    }

    @Override
    @Transactional(readOnly = true)
    public PermissionResponse getPermissionById(long id) {
        Permission permission = permissionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Permission", "id", id));
        return PermissionResponse.fromEntity(permission);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PermissionResponse> getAllPermissions() {
        return permissionRepository.findAll().stream()
                .map(PermissionResponse::fromEntity)
                .toList();
    }

    @Override
    @Transactional
    public void deletePermission(long id) {
        Permission permission = permissionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Permission", "id", id));
        permissionRepository.delete(permission);
    }
}
