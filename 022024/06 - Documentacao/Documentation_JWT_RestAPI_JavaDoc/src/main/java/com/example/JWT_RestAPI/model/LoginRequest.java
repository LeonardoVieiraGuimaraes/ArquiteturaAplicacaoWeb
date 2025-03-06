package com.example.JWT_RestAPI.model;

/**
 * Modelo de requisição de login contendo nome de usuário e senha.
 */
public class LoginRequest {
    private String username;
    private String password;

    /**
     * Construtor para criar uma nova requisição de login.
     *
     * @param username o nome de usuário.
     * @param password a senha.
     */
    public LoginRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }

    /**
     * Obtém o nome de usuário.
     *
     * @return o nome de usuário.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Define o nome de usuário.
     *
     * @param username o nome de usuário.
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Obtém a senha.
     *
     * @return a senha.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Define a senha.
     *
     * @param password a senha.
     */
    public void setPassword(String password) {
        this.password = password;
    }
}