package com.phatle.wolf_calie.feature.tag.dto;

import com.phatle.wolf_calie.feature.tag.Tag;

import java.time.Instant;

public record TagResponse(
        long id,
        String name,
        String slug,
        Instant createdAt,
        Instant updatedAt
) {
    public static TagResponse fromEntity(Tag tag) {
        return new TagResponse(
                tag.getId(),
                tag.getName(),
                tag.getSlug(),
                tag.getCreatedAt(),
                tag.getUpdatedAt()
        );
    }
}
