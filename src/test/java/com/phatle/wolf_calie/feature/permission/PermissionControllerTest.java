package com.phatle.wolf_calie.feature.permission;

import com.phatle.wolf_calie.feature.permission.dto.CreatePermissionRequest;
import com.phatle.wolf_calie.feature.permission.dto.UpdatePermissionRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class PermissionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private PermissionRepository permissionRepository;

    @BeforeEach
    void setUp() {
        permissionRepository.deleteAll();
    }

    @Test
    @DisplayName("Should return 401 when getting permissions without token")
    void getAllPermissions_unauthorized() throws Exception {
        mockMvc.perform(get("/api/v1/permissions"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @DisplayName("Should get all permissions when authorized")
    @WithMockUser(roles = "ADMIN")
    void getAllPermissions_authorized_returnsList() throws Exception {
        Permission p = new Permission();
        p.setName("View Users");
        p.setApiPath("/api/v1/users");
        p.setMethod("GET");
        p.setModule("User");
        permissionRepository.save(p);

        mockMvc.perform(get("/api/v1/permissions"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.statusCode").value(200))
                .andExpect(jsonPath("$.data[0].name").value("View Users"));
    }

    @Test
    @DisplayName("Should create permission and return 201")
    @WithMockUser(roles = "ADMIN")
    void createPermission_validRequest_returnsCreated() throws Exception {
        CreatePermissionRequest request = new CreatePermissionRequest("Manage Roles", "/api/v1/roles", "POST", "Role");

        mockMvc.perform(post("/api/v1/permissions")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.statusCode").value(201))
                .andExpect(jsonPath("$.data.name").value("Manage Roles"));
    }

    @Test
    @DisplayName("Should return 400 Bad Request when request is invalid")
    @WithMockUser(roles = "ADMIN")
    void createPermission_invalidRequest_returnsBadRequest() throws Exception {
        CreatePermissionRequest request = new CreatePermissionRequest("", "", "", "");

        mockMvc.perform(post("/api/v1/permissions")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.statusCode").value(400));
    }

    @Test
    @DisplayName("Should update permission successfully")
    @WithMockUser(roles = "ADMIN")
    void updatePermission_validRequest_returnsOk() throws Exception {
        Permission p = new Permission();
        p.setName("Old Name");
        p.setApiPath("/api/v1/old");
        p.setMethod("GET");
        p.setModule("Old");
        p = permissionRepository.save(p);

        UpdatePermissionRequest request = new UpdatePermissionRequest("New Name", "/api/v1/new", "POST", "New");

        mockMvc.perform(put("/api/v1/permissions/" + p.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.name").value("New Name"))
                .andExpect(jsonPath("$.data.apiPath").value("/api/v1/new"));
    }

    @Test
    @DisplayName("Should delete permission successfully")
    @WithMockUser(roles = "ADMIN")
    void deletePermission_existingId_returnsOk() throws Exception {
        Permission p = new Permission();
        p.setName("To Delete");
        p.setApiPath("/api/v1/delete");
        p.setMethod("DELETE");
        p.setModule("Delete");
        p = permissionRepository.save(p);

        mockMvc.perform(delete("/api/v1/permissions/" + p.getId()))
                .andExpect(status().isOk());

        mockMvc.perform(get("/api/v1/permissions/" + p.getId()))
                .andExpect(status().isNotFound());
    }
}
