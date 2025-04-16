package com.hijab.controller;

import com.hijab.colorAnalysis.model.ColorAnalysisRequest;
import com.hijab.colorAnalysis.service.ColorAnalysisRecordService;
import com.hijab.colorAnalysis.service.temp.ColorAnalysisJobService;
import jakarta.servlet.http.HttpServletRequest;
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
    public ResponseEntity<?> analyzeImage(
            @ModelAttribute ColorAnalysisRequest analysisRequest,
            HttpServletRequest request
    ) {
//        String requestId = (String) request.getAttribute("requestId");
        String requestId = analysisRequest.getRequestId();
        colorAnalysisRecordService.analyzeImage(requestId, analysisRequest.getImage());
        return ResponseEntity.ok().build();
    }
}
