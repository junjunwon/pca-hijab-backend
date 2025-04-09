package com.hijab.colorPalette.dao;

import com.hijab.colorPalette.entity.ColorPalette;
import com.hijab.common.exception.CustomException;
import com.hijab.repository.jpa.ColorPaletteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import static com.hijab.common.exception.ExceptionStatus.NOT_FOUND_PERSONAL_COLOR;

@Component
@RequiredArgsConstructor
public class ColorPaletteDAOImpl implements ColorPaletteDAO {
    private final ColorPaletteRepository colorPaletteRepository;

    @Override
    public ColorPalette getColorPalette(ColorPalette.PersonalColor personalColor) {
        return colorPaletteRepository.findByPersonalColor(personalColor)
                .orElseThrow(() -> new CustomException(NOT_FOUND_PERSONAL_COLOR));
    }
}
