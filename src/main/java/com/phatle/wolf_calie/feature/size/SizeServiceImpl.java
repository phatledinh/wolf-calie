package com.phatle.wolf_calie.feature.size;

import com.phatle.wolf_calie.exception.ResourceNotFoundException;
import com.phatle.wolf_calie.feature.size.dto.SizeResponse;
import com.phatle.wolf_calie.feature.size.dto.CreateSizeRequest;
import com.phatle.wolf_calie.feature.size.dto.UpdateSizeRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SizeServiceImpl implements SizeService {

    private final SizeRepository sizeRepository;

    public SizeServiceImpl(SizeRepository sizeRepository) {
        this.sizeRepository = sizeRepository;
    }

    @Override
    @Transactional
    public SizeResponse createSize(CreateSizeRequest request) {
        Size size = new Size();
        size.setName(request.name());
        
        if (request.sortOrder() != null) {
            size.setSortOrder(request.sortOrder());
        }

        return SizeResponse.fromEntity(sizeRepository.save(size));
    }

    @Override
    public SizeResponse getSizeById(long id) {
        Size size = sizeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Size", "id", id));
        return SizeResponse.fromEntity(size);
    }

    @Override
    @Transactional
    public SizeResponse updateSize(long id, UpdateSizeRequest request) {
        Size size = sizeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Size", "id", id));

        size.setName(request.name());

        if (request.sortOrder() != null) {
            size.setSortOrder(request.sortOrder());
        }

        return SizeResponse.fromEntity(sizeRepository.save(size));
    }

    @Override
    @Transactional
    public void deleteSize(long id) {
        Size size = sizeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Size", "id", id));

        size.setDeletedAt(Instant.now());
        sizeRepository.save(size);
    }

    @Override
    public List<SizeResponse> getAllSizes() {
        return sizeRepository.findAll().stream()
                .map(SizeResponse::fromEntity)
                .collect(Collectors.toList());
    }
}
