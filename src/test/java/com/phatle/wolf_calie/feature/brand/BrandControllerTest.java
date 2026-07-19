package com.phatle.wolf_calie.feature.brand;

import tools.jackson.databind.ObjectMapper;
import com.phatle.wolf_calie.feature.brand.dto.CreateBrandRequest;
import com.phatle.wolf_calie.feature.brand.dto.UpdateBrandRequest;
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
class BrandControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private BrandRepository brandRepository;

    @Test
    @DisplayName("Should return 200 and list of brands")
    void getAllBrands_returns200() throws Exception {
        mockMvc.perform(get("/api/v1/brands"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.statusCode").value(200))
                .andExpect(jsonPath("$.data").isArray());
    }

    @Test
    @DisplayName("Should create brand successfully")
    @WithMockUser(roles = "ADMIN")
    void createBrand_validRequest_returns201() throws Exception {
        CreateBrandRequest request = new CreateBrandRequest("Test Brand", "test.com", "US", "Desc", "url", true);

        mockMvc.perform(post("/api/v1/brands")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.statusCode").value(201))
                .andExpect(jsonPath("$.data.name").value("Test Brand"))
                .andExpect(jsonPath("$.data.slug").value("test-brand"));
    }

    @Test
    @DisplayName("Should return 400 when name is missing")
    @WithMockUser(roles = "ADMIN")
    void createBrand_missingName_returns400() throws Exception {
        CreateBrandRequest request = new CreateBrandRequest("", "test.com", "US", "Desc", "url", true);

        mockMvc.perform(post("/api/v1/brands")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.statusCode").value(400))
                .andExpect(jsonPath("$.details").exists());
    }

    @Test
    @DisplayName("Should get brand by id")
    @WithMockUser(roles = "ADMIN")
    void getBrandById_found_returns200() throws Exception {
        Brand brand = new Brand();
        brand.setName("Found Brand");
        brand.setSlug("found-brand");
        brand.setActive(true);
        brand = brandRepository.save(brand);

        mockMvc.perform(get("/api/v1/brands/" + brand.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.statusCode").value(200))
                .andExpect(jsonPath("$.data.name").value("Found Brand"));
    }

    @Test
    @DisplayName("Should return 404 when brand not found")
    void getBrandById_notFound_returns404() throws Exception {
        mockMvc.perform(get("/api/v1/brands/9999"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.statusCode").value(404));
    }

    @Test
    @DisplayName("Should update brand successfully")
    @WithMockUser(roles = "ADMIN")
    void updateBrand_validRequest_returns200() throws Exception {
        Brand brand = new Brand();
        brand.setName("Update Brand");
        brand.setSlug("update-brand");
        brand.setActive(true);
        brand = brandRepository.save(brand);

        UpdateBrandRequest request = new UpdateBrandRequest("Updated Name", "update.com", "UK", "Desc", "url", false);

        mockMvc.perform(put("/api/v1/brands/" + brand.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.statusCode").value(200))
                .andExpect(jsonPath("$.data.name").value("Updated Name"))
                .andExpect(jsonPath("$.data.slug").value("updated-name"));
    }

    @Test
    @DisplayName("Should delete brand successfully")
    @WithMockUser(roles = "ADMIN")
    void deleteBrand_validId_returns200() throws Exception {
        Brand brand = new Brand();
        brand.setName("Delete Brand");
        brand.setSlug("delete-brand");
        brand.setActive(true);
        brand = brandRepository.save(brand);

        mockMvc.perform(delete("/api/v1/brands/" + brand.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.statusCode").value(200));
    }
}
