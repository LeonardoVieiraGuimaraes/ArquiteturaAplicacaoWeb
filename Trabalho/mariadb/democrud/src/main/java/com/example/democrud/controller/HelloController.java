// Define o pacote onde esta classe está localizada
package com.example.democrud.controller;

// Importa as anotações e classes necessárias do Spring Framework
import org.springframework.beans.factory.annotation.Autowired; // Para injeção de dependência
import org.springframework.web.bind.annotation.GetMapping; // Para mapear requisições HTTP GET
import org.springframework.web.bind.annotation.RequestMapping; // Para definir o endpoint base
import org.springframework.web.bind.annotation.RequestParam; // Para capturar parâmetros da requisição
import org.springframework.web.bind.annotation.RestController; // Para indicar que esta classe é um controlador REST

// Importa o serviço que será utilizado pelo controlador
import com.example.democrud.service.HelloService;

// Indica que esta classe é um controlador REST e que responderá a requisições HTTP
@RestController
// Define o endpoint base para todas as requisições deste controlador
@RequestMapping("/hello")
public class HelloController {

    // Injeta automaticamente uma instância de HelloService nesta classe
    @Autowired
    private HelloService helloservice;

    // Mapeia requisições HTTP GET para o método abaixo
    @GetMapping
    // Define o método que será chamado quando uma requisição GET for feita para "/hello"
    // O parâmetro "name" é opcional e tem o valor padrão "World" caso não seja fornecido
    public String hello(@RequestParam(value = "name", defaultValue = "World") String name) {
        // Chama o método getHello do serviço injetado e retorna sua resposta
        return helloservice.getHello(name);
    }
}
