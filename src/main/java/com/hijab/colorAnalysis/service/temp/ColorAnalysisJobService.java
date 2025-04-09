package com.hijab.colorAnalysis.service.temp;

import com.hijab.colorPalette.model.ColorPaletteResponse;
import org.springframework.web.multipart.MultipartFile;

public interface ColorAnalysisJobService {
    ColorPaletteResponse analyzeImage(String requestId, MultipartFile image);
}
