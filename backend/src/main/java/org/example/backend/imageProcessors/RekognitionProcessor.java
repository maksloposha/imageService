package org.example.backend.imageProcessors;

import org.example.backend.aws.AmazonClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.rekognition.RekognitionClient;
import software.amazon.awssdk.services.rekognition.model.*;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Service
public class RekognitionProcessor {

    @Value("${MAX_NUMBER_OF_LABELS}")
    private int maxNumberOfLabels;

    @Value("${CONFIDENCE_THRESHOLD}")
    private float confidenceThreshold;

    private final RekognitionClient rekognitionClient;
    private final AmazonClient amazonClient;
    private final Map<String, List<String>> labelCache = new ConcurrentHashMap<>();

    public RekognitionProcessor(RekognitionClient rekognitionClient, AmazonClient amazonClient) {
        this.rekognitionClient = rekognitionClient;
        this.amazonClient = amazonClient;
    }

    public boolean detectLabelsInImage(String key, String item) {
        if (labelCache.containsKey(key)) {
            return labelCache.get(key).contains(item.toLowerCase());
        }

        DetectLabelsRequest detectLabelsRequest = DetectLabelsRequest.builder()
                .image(Image.builder()
                        .s3Object(S3Object.builder()
                                .bucket(amazonClient.getBucketName())
                                .name(key)
                                .build())
                        .build())
                .maxLabels(maxNumberOfLabels)
                .minConfidence(confidenceThreshold)
                .build();


        DetectLabelsResponse detectLabelsResponse = rekognitionClient.detectLabels(detectLabelsRequest);


        List<String> labels = detectLabelsResponse.labels().stream()
                .map(label -> label.name().toLowerCase())
                .collect(Collectors.toList());


        labelCache.put(key, labels);

        return labels.stream()
                .anyMatch(label -> label.equalsIgnoreCase(item));
    }
}
