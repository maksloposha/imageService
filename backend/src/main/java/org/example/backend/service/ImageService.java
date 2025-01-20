package org.example.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class ImageService {
    private final AmazonService amazonClient;

    @Value("${BASE64_PREFIX}")
    private String BASE64_PREFIX;

    @Autowired
    ImageService(AmazonService amazonClient) {
        this.amazonClient = amazonClient;
    }

    public String encodeBase64URL(String image) {
        String base64;
        base64 = BASE64_PREFIX + image;
        return base64;
    }

    public String[] getAllImages() throws IOException {
        List<String> allFiles = amazonClient.getAllFiles();
        return convertObjectListToByteArray(allFiles);
    }

    public String[] getImagesWithItem(String item) {
        List<String> imagesWithItem = amazonClient.getImagesWithItem(item);
        return convertObjectListToByteArray(imagesWithItem);
    }

    public String[] convertObjectListToByteArray(List<String> list) {
        String[] array = new String[list.size()];
        for (int i = 0; i < list.size(); i++) {
            array[i] = encodeBase64URL(list.get(i));
        }
        return array;
    }

    public void saveImageToS3(MultipartFile file) throws IOException {
        amazonClient.uploadFile(file);
    }
}
