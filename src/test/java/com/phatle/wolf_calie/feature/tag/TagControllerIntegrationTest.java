package com.phatle.wolf_calie.feature.tag;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import org.springframework.security.test.context.support.WithMockUser;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class TagControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TagRepository tagRepository;

    @Test
    @WithMockUser(roles = "ADMIN")
    @DisplayName("Should return 201 with created tag")
    void createTag_valid_returns201() throws Exception {
        mockMvc.perform(post("/api/v1/tags")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"name":"Summer Collection","slug":"summer-collection"}
                                """))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.statusCode").value(201))
                .andExpect(jsonPath("$.data.name").value("Summer Collection"))
                .andExpect(jsonPath("$.data.slug").value("summer-collection"));
        
        tagRepository.deleteAll();
    }
    
    @Test
    @WithMockUser
    @DisplayName("Should return list of tags with 200")
    void getAllTags_returnsList() throws Exception {
        Tag tag = new Tag();
        tag.setName("Winter");
        tag.setSlug("winter");
        tagRepository.save(tag);

        mockMvc.perform(get("/api/v1/tags"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.statusCode").value(200))
                .andExpect(jsonPath("$.data[0].name").value("Winter"));
                
        tagRepository.deleteAll();
    }
}
