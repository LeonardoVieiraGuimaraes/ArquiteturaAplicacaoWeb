package com.example.JWT_RestAPI;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Classe principal para iniciar a aplicação JWT Rest API com Spring Boot.
 * <p>
 * Esta classe contém o método principal que é o ponto de entrada da aplicação Spring Boot.
 * A anotação {@link SpringBootApplication} é usada para habilitar a configuração automática,
 * a varredura de componentes e outras funcionalidades do Spring Boot.
 * </p>
 */
@SpringBootApplication(scanBasePackages = {"com.example"})
public class JwtRestApiApplication {

    /**
     * Método principal que inicia a aplicação Spring Boot.
     * <p>
     * Este método chama {@link SpringApplication#run(Class, String...)} para iniciar a aplicação.
     * </p>
     *
     * @param args argumentos da linha de comando.
     */
    public static void main(String[] args) {
        SpringApplication.run(JwtRestApiApplication.class, args);
    }
}