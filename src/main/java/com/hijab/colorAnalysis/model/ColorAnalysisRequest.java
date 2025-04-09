package com.hijab.colorAnalysis.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class ColorAnalysisRequest {
    private MultipartFile image;
}
