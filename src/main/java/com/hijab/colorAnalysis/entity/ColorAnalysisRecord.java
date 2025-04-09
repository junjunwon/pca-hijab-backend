package com.hijab.colorAnalysis.entity;

import com.hijab.colorPalette.entity.ColorPalette;
import com.hijab.common.audit.Auditing;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

import java.time.LocalDateTime;

@Getter
@Entity
@Table(name = "color_analysis_record")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ColorAnalysisRecord extends Auditing {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Comment("사용자 ID 또는 세션 정보")
    private String requesterId;

    @Comment("요청 이미지 파일명 또는 경로")
    private String imagePath;

    @Comment("요청 상태 (PENDING, PROCESSING, DONE, FAILED)")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status = Status.PENDING;

    @Comment("분석 결과 퍼스널컬러")
    @Enumerated(EnumType.STRING)
    private ColorPalette.PersonalColor resultColor;

    @Comment("분석 서버에서 받은 원본 결과 (json 등)")
    @Lob
    private String rawResult;

    @Comment("분석 완료 시간")
    private LocalDateTime completedAt;

    @Comment("분석 실패시 메시지")
    private String failReason;

    public ColorAnalysisRecord(String imagePath, String requesterId) {
        this.imagePath = imagePath;
        this.requesterId = requesterId;
    }

    public static ColorAnalysisRecord create(String requestId, String imagePath) {
        return new ColorAnalysisRecord(requestId, imagePath);
    }

    @PrePersist
    public void initStatus() {
        if (this.status == null) {
            this.status = Status.PENDING;
        }
    }

    public enum Status {
        PENDING, PROCESSING, DONE, FAILED
    }
}