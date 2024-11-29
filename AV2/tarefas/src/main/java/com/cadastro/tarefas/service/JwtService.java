package com.cadastro.tarefas.service;

import com.cadastro.tarefas.config.JwtProperties;
import org.springframework.stereotype.Service;

@Service
public class JwtService {

    private final JwtProperties jwtProperties;

    public JwtService(JwtProperties jwtProperties) {
        this.jwtProperties = jwtProperties;
    }

    public void printKeys() {
        System.out.println("Public Key: " + jwtProperties.getPublicKey().getKey());
        System.out.println("Private Key: " + jwtProperties.getPrivateKey().getKey());
    }
}