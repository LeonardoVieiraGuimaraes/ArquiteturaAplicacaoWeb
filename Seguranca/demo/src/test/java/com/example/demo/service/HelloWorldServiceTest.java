package com.example.demo.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class HelloWorldServiceTest {

    private final HelloWorldService helloWorldService = new HelloWorldService();

    @Test
    public void testGetHelloMessage() {
        // Verifica se o serviço retorna a mensagem "Hello World"
        String message = helloWorldService.getHelloMessage();
        assertEquals("Hello World", message);
    }
}
