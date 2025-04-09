package com.hijab.colorAnalysis.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hijab.colorAnalysis.dao.ColorAnalysisRecordDAO;
import com.hijab.colorAnalysis.entity.ColorAnalysisRecord;
import com.hijab.image.service.ImageService;
import com.hijab.colorAnalysis.message.ColorAnalysisMessage;
import com.hijab.kafka.message.KafkaMessageEnvelope;
import com.hijab.kafka.producer.ColorAnalysisProducer;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class ColorAnalysisRecordServiceImpl implements ColorAnalysisRecordService {

    private final ImageService imageService;
    private final ColorAnalysisRecordDAO colorAnalysisRecordDAO;
    private final ColorAnalysisProducer colorAnalysisProducer;

    private final ObjectMapper objectMapper;

    @SneakyThrows
    @Override
    public void analyzeImage(String requestId, MultipartFile image) {
        // 로컬 디스크에 이미지 저장
        String imagePath = imageService.save(image);

        // 이미지 분석 요청 정보 저장
        ColorAnalysisRecord record = ColorAnalysisRecord.create(requestId, imagePath);
        colorAnalysisRecordDAO.saveColorAnalysisRecord(record);

        // 메세지 발행
        ColorAnalysisMessage message = ColorAnalysisMessage.create(requestId, imagePath);
        KafkaMessageEnvelope envelope = new KafkaMessageEnvelope(KafkaMessageEnvelope.MessageType.COLOR_ANALYSIS, message);
        colorAnalysisProducer.sendAnalysisRequest(envelope);
    }
}
