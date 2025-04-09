package com.hijab.common.sse.controller;

import com.hijab.common.sse.component.SseEmitters;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@RestController
@RequiredArgsConstructor
public class SseController {

    private final SseEmitters sseEmitters;

    @GetMapping("/sse/subscribe")
    public SseEmitter subscribe(@RequestParam String requestId) {
        SseEmitter emitter = new SseEmitter(60 * 1000L); // 1분 타임아웃
        sseEmitters.add(requestId, emitter);

        emitter.onCompletion(() -> sseEmitters.remove(requestId));
        emitter.onTimeout(() -> sseEmitters.remove(requestId));
        return emitter;
    }
}