package com.phatle.wolf_calie.feature.category;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    boolean existsBySlug(String slug);

    boolean existsByParentCategory_Id(Long parentId);
    
    Optional<Category> findBySlug(String slug);
}
