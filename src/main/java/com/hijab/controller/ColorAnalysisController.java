package com.hijab.controller;

import com.hijab.colorAnalysis.model.ColorAnalysisRequest;
import com.hijab.colorAnalysis.service.ColorAnalysisRecordService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/color/analysis")
@RequiredArgsConstructor
public class ColorAnalysisController {

    private final ColorAnalysisRecordService colorAnalysisRecordService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> analyzeImage(@ModelAttribute ColorAnalysisRequest analysisRequest) {
        colorAnalysisRecordService.analyzeImage(analysisRequest.getRequestId(), analysisRequest.getImage());
        return ResponseEntity.ok().build();
    }
}
