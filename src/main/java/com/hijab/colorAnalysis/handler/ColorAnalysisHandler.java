package com.hijab.colorAnalysis.handler;

import com.hijab.colorAnalysis.message.ColorAnalysisMessage;
import com.hijab.colorPalette.model.ColorPaletteResponse;

public interface ColorAnalysisHandler {
    ColorPaletteResponse handle(ColorAnalysisMessage message);
}
