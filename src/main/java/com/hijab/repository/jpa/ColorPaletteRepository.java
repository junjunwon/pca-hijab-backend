package com.hijab.repository.jpa;

import com.hijab.colorPalette.entity.ColorPalette;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ColorPaletteRepository extends JpaRepository<ColorPalette, Long> {
    Optional<ColorPalette> findByPersonalColor(ColorPalette.PersonalColor personalColor);
}
