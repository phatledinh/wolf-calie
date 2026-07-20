package com.phatle.wolf_calie.feature.collection;

import com.phatle.wolf_calie.exception.ResourceNotFoundException;
import com.phatle.wolf_calie.exception.DuplicateResourceException;
import com.phatle.wolf_calie.feature.collection.dto.CollectionRequest;
import com.phatle.wolf_calie.feature.collection.dto.CollectionResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Service
@Transactional
public class CollectionServiceImpl implements CollectionService {

    private final CollectionRepository collectionRepository;
    public CollectionServiceImpl(CollectionRepository collectionRepository) {
        this.collectionRepository = collectionRepository;
    }

    @Override
    public CollectionResponse create(CollectionRequest request) {
        if (collectionRepository.existsBySlug(request.getSlug())) {
            throw new DuplicateResourceException("Collection", "slug", request.getSlug());
        }

        Collection collection = new Collection();
        collection.setName(request.getName());
        collection.setSlug(request.getSlug());
        collection.setDescription(request.getDescription());
        collection.setImageUrl(request.getImageUrl());
        collection.setActive(request.isActive());
        collection.setStartDate(request.getStartDate());
        collection.setEndDate(request.getEndDate());
        
        collection = collectionRepository.save(collection);
        return CollectionResponse.fromEntity(collection);
    }

    @Override
    public CollectionResponse update(long id, CollectionRequest request) {
        Collection collection = collectionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Collection", "id", id));

        if (collectionRepository.existsBySlugAndIdNot(request.getSlug(), id)) {
            throw new DuplicateResourceException("Collection", "slug", request.getSlug());
        }

        collection.setName(request.getName());
        collection.setSlug(request.getSlug());
        collection.setDescription(request.getDescription());
        collection.setImageUrl(request.getImageUrl());
        collection.setActive(request.isActive());
        collection.setStartDate(request.getStartDate());
        collection.setEndDate(request.getEndDate());

        collection = collectionRepository.save(collection);
        return CollectionResponse.fromEntity(collection);
    }

    @Override
    public void delete(long id) {
        Collection collection = collectionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Collection", "id", id));

        collection.setDeletedAt(Instant.now());
        collectionRepository.save(collection);
    }

    @Override
    @Transactional(readOnly = true)
    public CollectionResponse getById(long id) {
        return collectionRepository.findById(id)
                .map(CollectionResponse::fromEntity)
                .orElseThrow(() -> new ResourceNotFoundException("Collection", "id", id));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CollectionResponse> getAll(Pageable pageable) {
        return collectionRepository.findAll(pageable)
                .map(CollectionResponse::fromEntity);
    }
}
