package com.phatle.wolf_calie.feature.tag;

import com.phatle.wolf_calie.exception.InvalidRequestException;
import com.phatle.wolf_calie.exception.ResourceNotFoundException;
import com.phatle.wolf_calie.feature.tag.dto.CreateTagRequest;
import com.phatle.wolf_calie.feature.tag.dto.TagResponse;
import com.phatle.wolf_calie.feature.tag.dto.UpdateTagRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TagServiceImpl implements TagService {

    private final TagRepository tagRepository;

    public TagServiceImpl(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    @Override
    public List<TagResponse> getAllTags() {
        return tagRepository.findAll().stream()
                .map(TagResponse::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public TagResponse getTagById(long id) {
        Tag tag = tagRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tag", "id", id));
        return TagResponse.fromEntity(tag);
    }

    @Override
    @Transactional
    public TagResponse createTag(CreateTagRequest request) {
        if (tagRepository.existsBySlug(request.slug())) {
            throw new InvalidRequestException("Tag slug already exists: " + request.slug());
        }

        Tag tag = new Tag();
        tag.setName(request.name());
        tag.setSlug(request.slug());

        return TagResponse.fromEntity(tagRepository.save(tag));
    }

    @Override
    @Transactional
    public TagResponse updateTag(long id, UpdateTagRequest request) {
        Tag tag = tagRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tag", "id", id));

        if (!tag.getSlug().equals(request.slug()) && tagRepository.existsBySlug(request.slug())) {
            throw new InvalidRequestException("Tag slug already exists: " + request.slug());
        }

        tag.setName(request.name());
        tag.setSlug(request.slug());

        return TagResponse.fromEntity(tagRepository.save(tag));
    }

    @Override
    @Transactional
    public void deleteTag(long id) {
        Tag tag = tagRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tag", "id", id));

        tag.setDeletedAt(Instant.now());
        tagRepository.save(tag);
    }
}
