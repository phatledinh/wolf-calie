package com.phatle.wolf_calie.feature.brand;

import com.phatle.wolf_calie.exception.ResourceNotFoundException;
import com.phatle.wolf_calie.feature.brand.dto.BrandResponse;
import com.phatle.wolf_calie.feature.brand.dto.CreateBrandRequest;
import com.phatle.wolf_calie.feature.brand.dto.UpdateBrandRequest;
import com.phatle.wolf_calie.util.SlugUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BrandServiceImpl implements BrandService {

    private final BrandRepository brandRepository;

    public BrandServiceImpl(BrandRepository brandRepository) {
        this.brandRepository = brandRepository;
    }

    @Override
    @Transactional
    public BrandResponse createBrand(CreateBrandRequest request) {
        Brand brand = new Brand();
        brand.setName(request.name());
        brand.setSlug(generateUniqueSlug(request.name(), null));
        brand.setWebsite(request.website());
        brand.setCountry(request.country());
        brand.setDescription(request.description());
        brand.setLogoUrl(request.logoUrl());
        brand.setActive(request.isActive() != null ? request.isActive() : true);

        return BrandResponse.fromEntity(brandRepository.save(brand));
    }

    @Override
    public BrandResponse getBrandById(long id) {
        Brand brand = brandRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Brand", "id", id));
        return BrandResponse.fromEntity(brand);
    }

    @Override
    @Transactional
    public BrandResponse updateBrand(long id, UpdateBrandRequest request) {
        Brand brand = brandRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Brand", "id", id));

        // If name changes, we update the slug
        if (!brand.getName().equals(request.name())) {
            brand.setName(request.name());
            brand.setSlug(generateUniqueSlug(request.name(), id));
        }

        brand.setWebsite(request.website());
        brand.setCountry(request.country());
        brand.setDescription(request.description());
        brand.setLogoUrl(request.logoUrl());
        
        if (request.isActive() != null) {
            brand.setActive(request.isActive());
        }

        return BrandResponse.fromEntity(brandRepository.save(brand));
    }

    @Override
    @Transactional
    public void deleteBrand(long id) {
        Brand brand = brandRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Brand", "id", id));

        brand.setDeletedAt(Instant.now());
        brandRepository.save(brand);
    }

    @Override
    public List<BrandResponse> getAllBrands() {
        return brandRepository.findAll().stream()
                .map(BrandResponse::fromEntity)
                .collect(Collectors.toList());
    }

    private String generateUniqueSlug(String name, Long excludeId) {
        String baseSlug = SlugUtil.toSlug(name);
        String uniqueSlug = baseSlug;
        int counter = 1;
        
        while (true) {
            Brand existing = brandRepository.findBySlug(uniqueSlug).orElse(null);
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
