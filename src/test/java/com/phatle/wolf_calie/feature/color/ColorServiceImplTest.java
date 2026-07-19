package com.phatle.wolf_calie.feature.color;

import com.phatle.wolf_calie.exception.ResourceNotFoundException;
import com.phatle.wolf_calie.feature.color.dto.ColorResponse;
import com.phatle.wolf_calie.feature.color.dto.CreateColorRequest;
import com.phatle.wolf_calie.feature.color.dto.UpdateColorRequest;
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
class ColorServiceImplTest {

    @Mock
    private ColorRepository colorRepository;

    @InjectMocks
    private ColorServiceImpl colorService;

    private Color sampleColor;

    @BeforeEach
    void setUp() {
        sampleColor = new Color();
        sampleColor.setId(1L);
        sampleColor.setName("Black");
        sampleColor.setHexCode("#000000");
        sampleColor.setSortOrder(1);
        sampleColor.setActive(true);
        sampleColor.setDeletedAt(null);
    }

    @Test
    void createColor_ShouldReturnResponse() {
        CreateColorRequest request = new CreateColorRequest("Black", "#000000", 1, true);

        when(colorRepository.save(any(Color.class))).thenReturn(sampleColor);

        ColorResponse response = colorService.createColor(request);

        assertThat(response).isNotNull();
        assertThat(response.id()).isEqualTo(1L);
        assertThat(response.name()).isEqualTo("Black");
        assertThat(response.hexCode()).isEqualTo("#000000");
        verify(colorRepository).save(any(Color.class));
    }

    @Test
    void getColorById_WhenExists_ShouldReturnResponse() {
        when(colorRepository.findById(1L)).thenReturn(Optional.of(sampleColor));

        ColorResponse response = colorService.getColorById(1L);

        assertThat(response).isNotNull();
        assertThat(response.id()).isEqualTo(1L);
        assertThat(response.name()).isEqualTo("Black");
    }

    @Test
    void getColorById_WhenNotExists_ShouldThrowException() {
        when(colorRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> colorService.getColorById(99L))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Không tìm thấy Color với id = '99'");
    }

    @Test
    void updateColor_WhenExists_ShouldReturnUpdatedResponse() {
        UpdateColorRequest request = new UpdateColorRequest("Black Updated", "#111111", 2, false);

        Color updatedColor = new Color();
        updatedColor.setId(1L);
        updatedColor.setName("Black Updated");
        updatedColor.setHexCode("#111111");
        updatedColor.setSortOrder(2);
        updatedColor.setActive(false);

        when(colorRepository.findById(1L)).thenReturn(Optional.of(sampleColor));
        when(colorRepository.save(any(Color.class))).thenReturn(updatedColor);

        ColorResponse response = colorService.updateColor(1L, request);

        assertThat(response).isNotNull();
        assertThat(response.name()).isEqualTo("Black Updated");
        assertThat(response.hexCode()).isEqualTo("#111111");
        assertThat(response.sortOrder()).isEqualTo(2);
        assertThat(response.isActive()).isFalse();
    }

    @Test
    void deleteColor_WhenExists_ShouldSetDeletedAt() {
        when(colorRepository.findById(1L)).thenReturn(Optional.of(sampleColor));
        when(colorRepository.save(any(Color.class))).thenReturn(sampleColor);

        colorService.deleteColor(1L);

        assertThat(sampleColor.getDeletedAt()).isNotNull();
        assertThat(sampleColor.getDeletedAt()).isBeforeOrEqualTo(Instant.now());
        verify(colorRepository).save(sampleColor);
    }

    @Test
    void getAllColors_ShouldReturnList() {
        when(colorRepository.findAll()).thenReturn(List.of(sampleColor));

        List<ColorResponse> responses = colorService.getAllColors();

        assertThat(responses).hasSize(1);
        assertThat(responses.get(0).name()).isEqualTo("Black");
    }
}
