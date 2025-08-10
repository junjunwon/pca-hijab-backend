package com.hijab.repository.jpa;

import com.hijab.colorPalette.entity.ColorPalette;
import com.hijab.product.entity.PaletteProductPriority;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PaletteProductPriorityRepository extends JpaRepository<PaletteProductPriority, Long> {
    List<PaletteProductPriority> findByColorPalette_PersonalColorOrderByPriorityAsc(ColorPalette.PersonalColor personalColor);
}
