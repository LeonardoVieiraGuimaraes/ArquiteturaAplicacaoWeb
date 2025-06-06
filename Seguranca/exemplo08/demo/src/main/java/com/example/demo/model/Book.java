package com.example.demo.model;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

/**
 * Entidade Book representa um livro cadastrado no sistema. Inclui validações e
 * relacionamento com Author.
 */
@Entity // Indica que esta classe é uma entidade JPA que será mapeada para uma tabela no banco de dados
@Data // Gera automaticamente getters, setters, equals, hashCode e toString
public class Book {

    @Id // Define o campo como chave primária
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Gera IDs automaticamente como Long
    private Long id; // Identificador único do livro

    @NotBlank(message = "ISBN é obrigatório")
    private String isbn; // Código ISBN do livro

    @NotBlank(message = "Título é obrigatório")
    private String titulo; // Título do livro

    @NotNull(message = "Data de publicação é obrigatória")
    private LocalDate dataPublicacao; // Data de publicação do livro

    @NotBlank(message = "Gênero é obrigatório")
    private String genero; // Gênero do livro

    @NotNull(message = "Preço é obrigatório")
    @Positive(message = "O preço deve ser positivo")
    private Double preco; // Preço do livro

    @ManyToOne
    @JoinColumn(name = "autor_id", nullable = false)
    private Author autor; // Autor do livro

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private User user; // Usuário que salvou o livro
}
