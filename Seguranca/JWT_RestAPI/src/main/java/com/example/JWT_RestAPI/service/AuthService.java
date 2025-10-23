package com.example.JWT_RestAPI.service;

import com.example.JWT_RestAPI.security.JwtUtil;
import org.springframework.stereotype.Service;

@Service
/**
 * Camada de serviço para encapsular operações com JWT.
 * <p>
 * Mantém o controller desacoplado da implementação concreta de geração/parsing de tokens.
 */
public class AuthService {
    /**
     * Gera um token para o usuário informado.
     */
    public String generateToken(String username) {
        String token = JwtUtil.generateToken(username);
        return token;
    }

    /**
     * Extrai o username a partir de um token.
     */
    public String extractUsername(String token) {
        String username = JwtUtil.extractUsername(token);
        return username;
    }
}

