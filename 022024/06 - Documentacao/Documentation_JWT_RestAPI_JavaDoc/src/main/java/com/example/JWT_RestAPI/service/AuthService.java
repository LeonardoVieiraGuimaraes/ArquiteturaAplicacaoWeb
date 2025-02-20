package com.example.JWT_RestAPI.service;

import com.example.JWT_RestAPI.security.JwtUtil;
import org.springframework.stereotype.Service;

/**
 * Serviço de autenticação para geração e extração de tokens JWT.
 */
@Service
public class AuthService {

    /**
     * Gera um token JWT para o usuário fornecido.
     *
     * @param username o nome de usuário para o qual o token será gerado.
     * @return o token JWT gerado.
     */
    public String generateToken(String username) {
        String token = JwtUtil.generateToken(username);
        return token;
    }

    /**
     * Extrai o nome de usuário de um token JWT fornecido.
     *
     * @param token o token JWT do qual o nome de usuário será extraído.
     * @return o nome de usuário extraído do token.
     */
    public String extractUsername(String token) {
        String username = JwtUtil.extractUsername(token);
        return username;
    }
}