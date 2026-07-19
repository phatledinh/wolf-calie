package com.phatle.wolf_calie.feature.category.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UpdateCategoryRequest(
        Long parentId,
        
        @NotBlank(message = "Name is required")
        @Size(max = 255, message = "Name must be at most 255 characters")
        String name,
        
        String description,
        
        @Size(max = 255, message = "Image URL must be at most 255 characters")
        String imageUrl,
        
        @NotNull(message = "Sort order is required")
        Integer sortOrder,
        
        @NotNull(message = "isActive is required")
        Boolean isActive
) {}
