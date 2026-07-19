package com.phatle.wolf_calie.feature.category;

import com.phatle.wolf_calie.exception.InvalidRequestException;
import com.phatle.wolf_calie.exception.ResourceNotFoundException;
import com.phatle.wolf_calie.feature.category.dto.CategoryResponse;
import com.phatle.wolf_calie.feature.category.dto.CreateCategoryRequest;
import com.phatle.wolf_calie.feature.category.dto.UpdateCategoryRequest;
import com.phatle.wolf_calie.util.SlugUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    @Transactional
    public CategoryResponse createCategory(CreateCategoryRequest request) {
        Category category = new Category();
        
        category.setName(request.name());
        category.setSlug(generateUniqueSlug(request.name(), null));
        category.setDescription(request.description());
        category.setImageUrl(request.imageUrl());
        category.setSortOrder(request.sortOrder());
        category.setActive(request.isActive() != null ? request.isActive() : true);
        
        if (request.parentId() != null) {
            Category parent = categoryRepository.findById(request.parentId())
                    .orElseThrow(() -> new ResourceNotFoundException("Category", "id", request.parentId()));
            category.setParentCategory(parent);
        }

        return CategoryResponse.fromEntity(categoryRepository.save(category));
    }

    @Override
    public CategoryResponse getCategoryById(long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "id", id));
        return CategoryResponse.fromEntity(category);
    }

    @Override
    @Transactional
    public CategoryResponse updateCategory(long id, UpdateCategoryRequest request) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "id", id));

        // If name changes, we update the slug
        if (!category.getName().equals(request.name())) {
            category.setName(request.name());
            category.setSlug(generateUniqueSlug(request.name(), id));
        }
        
        category.setDescription(request.description());
        category.setImageUrl(request.imageUrl());
        category.setSortOrder(request.sortOrder());
        category.setActive(request.isActive());

        if (request.parentId() != null) {
            if (request.parentId().equals(id)) {
                throw new InvalidRequestException("Category cannot be its own parent");
            }
            Category parent = categoryRepository.findById(request.parentId())
                    .orElseThrow(() -> new ResourceNotFoundException("Category", "id", request.parentId()));
            category.setParentCategory(parent);
        } else {
            category.setParentCategory(null);
        }

        return CategoryResponse.fromEntity(categoryRepository.save(category));
    }

    @Override
    @Transactional
    public void deleteCategory(long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "id", id));

        if (categoryRepository.existsByParentCategory_Id(id)) {
            throw new InvalidRequestException("Cannot delete category because it has child categories");
        }

        category.setDeletedAt(Instant.now());
        categoryRepository.save(category);
    }

    @Override
    public List<CategoryResponse> getAllCategories() {
        return categoryRepository.findAll().stream()
                .map(CategoryResponse::fromEntity)
                .collect(Collectors.toList());
    }
    
    private String generateUniqueSlug(String name, Long excludeId) {
        String baseSlug = SlugUtil.toSlug(name);
        String uniqueSlug = baseSlug;
        int counter = 1;
        
        while (true) {
            Category existing = categoryRepository.findBySlug(uniqueSlug).orElse(null);
            if (existing == null) {
                return uniqueSlug;
            }
            if (excludeId != null && existing.getId() == excludeId) {
                return uniqueSlug;
            }
            uniqueSlug = baseSlug + "-" + counter;
            counter++;
        }
    }
}
