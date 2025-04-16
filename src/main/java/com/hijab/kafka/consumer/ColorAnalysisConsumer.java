package com.hijab.kafka.consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hijab.colorAnalysis.handler.ColorAnalysisHandler;
import com.hijab.colorAnalysis.message.ColorAnalysisMessage;
import com.hijab.colorPalette.model.ColorPaletteResponse;
import com.hijab.common.exception.CustomException;
import com.hijab.common.sse.component.SseEmitters;
import com.hijab.kafka.message.KafkaMessageEnvelope;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import static com.hijab.common.exception.ExceptionStatus.NOT_FOUND_ANALYSIS_MESSAGE;

@Slf4j
@Component
@RequiredArgsConstructor
public class ColorAnalysisConsumer {

    private final ObjectMapper objectMapper;
    private final ColorAnalysisHandler colorAnalysisHandler;
    private final SseEmitters sseEmitters;

    @KafkaListener(topics = "color-analysis-topic", groupId = "color-analysis-group")
    public void listen(String envelopeJson) {
        log.info("📥 Received envelope: {}", envelopeJson);
        try {
            KafkaMessageEnvelope envelope = objectMapper.readValue(envelopeJson, KafkaMessageEnvelope.class);

            log.info("📥 Received envelope: {}", envelope);

            ColorAnalysisMessage message = objectMapper.convertValue(envelope.getPayload(), ColorAnalysisMessage.class);
            ColorPaletteResponse response = colorAnalysisHandler.handle(message);

            sseEmitters.send(message.getRequestId(),
                    KafkaMessageEnvelope.MessageType.COLOR_ANALYSIS.name(),
                    response);

        } catch (Exception e) {
            log.error("❌ Kafka 메시지 파싱 실패", e);
            throw new CustomException(NOT_FOUND_ANALYSIS_MESSAGE);
        }
    }
}