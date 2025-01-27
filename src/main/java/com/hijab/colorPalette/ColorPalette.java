/**
 * ColorAnalysis ↔ ColorPalette
 *
 * 분석 결과에 따라 추천된 퍼스널컬러와 색상 팔레트를 연결.
 * 관계: N:1
 */
package com.hijab.colorPalette;

import com.hijab.common.audit.Auditing;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ColorPalette extends Auditing {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Comment("퍼스널컬러 유형 (봄, 여름 등)")
    @Column(nullable = false, unique = true)
    private String personalColor;

    @Comment("색상 코드 배열 (JSON 배열 형태)")
    @Lob
    private String colors; // JSON 형태의 색상 배열 저장

    @Comment("팔레트 설명")
    private String description;
}
