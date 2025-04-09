package com.hijab.colorAnalysis.dao;

import com.hijab.colorAnalysis.entity.ColorAnalysisRecord;
import com.hijab.repository.jpa.ColorAnalysisRecordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ColorAnalysisRecordDAOImpl implements ColorAnalysisRecordDAO {

    private final ColorAnalysisRecordRepository colorAnalysisRecordRepository;

    @Override
    public void saveColorAnalysisRecord(ColorAnalysisRecord record) {
        colorAnalysisRecordRepository.save(record);
    }

    @Override
    public ColorAnalysisRecord getColorAnalysisRecordById(String id) {
        return null;
    }

    @Override
    public void deleteColorAnalysisRecordById(String id) {

    }

    @Override
    public List<ColorAnalysisRecord> getAllColorAnalysisRecords() {
        return List.of();
    }
}
