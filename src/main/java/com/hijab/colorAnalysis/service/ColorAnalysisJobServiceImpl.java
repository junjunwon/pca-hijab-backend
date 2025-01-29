package com.hijab.colorAnalysis.service;

import com.hijab.colorPalette.entity.ColorPalette;
import com.hijab.colorPalette.model.ColorPaletteResponse;
import com.hijab.colorPalette.service.ColorPaletteService;
import com.hijab.external.exchange.PcaClient;
import com.hijab.external.model.SendResponse;
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
    private final ColorPaletteService colorPaletteService;

    @Override
    @Transactional
    public ColorPaletteResponse analyzeImage(String email, MultipartFile image) {
        // 이미지 분석 로직
        SendResponse response = pcaClient.analyzePersonalColor(image);
        ColorPalette.PersonalColor color = ColorPalette.PersonalColor.fromValue(response.result());
        return colorPaletteService.getColorPalette(color);
        //실패할 경우 큐처리하는게 좋겠다.
//        MessagePayload<MultipartFile> messagePayload = new MessagePayload<>(image);
//        publisher.publishEvent(new MultipartFileEvent(messagePayload));
    }
}
