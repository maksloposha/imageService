package org.example.backend.imageProcessors;

import org.example.backend.aws.AmazonClient;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.model.*;
import software.amazon.awssdk.utils.IoUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ImageProcessor {

    private final AmazonClient amazonClient;

    public ImageProcessor(AmazonClient amazonClient) {
        this.amazonClient = amazonClient;
    }

    public void uploadFile(MultipartFile file) throws IOException {
        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                .bucket(amazonClient.getBucketName())
                .key(file.getOriginalFilename())
                .contentType(file.getContentType())
                .build();

        try (InputStream inputStream = file.getInputStream()) {
            amazonClient.getS3client().putObject(putObjectRequest, RequestBody.fromInputStream(inputStream, file.getSize()));
        }
    }

    public List<String> getAllFiles() {
        return amazonClient.listS3Objects()
                .parallelStream()
                .map(this::convertObjectToBase64)
                .collect(Collectors.toList());
    }

    public List<S3Object> getObjects() {
        return amazonClient.listS3Objects()
                .parallelStream()
                .collect(Collectors.toList());
    }

    public String convertObjectToBase64(S3Object s3Object) {
        try {
            return downloadAndConvertImageToBase64(s3Object.key());
        } catch (IOException e) {
            throw new RuntimeException("Failed to convert image to Base64: " + s3Object.key(), e);
        }
    }

    private String downloadAndConvertImageToBase64(String key) throws IOException {
        GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                .bucket(amazonClient.getBucketName())
                .key(key)
                .build();

        try (InputStream inputStream = amazonClient.getS3client().getObject(getObjectRequest)) {
            byte[] bytes = IoUtils.toByteArray(inputStream);
            return Base64.getEncoder().encodeToString(bytes);
        }
    }


}

