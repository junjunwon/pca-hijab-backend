package com.hijab.colorPalette.service;

import com.hijab.colorPalette.entity.ColorPalette;
import com.hijab.colorPalette.model.ColorPaletteResponse;

public interface ColorPaletteService {
    ColorPaletteResponse getColorPalette(ColorPalette.PersonalColor personalColor);
}
