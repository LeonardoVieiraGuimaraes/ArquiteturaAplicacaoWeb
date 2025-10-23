package com.example.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoController {

    @GetMapping("/public")
    public String publico() {
        return "Acesso público";
    }

    @GetMapping("/user")
    public String usuario() {
        return "Acesso autenticado";
    }

    @GetMapping("/admin")
    public String admin() {
        return "Acesso restrito a admins";
    }
}
