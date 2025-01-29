package com.hijab.controller;

import com.hijab.colorAnalysis.service.ColorAnalysisJobService;
import com.hijab.colorPalette.model.ColorPaletteResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/color/analysis")
@RequiredArgsConstructor
public class ColorAnalysisController {

    private final ColorAnalysisJobService colorAnalysisJobService;
    @PostMapping
    public ResponseEntity<ColorPaletteResponse> analyzeImage(@RequestParam("email") String email,
                                                             @RequestParam("image") MultipartFile image) {
        return ResponseEntity.ok(colorAnalysisJobService.analyzeImage(email, image));
    }
}
