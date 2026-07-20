package com.phatle.wolf_calie.feature.role;

import com.phatle.wolf_calie.exception.InvalidRequestException;
import com.phatle.wolf_calie.exception.ResourceNotFoundException;
import com.phatle.wolf_calie.feature.permission.Permission;
import com.phatle.wolf_calie.feature.permission.PermissionRepository;
import com.phatle.wolf_calie.feature.role.dto.CreateRoleRequest;
import com.phatle.wolf_calie.feature.role.dto.RoleResponse;
import com.phatle.wolf_calie.feature.role.dto.UpdateRoleRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;
    private final PermissionRepository permissionRepository;

    public RoleServiceImpl(RoleRepository roleRepository, PermissionRepository permissionRepository) {
        this.roleRepository = roleRepository;
        this.permissionRepository = permissionRepository;
    }

    @Override
    @Transactional
    public RoleResponse createRole(CreateRoleRequest request) {
        if (roleRepository.existsByName(request.name())) {
            throw new InvalidRequestException("Role name already exists: " + request.name());
        }

        Role role = new Role();
        role.setName(request.name());
        role.setDescription(request.description());
        role.setActive(true);

        if (request.permissionIds() != null && !request.permissionIds().isEmpty()) {
            List<Permission> permissions = permissionRepository.findAllById(request.permissionIds());
            if (permissions.size() != request.permissionIds().size()) {
                throw new InvalidRequestException("One or more permission IDs are invalid.");
            }
            role.setPermissions(new HashSet<>(permissions));
        }

        return RoleResponse.fromEntity(roleRepository.save(role));
    }

    @Override
    @Transactional
    public RoleResponse updateRole(Long id, UpdateRoleRequest request) {
        Role role = roleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Role", "id", id));

        if (!role.getName().equals(request.name()) && roleRepository.existsByName(request.name())) {
            throw new InvalidRequestException("Role name already exists: " + request.name());
        }

        role.setName(request.name());
        role.setDescription(request.description());
        
        if (request.isActive() != null) {
            role.setActive(request.isActive());
        }

        if (request.permissionIds() != null) {
            if (request.permissionIds().isEmpty()) {
                role.setPermissions(new HashSet<>());
            } else {
                List<Permission> permissions = permissionRepository.findAllById(request.permissionIds());
                if (permissions.size() != request.permissionIds().size()) {
                    throw new InvalidRequestException("One or more permission IDs are invalid.");
                }
                role.setPermissions(new HashSet<>(permissions));
            }
        }

        return RoleResponse.fromEntity(roleRepository.save(role));
    }

    @Override
    @Transactional
    public void deleteRole(Long id) {
        Role role = roleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Role", "id", id));

        role.setActive(false);
        roleRepository.save(role);
    }

    @Override
    public RoleResponse getRoleById(Long id) {
        Role role = roleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Role", "id", id));
        return RoleResponse.fromEntity(role);
    }

    @Override
    public Page<RoleResponse> getAllRoles(Pageable pageable) {
        return roleRepository.findAll(pageable)
                .map(RoleResponse::fromEntity);
    }
}
