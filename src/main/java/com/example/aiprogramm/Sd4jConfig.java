package com.example.aiprogramm;

import ai.onnxruntime.OrtEnvironment;
import ai.onnxruntime.OrtSession;
import com.oracle.labs.mlrg.sd4j.SD4J;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

@Configuration
public class Sd4jConfig {

    @Bean
    public SD4J diffusionPipeline() throws Exception {
        // 1. Загружаем ortextensions.dll из classpath во временный файл
        try (InputStream in = getClass().getResourceAsStream("/native/ortextensions.dll")) {
            if (in == null) {
                throw new IllegalStateException("ortextensions.dll не найден в classpath (native/)");
            }
            Path temp = Files.createTempFile("ortextensions", ".dll");
            Files.copy(in, temp, StandardCopyOption.REPLACE_EXISTING);
            temp.toFile().deleteOnExit();
            System.load(temp.toAbsolutePath().toString());
        }

        // 2. Путь к папке с моделью, которую вы скачали
        String modelPath = "C:\\Users\\SANYA\\stable-diffusion-v1-5";

        // 3. Создаем пайплайн
        OrtEnvironment env = OrtEnvironment.getEnvironment();
        OrtSession.SessionOptions options = new OrtSession.SessionOptions();
        // options.addCUDA(0); // Раскомментируйте, если у вас есть NVIDIA GPU

        return SD4J.factory(modelPath, true);
    }
}
