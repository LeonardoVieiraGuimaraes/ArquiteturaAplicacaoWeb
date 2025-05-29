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

@Entity // Indica que esta classe é uma entidade JPA que será mapeada para uma tabela no banco de dados
@Data // Gera automaticamente getters, setters, equals, hashCode e toString
public class Book {

    @Id // Define o campo como chave primária
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Gera IDs automaticamente como Long
    private Long id; // Identificador único do livro

    @NotBlank(message = "ISBN é obrigatório") // Validação: não pode ser nulo ou vazio
    private String isbn; // Código ISBN do livro

    @NotBlank(message = "Título é obrigatório") // Validação: não pode ser nulo ou vazio
    private String titulo; // Título do livro

    @NotNull(message = "Data de publicação é obrigatória") // Validação: não pode ser nulo
    private LocalDate dataPublicacao; // Data de publicação do livro

    @NotBlank(message = "Gênero é obrigatório") // Validação: não pode ser nulo ou vazio
    private String genero; // Gênero do livro

    @NotNull(message = "Preço é obrigatório") // Validação: não pode ser nulo
    @Positive(message = "O preço deve ser positivo") // Validação: deve ser maior que zero
    private Double preco; // Preço do livro

    @ManyToOne // Relacionamento muitos-para-um com a entidade Author
    @JoinColumn(name = "autor_id", nullable = false) // Define a chave estrangeira para o autor
    private Author autor; // Autor do livro
}
