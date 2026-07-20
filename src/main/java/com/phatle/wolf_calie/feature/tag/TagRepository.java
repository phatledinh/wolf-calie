package com.phatle.wolf_calie.feature.tag;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {
    boolean existsBySlug(String slug);
    Optional<Tag> findBySlug(String slug);
}
