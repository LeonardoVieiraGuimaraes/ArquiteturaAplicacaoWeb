package com.example.demo.model;

import jakarta.persistence.*;
import lombok.*;

/**
 * Entidade de domínio que representa um Livro.
 * - Usa JPA para mapear para tabela "books".
 * - Relacionamento N:1 com Author (muitos livros pertencem a um autor).
 */
@Entity
@Table(name = "books")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Book {

    /**
     * Identificador único do livro (chave primária, gerada automaticamente).
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Título do livro. Campo obrigatório, validado na camada de DTO/Controller.
     */
    @Column(nullable = false, length = 200)
    private String title;

    /**
     * Autor a quem este livro pertence. É a extremidade "muitos" do relacionamento.
     */
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id", nullable = false)
    private Author author;
}
