/**
 * ColorAnalysisJob ↔ ColorAnalysis
 *
 * 하나의 요청은 하나의 결과를 가짐.
 * 관계: 1:1
 */
package com.hijab.colorAnalysis.entity;

import com.hijab.common.audit.Auditing;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ColorAnalysis extends Auditing {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Comment("분석 작업 ID")
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "analysis_job_id", nullable = false)
    private ColorAnalysisJob colorAnalysisJob;

    @Comment("분석된 피부 톤")
    @Column(nullable = false)
    private String skinTone;

    @Comment("분석된 눈동자 색상")
    @Column(nullable = false)
    private String eyeColor;

    @Comment("분석된 머리카락 색상")
    @Column(nullable = false)
    private String hairColor;

    @Comment("추천된 퍼스널컬러 (봄, 여름 등)")
    @Column(nullable = false)
    private String personalColor;

    @Comment("추천된 색상 팔레트 (JSON 배열 형태)")
    @Lob
    private String palette; // JSON 형태의 색상 팔레트 저장
}
