package com.example.demo.service;

import org.springframework.stereotype.Service;

@Service // Indica que esta classe é um serviço gerenciado pelo Spring
public class HelloWorldService {

    public String getHelloMessage() {
        // Retorna a mensagem "Hello World"
        return "Hello World";
    }
}
