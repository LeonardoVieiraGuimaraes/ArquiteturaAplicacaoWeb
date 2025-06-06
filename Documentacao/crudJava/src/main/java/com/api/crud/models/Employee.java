package com.api.crud.models;

import java.util.Date;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Representa um funcionário na aplicação.
 */
@Entity
@Data
@NoArgsConstructor
public class Employee {
    /**
     * Identificador único do funcionário.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Primeiro nome do funcionário.
     */
    private String firstName;

    /**
     * Último nome do funcionário.
     */
    private String lastName;

    /**
     * Departamento ao qual o funcionário pertence.
     */
    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;

    /**
     * Data de nascimento do funcionário.
     */
    private Date birthdate;

    /**
     * Currículo do funcionário.
     */
    private String cv;
}