package com.example.democrud.model;

// Importações necessárias para o mapeamento e funcionamento da entidade JPA.

// Configura o escaneamento de entidades.
import jakarta.persistence.Entity; // Marca a classe como uma entidade JPA.
import jakarta.persistence.GeneratedValue; // Gera valores automaticamente para campos.
import jakarta.persistence.GenerationType; // Define a estratégia de geração de valores.
import jakarta.persistence.Id; // Marca o campo como chave primária.
import lombok.AllArgsConstructor; // Gera automaticamente um construtor com todos os campos.
import lombok.Data; // Gera automaticamente getters, setters, equals, hashCode e toString.
import lombok.NoArgsConstructor;


// Marca a classe como uma entidade JPA, permitindo que ela seja mapeada para uma tabela no banco de dados.
@Entity
// Gera automaticamente métodos como getters, setters, equals, hashCode e toString.
@Data
// Gera automaticamente um construtor que aceita todos os campos como parâmetros.
@AllArgsConstructor
@NoArgsConstructor // Gera automaticamente um construtor sem parâmetros.
// Define a classe Produto como uma entidade JPA, representando um produto no sistema.
public class Produto {

    // Marca o campo "id" como chave primária da tabela.
    @Id
    // Configura a geração automática de valores para o campo "id" usando a estratégia de identidade.
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Identificador único do produto.

    private String name; // Nome do produto.
    private String description; // Descrição do produto.
}
