package com.example.demo.service;

import com.example.demo.dto.LivroDTO;
import com.example.demo.model.Livro;
import com.example.demo.repository.LivroRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

// Teste automatizado para LivroService usando JUnit e Mockito
class LivroServiceTest {
    @Mock
    private LivroRepository livroRepository;

    @InjectMocks
    private LivroService livroService;

    public LivroServiceTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Deve listar todos os livros como DTO")
    void testListarTodos() {
        Livro livro1 = new Livro();
        livro1.setId(1L);
        livro1.setTitulo("Clean Code");
        livro1.setAutor("Robert C. Martin");
        livro1.setIsbn("9780132350884");

        Livro livro2 = new Livro();
        livro2.setId(2L);
        livro2.setTitulo("Effective Java");
        livro2.setAutor("Joshua Bloch");
        livro2.setIsbn("9780134685991");

        when(livroRepository.findAll()).thenReturn(Arrays.asList(livro1, livro2));

        List<LivroDTO> dtos = livroService.listarTodos();
        assertThat(dtos).hasSize(2);
        assertThat(dtos.get(0).getTitulo()).isEqualTo("Clean Code");
        assertThat(dtos.get(1).getAutor()).isEqualTo("Joshua Bloch");
    }

    @Test
    @DisplayName("Deve salvar um livro e retornar DTO")
    void testSalvar() {
        LivroDTO dto = new LivroDTO();
        dto.setTitulo("Refactoring");
        dto.setAutor("Martin Fowler");
        dto.setIsbn("9780201485677");

        Livro livro = new Livro();
        livro.setId(3L);
        livro.setTitulo(dto.getTitulo());
        livro.setAutor(dto.getAutor());
        livro.setIsbn(dto.getIsbn());

        when(livroRepository.save(any(Livro.class))).thenReturn(livro);

        LivroDTO salvo = livroService.salvar(dto);
        assertThat(salvo.getId()).isEqualTo(3L);
        assertThat(salvo.getAutor()).isEqualTo("Martin Fowler");
    }
}
