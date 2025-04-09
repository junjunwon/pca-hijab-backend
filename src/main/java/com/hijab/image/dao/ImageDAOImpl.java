package com.hijab.image.dao;

import com.hijab.repository.dynamo.ImageRepository;
import com.hijab.image.entity.Image;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ImageDAOImpl {

    private final ImageRepository imageRepository;

    public Image save(Image image) {
        return imageRepository.save(image);

    }

    public List<Image> findAll() {
        return (List<Image>) imageRepository.findAll();
    }

    public Image findById(String id) {
        return imageRepository.findById(id).orElseThrow(() -> new RuntimeException());
    }
}
