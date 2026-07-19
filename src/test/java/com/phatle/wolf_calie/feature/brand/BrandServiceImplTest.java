package com.phatle.wolf_calie.feature.brand;

import com.phatle.wolf_calie.exception.ResourceNotFoundException;
import com.phatle.wolf_calie.feature.brand.dto.BrandResponse;
import com.phatle.wolf_calie.feature.brand.dto.CreateBrandRequest;
import com.phatle.wolf_calie.feature.brand.dto.UpdateBrandRequest;
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
class BrandServiceImplTest {

    @Mock
    private BrandRepository brandRepository;

    @InjectMocks
    private BrandServiceImpl brandService;

    @Test
    @DisplayName("Should return all brands")
    void getAllBrands_returnsBrandResponses() {
        Brand b1 = new Brand();
        b1.setId(1L);
        b1.setName("Nike");
        b1.setSlug("nike");

        when(brandRepository.findAll()).thenReturn(List.of(b1));

        List<BrandResponse> result = brandService.getAllBrands();

        assertEquals(1, result.size());
        assertEquals("Nike", result.get(0).name());
    }

    @Test
    @DisplayName("Should return brand by id when found")
    void getBrandById_found_returnsBrandResponse() {
        Brand brand = new Brand();
        brand.setId(1L);
        brand.setName("Nike");

        when(brandRepository.findById(1L)).thenReturn(Optional.of(brand));

        BrandResponse result = brandService.getBrandById(1L);

        assertNotNull(result);
        assertEquals("Nike", result.name());
    }

    @Test
    @DisplayName("Should throw ResourceNotFoundException when get brand by id not found")
    void getBrandById_notFound_throwsException() {
        when(brandRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> brandService.getBrandById(1L));
    }

    @Test
    @DisplayName("Should create brand successfully")
    void createBrand_validRequest_returnsBrandResponse() {
        CreateBrandRequest request = new CreateBrandRequest("Adidas", "adidas.com", "Germany", "Desc", "url", true);

        when(brandRepository.findBySlug("adidas")).thenReturn(Optional.empty());
        when(brandRepository.save(any(Brand.class))).thenAnswer(invocation -> {
            Brand b = invocation.getArgument(0);
            b.setId(1L);
            return b;
        });

        BrandResponse result = brandService.createBrand(request);

        assertNotNull(result);
        assertEquals(1L, result.id());
        assertEquals("Adidas", result.name());
        assertEquals("adidas", result.slug());
    }

    @Test
    @DisplayName("Should create brand with unique slug when slug already exists")
    void createBrand_duplicateSlug_generatesUniqueSlug() {
        CreateBrandRequest request = new CreateBrandRequest("Nike", "nike.com", "USA", "Desc", "url", true);

        Brand existingBrand = new Brand();
        existingBrand.setId(2L);
        existingBrand.setSlug("nike");

        when(brandRepository.findBySlug("nike")).thenReturn(Optional.of(existingBrand));
        when(brandRepository.findBySlug("nike-1")).thenReturn(Optional.empty());
        when(brandRepository.save(any(Brand.class))).thenAnswer(invocation -> {
            Brand b = invocation.getArgument(0);
            b.setId(1L);
            return b;
        });

        BrandResponse result = brandService.createBrand(request);

        assertEquals("nike-1", result.slug());
    }

    @Test
    @DisplayName("Should update brand successfully")
    void updateBrand_validRequest_returnsBrandResponse() {
        UpdateBrandRequest request = new UpdateBrandRequest("New Nike", "newnike.com", "USA", "Desc", "url", false);

        Brand existing = new Brand();
        existing.setId(1L);
        existing.setName("Old Nike");
        existing.setSlug("old-nike");

        when(brandRepository.findById(1L)).thenReturn(Optional.of(existing));
        when(brandRepository.findBySlug("new-nike")).thenReturn(Optional.empty());
        when(brandRepository.save(any(Brand.class))).thenAnswer(i -> i.getArgument(0));

        BrandResponse result = brandService.updateBrand(1L, request);

        assertEquals("New Nike", result.name());
        assertEquals("new-nike", result.slug());
        assertEquals("newnike.com", result.website());
        assertFalse(result.isActive());
    }

    @Test
    @DisplayName("Should soft delete brand successfully")
    void deleteBrand_validId_softDeletes() {
        Brand existing = new Brand();
        existing.setId(1L);

        when(brandRepository.findById(1L)).thenReturn(Optional.of(existing));

        brandService.deleteBrand(1L);

        ArgumentCaptor<Brand> captor = ArgumentCaptor.forClass(Brand.class);
        verify(brandRepository).save(captor.capture());

        assertNotNull(captor.getValue().getDeletedAt());
    }
}
