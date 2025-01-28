package com.hijab.colorAnalysis.model;

import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

@Getter
public class ColorAnalysisRequest {
    private final MultipartFile image;

    public ColorAnalysisRequest(MultipartFile image) {
        this.image = image;
    }
}
