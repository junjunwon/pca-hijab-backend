package com.hijab.colorPalette.dao;

import com.hijab.colorPalette.entity.ColorPalette;

public interface ColorPaletteDAO {
    ColorPalette getColorPalette(ColorPalette.PersonalColor personalColor);
}
