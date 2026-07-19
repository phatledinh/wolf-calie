package com.phatle.wolf_calie.feature.category;

import com.phatle.wolf_calie.exception.InvalidRequestException;
import com.phatle.wolf_calie.exception.ResourceNotFoundException;
import com.phatle.wolf_calie.feature.category.dto.CategoryResponse;
import com.phatle.wolf_calie.feature.category.dto.CreateCategoryRequest;
import com.phatle.wolf_calie.feature.category.dto.UpdateCategoryRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CategoryServiceImplTest {

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private CategoryServiceImpl categoryService;

    @Test
    @DisplayName("Should return all categories")
    void getAllCategories_returnsCategoryResponses() {
        Category c1 = new Category();
        c1.setId(1L);
        c1.setName("Men");
        c1.setSlug("men");
        
        when(categoryRepository.findAll()).thenReturn(List.of(c1));

        List<CategoryResponse> result = categoryService.getAllCategories();

        assertEquals(1, result.size());
        assertEquals("Men", result.get(0).name());
    }

    @Test
    @DisplayName("Should return category by id when found")
    void getCategoryById_found_returnsCategoryResponse() {
        Category category = new Category();
        category.setId(1L);
        category.setName("Men");
        
        when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));

        CategoryResponse result = categoryService.getCategoryById(1L);

        assertNotNull(result);
        assertEquals("Men", result.name());
    }

    @Test
    @DisplayName("Should throw ResourceNotFoundException when get category by id not found")
    void getCategoryById_notFound_throwsException() {
        when(categoryRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> categoryService.getCategoryById(1L));
    }

    @Test
    @DisplayName("Should create category successfully")
    void createCategory_validRequest_returnsCategoryResponse() {
        CreateCategoryRequest request = new CreateCategoryRequest(null, "Men Clothing", "Desc", "url", 1, true);
        
        when(categoryRepository.findBySlug("men-clothing")).thenReturn(Optional.empty());
        when(categoryRepository.save(any(Category.class))).thenAnswer(invocation -> {
            Category c = invocation.getArgument(0);
            c.setId(1L);
            return c;
        });

        CategoryResponse result = categoryService.createCategory(request);

        assertNotNull(result);
        assertEquals(1L, result.id());
        assertEquals("Men Clothing", result.name());
        assertEquals("men-clothing", result.slug());
    }
    
    @Test
    @DisplayName("Should create category with unique slug when slug already exists")
    void createCategory_duplicateSlug_generatesUniqueSlug() {
        CreateCategoryRequest request = new CreateCategoryRequest(null, "Men", "Desc", "url", 1, true);
        
        Category existingCategory = new Category();
        existingCategory.setId(2L);
        existingCategory.setSlug("men");
        
        when(categoryRepository.findBySlug("men")).thenReturn(Optional.of(existingCategory));
        when(categoryRepository.findBySlug("men-1")).thenReturn(Optional.empty());
        when(categoryRepository.save(any(Category.class))).thenAnswer(invocation -> {
            Category c = invocation.getArgument(0);
            c.setId(1L);
            return c;
        });

        CategoryResponse result = categoryService.createCategory(request);

        assertEquals("men-1", result.slug());
    }

    @Test
    @DisplayName("Should create category with parent successfully")
    void createCategory_withParent_returnsCategoryResponse() {
        CreateCategoryRequest request = new CreateCategoryRequest(2L, "Shirts", "Desc", "url", 1, true);
        
        Category parent = new Category();
        parent.setId(2L);
        
        when(categoryRepository.findById(2L)).thenReturn(Optional.of(parent));
        when(categoryRepository.findBySlug("shirts")).thenReturn(Optional.empty());
        when(categoryRepository.save(any(Category.class))).thenAnswer(invocation -> {
            Category c = invocation.getArgument(0);
            c.setId(1L);
            return c;
        });

        CategoryResponse result = categoryService.createCategory(request);

        assertEquals(2L, result.parentId());
    }
    
    @Test
    @DisplayName("Should throw ResourceNotFoundException when parent category not found on create")
    void createCategory_parentNotFound_throwsException() {
        CreateCategoryRequest request = new CreateCategoryRequest(2L, "Shirts", "Desc", "url", 1, true);
        when(categoryRepository.findById(2L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> categoryService.createCategory(request));
    }

    @Test
    @DisplayName("Should update category successfully")
    void updateCategory_validRequest_returnsCategoryResponse() {
        UpdateCategoryRequest request = new UpdateCategoryRequest(null, "New Name", "Desc", "url", 2, false);
        
        Category existing = new Category();
        existing.setId(1L);
        existing.setName("Old Name");
        existing.setSlug("old-name");
        
        when(categoryRepository.findById(1L)).thenReturn(Optional.of(existing));
        when(categoryRepository.findBySlug("new-name")).thenReturn(Optional.empty());
        when(categoryRepository.save(any(Category.class))).thenAnswer(i -> i.getArgument(0));

        CategoryResponse result = categoryService.updateCategory(1L, request);

        assertEquals("New Name", result.name());
        assertEquals("new-name", result.slug());
        assertEquals(2, result.sortOrder());
        assertFalse(result.isActive());
    }
    
    @Test
    @DisplayName("Should throw InvalidRequestException when category is its own parent on update")
    void updateCategory_ownParent_throwsException() {
        UpdateCategoryRequest request = new UpdateCategoryRequest(1L, "New Name", "Desc", "url", 2, false);
        
        Category existing = new Category();
        existing.setId(1L);
        
        when(categoryRepository.findById(1L)).thenReturn(Optional.of(existing));

        assertThrows(InvalidRequestException.class, () -> categoryService.updateCategory(1L, request));
    }

    @Test
    @DisplayName("Should soft delete category successfully")
    void deleteCategory_validId_softDeletes() {
        Category existing = new Category();
        existing.setId(1L);
        
        when(categoryRepository.findById(1L)).thenReturn(Optional.of(existing));
        when(categoryRepository.existsByParentCategory_Id(1L)).thenReturn(false);

        categoryService.deleteCategory(1L);

        ArgumentCaptor<Category> captor = ArgumentCaptor.forClass(Category.class);
        verify(categoryRepository).save(captor.capture());
        
        assertNotNull(captor.getValue().getDeletedAt());
    }

    @Test
    @DisplayName("Should throw InvalidRequestException when deleting category with children")
    void deleteCategory_withChildren_throwsException() {
        Category existing = new Category();
        existing.setId(1L);
        
        when(categoryRepository.findById(1L)).thenReturn(Optional.of(existing));
        when(categoryRepository.existsByParentCategory_Id(1L)).thenReturn(true);

        assertThrows(InvalidRequestException.class, () -> categoryService.deleteCategory(1L));
        verify(categoryRepository, never()).save(any());
    }
}
