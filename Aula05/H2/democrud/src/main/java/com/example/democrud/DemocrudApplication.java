// Define o pacote onde esta classe está localizada
package com.example.democrud;

// Importa a classe SpringApplication, que é usada para iniciar a aplicação
import org.springframework.boot.SpringApplication;

// Importa a anotação SpringBootApplication, que marca esta classe como a principal da aplicação Spring Boot
import org.springframework.boot.autoconfigure.SpringBootApplication;

// Marca esta classe como a principal da aplicação Spring Boot
@SpringBootApplication
public class DemocrudApplication {

	// Método principal (main) que é o ponto de entrada da aplicação
	public static void main(String[] args) {
		// Inicia a aplicação Spring Boot usando a classe DemocrudApplication
		SpringApplication.run(DemocrudApplication.class, args);
	}

}
