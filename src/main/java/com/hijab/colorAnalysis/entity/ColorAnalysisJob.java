/**
 * User ↔ ColorAnalysisJob
 *
 * 한 사용자는 여러 번 분석 요청을 보낼 수 있음.
 * 관계: 1:N
 */
package com.hijab.colorAnalysis.entity;

import com.hijab.common.audit.Auditing;
import com.hijab.user.entity.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

import java.time.LocalDateTime;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ColorAnalysisJob extends Auditing {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Comment("분석 요청을 한 사용자")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Comment("분석 대상 얼굴 이미지 URL")
    @Column(nullable = false)
    private String imageUrl;

    @Comment("분석 상태 (PENDING, COMPLETED 등)")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AnalysisStatus status;

    private LocalDateTime completedAt;

    @OneToOne(mappedBy = "colorAnalysisJob", cascade = CascadeType.ALL, orphanRemoval = true)
    private ColorAnalysis colorAnalysis;

    public enum AnalysisStatus {
        PENDING, COMPLETED, FAILED
    }
}
