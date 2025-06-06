package com.example.demo.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.example.demo.service.HelloWorldService;

public class HelloWorldControllerTest {

    private MockMvc mockMvc;

    @Mock // Simula o comportamento do serviço HelloWorldService
    private HelloWorldService helloWorldService;

    @InjectMocks // Injeta o mock do serviço no controlador
    private HelloWorldController helloWorldController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this); // Inicializa os mocks
        mockMvc = MockMvcBuilders.standaloneSetup(helloWorldController).build(); // Configura o MockMvc
    }

    @Test
    public void testHelloWorld() throws Exception {
        // Configura o mock para retornar "Hello World"
        when(helloWorldService.getHelloMessage()).thenReturn("Hello World");

        // Faz uma requisição GET para o endpoint /api/hello e verifica a resposta
        mockMvc.perform(get("/api/hello"))
                .andExpect(status().isOk()) // Verifica se o status HTTP é 200 (OK)
                .andExpect(content().string("Hello World")); // Verifica se o conteúdo da resposta é "Hello World"
    }
}
