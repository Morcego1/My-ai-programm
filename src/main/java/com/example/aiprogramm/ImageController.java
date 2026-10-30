package com.example.aiprogramm;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.io.File;
import java.nio.file.Files;

import java.util.List;

@RestController
public class ImageController {

    private final ImageGenerationService generationService;

    public ImageController(ImageGenerationService generationService) {
        this.generationService = generationService;
    }

    @GetMapping(value = "/generate", produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<byte[]> generateImage(
            @RequestParam String prompt,
            @RequestParam(defaultValue = "256") int width,
            @RequestParam(defaultValue = "256") int height,
            @RequestParam(defaultValue = "15") int steps) {
        try {
            // Передаем 4 аргумента в сервис
            File imageFile = generationService.generate(prompt, width, height, steps);

            byte[] imageBytes = Files.readAllBytes(imageFile.toPath());

            return ResponseEntity.ok()
                    .contentType(MediaType.IMAGE_PNG)
                    .body(imageBytes);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/history")
    public List<GenerationHistory>
    getHistory() {
        return generationService.getHistory();
    }
        @GetMapping("/images/{filename:.+}")
        public ResponseEntity<Resource> serveFile(@PathVariable String filename) {
            try {
                Path file = Paths.get("images").resolve(filename);
                Resource resource = new UrlResource(file.toUri());

                if (resource.exists() || resource.isReadable()) {
                    return ResponseEntity.ok()
                            .header(HttpHeaders.CONTENT_TYPE, "image/png")
                            .body(resource);
                } else {
                    return ResponseEntity.notFound().build();
                }
            } catch (Exception e) {
                return ResponseEntity.internalServerError().build();
            }
        }


    }



