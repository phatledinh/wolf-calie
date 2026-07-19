package com.phatle.wolf_calie.feature.brand;

import com.phatle.wolf_calie.feature.brand.dto.BrandResponse;
import com.phatle.wolf_calie.feature.brand.dto.CreateBrandRequest;
import com.phatle.wolf_calie.feature.brand.dto.UpdateBrandRequest;

import java.util.List;

public interface BrandService {
    BrandResponse createBrand(CreateBrandRequest request);
    BrandResponse getBrandById(long id);
    BrandResponse updateBrand(long id, UpdateBrandRequest request);
    void deleteBrand(long id);
    List<BrandResponse> getAllBrands();
}
