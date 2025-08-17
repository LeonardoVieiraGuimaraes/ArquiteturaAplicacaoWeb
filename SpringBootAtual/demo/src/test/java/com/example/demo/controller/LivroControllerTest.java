package com.example.demo.controller;

import com.example.demo.dto.LivroDTO;
import com.example.demo.service.LivroService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.Mockito.*;

// Teste automatizado para LivroController usando MockMvc
@WebMvcTest(LivroController.class)
class LivroControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private LivroService livroService;

    @Test
    @DisplayName("Deve retornar lista de livros no endpoint GET /livros")
    void testListarTodos() throws Exception {
        LivroDTO dto1 = new LivroDTO();
        dto1.setId(1L);
        dto1.setTitulo("Clean Code");
        dto1.setAutor("Robert C. Martin");
        dto1.setIsbn("9780132350884");

        LivroDTO dto2 = new LivroDTO();
        dto2.setId(2L);
        dto2.setTitulo("Effective Java");
        dto2.setAutor("Joshua Bloch");
        dto2.setIsbn("9780134685991");

        when(livroService.listarTodos()).thenReturn(Arrays.asList(dto1, dto2));

        mockMvc.perform(get("/livros"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].titulo").value("Clean Code"))
                .andExpect(jsonPath("$[1].autor").value("Joshua Bloch"));
    }

    @Test
    @DisplayName("Deve salvar livro no endpoint POST /livros")
    void testSalvar() throws Exception {
        LivroDTO dto = new LivroDTO();
        dto.setId(3L);
        dto.setTitulo("Refactoring");
        dto.setAutor("Martin Fowler");
        dto.setIsbn("9780201485677");

        when(livroService.salvar(any(LivroDTO.class))).thenReturn(dto);

        String json = "{\"titulo\":\"Refactoring\",\"autor\":\"Martin Fowler\",\"isbn\":\"9780201485677\"}";

        mockMvc.perform(post("/livros")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(3L))
                .andExpect(jsonPath("$.autor").value("Martin Fowler"));
    }
}
