package com.phatle.wolf_calie.feature.color;

import com.phatle.wolf_calie.exception.ResourceNotFoundException;
import com.phatle.wolf_calie.feature.color.dto.ColorResponse;
import com.phatle.wolf_calie.feature.color.dto.CreateColorRequest;
import com.phatle.wolf_calie.feature.color.dto.UpdateColorRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ColorServiceImpl implements ColorService {

    private final ColorRepository colorRepository;

    public ColorServiceImpl(ColorRepository colorRepository) {
        this.colorRepository = colorRepository;
    }

    @Override
    @Transactional
    public ColorResponse createColor(CreateColorRequest request) {
        Color color = new Color();
        color.setName(request.name());
        color.setHexCode(request.hexCode());
        
        if (request.sortOrder() != null) {
            color.setSortOrder(request.sortOrder());
        }
        
        if (request.isActive() != null) {
            color.setActive(request.isActive());
        }

        return ColorResponse.fromEntity(colorRepository.save(color));
    }

    @Override
    public ColorResponse getColorById(long id) {
        Color color = colorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Color", "id", id));
        return ColorResponse.fromEntity(color);
    }

    @Override
    @Transactional
    public ColorResponse updateColor(long id, UpdateColorRequest request) {
        Color color = colorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Color", "id", id));

        color.setName(request.name());
        color.setHexCode(request.hexCode());

        if (request.sortOrder() != null) {
            color.setSortOrder(request.sortOrder());
        }

        if (request.isActive() != null) {
            color.setActive(request.isActive());
        }

        return ColorResponse.fromEntity(colorRepository.save(color));
    }

    @Override
    @Transactional
    public void deleteColor(long id) {
        Color color = colorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Color", "id", id));

        color.setDeletedAt(Instant.now());
        colorRepository.save(color);
    }

    @Override
    public List<ColorResponse> getAllColors() {
        return colorRepository.findAll().stream()
                .map(ColorResponse::fromEntity)
                .collect(Collectors.toList());
    }
}
