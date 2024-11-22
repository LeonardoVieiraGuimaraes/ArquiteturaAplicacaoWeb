package com.av.first.services;

import org.springframework.stereotype.Service;

// Anotação que indica que esta classe é um serviço do Spring
@Service
public class HelloService {

    // Método que retorna uma saudação personalizada
    public String sayHello(String name) {
        // Retorna uma string formatada com o nome fornecido
        return String.format("Hello %s!", name);
    }
}