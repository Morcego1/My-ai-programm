package com.example.aiprogramm;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.Map;

@Service
public class TranslationService {

    private final RestTemplate restTemplate = new RestTemplate();

    public String translateRuToEn(String text) {
        if (text == null || text.trim().isEmpty() || text.matches("^[a-zA-Z0-9\\s.,!?'-]+$")) {
            return text;
        }

        try {
            String url = "https://translate.googleapis.com/translate_a/single?client=gtx&sl=ru&tl=en&dt=t&q={q}";

            // Передаем текст как параметр {q}, RestTemplate сам его закодирует
            java.util.List<?> response = restTemplate.getForObject(url, java.util.List.class, text);

            if (response != null && !response.isEmpty()) {
                java.util.List<?> translations = (java.util.List<?>) response.get(0);

                if (translations != null && !translations.isEmpty()) {
                    java.util.List<?> firstTranslation = (java.util.List<?>) translations.get(0);
                    String translatedText = (String) firstTranslation.get(0);

                    // Проверка на всякий случай: если вернулись проценты, значит перевод не удался
                    if (translatedText != null && translatedText.startsWith("%")) {
                        System.err.println("Google вернул закодированную строку вместо перевода.");
                        return text;
                    }

                    System.out.println("Перевод: '" + text + "' -> '" + translatedText + "'");
                    return translatedText;
                }
            }
        } catch (Exception e) {
            System.err.println("Ошибка при переводе через Google: " + e.getMessage());
        }

        return text;
    }
}
