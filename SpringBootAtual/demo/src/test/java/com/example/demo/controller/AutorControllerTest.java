
// Teste automatizado para AutorController usando JUnit 5 e Mockito
// Valida os endpoints REST de autores simulando requisições HTTP
package com.example.demo.controller;

import com.example.demo.dto.AutorDTO; // DTO para transferir dados do autor
import com.example.demo.service.AutorService; // Service de regras de negócio do autor
import org.junit.jupiter.api.DisplayName; // Para nomear os testes
import org.junit.jupiter.api.Test; // Para marcar métodos de teste
import org.mockito.Mock; // Cria mocks do serviço
import org.mockito.junit.jupiter.MockitoExtension; // Extensão do Mockito para JUnit 5
import org.junit.jupiter.api.extension.ExtendWith; // Permite usar extensões no JUnit
import org.springframework.http.MediaType; // Define o tipo de conteúdo das requisições
import org.springframework.test.web.servlet.MockMvc; // Simula requisições HTTP

import java.util.Arrays; // Utilitário para listas

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*; // Métodos para criar requisições
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*; // Métodos para validar respostas
import static org.mockito.Mockito.*; // Métodos para configurar mocks

// Teste automatizado para AutorController usando MockMvc
@ExtendWith(MockitoExtension.class) // Ativa o Mockito para os testes
class AutorControllerTest {
    private MockMvc mockMvc;

    @org.mockito.InjectMocks
    private AutorController autorController;

    @org.junit.jupiter.api.BeforeEach
    void setup() {
        mockMvc = org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup(autorController).build();
    }

    @Mock // Cria um mock do serviço AutorService
    private AutorService autorService;

    /**
     * Testa o endpoint GET /autores
     * Simula a busca de todos os autores e valida o JSON de resposta
     */
    @Test
    @DisplayName("Deve retornar lista de autores no endpoint GET /autores")
    void testListarTodos() throws Exception {
        // Cria dois autores simulados
        AutorDTO dto1 = new AutorDTO();
        dto1.setId(1L);
        dto1.setNome("Robert C. Martin");
        AutorDTO dto2 = new AutorDTO();
        dto2.setId(2L);
        dto2.setNome("Joshua Bloch");

        // Configura o mock para retornar a lista simulada
        when(autorService.listarTodos()).thenReturn(Arrays.asList(dto1, dto2));

        // Simula requisição GET e valida o JSON de resposta
        mockMvc.perform(get("/autores"))
                .andExpect(status().isOk()) // Espera status 200 OK
                .andExpect(jsonPath("$[0].nome").value("Robert C. Martin")) // Valida nome do primeiro autor
                .andExpect(jsonPath("$[1].nome").value("Joshua Bloch")); // Valida nome do segundo autor
    }

    /**
     * Testa o endpoint POST /autores
     * Simula o cadastro de um autor e valida o JSON de resposta
     */
    @Test
    @DisplayName("Deve salvar autor no endpoint POST /autores")
    void testSalvar() throws Exception {
        // Cria DTO do autor a ser salvo
        AutorDTO dto = new AutorDTO();
        dto.setId(3L);
        dto.setNome("Martin Fowler");

        // Configura o mock para retornar o autor salvo
        when(autorService.salvar(any(AutorDTO.class))).thenReturn(dto);

        // JSON de entrada simulando o corpo da requisição
        String json = "{\"nome\":\"Martin Fowler\"}";

        // Simula requisição POST e valida o JSON de resposta
        mockMvc.perform(post("/autores")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isOk()) // Espera status 200 OK
                .andExpect(jsonPath("$.id").value(3L)) // Valida o id retornado
                .andExpect(jsonPath("$.nome").value("Martin Fowler")); // Valida o nome retornado
    }
}
