package com.phatle.wolf_calie.feature.brand.dto;

import com.phatle.wolf_calie.feature.brand.Brand;

import java.time.Instant;

public record BrandResponse(
        long id,
        String name,
        String slug,
        String website,
        String country,
        String description,
        String logoUrl,
        boolean isActive,
        Instant createdAt,
        Instant updatedAt
) {
    public static BrandResponse fromEntity(Brand brand) {
        return new BrandResponse(
                brand.getId(),
                brand.getName(),
                brand.getSlug(),
                brand.getWebsite(),
                brand.getCountry(),
                brand.getDescription(),
                brand.getLogoUrl(),
                brand.isActive(),
                brand.getCreatedAt(),
                brand.getUpdatedAt()
        );
    }
}
