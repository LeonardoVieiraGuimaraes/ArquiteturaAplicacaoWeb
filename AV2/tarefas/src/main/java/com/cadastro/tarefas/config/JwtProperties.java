package com.cadastro.tarefas.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import lombok.Data;

@Configuration
@ConfigurationProperties(prefix = "jwt")
@Data
public class JwtProperties {
    private KeyConfig publicKey;
    private KeyConfig privateKey;

    @Data
    public static class KeyConfig {
        private String key;
    }
}