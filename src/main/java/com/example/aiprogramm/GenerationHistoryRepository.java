package com.example.aiprogramm;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GenerationHistoryRepository extends JpaRepository<GenerationHistory, Long> {
    // Spring сам сгенерирует SQL для получения последних 10 записей
    List<GenerationHistory> findTop10ByOrderByCreatedAtDesc();
}
