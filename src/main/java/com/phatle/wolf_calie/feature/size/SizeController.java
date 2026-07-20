package com.phatle.wolf_calie.feature.size;

import com.phatle.wolf_calie.dto.ApiResponse;
import com.phatle.wolf_calie.feature.size.dto.SizeResponse;
import com.phatle.wolf_calie.feature.size.dto.CreateSizeRequest;
import com.phatle.wolf_calie.feature.size.dto.UpdateSizeRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/sizes")
public class SizeController {

    private final SizeService sizeService;

    public SizeController(SizeService sizeService) {
        this.sizeService = sizeService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<SizeResponse>>> getAllSizes() {
        return ResponseEntity.ok(ApiResponse.success(sizeService.getAllSizes()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<SizeResponse>> getSizeById(@PathVariable long id) {
        return ResponseEntity.ok(ApiResponse.success(sizeService.getSizeById(id)));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<SizeResponse>> createSize(
            @RequestBody @Valid CreateSizeRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.created(sizeService.createSize(request)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<SizeResponse>> updateSize(
            @PathVariable long id,
            @RequestBody @Valid UpdateSizeRequest request) {
        return ResponseEntity.ok(ApiResponse.success(sizeService.updateSize(id, request)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteSize(@PathVariable long id) {
        sizeService.deleteSize(id);
        return ResponseEntity.ok(ApiResponse.success(null));
    }
}
