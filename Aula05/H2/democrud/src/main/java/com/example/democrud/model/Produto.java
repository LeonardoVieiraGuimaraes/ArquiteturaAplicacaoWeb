package com.example.democrud.model;

import org.springframework.boot.autoconfigure.domain.EntityScan;

import jakarta.persistence.Entity; // Importa anotações para mapeamento JPA.
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@EntityScan("com.example.democrud.model")

@Entity // Indica que esta classe é uma entidade JPA.
public class Produto {

    @Id // Define o campo "id" como chave primária.
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Gera o ID automaticamente.
    private Long id;
    private String name; // Nome do registro.
    private String description; // Descrição do registro.

    public Produto() {
        // Construtor padrão necessário para o JPA.
    }

    public Produto(String name, String description) {
        // Construtor para inicializar os campos.
        this.name = name;
        this.description = description;
    }

    // Métodos getters e setters para acessar e modificar os campos.
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
