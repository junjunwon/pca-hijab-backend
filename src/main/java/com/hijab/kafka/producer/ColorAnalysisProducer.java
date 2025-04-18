package com.hijab.kafka.producer;

import com.hijab.kafka.message.KafkaMessageEnvelope;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ColorAnalysisProducer {

    private final KafkaTemplate<String, KafkaMessageEnvelope> kafkaTemplate;

    public void sendAnalysisRequest(KafkaMessageEnvelope envelope) {
        kafkaTemplate.send("color-analysis-topic", envelope);
    }
}