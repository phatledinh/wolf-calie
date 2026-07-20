package com.phatle.wolf_calie.feature.role;

import com.phatle.wolf_calie.feature.role.dto.CreateRoleRequest;
import com.phatle.wolf_calie.feature.role.dto.RoleResponse;
import com.phatle.wolf_calie.feature.role.dto.UpdateRoleRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface RoleService {
    RoleResponse createRole(CreateRoleRequest request);
    RoleResponse updateRole(Long id, UpdateRoleRequest request);
    void deleteRole(Long id);
    RoleResponse getRoleById(Long id);
    Page<RoleResponse> getAllRoles(Pageable pageable);
}
