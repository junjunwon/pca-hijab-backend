package com.hijab.colorAnalysis.service.temp;

import com.hijab.colorPalette.dao.ColorPaletteDAO;
import com.hijab.colorPalette.entity.ColorPalette;
import com.hijab.colorPalette.model.ColorPaletteResponse;
import com.hijab.colorPalette.service.ColorPaletteService;
import com.hijab.external.exchange.PcaClient;
import com.hijab.external.model.SendResponse;
import com.hijab.image.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class ColorAnalysisJobServiceImpl implements ColorAnalysisJobService {

    private final PcaClient pcaClient;
    private final ColorPaletteService colorPaletteService;
    private final ImageService imageService;
    private final ColorPaletteDAO colorPaletteDAO;

    @Override
    @Deprecated
    @Transactional
    public ColorPaletteResponse analyzeImage(String requestId, MultipartFile image) {
        // 이미지 분석 요청
        SendResponse response = pcaClient.analyzePersonalColor(image);

        ColorPalette.PersonalColor color = ColorPalette.PersonalColor.fromValue(response.result());
        ColorPalette colorPalette = colorPaletteDAO.getColorPalette(color);

        //TODO: 실패 케이스 큐 처리
//        MessagePayload<MultipartFile> messagePayload = new MessagePayload<>(image);
//        publisher.publishEvent(new MultipartFileEvent(messagePayload));

        return new ColorPaletteResponse(colorPalette.getPersonalColor().getName(), colorPalette.getDescription());

    }
}
