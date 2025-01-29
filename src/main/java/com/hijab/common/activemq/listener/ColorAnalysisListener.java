package com.hijab.common.activemq.listener;

import com.hijab.common.activemq.message.MessagePayload;
import com.hijab.common.exception.CustomException;
import com.hijab.common.exception.ExceptionStatus;
import com.hijab.external.exchange.PcaClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Component
@RequiredArgsConstructor
public class ColorAnalysisListener {

    private final PcaClient pcaClient;

    @JmsListener(destination = "multipartAnalysisQueue")
    public void multipartAnalysisQueue(MessagePayload<MultipartFile> payload) {
        MultipartFile multipartFile = payload.data();
        try {
            log.info("발송서버로 알림 발송 시도....");
//            String resultPca = pcaClient.analyzePersonalColor(multipartFile);
//            log.info("발송서버로부터 전달받은 결과값 : {}", resultPca);
        } catch (CustomException e) {
            // 실패한 메시지를 재시도하거나 다른 큐에 저장할 수 있습니다.
            log.error("알림발송 실패", e);
            throw new CustomException(ExceptionStatus.SERVER_ERROR_PERSONAL_COLOR);
        }
    }
}
