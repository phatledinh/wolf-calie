package com.phatle.wolf_calie.feature.size;

import com.phatle.wolf_calie.feature.size.dto.SizeResponse;
import com.phatle.wolf_calie.feature.size.dto.CreateSizeRequest;
import com.phatle.wolf_calie.feature.size.dto.UpdateSizeRequest;

import java.util.List;

public interface SizeService {
    SizeResponse createSize(CreateSizeRequest request);
    SizeResponse getSizeById(long id);
    SizeResponse updateSize(long id, UpdateSizeRequest request);
    void deleteSize(long id);
    List<SizeResponse> getAllSizes();
}
