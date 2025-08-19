package com.example.demo.controller;

import com.example.demo.dto.LivroDTO;
import com.example.demo.dto.AutorDTO;
import com.example.demo.service.LivroService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mock;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.Mockito.*;

// Teste automatizado para LivroController usando MockMvc
@ExtendWith(MockitoExtension.class)
class LivroControllerTest {
    private MockMvc mockMvc;

    @Mock
    private LivroService livroService;

    @InjectMocks
    private LivroController livroController;

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(livroController).build();
    }

    @Test
    @DisplayName("Deve retornar lista de livros no endpoint GET /livros")
    void testListarTodos() throws Exception {
        LivroDTO dto1 = new LivroDTO();
    AutorDTO autor1 = new AutorDTO();
    autor1.setId(10L);
    autor1.setNome("Robert C. Martin");
    AutorDTO autor2 = new AutorDTO();
    autor2.setId(20L);
    autor2.setNome("Joshua Bloch");

    dto1.setId(1L);
    dto1.setTitulo("Clean Code");
    dto1.setAutor(autor1);
    dto1.setIsbn("9780132350884");

    LivroDTO dto2 = new LivroDTO();
    dto2.setId(2L);
    dto2.setTitulo("Effective Java");
    dto2.setAutor(autor2);
    dto2.setIsbn("9780134685991");

        when(livroService.listarTodos()).thenReturn(Arrays.asList(dto1, dto2));

    mockMvc.perform(get("/livros"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].titulo").value("Clean Code"))
        .andExpect(jsonPath("$[0].autor.nome").value("Robert C. Martin"))
        .andExpect(jsonPath("$[1].autor.nome").value("Joshua Bloch"));
    }

    @Test
    @DisplayName("Deve salvar livro no endpoint POST /livros")
    void testSalvar() throws Exception {
        LivroDTO dto = new LivroDTO();
    AutorDTO autorDTO = new AutorDTO();
    autorDTO.setId(30L);
    autorDTO.setNome("Martin Fowler");
    dto.setId(3L);
    dto.setTitulo("Refactoring");
    dto.setAutor(autorDTO);
    dto.setIsbn("9780201485677");

    when(livroService.salvar(any(LivroDTO.class))).thenReturn(dto);

    String json = "{\"titulo\":\"Refactoring\",\"autor\":{\"id\":30,\"nome\":\"Martin Fowler\"},\"isbn\":\"9780201485677\"}";

    mockMvc.perform(post("/livros")
        .contentType(MediaType.APPLICATION_JSON)
        .content(json))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(3L))
        .andExpect(jsonPath("$.autor.nome").value("Martin Fowler"));
    }
}
