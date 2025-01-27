package com.hijab.common.activemq.converter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hijab.common.activemq.message.MessagePayload;
import jakarta.jms.JMSException;
import jakarta.jms.Message;
import jakarta.jms.Session;
import jakarta.jms.TextMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class MessagePayloadMessageConverter implements MessageConverter {

    private final ObjectMapper mapper;

    @Override
    public Message toMessage(Object object, Session session) throws JMSException {
        MessagePayload<?> messagePayload = (MessagePayload<?>) object;
        String payload = null;

        try {
            payload = mapper.writeValueAsString(messagePayload);
            log.info("outbound json='{}'", payload);
        } catch (JsonProcessingException e) {
            log.error("error converting from MessagePayload", e);
        }

        TextMessage message = session.createTextMessage();
        message.setText(payload);

        return message;
    }

    @Override
    public Object fromMessage(Message message) throws JMSException {
        TextMessage textMessage = (TextMessage) message;
        String payload = textMessage.getText();
        log.info("inbound json='{}'", payload);

        MessagePayload<?> messagePayload = null;
        try {
            messagePayload = mapper.readValue(payload, MessagePayload.class);
        } catch (Exception e) {
            log.error("error converting to MessagePayload", e);
        }

        return messagePayload;
    }
}