package com.phatle.wolf_calie.feature.collection;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class CollectionControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CollectionRepository collectionRepository;

    @AfterEach
    void tearDown() {
        collectionRepository.deleteAll();
    }

    @Test
    @WithMockUser(authorities = "ADMIN")
    void createCollection_ValidRequest_ReturnsCreated() throws Exception {
        mockMvc.perform(post("/api/v1/collections")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {"name":"Winter Collection","slug":"winter-collection"}
                        """))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.statusCode", is(201)))
                .andExpect(jsonPath("$.data.id", notNullValue()))
                .andExpect(jsonPath("$.data.name", is("Winter Collection")));
    }

    @Test
    @WithMockUser(authorities = "ADMIN")
    void updateCollection_ValidRequest_ReturnsOk() throws Exception {
        Collection collection = new Collection();
        collection.setName("Old Name");
        collection.setSlug("old-name");
        collection = collectionRepository.save(collection);

        mockMvc.perform(put("/api/v1/collections/{id}", collection.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {"name":"New Name","slug":"new-name"}
                        """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.statusCode", is(200)))
                .andExpect(jsonPath("$.data.name", is("New Name")));
    }

    @Test
    void getCollection_PublicAccess_ReturnsOk() throws Exception {
        Collection collection = new Collection();
        collection.setName("Public Collection");
        collection.setSlug("public-collection");
        collection = collectionRepository.save(collection);

        mockMvc.perform(get("/api/v1/collections/{id}", collection.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.statusCode", is(200)))
                .andExpect(jsonPath("$.data.name", is("Public Collection")));
    }
}
