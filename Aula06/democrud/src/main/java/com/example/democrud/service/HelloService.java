package com.example.democrud.service; 
// Define o pacote onde esta classe está localizada.

import org.springframework.stereotype.Service; 
// Importa a anotação @Service, que indica que esta classe é um serviço gerenciado pelo Spring.

@Service 
// Marca a classe como um serviço do Spring, permitindo que ela seja injetada em outras partes do código.
public class HelloService {

    public String getHello(String name) {
        // Método público que recebe um nome como parâmetro e retorna uma saudação personalizada.
        return "Hello, " + name + "!"; 
        // Concatena a string "Hello, " com o nome fornecido e um ponto de exclamação.
    }
}
