package com.hijab.colorAnalysis.dao;

import com.hijab.colorAnalysis.entity.ColorAnalysisRecord;

import java.util.List;

public interface ColorAnalysisRecordDAO {
    void saveColorAnalysisRecord(ColorAnalysisRecord record);

    ColorAnalysisRecord getColorAnalysisRecordById(String id);

    void deleteColorAnalysisRecordById(String id);

    List<ColorAnalysisRecord> getAllColorAnalysisRecords();
}
