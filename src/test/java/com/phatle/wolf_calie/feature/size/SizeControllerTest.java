package com.phatle.wolf_calie.feature.size;

import tools.jackson.databind.ObjectMapper;
import com.phatle.wolf_calie.feature.size.dto.CreateSizeRequest;
import com.phatle.wolf_calie.feature.size.dto.UpdateSizeRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class SizeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private SizeRepository sizeRepository;

    @Test
    @DisplayName("Should return 200 and list of sizes")
    void getAllSizes_returns200() throws Exception {
        mockMvc.perform(get("/api/v1/sizes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.statusCode").value(200))
                .andExpect(jsonPath("$.data").isArray());
    }

    @Test
    @DisplayName("Should create size successfully")
    @WithMockUser(roles = "ADMIN")
    void createSize_validRequest_returns201() throws Exception {
        CreateSizeRequest request = new CreateSizeRequest("XL", 1);

        mockMvc.perform(post("/api/v1/sizes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.statusCode").value(201))
                .andExpect(jsonPath("$.data.name").value("XL"));
    }

    @Test
    @DisplayName("Should return 400 when name is missing")
    @WithMockUser(roles = "ADMIN")
    void createSize_missingName_returns400() throws Exception {
        CreateSizeRequest request = new CreateSizeRequest("", 1);

        mockMvc.perform(post("/api/v1/sizes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.statusCode").value(400))
                .andExpect(jsonPath("$.details").exists());
    }

    @Test
    @DisplayName("Should get size by id")
    @WithMockUser(roles = "ADMIN")
    void getSizeById_found_returns200() throws Exception {
        Size size = new Size();
        size.setName("Found Size");
        size.setSortOrder(1);
        size = sizeRepository.save(size);

        mockMvc.perform(get("/api/v1/sizes/" + size.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.statusCode").value(200))
                .andExpect(jsonPath("$.data.name").value("Found Size"));
    }

    @Test
    @DisplayName("Should return 404 when size not found")
    void getSizeById_notFound_returns404() throws Exception {
        mockMvc.perform(get("/api/v1/sizes/9999"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.statusCode").value(404));
    }

    @Test
    @DisplayName("Should update size successfully")
    @WithMockUser(roles = "ADMIN")
    void updateSize_validRequest_returns200() throws Exception {
        Size size = new Size();
        size.setName("Update Size");
        size.setSortOrder(1);
        size = sizeRepository.save(size);

        UpdateSizeRequest request = new UpdateSizeRequest("Updated Name", 2);

        mockMvc.perform(put("/api/v1/sizes/" + size.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.statusCode").value(200))
                .andExpect(jsonPath("$.data.name").value("Updated Name"))
                .andExpect(jsonPath("$.data.sortOrder").value(2));
    }

    @Test
    @DisplayName("Should delete size successfully")
    @WithMockUser(roles = "ADMIN")
    void deleteSize_validId_returns200() throws Exception {
        Size size = new Size();
        size.setName("Delete Size");
        size.setSortOrder(1);
        size = sizeRepository.save(size);

        mockMvc.perform(delete("/api/v1/sizes/" + size.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.statusCode").value(200));
    }
}
