package com.phatle.wolf_calie.feature.brand.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateBrandRequest(
        @NotBlank(message = "Brand name is required")
        @Size(max = 255, message = "Brand name must not exceed 255 characters")
        String name,

        @Size(max = 255, message = "Website URL must not exceed 255 characters")
        String website,

        @Size(max = 100, message = "Country must not exceed 100 characters")
        String country,

        String description,

        @Size(max = 255, message = "Logo URL must not exceed 255 characters")
        String logoUrl,

        Boolean isActive
) {}
