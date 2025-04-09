package com.hijab.repository.jpa;

import com.hijab.colorAnalysis.entity.ColorAnalysisRecord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ColorAnalysisRecordRepository extends JpaRepository<ColorAnalysisRecord, Long> {
}
