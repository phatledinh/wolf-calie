package com.phatle.wolf_calie.feature.color;

import com.phatle.wolf_calie.dto.ApiResponse;
import com.phatle.wolf_calie.feature.color.dto.ColorResponse;
import com.phatle.wolf_calie.feature.color.dto.CreateColorRequest;
import com.phatle.wolf_calie.feature.color.dto.UpdateColorRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/colors")
public class ColorController {

    private final ColorService colorService;

    public ColorController(ColorService colorService) {
        this.colorService = colorService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<ColorResponse>>> getAllColors() {
        return ResponseEntity.ok(ApiResponse.success(colorService.getAllColors()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ColorResponse>> getColorById(@PathVariable long id) {
        return ResponseEntity.ok(ApiResponse.success(colorService.getColorById(id)));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<ColorResponse>> createColor(
            @RequestBody @Valid CreateColorRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.created(colorService.createColor(request)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<ColorResponse>> updateColor(
            @PathVariable long id,
            @RequestBody @Valid UpdateColorRequest request) {
        return ResponseEntity.ok(ApiResponse.success(colorService.updateColor(id, request)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteColor(@PathVariable long id) {
        colorService.deleteColor(id);
        return ResponseEntity.ok(ApiResponse.success(null));
    }
}
