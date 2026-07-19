package com.phatle.wolf_calie.feature.category;

import tools.jackson.databind.ObjectMapper;
import com.phatle.wolf_calie.feature.category.dto.CreateCategoryRequest;
import com.phatle.wolf_calie.feature.category.dto.UpdateCategoryRequest;
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
class CategoryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    @DisplayName("Should return 200 and list of categories")
    void getAllCategories_returns200() throws Exception {
        mockMvc.perform(get("/api/v1/categories"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.statusCode").value(200))
                .andExpect(jsonPath("$.data").isArray());
    }

    @Test
    @DisplayName("Should create category successfully")
    @WithMockUser(roles = "ADMIN")
    void createCategory_validRequest_returns201() throws Exception {
        CreateCategoryRequest request = new CreateCategoryRequest(null, "Test Category", "Desc", "url", 1, true);

        mockMvc.perform(post("/api/v1/categories")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.statusCode").value(201))
                .andExpect(jsonPath("$.data.name").value("Test Category"))
                .andExpect(jsonPath("$.data.slug").value("test-category"));
    }

    @Test
    @DisplayName("Should return 400 when name is missing")
    @WithMockUser(roles = "ADMIN")
    void createCategory_missingName_returns400() throws Exception {
        CreateCategoryRequest request = new CreateCategoryRequest(null, "", "Desc", "url", 1, true);

        mockMvc.perform(post("/api/v1/categories")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.statusCode").value(400))
                .andExpect(jsonPath("$.details").exists());
    }

    @Test
    @DisplayName("Should get category by id")
    @WithMockUser(roles = "ADMIN")
    void getCategoryById_found_returns200() throws Exception {
        Category category = new Category();
        category.setName("Found Category");
        category.setSlug("found-category");
        category.setSortOrder(1);
        category.setActive(true);
        category = categoryRepository.save(category);

        mockMvc.perform(get("/api/v1/categories/" + category.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.statusCode").value(200))
                .andExpect(jsonPath("$.data.name").value("Found Category"));
    }
    
    @Test
    @DisplayName("Should return 404 when category not found")
    void getCategoryById_notFound_returns404() throws Exception {
        mockMvc.perform(get("/api/v1/categories/9999"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.statusCode").value(404));
    }

    @Test
    @DisplayName("Should update category successfully")
    @WithMockUser(roles = "ADMIN")
    void updateCategory_validRequest_returns200() throws Exception {
        Category category = new Category();
        category.setName("Update Category");
        category.setSlug("update-category");
        category.setSortOrder(1);
        category.setActive(true);
        category = categoryRepository.save(category);

        UpdateCategoryRequest request = new UpdateCategoryRequest(null, "Updated Name", "Desc", "url", 2, false);

        mockMvc.perform(put("/api/v1/categories/" + category.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.statusCode").value(200))
                .andExpect(jsonPath("$.data.name").value("Updated Name"))
                .andExpect(jsonPath("$.data.slug").value("updated-name"));
    }

    @Test
    @DisplayName("Should delete category successfully")
    @WithMockUser(roles = "ADMIN")
    void deleteCategory_validId_returns200() throws Exception {
        Category category = new Category();
        category.setName("Delete Category");
        category.setSlug("delete-category");
        category.setSortOrder(1);
        category.setActive(true);
        category = categoryRepository.save(category);

        mockMvc.perform(delete("/api/v1/categories/" + category.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.statusCode").value(200));
    }
}
