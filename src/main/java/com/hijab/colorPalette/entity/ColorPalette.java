/**
 * ColorAnalysis ↔ ColorPalette
 *
 * 분석 결과에 따라 추천된 퍼스널컬러와 색상 팔레트를 연결.
 * 관계: N:1
 */
package com.hijab.colorPalette.entity;

import com.hijab.common.audit.Auditing;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Getter
@Entity
@Table(name = "color_palette")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ColorPalette extends Auditing {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Comment("퍼스널컬러 유형 (봄, 여름 등)")
    @Column(nullable = false, unique = true)
    @Enumerated(value = EnumType.STRING)
    private PersonalColor personalColor;

    @Comment("색상 코드 배열 (JSON 배열 형태)")
    @Lob
    private String colors; // JSON 형태의 색상 배열 저장

    @Comment("팔레트 설명")
    private String description;

    @Getter
    public enum PersonalColor {
        SPRING("Spring Warm Tone"),
        SUMMER("Summer Cool Tone"),
        FALL("Fall Warm Tone"),
        WINTER("Winter Cool Tone");

        private final String name;

        PersonalColor(String name) {
            this.name = name;
        }

        private static final Map<String, PersonalColor> VALUE_MAP = Collections.unmodifiableMap(
                Arrays.stream(values())
                        .collect(Collectors.toMap(
                                PersonalColor::getName,
                                Function.identity()
                        ))
        );

        public static PersonalColor fromValue(String name) {
            PersonalColor personalColor = VALUE_MAP.get(name);
            if (personalColor == null) {
                throw new IllegalArgumentException("Unexpected value: " + name);
            }
            return personalColor;
        }
    }
}
