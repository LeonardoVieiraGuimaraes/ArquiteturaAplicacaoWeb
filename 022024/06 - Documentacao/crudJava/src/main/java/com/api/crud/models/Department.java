package com.api.crud.models;

// Importações necessárias para anotações JPA e Lombok
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;


// Anotação que indica que esta classe é uma entidade JPA
@Entity
// Anotação Lombok que gera automaticamente getters, setters, toString, equals e hashCode
@Data
// Anotação Lombok que gera um construtor sem argumentos
@NoArgsConstructor
public class Department {
    // Anotação que indica que este campo é a chave pri++ária da entidade
    @Id
    // Anotação que indica que o valor deste campo será gerado automaticamente pelo banco de dados
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Campo que representa o título do departamento
    private String title;
}