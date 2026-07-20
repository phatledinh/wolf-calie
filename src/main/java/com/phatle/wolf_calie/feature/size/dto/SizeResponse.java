package com.phatle.wolf_calie.feature.size.dto;

import com.phatle.wolf_calie.feature.size.Size;

import java.time.Instant;

public record SizeResponse(
        long id,
        String name,
        int sortOrder,
        Instant createdAt,
        Instant updatedAt
) {
    public static SizeResponse fromEntity(Size size) {
        return new SizeResponse(
                size.getId(),
                size.getName(),
                size.getSortOrder(),
                size.getCreatedAt(),
                size.getUpdatedAt()
        );
    }
}
