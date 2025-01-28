package com.hijab.colorAnalysis.service;

import org.springframework.web.multipart.MultipartFile;

public interface ColorAnalysisJobService {
    String analyzeImage(String email, MultipartFile image);
}
