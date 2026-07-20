package com.phatle.wolf_calie.feature.tag;

import com.phatle.wolf_calie.dto.ApiResponse;
import com.phatle.wolf_calie.feature.tag.dto.CreateTagRequest;
import com.phatle.wolf_calie.feature.tag.dto.TagResponse;
import com.phatle.wolf_calie.feature.tag.dto.UpdateTagRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/tags")
public class TagController {

    private final TagService tagService;

    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<TagResponse>>> getAllTags() {
        return ResponseEntity.ok(ApiResponse.success(tagService.getAllTags()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<TagResponse>> getTagById(@PathVariable long id) {
        return ResponseEntity.ok(ApiResponse.success(tagService.getTagById(id)));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<TagResponse>> createTag(
            @RequestBody @Valid CreateTagRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.created(tagService.createTag(request)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<TagResponse>> updateTag(
            @PathVariable long id,
            @RequestBody @Valid UpdateTagRequest request) {
        return ResponseEntity.ok(ApiResponse.success(tagService.updateTag(id, request)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteTag(@PathVariable long id) {
        tagService.deleteTag(id);
        return ResponseEntity.ok(ApiResponse.success(null));
    }
}
