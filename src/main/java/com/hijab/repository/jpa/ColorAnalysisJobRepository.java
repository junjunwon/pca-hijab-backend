package com.hijab.repository.jpa;

import com.hijab.colorAnalysis.entity.temp.ColorAnalysisJob;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ColorAnalysisJobRepository extends JpaRepository<ColorAnalysisJob, Long> {
}
