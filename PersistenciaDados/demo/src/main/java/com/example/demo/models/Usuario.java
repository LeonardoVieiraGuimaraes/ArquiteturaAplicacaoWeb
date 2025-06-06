package com.example.demo.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity // Marca esta classe como uma entidade JPA, que será mapeada para uma tabela no banco de dados.
@Data // Gera automaticamente getters, setters, toString, equals e hashCode usando o Lombok.
@NoArgsConstructor // Gera automaticamente um construtor sem parâmetros usando o Lombok.
@AllArgsConstructor // Gera automaticamente um construtor com todos os campos como parâmetros usando o Lombok.

public class Usuario {

    @Id // Marca este campo como a chave primária da entidade.
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Especifica que o valor do ID será gerado automaticamente pelo banco de dados usando a estratégia de identidade.
    private Long id; // Campo que representa o ID do usuário.

    private String nome; // Campo que representa o nome do usuário.
    private String email; // Campo que representa o email do usuário.
}
