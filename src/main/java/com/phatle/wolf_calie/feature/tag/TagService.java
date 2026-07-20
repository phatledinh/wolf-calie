package com.phatle.wolf_calie.feature.tag;

import com.phatle.wolf_calie.feature.tag.dto.CreateTagRequest;
import com.phatle.wolf_calie.feature.tag.dto.TagResponse;
import com.phatle.wolf_calie.feature.tag.dto.UpdateTagRequest;

import java.util.List;

public interface TagService {
    List<TagResponse> getAllTags();
    TagResponse getTagById(long id);
    TagResponse createTag(CreateTagRequest request);
    TagResponse updateTag(long id, UpdateTagRequest request);
    void deleteTag(long id);
}
