package com.phatle.wolf_calie.feature.useraddress;

import com.phatle.wolf_calie.feature.user.User;
import com.phatle.wolf_calie.feature.user.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class UserAddressControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserAddressRepository userAddressRepository;

    private User savedUser;

    @BeforeEach
    void setUp() {
        userAddressRepository.deleteAll();
        userRepository.deleteAll();

        User user = new User();
        user.setEmail("address.test@example.com");
        user.setPassword("password");
        user.setName("Test Address User");
        savedUser = userRepository.save(user);
    }

    @Test
    @DisplayName("Should return empty list for user with no addresses")
    void getUserAddresses_noAddresses_returnsEmptyList() throws Exception {
        mockMvc.perform(get("/api/v1/users/me/addresses")
                        .with(jwt().jwt(j -> j.claim("userId", savedUser.getId()))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.statusCode").value(200))
                .andExpect(jsonPath("$.data").isEmpty());
    }

    @Test
    @DisplayName("Should create user address successfully")
    void createUserAddress_validRequest_createsAddress() throws Exception {
        String requestBody = """
                {
                  "receiverName": "John Doe",
                  "receiverPhone": "0123456789",
                  "provinceId": "79",
                  "districtId": "760",
                  "wardId": "26734",
                  "street": "123 Main St",
                  "isDefault": true
                }
                """;

        mockMvc.perform(post("/api/v1/users/me/addresses")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody)
                        .with(jwt().jwt(j -> j.claim("userId", savedUser.getId()))))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.statusCode").value(201))
                .andExpect(jsonPath("$.data.id").isNumber())
                .andExpect(jsonPath("$.data.receiverName").value("John Doe"))
                .andExpect(jsonPath("$.data.isDefault").value(true));
    }

    @Test
    @DisplayName("Should update user address successfully")
    void updateUserAddress_validRequest_updatesAddress() throws Exception {
        UserAddress address = new UserAddress();
        address.setUser(savedUser);
        address.setReceiverName("Old Name");
        address.setReceiverPhone("0111111111");
        address.setProvinceId("1");
        address.setDistrictId("1");
        address.setWardId("1");
        address.setStreet("Old St");
        address.setDefault(true);
        UserAddress savedAddress = userAddressRepository.save(address);

        String requestBody = """
                {
                  "receiverName": "New Name",
                  "receiverPhone": "0999999999",
                  "provinceId": "2",
                  "districtId": "2",
                  "wardId": "2",
                  "street": "New St",
                  "isDefault": false
                }
                """;

        mockMvc.perform(put("/api/v1/users/me/addresses/{id}", savedAddress.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody)
                        .with(jwt().jwt(j -> j.claim("userId", savedUser.getId()))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.statusCode").value(200))
                .andExpect(jsonPath("$.data.receiverName").value("New Name"))
                .andExpect(jsonPath("$.data.isDefault").value(false));
    }

    @Test
    @DisplayName("Should soft delete user address successfully")
    void deleteUserAddress_existingId_softDeletes() throws Exception {
        UserAddress address = new UserAddress();
        address.setUser(savedUser);
        address.setReceiverName("Old Name");
        address.setReceiverPhone("0111111111");
        address.setProvinceId("1");
        address.setDistrictId("1");
        address.setWardId("1");
        address.setStreet("Old St");
        address.setDefault(true);
        UserAddress savedAddress = userAddressRepository.save(address);

        mockMvc.perform(delete("/api/v1/users/me/addresses/{id}", savedAddress.getId())
                        .with(jwt().jwt(j -> j.claim("userId", savedUser.getId()))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.statusCode").value(200));

        // Verify it was soft deleted (no longer returned in list)
        mockMvc.perform(get("/api/v1/users/me/addresses")
                        .with(jwt().jwt(j -> j.claim("userId", savedUser.getId()))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").isEmpty());
    }

    @Test
    @DisplayName("Should return 400 validation error for missing fields")
    void createUserAddress_missingFields_returns400() throws Exception {
        String requestBody = """
                {
                  "receiverName": "John Doe",
                  "street": "123 Main St"
                }
                """; // missing phone, province, district, ward, isDefault

        mockMvc.perform(post("/api/v1/users/me/addresses")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody)
                        .with(jwt().jwt(j -> j.claim("userId", savedUser.getId()))))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.statusCode").value(400));
    }
}
