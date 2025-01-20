package org.example.backend.controller;

import org.example.backend.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController()
@RequestMapping("/images")
public class ImageController {
    private final ImageService imageService;

    @Autowired
    ImageController(ImageService imageService) {
        this.imageService = imageService;
    }

    @PostMapping("/uploadFile")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {
        try {
            imageService.saveImageToS3(file);
            return ResponseEntity.ok("File uploaded successfully.");
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Failed to read the file. Please check the file and try again.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An unexpected error occurred while uploading the file.");
        }
    }

    @GetMapping("/getAll")
    public ResponseEntity<?> getImages() {
        try {
            String[] imageUrls = imageService.getAllImages();
            if (imageUrls.length == 0) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("No images found.");
            }
            return ResponseEntity.ok(imageUrls);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to retrieve images. Please try again later.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An unexpected error occurred while retrieving images.");
        }
    }

    @GetMapping("/getAllWithItem/{imageItem}")
    public ResponseEntity<?> getImageWithItem(@PathVariable String imageItem) {
        try {
            String[] imageUrls = imageService.getImagesWithItem(imageItem);
            if (imageUrls.length == 0) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("No images found with the item: " + imageItem);
            }
            return ResponseEntity.ok(imageUrls);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An unexpected error occurred while searching for images with item: " + imageItem);
        }
    }
}
