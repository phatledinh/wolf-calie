package com.phatle.wolf_calie.feature.collection;

import com.phatle.wolf_calie.dto.ApiResponse;
import com.phatle.wolf_calie.feature.collection.dto.CollectionRequest;
import com.phatle.wolf_calie.feature.collection.dto.CollectionResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/collections")
@Tag(name = "Collection", description = "Collection Management APIs")
public class CollectionController {

    private final CollectionService collectionService;

    public CollectionController(CollectionService collectionService) {
        this.collectionService = collectionService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create a new collection")
    public ApiResponse<CollectionResponse> create(@Valid @RequestBody CollectionRequest request) {
        return ApiResponse.created("Collection created successfully", collectionService.create(request));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an existing collection")
    public ApiResponse<CollectionResponse> update(@PathVariable long id, @Valid @RequestBody CollectionRequest request) {
        return ApiResponse.success("Collection updated successfully", collectionService.update(id, request));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete a collection")
    public ApiResponse<Void> delete(@PathVariable long id) {
        collectionService.delete(id);
        return ApiResponse.success("Collection deleted successfully", null);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a collection by ID")
    public ApiResponse<CollectionResponse> getById(@PathVariable long id) {
        return ApiResponse.success("Collection retrieved successfully", collectionService.getById(id));
    }

    @GetMapping
    @Operation(summary = "Get all collections with pagination")
    public ApiResponse<Page<CollectionResponse>> getAll(Pageable pageable) {
        return ApiResponse.success("Collections retrieved successfully", collectionService.getAll(pageable));
    }
}
