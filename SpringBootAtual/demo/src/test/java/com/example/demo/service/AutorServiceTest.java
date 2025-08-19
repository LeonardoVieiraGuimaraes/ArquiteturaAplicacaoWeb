package com.example.demo.service;

import com.example.demo.dto.AutorDTO;
import com.example.demo.model.Autor;
import com.example.demo.repository.AutorRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

// Teste automatizado para AutorService usando JUnit e Mockito
class AutorServiceTest {
    @Mock
    private AutorRepository autorRepository;

    @InjectMocks
    private AutorService autorService;

    public AutorServiceTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Deve listar todos os autores como DTO")
    void testListarTodos() {
        Autor autor1 = new Autor();
        autor1.setId(1L);
        autor1.setNome("Robert C. Martin");
        Autor autor2 = new Autor();
        autor2.setId(2L);
        autor2.setNome("Joshua Bloch");

        when(autorRepository.findAll()).thenReturn(Arrays.asList(autor1, autor2));

        List<AutorDTO> dtos = autorService.listarTodos();
        assertThat(dtos).hasSize(2);
        assertThat(dtos.get(0).getNome()).isEqualTo("Robert C. Martin");
        assertThat(dtos.get(1).getNome()).isEqualTo("Joshua Bloch");
    }

    @Test
    @DisplayName("Deve salvar um autor e retornar DTO")
    void testSalvar() {
        AutorDTO dto = new AutorDTO();
        dto.setNome("Martin Fowler");

        Autor autor = new Autor();
        autor.setId(3L);
        autor.setNome(dto.getNome());

        when(autorRepository.save(any(Autor.class))).thenReturn(autor);

        AutorDTO salvo = autorService.salvar(dto);
        assertThat(salvo.getId()).isEqualTo(3L);
        assertThat(salvo.getNome()).isEqualTo("Martin Fowler");
    }
}
