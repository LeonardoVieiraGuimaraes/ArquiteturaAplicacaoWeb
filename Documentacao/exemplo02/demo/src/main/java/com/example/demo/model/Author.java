package com.example.demo.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Entidade de domínio que representa um Autor.
 * - Usa JPA para mapear para tabela "authors" no H2 em memória.
 * - Relacionamento 1:N com Book (um autor possui vários livros).
 * - Lombok reduz o boilerplate (getters, setters, construtores e builder).
 */
@Entity
@Table(name = "authors")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Author {

    /**
     * Identificador único do autor (chave primária, gerada automaticamente).
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Nome do autor. Campo obrigatório, validado na camada de DTO/Controller.
     */
    @Column(nullable = false, length = 120)
    private String name;

    /**
     * Lista de livros associados a este autor.
     * mappedBy indica que a FK está no lado "Book.author".
     * cascade=ALL para propagar operações e orphanRemoval para remoção limpa.
     */
    @Builder.Default
    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Book> books = new ArrayList<>();

    /**
     * Método utilitário para manter a relação bidirecional consistente ao adicionar um livro.
     */
    public void addBook(Book book) {
        books.add(book);
        book.setAuthor(this);
    }

    /**
     * Método utilitário para remover um livro mantendo a relação consistente.
     */
    public void removeBook(Book book) {
        books.remove(book);
        book.setAuthor(null);
    }
}
