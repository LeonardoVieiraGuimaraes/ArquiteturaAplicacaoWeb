package com.example.democrud.model;

import org.springframework.boot.autoconfigure.domain.EntityScan;

import jakarta.persistence.Entity; // Importa anotações para mapeamento JPA.
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;

@EntityScan("com.example.democrud.model")

@Entity // Indica que esta classe é uma entidade JPA.
@Data // Adiciona os métodos getters e setters automaticamente.
@AllArgsConstructor // Adiciona um construtor com todos os campos.
public class Produto {

    @Id // Define o campo "id" como chave primária.
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Gera o ID automaticamente.
    private Long id;
    private String name; // Nome do registro.
    private String description; // Descrição do registro.

}
