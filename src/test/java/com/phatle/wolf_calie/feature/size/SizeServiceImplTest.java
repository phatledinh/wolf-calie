package com.phatle.wolf_calie.feature.size;

import com.phatle.wolf_calie.exception.ResourceNotFoundException;
import com.phatle.wolf_calie.feature.size.dto.SizeResponse;
import com.phatle.wolf_calie.feature.size.dto.CreateSizeRequest;
import com.phatle.wolf_calie.feature.size.dto.UpdateSizeRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SizeServiceImplTest {

    @Mock
    private SizeRepository sizeRepository;

    @InjectMocks
    private SizeServiceImpl sizeService;

    private Size sampleSize;

    @BeforeEach
    void setUp() {
        sampleSize = new Size();
        sampleSize.setId(1L);
        sampleSize.setName("XL");
        sampleSize.setSortOrder(1);
        sampleSize.setDeletedAt(null);
    }

    @Test
    void createSize_ShouldReturnResponse() {
        CreateSizeRequest request = new CreateSizeRequest("XL", 1);

        when(sizeRepository.save(any(Size.class))).thenReturn(sampleSize);

        SizeResponse response = sizeService.createSize(request);

        assertThat(response).isNotNull();
        assertThat(response.id()).isEqualTo(1L);
        assertThat(response.name()).isEqualTo("XL");
        verify(sizeRepository).save(any(Size.class));
    }

    @Test
    void getSizeById_WhenExists_ShouldReturnResponse() {
        when(sizeRepository.findById(1L)).thenReturn(Optional.of(sampleSize));

        SizeResponse response = sizeService.getSizeById(1L);

        assertThat(response).isNotNull();
        assertThat(response.id()).isEqualTo(1L);
        assertThat(response.name()).isEqualTo("XL");
    }

    @Test
    void getSizeById_WhenNotExists_ShouldThrowException() {
        when(sizeRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> sizeService.getSizeById(99L))
                .isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void updateSize_WhenExists_ShouldReturnUpdatedResponse() {
        UpdateSizeRequest request = new UpdateSizeRequest("XXL", 2);

        Size updatedSize = new Size();
        updatedSize.setId(1L);
        updatedSize.setName("XXL");
        updatedSize.setSortOrder(2);

        when(sizeRepository.findById(1L)).thenReturn(Optional.of(sampleSize));
        when(sizeRepository.save(any(Size.class))).thenReturn(updatedSize);

        SizeResponse response = sizeService.updateSize(1L, request);

        assertThat(response).isNotNull();
        assertThat(response.name()).isEqualTo("XXL");
        assertThat(response.sortOrder()).isEqualTo(2);
    }

    @Test
    void deleteSize_WhenExists_ShouldSetDeletedAt() {
        when(sizeRepository.findById(1L)).thenReturn(Optional.of(sampleSize));
        when(sizeRepository.save(any(Size.class))).thenReturn(sampleSize);

        sizeService.deleteSize(1L);

        assertThat(sampleSize.getDeletedAt()).isNotNull();
        assertThat(sampleSize.getDeletedAt()).isBeforeOrEqualTo(Instant.now());
        verify(sizeRepository).save(sampleSize);
    }

    @Test
    void getAllSizes_ShouldReturnList() {
        when(sizeRepository.findAll()).thenReturn(List.of(sampleSize));

        List<SizeResponse> responses = sizeService.getAllSizes();

        assertThat(responses).hasSize(1);
        assertThat(responses.get(0).name()).isEqualTo("XL");
    }
}
