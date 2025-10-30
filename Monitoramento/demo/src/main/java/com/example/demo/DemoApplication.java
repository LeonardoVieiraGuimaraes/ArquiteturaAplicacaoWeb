package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoApplication {

	/**
	 * Ponto de entrada da aplicação Spring Boot.
	 * A anotação {@link SpringBootApplication} habilita auto-configuração,
	 * scan de componentes e configuração baseada em convenção.
	 */
	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

}
