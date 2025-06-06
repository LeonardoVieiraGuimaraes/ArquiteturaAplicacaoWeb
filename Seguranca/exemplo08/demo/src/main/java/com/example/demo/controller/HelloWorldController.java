package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.service.HelloWorldService;

@RestController // Indica que esta classe é um controlador REST
@RequestMapping("/api") // Define o prefixo para os endpoints deste controlador
@PreAuthorize("isAuthenticated()")
public class HelloWorldController {

    @Autowired // Injeta automaticamente a dependência do serviço HelloWorldService
    private HelloWorldService helloWorldService;

    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @GetMapping("/hello") // Define o endpoint para retornar a mensagem "Hello World"
    public String helloWorld() {
        // Chama o serviço para obter a mensagem "Hello World"
        return helloWorldService.getHelloMessage();
    }
}
