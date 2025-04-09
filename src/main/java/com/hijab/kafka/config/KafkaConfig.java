package com.hijab.kafka.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hijab.kafka.message.KafkaMessageEnvelope;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaConfig {

    @Bean
    public ProducerFactory<String, KafkaMessageEnvelope> producerFactory(ObjectMapper objectMapper) {
        Map<String, Object> configProps = new HashMap<>();
        configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092"); // 실제 환경에 맞게 변경
        configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);

        // JsonSerializer가 기본 ObjectMapper 대신 커스텀 ObjectMapper를 사용하도록 설정
        JsonSerializer<KafkaMessageEnvelope> jsonSerializer = new JsonSerializer<>(objectMapper);
        jsonSerializer.setAddTypeInfo(false); // 불필요한 @class 정보 제거

        return new DefaultKafkaProducerFactory<>(configProps, new StringSerializer(), jsonSerializer);
    }

    @Bean
    public KafkaTemplate<String, KafkaMessageEnvelope> kafkaTemplate(ProducerFactory<String, KafkaMessageEnvelope> producerFactory) {
        return new KafkaTemplate<>(producerFactory);
    }
}
