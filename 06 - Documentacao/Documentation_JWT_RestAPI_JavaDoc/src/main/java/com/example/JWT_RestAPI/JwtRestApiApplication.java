package com.example.JWT_RestAPI;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Classe principal para iniciar a aplicação JWT Rest API com Spring Boot.
 */
@SpringBootApplication(scanBasePackages = {"com.example"})
public class JwtRestApiApplication {

    /**
     * Método principal que inicia a aplicação Spring Boot.
     *
     * @param args argumentos da linha de comando.
     */
    public static void main(String[] args) {
        SpringApplication.run(JwtRestApiApplication.class, args);
    }
}