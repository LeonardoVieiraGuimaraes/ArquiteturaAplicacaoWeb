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
import lombok.Data;

/**
 * Entidade Author representa um autor de livros.
 */
@Entity // Indica que esta classe é uma entidade JPA que será mapeada para uma tabela no banco de dados
@Data // Gera automaticamente getters, setters, equals, hashCode e toString
public class Author {

    @Id // Define o campo como chave primária
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Gera IDs automaticamente como Long
    private Long id; // Identificador único do autor

    @NotBlank(message = "Nome é obrigatório")
    private String nome; // Nome do autor

    @NotNull(message = "Data de nascimento é obrigatória")
    private LocalDate dataNascimento; // Alterado para LocalDate

    @NotBlank(message = "Nacionalidade é obrigatória")
    private String nacionalidade; // Nacionalidade do autor

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private User user; // Usuário que salvou o autor
}
