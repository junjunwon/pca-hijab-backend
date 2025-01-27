package com.hijab.image.service;

import com.amazonaws.services.s3.AmazonS3;
import com.hijab.common.aws.model.PutObjectAwsRequest;
import com.hijab.common.aws.properties.AwsProperties;
import com.hijab.common.util.DateUtil;
import com.hijab.image.entity.Image;
import com.hijab.image.model.ImageResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class ImageService {

    private final ImageDAOService imageDaoService;
    private final AmazonS3 s3Client;
    private final AwsProperties awsProperties;

    public ImageResponseDto saveImage(MultipartFile file) {
        String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
        Path tempFile = null;

        try {
            tempFile = Files.createTempFile(UUID.randomUUID().toString(), file.getOriginalFilename());
            Files.write(tempFile, file.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }

        Date inputExpirationDate = new Date(System.currentTimeMillis() + TimeUnit.DAYS.toMillis(7));
        s3Client.putObject(
                new PutObjectAwsRequest(
                        awsProperties.getS3BucketName(),
                        fileName,
                        tempFile.toFile(),
                        inputExpirationDate
                )
        );

        URL fileUrl = s3Client.getUrl(awsProperties.getS3BucketName(), fileName);

        Image image = new Image(UUID.randomUUID().toString(), fileName,
                fileUrl.toString(), DateUtil.convertToLocalDateTime(inputExpirationDate));

        try {
            Files.delete(tempFile);
        } catch (IOException e) {
            e.printStackTrace();
        }

        imageDaoService.save(image);
        return new ImageResponseDto(image.getId(), image.getName(), image.getUrl(), image.getExpiryDate());
    }

    public List<ImageResponseDto> getAllImages() {
        List<Image> imageList = imageDaoService.findAll();

        return imageList.stream().map(ImageResponseDto::of).toList();
    }

    public ImageResponseDto getImageById(String id) {
        Image image = imageDaoService.findById(id);
        if (image.getExpiryDate().isBefore(LocalDateTime.now())) {
            throw new RuntimeException();
        }
        return new ImageResponseDto(image.getId(), image.getName(), image.getUrl(), image.getExpiryDate());
    }
}
