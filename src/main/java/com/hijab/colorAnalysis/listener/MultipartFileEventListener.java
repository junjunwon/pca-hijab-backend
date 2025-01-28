package com.hijab.colorAnalysis.listener;

import com.hijab.colorAnalysis.event.MultipartFileEvent;
import com.hijab.common.activemq.message.MessagePayload;
import com.hijab.external.exchange.PcaClient;
import lombok.RequiredArgsConstructor;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;
import org.springframework.web.multipart.MultipartFile;

@Component
@RequiredArgsConstructor
public class MultipartFileEventListener {
    private final JmsTemplate jmsTemplate;
    private final PcaClient pcaClient;

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void MultipartFileEventListener(MultipartFileEvent event) {
        MessagePayload<MultipartFile> payload = event.getPayload();
        String resultPca = pcaClient.uploadFile(payload.data());
        System.out.println("발송서버로부터 전달받은 결과값 : " + resultPca);
//        jmsTemplate.convertAndSend("multipartAnalysisQueue", payload);
    }
}
