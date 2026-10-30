package com.example.aiprogramm;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class StartupRunner implements CommandLineRunner {

    private final ImageGenerationService generationService;

    public StartupRunner(ImageGenerationService generationService) {
        this.generationService = generationService;
    }

     @Override
    public void run(String... args) throws Exception {
//        System.out.println("=== ЗАПУСК ТЕСТОВОЙ ГЕНЕРАЦИИ ===");
//        // Простой промпт для первого теста
//        generationService.generate("a cute cat, realistic, 4k");
    }
}
