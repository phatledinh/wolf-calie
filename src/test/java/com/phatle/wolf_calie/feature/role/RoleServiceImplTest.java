package com.phatle.wolf_calie.feature.role;

import com.phatle.wolf_calie.exception.InvalidRequestException;
import com.phatle.wolf_calie.exception.ResourceNotFoundException;
import com.phatle.wolf_calie.feature.permission.Permission;
import com.phatle.wolf_calie.feature.permission.PermissionRepository;
import com.phatle.wolf_calie.feature.role.dto.CreateRoleRequest;
import com.phatle.wolf_calie.feature.role.dto.RoleResponse;
import com.phatle.wolf_calie.feature.role.dto.UpdateRoleRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RoleServiceImplTest {

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private PermissionRepository permissionRepository;

    @InjectMocks
    private RoleServiceImpl roleService;

    private Role mockRole;
    private Permission mockPermission;

    @BeforeEach
    void setUp() {
        mockPermission = new Permission();
        mockPermission.setId(1L);
        mockPermission.setName("READ_USER");

        mockRole = new Role();
        mockRole.setId(1L);
        mockRole.setName("ADMIN");
        mockRole.setDescription("Administrator role");
        mockRole.setActive(true);
        mockRole.setCreatedAt(Instant.now());
        mockRole.setUpdatedAt(Instant.now());
        mockRole.setPermissions(new HashSet<>(Set.of(mockPermission)));
    }

    @Test
    void createRole_Success() {
        CreateRoleRequest request = new CreateRoleRequest("USER", "User role", Set.of(1L));

        when(roleRepository.existsByName("USER")).thenReturn(false);
        when(permissionRepository.findAllById(Set.of(1L))).thenReturn(List.of(mockPermission));
        when(roleRepository.save(any(Role.class))).thenAnswer(invocation -> {
            Role saved = invocation.getArgument(0);
            saved.setId(2L);
            saved.setCreatedAt(Instant.now());
            saved.setUpdatedAt(Instant.now());
            return saved;
        });

        RoleResponse response = roleService.createRole(request);

        assertNotNull(response);
        assertEquals("USER", response.name());
        assertEquals("User role", response.description());
        assertTrue(response.isActive());
        assertEquals(1, response.permissions().size());

        verify(roleRepository, times(1)).save(any(Role.class));
    }

    @Test
    void createRole_ThrowsInvalidRequestException_WhenNameExists() {
        CreateRoleRequest request = new CreateRoleRequest("ADMIN", "Admin role", null);

        when(roleRepository.existsByName("ADMIN")).thenReturn(true);

        assertThrows(InvalidRequestException.class, () -> roleService.createRole(request));
        verify(roleRepository, never()).save(any(Role.class));
    }

    @Test
    void getRoleById_Success() {
        when(roleRepository.findById(1L)).thenReturn(Optional.of(mockRole));

        RoleResponse response = roleService.getRoleById(1L);

        assertNotNull(response);
        assertEquals("ADMIN", response.name());
    }

    @Test
    void getRoleById_ThrowsResourceNotFoundException() {
        when(roleRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> roleService.getRoleById(99L));
    }

    @Test
    void updateRole_Success() {
        UpdateRoleRequest request = new UpdateRoleRequest("SUPER_ADMIN", "Super Admin role", false, Set.of(1L));

        when(roleRepository.findById(1L)).thenReturn(Optional.of(mockRole));
        when(roleRepository.existsByName("SUPER_ADMIN")).thenReturn(false);
        when(permissionRepository.findAllById(Set.of(1L))).thenReturn(List.of(mockPermission));
        when(roleRepository.save(any(Role.class))).thenReturn(mockRole);

        RoleResponse response = roleService.updateRole(1L, request);

        assertNotNull(response);
        assertEquals("SUPER_ADMIN", response.name());
        assertEquals("Super Admin role", response.description());
        assertFalse(response.isActive());
    }

    @Test
    void deleteRole_Success() {
        when(roleRepository.findById(1L)).thenReturn(Optional.of(mockRole));
        when(roleRepository.save(any(Role.class))).thenReturn(mockRole);

        roleService.deleteRole(1L);

        assertFalse(mockRole.isActive());
        verify(roleRepository, times(1)).save(mockRole);
    }
}
