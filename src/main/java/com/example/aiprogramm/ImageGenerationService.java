package com.example.aiprogramm;

import com.oracle.labs.mlrg.sd4j.SD4J;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;
import java.util.UUID; // Не забудьте импорт

@Service
public class ImageGenerationService {

    private final SD4J sd4j;
    private final GenerationHistoryRepository historyRepository;
    private final TranslationService translationService;

    // Добавляем TranslationService в аргументы конструктора
    public ImageGenerationService(SD4J sd4j,
                                  GenerationHistoryRepository historyRepository,
                                  TranslationService translationService) {
        this.sd4j = sd4j;
        this.historyRepository = historyRepository;
        this.translationService = translationService; // Теперь всё присвоится правильно
    }

    // Добавляем новые параметры в сигнатуру метода
    public File generate(String prompt, int width, int height, int steps) throws Exception {
        // Переводим промпт на английский
        String englishPrompt = translationService.translateRuToEn(prompt);

        System.out.println("Начинаю генерацию (оригинал: '" + prompt + "', перевод: '" + englishPrompt + "')");

        // ... дальше используем englishPrompt вместо prompt при вызове sd4j.generateImage ...
        SD4J.ImageSize imageSize = new SD4J.ImageSize(width, height);
        List<SD4J.SDImage> images = sd4j.generateImage(
                42, englishPrompt, "", 7.5f, steps, imageSize, 1
        );


        if (images == null || images.isEmpty()) {
            throw new RuntimeException("Генерация не вернула ни одного изображения");
        }

        SD4J.SDImage sdImage = images.get(0);

        File imagesDir = new File("images");
        if (!imagesDir.exists()) {
            imagesDir.mkdirs();
        }

        String uniqueFileName = java.util.UUID.randomUUID().toString() + ".png";
        File output = new File(imagesDir, uniqueFileName);
        SD4J.save(sdImage, output);

        GenerationHistory history = new GenerationHistory(prompt, uniqueFileName);
        historyRepository.save(history);
        System.out.println("Запись сохранена в базу данных! Файл: " + uniqueFileName);

        return output;
    }

    public List<GenerationHistory> getHistory() {
        return historyRepository.findTop10ByOrderByCreatedAtDesc();
    }
}

