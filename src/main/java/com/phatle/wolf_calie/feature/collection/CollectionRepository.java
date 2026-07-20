package com.phatle.wolf_calie.feature.collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CollectionRepository extends JpaRepository<Collection, Long> {

    boolean existsBySlug(String slug);

    boolean existsBySlugAndIdNot(String slug, long id);
}
