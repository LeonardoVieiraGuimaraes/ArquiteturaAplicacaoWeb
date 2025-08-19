package com.example.demo.service;

import com.example.demo.dto.LivroDTO;
import com.example.demo.dto.AutorDTO;
import com.example.demo.model.Livro;
import com.example.demo.model.Autor;
import com.example.demo.repository.LivroRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;


import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

// Teste automatizado para LivroService usando JUnit e Mockito
class LivroServiceTest {
    @Mock
    private LivroRepository livroRepository;

    @Mock
    private com.example.demo.repository.AutorRepository autorRepository;

    @InjectMocks
    private LivroService livroService;

    public LivroServiceTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Deve listar todos os livros como DTO")
    void testListarTodos() {
    Autor autor1 = new Autor();
    autor1.setId(10L);
    autor1.setNome("Robert C. Martin");
    Autor autor2 = new Autor();
    autor2.setId(20L);
    autor2.setNome("Joshua Bloch");

    Livro livro1 = new Livro();
    livro1.setId(1L);
    livro1.setTitulo("Clean Code");
    livro1.setAutor(autor1);
    livro1.setIsbn("9780132350884");

    Livro livro2 = new Livro();
    livro2.setId(2L);
    livro2.setTitulo("Effective Java");
    livro2.setAutor(autor2);
    livro2.setIsbn("9780134685991");

        when(livroRepository.findAll()).thenReturn(Arrays.asList(livro1, livro2));

        List<LivroDTO> dtos = livroService.listarTodos();
        assertThat(dtos).hasSize(2);
    assertThat(dtos.get(0).getTitulo()).isEqualTo("Clean Code");
    assertThat(dtos.get(0).getAutor().getNome()).isEqualTo("Robert C. Martin");
    assertThat(dtos.get(1).getAutor().getNome()).isEqualTo("Joshua Bloch");
    }

    @Test
    @DisplayName("Deve salvar um livro e retornar DTO")
    void testSalvar() {
    AutorDTO autorDTO = new AutorDTO();
    autorDTO.setId(30L);
    autorDTO.setNome("Martin Fowler");
    Autor autor = new Autor();
    autor.setId(30L);
    autor.setNome("Martin Fowler");

    LivroDTO dto = new LivroDTO();
    dto.setTitulo("Refactoring");
    dto.setAutor(autorDTO);
    dto.setIsbn("9780201485677");

    Livro livro = new Livro();
    livro.setId(3L);
    livro.setTitulo(dto.getTitulo());
    livro.setAutor(autor);
    livro.setIsbn(dto.getIsbn());

    when(livroRepository.save(any(Livro.class))).thenReturn(livro);

    LivroDTO salvo = livroService.salvar(dto);
    assertThat(salvo.getId()).isEqualTo(3L);
    assertThat(salvo.getAutor().getNome()).isEqualTo("Martin Fowler");
    }
}
