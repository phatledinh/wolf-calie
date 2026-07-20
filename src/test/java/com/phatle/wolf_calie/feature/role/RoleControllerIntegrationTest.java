package com.phatle.wolf_calie.feature.role;

import com.phatle.wolf_calie.feature.permission.Permission;
import com.phatle.wolf_calie.feature.permission.PermissionRepository;
import com.phatle.wolf_calie.feature.role.dto.CreateRoleRequest;
import com.phatle.wolf_calie.feature.role.dto.UpdateRoleRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class RoleControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PermissionRepository permissionRepository;

    private Permission testPermission;
    private Role testRole;

    @BeforeEach
    void setUp() {
        roleRepository.deleteAll();
        permissionRepository.deleteAll();

        testPermission = new Permission();
        testPermission.setName("MANAGE_USERS");
        testPermission.setApiPath("/api/v1/users");
        testPermission.setMethod("POST");
        testPermission.setModule("USER");
        testPermission = permissionRepository.save(testPermission);

        testRole = new Role();
        testRole.setName("MANAGER");
        testRole.setDescription("Manager role");
        testRole.setActive(true);
        testRole.addPermission(testPermission);
        testRole = roleRepository.save(testRole);
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    @DisplayName("Should return 201 with created role")
    void createRole_valid_returns201() throws Exception {
        mockMvc.perform(post("/api/v1/roles")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "name": "EDITOR",
                                    "description": "Content editor",
                                    "permissionIds": [%d]
                                }
                                """.formatted(testPermission.getId())))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.statusCode").value(201))
                .andExpect(jsonPath("$.data.name").value("EDITOR"))
                .andExpect(jsonPath("$.data.permissions[0].name").value("MANAGE_USERS"));
                
        roleRepository.deleteAll();
        permissionRepository.deleteAll();
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    @DisplayName("Should return list of roles with 200")
    void getAllRoles_returnsList() throws Exception {
        mockMvc.perform(get("/api/v1/roles"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.statusCode").value(200))
                .andExpect(jsonPath("$.data.content[0].name").value("MANAGER"))
                .andExpect(jsonPath("$.data.totalElements").value(1));
                
        roleRepository.deleteAll();
        permissionRepository.deleteAll();
    }
}
