package com.example.demo.model;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity // Indica que esta classe é uma entidade JPA que será mapeada para uma tabela no banco de dados
@Data // Gera automaticamente getters, setters, equals, hashCode e toString
public class Author {

    @Id // Define o campo como chave primária
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Gera IDs automaticamente como Long
    private Long id; // Identificador único do autor

    private String nome; // Nome do autor
    private LocalDate dataNascimento; // Alterado para LocalDate
    private String nacionalidade; // Nacionalidade do autor
}
