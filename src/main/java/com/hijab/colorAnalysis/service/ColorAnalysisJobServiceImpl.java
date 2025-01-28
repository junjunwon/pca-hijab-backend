package com.hijab.colorAnalysis.service;

import com.hijab.colorAnalysis.event.MultipartFileEvent;
import com.hijab.common.activemq.message.MessagePayload;
import com.hijab.external.exchange.PcaClient;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class ColorAnalysisJobServiceImpl implements ColorAnalysisJobService {

    private final ApplicationEventPublisher publisher;
    private final PcaClient pcaClient;

    @Override
    @Transactional
    public String analyzeImage(String email, MultipartFile image) {
        // 이미지 분석 로직
        return pcaClient.uploadFile(image);

//        MessagePayload<MultipartFile> messagePayload = new MessagePayload<>(image);
//        publisher.publishEvent(new MultipartFileEvent(messagePayload));
    }
}
