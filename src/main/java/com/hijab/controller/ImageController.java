package com.hijab.controller;

import com.hijab.image.model.ImageResponseDto;
import com.hijab.image.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/image")
@RequiredArgsConstructor
public class ImageController {
    private final ImageService imageService;

    @PostMapping("/upload")
    public ResponseEntity<ImageResponseDto> uploadImage(@RequestParam("file") MultipartFile file) {
        ImageResponseDto imageResponseDto  = imageService.saveImage(file);
        return ResponseEntity.ok(imageResponseDto);
    }

    @GetMapping
    public ResponseEntity<List<ImageResponseDto>> getAllImages() {
        return ResponseEntity.ok(imageService.getAllImages());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ImageResponseDto> getImageById(@PathVariable String id) {
        ImageResponseDto imageResponseDto = imageService.getImageById(id);
        return ResponseEntity.ok(imageResponseDto);
    }
}
