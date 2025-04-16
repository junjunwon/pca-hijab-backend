package com.hijab.colorPalette.service;

import com.hijab.colorPalette.entity.ColorPalette;
import com.hijab.colorPalette.model.ColorPaletteResponse;
import com.hijab.common.exception.CustomException;
import com.hijab.repository.jpa.ColorPaletteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.hijab.common.exception.ExceptionStatus.NOT_FOUND_PERSONAL_COLOR;

@Service
@RequiredArgsConstructor
public class ColorPaletteServiceImpl implements ColorPaletteService {
    private final ColorPaletteRepository colorPaletteRepository;

    @Override
    public ColorPaletteResponse getColorPalette(ColorPalette.PersonalColor personalColor) {
        return colorPaletteRepository.findByPersonalColor(personalColor)
                .map(colorPalette -> new ColorPaletteResponse(Boolean.TRUE, colorPalette.getPersonalColor().getName(), colorPalette.getDescription()))
                .orElseThrow(() -> new CustomException(NOT_FOUND_PERSONAL_COLOR));
    }
}
