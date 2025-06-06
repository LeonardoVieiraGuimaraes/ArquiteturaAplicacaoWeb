package com.example.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PublicController {

    @GetMapping("/public")
    public String publicEndpoint() {
        return "Endpoint público - não requer autenticação";
    }
}
