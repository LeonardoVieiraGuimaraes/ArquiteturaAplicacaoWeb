package com.example.JWT_RestAPI.model;

/**
 * DTO simples para capturar credenciais de login.
 * <p>
 * Observação: Inclui construtor sem argumentos para compatibilidade com Jackson
 * ao desserializar o corpo da requisição JSON em @RequestBody.
 */
public class LoginRequest {
    private String username;
    private String password;

    /**
     * Construtor padrão necessário para desserialização (Jackson).
     */
    public LoginRequest() {
    }

    public LoginRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }

    /**
     * Nome de usuário.
     */
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Senha do usuário.
     */
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

