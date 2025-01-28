package com.hijab.colorAnalysis.event;

import com.hijab.common.activemq.message.MessagePayload;
import lombok.Getter;

@Getter
public class MultipartFileEvent {
    private final MessagePayload payload;

    public MultipartFileEvent(MessagePayload payload) {
        this.payload = payload;
    }
}
