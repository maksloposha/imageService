package org.example.backend.service;

import software.amazon.awssdk.services.s3.model.S3Object;
import org.example.backend.imageProcessors.ImageProcessor;
import org.example.backend.imageProcessors.RekognitionProcessor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AmazonService {

    private final ImageProcessor imageProcessor;
    private final RekognitionProcessor rekognitionProcessor;

    public AmazonService(ImageProcessor imageProcessor, RekognitionProcessor rekognitionProcessor) {
        this.imageProcessor = imageProcessor;
        this.rekognitionProcessor = rekognitionProcessor;
    }

    public List<String> getAllFiles() {
        return imageProcessor.getAllFiles();
    }

    public void uploadFile(MultipartFile file) throws IOException {
        imageProcessor.uploadFile(file);
    }

    public List<String> getImagesWithItem(String item) {
        List<S3Object> fileKeys = imageProcessor.getObjects();

        List<S3Object> filteredObjects = fileKeys.parallelStream()
                .filter(s3Object -> rekognitionProcessor.detectLabelsInImage(s3Object.key(), item))
                .toList();

        return filteredObjects.parallelStream()
                .map(imageProcessor::convertObjectToBase64)
                .collect(Collectors.toList());
    }
}
