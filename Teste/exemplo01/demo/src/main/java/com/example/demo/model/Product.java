// Pacote onde a classe Product está localizada
package com.example.demo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

// Declaração da classe Product como entidade JPA
@Entity
@Table(name = "products")
public class Product {

    // Atributo privado que armazena o ID do produto (chave primária auto-incrementada)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Atributo privado que armazena o nome do produto
    private String name;

    // Novos campos usados pelos testes
    private String description;
    private Double price;
    private Integer stock;

    // Construtor vazio (obrigatório para JPA e desserialização)
    public Product() {
    }
    

    // Construtor com campos para inicializar os atributos id e name
    public Product(Long id, String name) {
        this.id = id; // Inicializa o atributo id com o valor passado como parâmetro
        this.name = name; // Inicializa o atributo name com o valor passado como parâmetro
    }

    public Product(Long id, String name, String description, Double price, Integer stock) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.stock = stock;
    }

    // Método getter para obter o valor do atributo id
    public Long getId() {
        return id;
    }

    // Método setter para definir o valor do atributo id
    public void setId(Long id) {
        this.id = id;
    }

    // Método getter para obter o valor do atributo name
    public String getName() {
        return name;
    }

    // Método setter para definir o valor do atributo name
    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }
}
