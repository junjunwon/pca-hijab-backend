package com.hijab.colorAnalysis.service;

import com.hijab.colorPalette.model.ColorPaletteResponse;
import org.springframework.web.multipart.MultipartFile;

public interface ColorAnalysisRecordService {
    void analyzeImage(String requestId, MultipartFile image);
}
