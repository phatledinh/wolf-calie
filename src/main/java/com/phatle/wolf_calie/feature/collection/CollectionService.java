package com.phatle.wolf_calie.feature.collection;

import com.phatle.wolf_calie.feature.collection.dto.CollectionRequest;
import com.phatle.wolf_calie.feature.collection.dto.CollectionResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CollectionService {

    CollectionResponse create(CollectionRequest request);

    CollectionResponse update(long id, CollectionRequest request);

    void delete(long id);

    CollectionResponse getById(long id);

    Page<CollectionResponse> getAll(Pageable pageable);
}
