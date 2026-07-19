package com.phatle.wolf_calie.feature.brand;

import com.phatle.wolf_calie.dto.ApiResponse;
import com.phatle.wolf_calie.feature.brand.dto.BrandResponse;
import com.phatle.wolf_calie.feature.brand.dto.CreateBrandRequest;
import com.phatle.wolf_calie.feature.brand.dto.UpdateBrandRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/brands")
public class BrandController {

    private final BrandService brandService;

    public BrandController(BrandService brandService) {
        this.brandService = brandService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<BrandResponse>>> getAllBrands() {
        return ResponseEntity.ok(ApiResponse.success(brandService.getAllBrands()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<BrandResponse>> getBrandById(@PathVariable long id) {
        return ResponseEntity.ok(ApiResponse.success(brandService.getBrandById(id)));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<BrandResponse>> createBrand(
            @RequestBody @Valid CreateBrandRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.created(brandService.createBrand(request)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<BrandResponse>> updateBrand(
            @PathVariable long id,
            @RequestBody @Valid UpdateBrandRequest request) {
        return ResponseEntity.ok(ApiResponse.success(brandService.updateBrand(id, request)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteBrand(@PathVariable long id) {
        brandService.deleteBrand(id);
        return ResponseEntity.ok(ApiResponse.success(null));
    }
}
