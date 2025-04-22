package com.hijab.controller;

import com.hijab.colorAnalysis.service.ColorAnalysisRecordService;
import com.hijab.config.TestSecurityConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ColorAnalysisController.class)
@Import(TestSecurityConfig.class)
@ActiveProfiles("test")
class ColorAnalysisControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ColorAnalysisRecordService colorAnalysisRecordService;

    @Test
    void analyzeImage_shouldCallServiceAndReturnOk() throws Exception {
        // given
        String requestId = "test-request-id";
        MockMultipartFile imageFile = new MockMultipartFile(
                "image", "test.jpg", "image/jpeg", "fake-image-content".getBytes()
        );

        // when & then
        mockMvc.perform(multipart("/api/color/analysis")
                        .file(imageFile)
                        .param("requestId", requestId)
                        .contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(status().isOk());

        // 서비스가 요청대로 호출됐는지 확인
        verify(colorAnalysisRecordService).analyzeImage(eq(requestId), any());
    }
}