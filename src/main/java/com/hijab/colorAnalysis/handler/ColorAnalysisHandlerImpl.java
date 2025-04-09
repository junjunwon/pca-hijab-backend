package com.hijab.colorAnalysis.handler;

import com.hijab.colorAnalysis.message.ColorAnalysisMessage;
import com.hijab.colorPalette.dao.ColorPaletteDAO;
import com.hijab.colorPalette.entity.ColorPalette;
import com.hijab.colorPalette.model.ColorPaletteResponse;
import com.hijab.common.util.MultipartFileUtils;
import com.hijab.external.exchange.PcaClient;
import com.hijab.external.model.SendResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
@RequiredArgsConstructor
public class ColorAnalysisHandlerImpl implements ColorAnalysisHandler {

    private final PcaClient pcaClient;
    private final ColorPaletteDAO colorPaletteDAO;

    @Override
    public ColorPaletteResponse handle(ColorAnalysisMessage message) {
        // 이미지 분석 요청
        String imagePath = message.getImagePath();
        MultipartFile image = MultipartFileUtils.fromPath(imagePath);
        SendResponse response = pcaClient.analyzePersonalColor(image);

        ColorPalette.PersonalColor color = ColorPalette.PersonalColor.fromValue(response.result());
        ColorPalette colorPalette = colorPaletteDAO.getColorPalette(color);

        return new ColorPaletteResponse(colorPalette.getPersonalColor().getName(), colorPalette.getDescription());
    }
}
