package com.phatle.wolf_calie.feature.color;

import tools.jackson.databind.ObjectMapper;
import com.phatle.wolf_calie.feature.color.dto.CreateColorRequest;
import com.phatle.wolf_calie.feature.color.dto.UpdateColorRequest;
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
class ColorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ColorRepository colorRepository;

    @Test
    @DisplayName("Should return 200 and list of colors")
    void getAllColors_returns200() throws Exception {
        mockMvc.perform(get("/api/v1/colors"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.statusCode").value(200))
                .andExpect(jsonPath("$.data").isArray());
    }

    @Test
    @DisplayName("Should create color successfully")
    @WithMockUser(roles = "ADMIN")
    void createColor_validRequest_returns201() throws Exception {
        CreateColorRequest request = new CreateColorRequest("Red", "#FF0000", 1, true);

        mockMvc.perform(post("/api/v1/colors")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.statusCode").value(201))
                .andExpect(jsonPath("$.data.name").value("Red"))
                .andExpect(jsonPath("$.data.hexCode").value("#FF0000"));
    }

    @Test
    @DisplayName("Should return 400 when name is missing")
    @WithMockUser(roles = "ADMIN")
    void createColor_missingName_returns400() throws Exception {
        CreateColorRequest request = new CreateColorRequest("", "#FF0000", 1, true);

        mockMvc.perform(post("/api/v1/colors")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.statusCode").value(400))
                .andExpect(jsonPath("$.details").exists());
    }

    @Test
    @DisplayName("Should get color by id")
    @WithMockUser(roles = "ADMIN")
    void getColorById_found_returns200() throws Exception {
        Color color = new Color();
        color.setName("Found Color");
        color.setHexCode("#123456");
        color.setSortOrder(1);
        color.setActive(true);
        color = colorRepository.save(color);

        mockMvc.perform(get("/api/v1/colors/" + color.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.statusCode").value(200))
                .andExpect(jsonPath("$.data.name").value("Found Color"));
    }

    @Test
    @DisplayName("Should return 404 when color not found")
    void getColorById_notFound_returns404() throws Exception {
        mockMvc.perform(get("/api/v1/colors/9999"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.statusCode").value(404));
    }

    @Test
    @DisplayName("Should update color successfully")
    @WithMockUser(roles = "ADMIN")
    void updateColor_validRequest_returns200() throws Exception {
        Color color = new Color();
        color.setName("Update Color");
        color.setHexCode("#111111");
        color.setSortOrder(1);
        color.setActive(true);
        color = colorRepository.save(color);

        UpdateColorRequest request = new UpdateColorRequest("Updated Name", "#222222", 2, false);

        mockMvc.perform(put("/api/v1/colors/" + color.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.statusCode").value(200))
                .andExpect(jsonPath("$.data.name").value("Updated Name"))
                .andExpect(jsonPath("$.data.hexCode").value("#222222"));
    }

    @Test
    @DisplayName("Should delete color successfully")
    @WithMockUser(roles = "ADMIN")
    void deleteColor_validId_returns200() throws Exception {
        Color color = new Color();
        color.setName("Delete Color");
        color.setHexCode("#333333");
        color.setSortOrder(1);
        color.setActive(true);
        color = colorRepository.save(color);

        mockMvc.perform(delete("/api/v1/colors/" + color.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.statusCode").value(200));
    }
}
