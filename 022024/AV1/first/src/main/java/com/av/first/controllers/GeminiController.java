package com.av.first.controllers;

import com.av.first.services.GeminiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

// Anotação que indica que esta classe é um controlador REST
@RestController
public class GeminiController {

    // Injeção de dependência do serviço GeminiService
    @Autowired
    private GeminiService geminiService;

    // Endpoint para gerar conteúdo usando a API do Gemini
    @GetMapping("/chatbot")
    public String generateContent() {
        // Chama o método generateContent do serviço GeminiService e retorna o resultado
        return geminiService.generateContent();
    }
}