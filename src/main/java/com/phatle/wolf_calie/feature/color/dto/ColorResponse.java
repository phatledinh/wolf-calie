package com.phatle.wolf_calie.feature.color.dto;

import com.phatle.wolf_calie.feature.color.Color;

import java.time.Instant;

public record ColorResponse(
        long id,
        String name,
        String hexCode,
        int sortOrder,
        boolean isActive,
        Instant createdAt,
        Instant updatedAt
) {
    public static ColorResponse fromEntity(Color color) {
        return new ColorResponse(
                color.getId(),
                color.getName(),
                color.getHexCode(),
                color.getSortOrder(),
                color.isActive(),
                color.getCreatedAt(),
                color.getUpdatedAt()
        );
    }
}
