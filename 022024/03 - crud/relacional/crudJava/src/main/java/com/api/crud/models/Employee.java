package com.api.crud.models;

// Importações necessárias para anotações JPA e Lombok
import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.NoArgsConstructor; // Adicione esta linha para importar a classe Date

// Anotação que indica que esta classe é uma entidade JPA
@Entity
// Anotação Lombok que gera automaticamente getters, setters, toString, equals e hashCode
@Data
// Anotação Lombok que gera um construtor sem argumentos
@NoArgsConstructor
public class Employee {
    // Anotação que indica que este campo é a chave primária da entidade
    @Id
    // Anotação que indica que o valor deste campo será gerado automaticamente pelo banco de dados
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Campos que representam o primeiro e último nome do funcionário
    private String firstName;
    private String lastName;

    // Anotação que indica um relacionamento muitos-para-um com a entidade Department
    @ManyToOne
    // Anotação que especifica a coluna de junção para o relacionamento muitos-para-um
    @JoinColumn(name = "department_id")
    private Department department;

    // Campo que representa a data de nascimento do funcionário
    private Date birthdate;

    // Campo que representa o currículo do funcionário
    private String cv;
}