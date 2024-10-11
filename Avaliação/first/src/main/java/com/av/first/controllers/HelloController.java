package com.av.first.controllers;

import com.av.first.services.HelloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

// Anotação que indica que esta classe é um controlador REST
@RestController
public class HelloController {

    // Injeção de dependência do serviço HelloService
    @Autowired
    private HelloService helloService;

    // Endpoint para retornar uma saudação
    @GetMapping("/hello")
    public String hello(@RequestParam(value = "name", defaultValue = "World") String name) {
        // Chama o método sayHello do serviço HelloService, passando o nome como parâmetro
        return helloService.sayHello(name);
    }
}