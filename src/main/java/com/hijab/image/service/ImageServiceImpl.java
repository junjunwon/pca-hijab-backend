package com.hijab.image.service;

import com.amazonaws.services.s3.AmazonS3;
import com.hijab.common.aws.model.PutObjectAwsRequest;
import com.hijab.common.aws.properties.AwsProperties;
import com.hijab.common.util.DateUtil;
import com.hijab.image.dao.ImageDAOImpl;
import com.hijab.image.entity.Image;
import com.hijab.image.model.ImageResponseDto;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class ImageServiceImpl implements ImageService{

    private final ImageDAOImpl imageDaoImpl;
    private final AmazonS3 s3Client;
    private final AwsProperties awsProperties;

    private final Path uploadDir = Paths.get("uploads");

    public ImageServiceImpl(ImageDAOImpl imageDaoImpl,
                            AmazonS3 s3Client, AwsProperties awsProperties) throws IOException {
        this.imageDaoImpl = imageDaoImpl;
        this.s3Client = s3Client;
        this.awsProperties = awsProperties;

        if (!Files.exists(uploadDir)) {
            Files.createDirectories(uploadDir);
        }
    }

    @Override
    public String save(MultipartFile image) {
        try {
            String filename = UUID.randomUUID() + "_" + image.getOriginalFilename();
            Path filePath = uploadDir.resolve(filename);
            Files.copy(image.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
            return filePath.toString(); // 저장된 경로
        } catch (IOException e) {
            throw new RuntimeException("Failed to save image file", e);
        }
    }

    @Override
    public InputStream loadImageAsStream(String savedPath) {
        try {
            Path path = Paths.get(savedPath);
            return Files.newInputStream(path);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load image as stream", e);
        }
    }

    @Override
    public ImageResponseDto saveImageToS3(MultipartFile file) {
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

        imageDaoImpl.save(image);
        return new ImageResponseDto(image.getId(), image.getName(), image.getUrl(), image.getExpiryDate());
    }

    @Override
    public List<ImageResponseDto> getAllImages() {
        List<Image> imageList = imageDaoImpl.findAll();

        return imageList.stream().map(ImageResponseDto::of).toList();
    }

    @Override
    public ImageResponseDto getImageById(String id) {
        Image image = imageDaoImpl.findById(id);
        if (image.getExpiryDate().isBefore(LocalDateTime.now())) {
            throw new RuntimeException();
        }
        return new ImageResponseDto(image.getId(), image.getName(), image.getUrl(), image.getExpiryDate());
    }
}
