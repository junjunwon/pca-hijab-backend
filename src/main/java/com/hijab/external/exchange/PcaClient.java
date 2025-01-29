package com.hijab.external.exchange;

import com.hijab.external.model.SendResponse;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.service.annotation.PostExchange;

public interface PcaClient {
    @PostExchange("/analyze")  // baseUrl이 config에 설정되어 있으므로 경로만 지정
    SendResponse analyzePersonalColor(@RequestPart("file") MultipartFile file);

}
