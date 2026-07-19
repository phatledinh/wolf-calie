package com.phatle.wolf_calie.feature.permission;

import com.phatle.wolf_calie.exception.InvalidRequestException;
import com.phatle.wolf_calie.exception.ResourceNotFoundException;
import com.phatle.wolf_calie.feature.permission.dto.CreatePermissionRequest;
import com.phatle.wolf_calie.feature.permission.dto.PermissionResponse;
import com.phatle.wolf_calie.feature.permission.dto.UpdatePermissionRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PermissionServiceImplTest {

    @Mock
    private PermissionRepository permissionRepository;

    @InjectMocks
    private PermissionServiceImpl permissionService;

    @Test
    @DisplayName("Should throw ResourceNotFoundException when get permission by id not found")
    void getPermissionById_notFound_throwsException() {
        when(permissionRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> permissionService.getPermissionById(1L));
    }

    @Test
    @DisplayName("Should return permission response when get permission by id is found")
    void getPermissionById_found_returnsResponse() {
        Permission permission = new Permission();
        permission.setId(1L);
        permission.setName("View Users");
        when(permissionRepository.findById(1L)).thenReturn(Optional.of(permission));

        PermissionResponse result = permissionService.getPermissionById(1L);
        assertEquals("View Users", result.name());
    }

    @Test
    @DisplayName("Should create permission when valid request")
    void createPermission_validRequest_createsAndReturnsResponse() {
        CreatePermissionRequest request = new CreatePermissionRequest("View Users", "/api/v1/users", "GET", "User");
        when(permissionRepository.existsByName("View Users")).thenReturn(false);
        when(permissionRepository.existsByApiPathAndMethod("/api/v1/users", "GET")).thenReturn(false);
        
        Permission savedPermission = new Permission();
        savedPermission.setId(1L);
        savedPermission.setName("View Users");
        when(permissionRepository.save(any(Permission.class))).thenReturn(savedPermission);

        PermissionResponse response = permissionService.createPermission(request);
        assertEquals(1L, response.id());
        assertEquals("View Users", response.name());
    }

    @Test
    @DisplayName("Should throw InvalidRequestException when creating permission with duplicate name")
    void createPermission_duplicateName_throwsException() {
        CreatePermissionRequest request = new CreatePermissionRequest("View Users", "/api/v1/users", "GET", "User");
        when(permissionRepository.existsByName("View Users")).thenReturn(true);

        assertThrows(InvalidRequestException.class, () -> permissionService.createPermission(request));
    }

    @Test
    @DisplayName("Should delete permission when valid id")
    void deletePermission_validId_deletes() {
        Permission permission = new Permission();
        permission.setId(1L);
        when(permissionRepository.findById(1L)).thenReturn(Optional.of(permission));

        permissionService.deletePermission(1L);
        verify(permissionRepository, times(1)).delete(permission);
    }
}
