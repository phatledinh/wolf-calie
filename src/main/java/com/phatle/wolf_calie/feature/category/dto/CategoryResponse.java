package com.phatle.wolf_calie.feature.category.dto;

import com.phatle.wolf_calie.feature.category.Category;

import java.time.Instant;

public record CategoryResponse(
        long id,
        Long parentId,
        String name,
        String slug,
        String description,
        String imageUrl,
        int sortOrder,
        boolean isActive,
        Instant createdAt
) {
    public static CategoryResponse fromEntity(Category category) {
        return new CategoryResponse(
                category.getId(),
                category.getParentCategory() != null ? category.getParentCategory().getId() : null,
                category.getName(),
                category.getSlug(),
                category.getDescription(),
                category.getImageUrl(),
                category.getSortOrder(),
                category.isActive(),
                category.getCreatedAt()
        );
    }
}
