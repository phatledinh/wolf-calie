package com.phatle.wolf_calie.feature.color;

import com.phatle.wolf_calie.feature.color.dto.ColorResponse;
import com.phatle.wolf_calie.feature.color.dto.CreateColorRequest;
import com.phatle.wolf_calie.feature.color.dto.UpdateColorRequest;

import java.util.List;

public interface ColorService {
    ColorResponse createColor(CreateColorRequest request);
    ColorResponse getColorById(long id);
    ColorResponse updateColor(long id, UpdateColorRequest request);
    void deleteColor(long id);
    List<ColorResponse> getAllColors();
}
