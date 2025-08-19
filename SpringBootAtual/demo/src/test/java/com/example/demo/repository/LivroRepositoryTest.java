package com.example.demo.repository;

import com.example.demo.model.Livro;
import com.example.demo.model.Autor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

// Teste automatizado para LivroRepository usando JUnit e Spring Boot
@DataJpaTest // Cria contexto apenas para JPA
public class LivroRepositoryTest {
    @Autowired
    private LivroRepository livroRepository;

    @Test
    @DisplayName("Deve salvar e buscar um livro pelo ID")
    void testSalvarEBuscarLivro() {
        // Cria e salva um livro
    Autor autor = new Autor();
    autor.setNome("Robert C. Martin");

    Livro livro = new Livro();
    livro.setTitulo("Clean Code");
    livro.setAutor(autor);
    livro.setIsbn("9780132350884");
    Livro salvo = livroRepository.save(livro);

        // Busca pelo ID
        Optional<Livro> encontrado = livroRepository.findById(salvo.getId());
        assertThat(encontrado).isPresent();
        assertThat(encontrado.get().getTitulo()).isEqualTo("Clean Code");
    }
}
