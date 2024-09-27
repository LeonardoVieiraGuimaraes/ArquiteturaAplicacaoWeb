package com.cadastro.usuario.models;

import jakarta.persistence.Entity; // Importa a anotação Entity do JPA para marcar esta classe como uma entidade persistente.
import jakarta.persistence.GeneratedValue; // Importa a anotação GeneratedValue do JPA para especificar a estratégia de geração de valores para o ID.
import jakarta.persistence.GenerationType; // Importa a enumeração GenerationType do JPA para definir as estratégias de geração de valores para o ID.
import jakarta.persistence.Id; // Importa a anotação Id do JPA para marcar o campo como a chave primária da entidade.
import lombok.AllArgsConstructor; // Importa a anotação AllArgsConstructor do Lombok para gerar um construtor com todos os campos como parâmetros.
import lombok.Data; // Importa a anotação Data do Lombok para gerar automaticamente getters, setters, toString, equals e hashCode.
import lombok.NoArgsConstructor; // Importa a anotação NoArgsConstructor do Lombok para gerar um construtor sem parâmetros.

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