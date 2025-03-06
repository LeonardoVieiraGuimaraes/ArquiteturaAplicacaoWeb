package com.register.user.models;

// Importa a anotação @Data da biblioteca Lombok, que gera automaticamente getters, setters, toString, equals e hashCode
import lombok.Data;

// Importa a anotação @Id do Spring Data, que é usada para marcar o campo id como o identificador do documento
import org.springframework.data.annotation.Id;

// Importa a anotação @Document do Spring Data MongoDB, que é usada para indicar que esta classe é um documento MongoDB
import org.springframework.data.mongodb.core.mapping.Document;

// A anotação @Data do Lombok gera automaticamente getters, setters, toString, equals e hashCode para todos os campos da classe
@Data
// A anotação @Document indica que esta classe é um documento MongoDB e especifica o nome da coleção como "users"
@Document(collection = "users")
public class User {
    // A anotação @Id indica que este campo é o identificador do documento
    @Id
    private String id;
    // Campo para armazenar o nome do usuário
    private String nome;
    // Campo para armazenar o email do usuário
    private String email;
}