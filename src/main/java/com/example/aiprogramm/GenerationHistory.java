package com.example.aiprogramm;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
        @Table(name = "generation_history")
public class GenerationHistory {

    @Id
            @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 1000)
    private String prompt;

    @Column(name = "image_path")
    private String imagePath;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    // Пустой конструктор обязателен для JPA
    public GenerationHistory() {}

    public GenerationHistory(String prompt, String imagePath) {
        this.prompt = prompt;
        this.imagePath = imagePath;
        this.createdAt = LocalDateTime.now();
    }

    // Геттеры и сеттеры
    public Long getId() { return id; }
    public String getPrompt() { return prompt; }
    public void setPrompt(String prompt) { this.prompt = prompt; }
    public String getImagePath() { return imagePath; }
    public void setImagePath(String imagePath) { this.imagePath = imagePath; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
