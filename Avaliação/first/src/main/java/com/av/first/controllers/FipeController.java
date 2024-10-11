package com.av.first.controllers;

import com.av.first.services.FipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

// Anotação que indica que esta classe é um controlador REST
@RestController
public class FipeController {

    // Injeção de dependência do serviço FipeService
    @Autowired
    private FipeService fipeService;

    // Endpoint para consultar marcas
    @GetMapping("/consultarMarcas")
    public String consultarMarcas() {
        // Chama o método consultarMarcas do serviço FipeService
        return fipeService.consultarMarcas();
    }

    // Endpoint para consultar modelos de uma marca específica
    @GetMapping("/consultarModelos/{marca}")
    public String consultarModelos(@PathVariable int marca) {
        // Chama o método consultarModelos do serviço FipeService, passando a marca como parâmetro
        return fipeService.consultarModelos(marca);
    }

    // Endpoint para consultar anos de um modelo específico de uma marca específica
    @GetMapping("/consultarAnos/{marca}/{modelo}")
    public String consultarAnos(@PathVariable int marca, @PathVariable int modelo) {
        // Chama o método consultarAnos do serviço FipeService, passando a marca e o modelo como parâmetros
        return fipeService.consultarAnos(marca, modelo);
    }

    // Endpoint para consultar o valor de um modelo específico de uma marca específica em um ano específico
    @GetMapping("/consultarValor/{marca}/{modelo}/{ano}")
    public String consultarValor(@PathVariable int marca, @PathVariable int modelo, @PathVariable String ano) {
        // Chama o método consultarValor do serviço FipeService, passando a marca, o modelo e o ano como parâmetros
        return fipeService.consultarValor(marca, modelo, ano);
    }
}