package com.example.demo.config;

import com.example.demo.model.Author;
import com.example.demo.model.Book;
import com.example.demo.repository.AuthorRepository;
import com.example.demo.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.util.Objects;

/**
 * Carrega alguns dados iniciais na base H2 em memória para facilitar testes.
 */
@Configuration
@RequiredArgsConstructor
public class DataLoader {

    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;

    @Bean
    CommandLineRunner seedData() {
        return args -> {
            if (authorRepository.count() == 0) {
                Author a1 = Objects.requireNonNull(Author.builder().name("Machado de Assis").build());
                Author a2 = Objects.requireNonNull(Author.builder().name("Clarice Lispector").build());
                authorRepository.save(a1);
                authorRepository.save(a2);

                Book b1 = Objects.requireNonNull(Book.builder().title("Dom Casmurro").author(a1).build());
                Book b2 = Objects.requireNonNull(Book.builder().title("Memórias Póstumas de Brás Cubas").author(a1).build());
                Book b3 = Objects.requireNonNull(Book.builder().title("A Hora da Estrela").author(a2).build());
                bookRepository.save(b1);
                bookRepository.save(b2);
                bookRepository.save(b3);
            }
        };
    }
}
