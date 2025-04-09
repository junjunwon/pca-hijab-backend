package com.hijab.controller;

import com.hijab.image.model.ImageResponseDto;
import com.hijab.image.service.ImageServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/image")
@RequiredArgsConstructor
public class ImageController {
    private final ImageServiceImpl imageServiceImpl;

    @PostMapping("/upload")
    public ResponseEntity<ImageResponseDto> uploadImage(@RequestParam("file") MultipartFile file) {
        ImageResponseDto imageResponseDto  = imageServiceImpl.saveImageToS3(file);
        return ResponseEntity.ok(imageResponseDto);
    }

    @GetMapping
    public ResponseEntity<List<ImageResponseDto>> getAllImages() {
        return ResponseEntity.ok(imageServiceImpl.getAllImages());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ImageResponseDto> getImageById(@PathVariable String id) {
        ImageResponseDto imageResponseDto = imageServiceImpl.getImageById(id);
        return ResponseEntity.ok(imageResponseDto);
    }
}
