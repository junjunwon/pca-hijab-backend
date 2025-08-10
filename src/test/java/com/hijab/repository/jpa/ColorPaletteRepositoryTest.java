package com.hijab.repository.jpa;

import com.hijab.colorPalette.entity.ColorPalette;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest(showSql = true)
@TestPropertySource("classpath:application-test.properties")
class ColorPaletteRepositoryTest {

    @Autowired
    ColorPaletteRepository colorPaletteRepository;

    @BeforeEach
    void setUp() {
        // 데이터베이스 초기화 작업이 필요하다면 여기에 작성
        // given
        ColorPalette.PersonalColor personalColor = ColorPalette.PersonalColor.SPRING;
        List<String> colors = new ArrayList<>();
        String description = "Spring Warm Tone";
        ColorPalette colorPalette = new ColorPalette(personalColor, colors, description);

        // when
        colorPaletteRepository.save(colorPalette);
    }

    @Test
    void findById_로_데이터_조회() {
        // when
        Optional<ColorPalette> result = colorPaletteRepository.findById(1L);

        // then
        assertThat(result.isPresent()).isTrue();
    }

    @Test
    void findById_로_데이터가_존재하지않으면_Optional_Empty_return() {
        // when
        Optional<ColorPalette> result = colorPaletteRepository.findById(999L); // 존재하지 않는 ID

        // then
        assertThat(result.isEmpty()).isTrue();
    }
}