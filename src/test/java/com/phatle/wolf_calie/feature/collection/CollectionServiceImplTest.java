package com.phatle.wolf_calie.feature.collection;

import com.phatle.wolf_calie.exception.DuplicateResourceException;
import com.phatle.wolf_calie.exception.ResourceNotFoundException;
import com.phatle.wolf_calie.feature.collection.dto.CollectionRequest;
import com.phatle.wolf_calie.feature.collection.dto.CollectionResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CollectionServiceImplTest {

    @Mock
    private CollectionRepository collectionRepository;

    @InjectMocks
    private CollectionServiceImpl collectionService;

    private Collection collection;
    private CollectionRequest request;
    private CollectionResponse response;

    @BeforeEach
    void setUp() {
        collection = new Collection();
        collection.setId(1L);
        collection.setName("Summer Collection");
        collection.setSlug("summer-collection");

        request = new CollectionRequest();
        request.setName("Summer Collection");
        request.setSlug("summer-collection");

        response = new CollectionResponse();
        response.setId(1L);
        response.setName("Summer Collection");
        response.setSlug("summer-collection");
    }

    @Test
    void create_WhenSlugExists_ThrowsDuplicateResourceException() {
        when(collectionRepository.existsBySlug(request.getSlug())).thenReturn(true);

        assertThatThrownBy(() -> collectionService.create(request))
                .isInstanceOf(DuplicateResourceException.class)
                .hasMessageContaining("Collection đã tồn tại với slug = 'summer-collection'");
        
        verify(collectionRepository, never()).save(any());
    }

    @Test
    void create_WhenValid_ReturnsCollectionResponse() {
        when(collectionRepository.existsBySlug(request.getSlug())).thenReturn(false);
        when(collectionRepository.save(any(Collection.class))).thenAnswer(invocation -> {
            Collection saved = invocation.getArgument(0);
            saved.setId(1L);
            return saved;
        });

        CollectionResponse result = collectionService.create(request);

        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getName()).isEqualTo("Summer Collection");
        verify(collectionRepository).save(any(Collection.class));
    }

    @Test
    void update_WhenNotFound_ThrowsResourceNotFoundException() {
        when(collectionRepository.findById(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> collectionService.update(1L, request))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Không tìm thấy Collection với id = '1'");
        
        verify(collectionRepository, never()).save(any());
    }

    @Test
    void update_WhenSlugExistsForOtherId_ThrowsDuplicateResourceException() {
        when(collectionRepository.findById(1L)).thenReturn(Optional.of(collection));
        when(collectionRepository.existsBySlugAndIdNot(request.getSlug(), 1L)).thenReturn(true);

        assertThatThrownBy(() -> collectionService.update(1L, request))
                .isInstanceOf(DuplicateResourceException.class)
                .hasMessageContaining("Collection đã tồn tại với slug = 'summer-collection'");
        
        verify(collectionRepository, never()).save(any());
    }

    @Test
    void update_WhenValid_ReturnsUpdatedResponse() {
        when(collectionRepository.findById(1L)).thenReturn(Optional.of(collection));
        when(collectionRepository.existsBySlugAndIdNot(request.getSlug(), 1L)).thenReturn(false);
        when(collectionRepository.save(any(Collection.class))).thenAnswer(invocation -> invocation.getArgument(0));

        CollectionResponse result = collectionService.update(1L, request);

        assertThat(result.getId()).isEqualTo(1L);
        verify(collectionRepository).save(collection);
    }

    @Test
    void delete_WhenNotFound_ThrowsResourceNotFoundException() {
        when(collectionRepository.findById(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> collectionService.delete(1L))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Không tìm thấy Collection với id = '1'");
        
        verify(collectionRepository, never()).save(any());
    }

    @Test
    void delete_WhenFound_SetsDeletedAtAndSaves() {
        when(collectionRepository.findById(1L)).thenReturn(Optional.of(collection));

        collectionService.delete(1L);

        assertThat(collection.getDeletedAt()).isNotNull();
        verify(collectionRepository).save(collection);
    }

    @Test
    void getById_WhenNotFound_ThrowsResourceNotFoundException() {
        when(collectionRepository.findById(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> collectionService.getById(1L))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Không tìm thấy Collection với id = '1'");
    }

    @Test
    void getById_WhenFound_ReturnsResponse() {
        when(collectionRepository.findById(1L)).thenReturn(Optional.of(collection));

        CollectionResponse result = collectionService.getById(1L);

        assertThat(result.getId()).isEqualTo(1L);
    }
}
