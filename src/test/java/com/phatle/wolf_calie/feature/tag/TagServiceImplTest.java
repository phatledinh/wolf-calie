package com.phatle.wolf_calie.feature.tag;

import com.phatle.wolf_calie.exception.InvalidRequestException;
import com.phatle.wolf_calie.exception.ResourceNotFoundException;
import com.phatle.wolf_calie.feature.tag.dto.CreateTagRequest;
import com.phatle.wolf_calie.feature.tag.dto.TagResponse;
import com.phatle.wolf_calie.feature.tag.dto.UpdateTagRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TagServiceImplTest {

    @Mock
    private TagRepository tagRepository;

    @InjectMocks
    private TagServiceImpl tagService;

    @Test
    @DisplayName("Should return list of tags")
    void getAllTags_returnsList() {
        Tag tag1 = new Tag();
        tag1.setId(1L);
        tag1.setName("Tag 1");
        tag1.setSlug("tag-1");

        Tag tag2 = new Tag();
        tag2.setId(2L);
        tag2.setName("Tag 2");
        tag2.setSlug("tag-2");

        when(tagRepository.findAll()).thenReturn(List.of(tag1, tag2));

        List<TagResponse> responses = tagService.getAllTags();

        assertEquals(2, responses.size());
        assertEquals("Tag 1", responses.get(0).name());
        assertEquals("tag-2", responses.get(1).slug());
    }

    @Test
    @DisplayName("Should throw ResourceNotFoundException when tag not found")
    void getTagById_notFound_throwsException() {
        when(tagRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> tagService.getTagById(1L));
    }

    @Test
    @DisplayName("Should return tag when found")
    void getTagById_found_returnsTag() {
        Tag tag = new Tag();
        tag.setId(1L);
        tag.setName("Tag 1");
        tag.setSlug("tag-1");

        when(tagRepository.findById(1L)).thenReturn(Optional.of(tag));

        TagResponse response = tagService.getTagById(1L);

        assertEquals("Tag 1", response.name());
        assertEquals("tag-1", response.slug());
    }

    @Test
    @DisplayName("Should throw InvalidRequestException when slug exists")
    void createTag_slugExists_throwsException() {
        CreateTagRequest request = new CreateTagRequest("Tag 1", "tag-1");
        when(tagRepository.existsBySlug("tag-1")).thenReturn(true);

        assertThrows(InvalidRequestException.class, () -> tagService.createTag(request));
    }

    @Test
    @DisplayName("Should save and return tag when valid")
    void createTag_valid_savesTag() {
        CreateTagRequest request = new CreateTagRequest("Tag 1", "tag-1");
        when(tagRepository.existsBySlug("tag-1")).thenReturn(false);

        Tag savedTag = new Tag();
        savedTag.setId(1L);
        savedTag.setName("Tag 1");
        savedTag.setSlug("tag-1");

        when(tagRepository.save(any(Tag.class))).thenReturn(savedTag);

        TagResponse response = tagService.createTag(request);

        assertEquals(1L, response.id());
        assertEquals("Tag 1", response.name());
        assertEquals("tag-1", response.slug());
    }

    @Test
    @DisplayName("Should throw ResourceNotFoundException when updating non-existent tag")
    void updateTag_notFound_throwsException() {
        UpdateTagRequest request = new UpdateTagRequest("Tag 1", "tag-1");
        when(tagRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> tagService.updateTag(1L, request));
    }

    @Test
    @DisplayName("Should throw InvalidRequestException when updating to an existing slug")
    void updateTag_newSlugExists_throwsException() {
        UpdateTagRequest request = new UpdateTagRequest("Tag 2", "tag-2");
        Tag existingTag = new Tag();
        existingTag.setId(1L);
        existingTag.setName("Tag 1");
        existingTag.setSlug("tag-1");

        when(tagRepository.findById(1L)).thenReturn(Optional.of(existingTag));
        when(tagRepository.existsBySlug("tag-2")).thenReturn(true);

        assertThrows(InvalidRequestException.class, () -> tagService.updateTag(1L, request));
    }

    @Test
    @DisplayName("Should save and return updated tag when valid")
    void updateTag_valid_savesTag() {
        UpdateTagRequest request = new UpdateTagRequest("Tag 2", "tag-2");
        Tag existingTag = new Tag();
        existingTag.setId(1L);
        existingTag.setName("Tag 1");
        existingTag.setSlug("tag-1");

        Tag updatedTag = new Tag();
        updatedTag.setId(1L);
        updatedTag.setName("Tag 2");
        updatedTag.setSlug("tag-2");

        when(tagRepository.findById(1L)).thenReturn(Optional.of(existingTag));
        when(tagRepository.existsBySlug("tag-2")).thenReturn(false);
        when(tagRepository.save(any(Tag.class))).thenReturn(updatedTag);

        TagResponse response = tagService.updateTag(1L, request);

        assertEquals("Tag 2", response.name());
        assertEquals("tag-2", response.slug());
    }

    @Test
    @DisplayName("Should set deletedAt when deleting")
    void deleteTag_valid_softDeletes() {
        Tag existingTag = new Tag();
        existingTag.setId(1L);
        
        when(tagRepository.findById(1L)).thenReturn(Optional.of(existingTag));

        tagService.deleteTag(1L);

        assertNotNull(existingTag.getDeletedAt());
        verify(tagRepository).save(existingTag);
    }
}
