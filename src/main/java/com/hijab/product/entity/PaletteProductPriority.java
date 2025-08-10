package com.hijab.product.entity;

import com.hijab.colorPalette.entity.ColorPalette;
import com.hijab.common.audit.Auditing;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "palette_product_priority")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PaletteProductPriority extends Auditing {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "color_palette_id", nullable = false)
    private ColorPalette colorPalette;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Column(nullable = false)
    private int priority; // 우선순위 (낮을수록 먼저 노출)
}