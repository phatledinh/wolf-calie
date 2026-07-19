package com.phatle.wolf_calie.feature.category;

import com.phatle.wolf_calie.feature.category.dto.CategoryResponse;
import com.phatle.wolf_calie.feature.category.dto.CreateCategoryRequest;
import com.phatle.wolf_calie.feature.category.dto.UpdateCategoryRequest;

import java.util.List;

public interface CategoryService {
    CategoryResponse createCategory(CreateCategoryRequest request);
    CategoryResponse getCategoryById(long id);
    CategoryResponse updateCategory(long id, UpdateCategoryRequest request);
    void deleteCategory(long id);
    List<CategoryResponse> getAllCategories();
}
