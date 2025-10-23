package com.example.JWT_RestAPI;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
/**
 * Aplicação principal Spring Boot.
 * <p>
 * Responsável por inicializar o contexto da aplicação e subir o servidor embutido.
 */
public class JwtRestApiApplication {

    /**
     * Ponto de entrada da aplicação.
     * @param args argumentos de linha de comando (não utilizados)
     */
    public static void main(String[] args) {
        SpringApplication.run(JwtRestApiApplication.class, args);
    }
}
