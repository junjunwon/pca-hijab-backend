package com.hijab.image.service;

import com.hijab.image.model.ImageResponseDto;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.List;

public interface ImageService {
    /**
     * save image to local directory
     * @param image
     * @return
     */
    String save(MultipartFile image);

    /**
     * return image as stream
     */
    InputStream loadImageAsStream(String savedPath);
    ImageResponseDto saveImageToS3(MultipartFile file);
    List<ImageResponseDto> getAllImages();
    ImageResponseDto getImageById(String id);
}
