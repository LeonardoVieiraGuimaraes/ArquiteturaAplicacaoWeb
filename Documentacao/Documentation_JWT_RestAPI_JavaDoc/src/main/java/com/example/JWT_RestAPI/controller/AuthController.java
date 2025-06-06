package com.example.JWT_RestAPI.controller;

import com.example.JWT_RestAPI.model.LoginRequest;
import com.example.JWT_RestAPI.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

/**
 * Controlador de autenticação para lidar com requisições de login e extração de nome de usuário.
 */
@RestController
public class AuthController {

    @Autowired
    private AuthService authService;

    /**
     * Endpoint para login.
     *
     * @param request a requisição de login contendo nome de usuário e senha.
     * @return o token JWT gerado.
     */
    @PostMapping("/login")
    public String login(@RequestBody LoginRequest request) {
        String token = authService.generateToken(request.getUsername());
        return token;
    }

    /**
     * Endpoint para extrair o nome de usuário de um token JWT.
     *
     * @param token o token JWT.
     * @return o nome de usuário extraído do token.
     */
    @GetMapping("/username/{token}")
    public String extractUsername(@PathVariable String token) {
        String username = authService.extractUsername(token);
        return username;
    }

    /**
     * Endpoint acessível por qualquer usuário autenticado.
     *
     * @param authentication o objeto de autenticação.
     * @return o nome do usuário autenticado.
     */
    @GetMapping("/user")
    public String getUser(Authentication authentication) {
        return "User: " + authentication.getName();
    }

    /**
     * Endpoint acessível apenas por administradores.
     *
     * @param authentication o objeto de autenticação.
     * @return o nome do administrador autenticado.
     */
    @Secured("ADMIN")
    @GetMapping("/admin")
    public String onlyAdmin(Authentication authentication) {
        return "Admin: " + authentication.getName();
    }
}