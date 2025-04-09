package com.hijab.colorAnalysis.message;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ColorAnalysisMessage {
    private String requestId;
    private String imagePath;

    public ColorAnalysisMessage(String requestId, String imagePath) {
        this.requestId = requestId;
        this.imagePath = imagePath;
    }

    public static ColorAnalysisMessage create(String requestId, String imagePath) {
        return new ColorAnalysisMessage(requestId, imagePath);
    }
}