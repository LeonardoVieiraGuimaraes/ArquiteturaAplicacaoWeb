package com.example.democrud.model;

import org.springframework.data.annotation.Id; // Importa a anotação para ID no MongoDB.
import org.springframework.data.mongodb.core.mapping.Document; // Importa a anotação para documentos MongoDB.

@Document(collection = "produtos") // Indica que esta classe é um documento MongoDB.
public class Produto {

    @Id // Define o campo "id" como identificador único.
    private String id; // Alterado para String, pois o MongoDB usa ObjectId como padrão.
    private String name; // Nome do registro.
    private String description; // Descrição do registro.

    public Produto() {
        // Construtor padrão necessário para o MongoDB.
    }

    public Produto(String name, String description) {
        // Construtor para inicializar os campos.
        this.name = name;
        this.description = description;
    }

    // Métodos getters e setters para acessar e modificar os campos.
    public String getId() {
        return id;
    }

    public void setId(String id) {
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
