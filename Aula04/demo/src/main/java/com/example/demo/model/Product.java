// Pacote onde a classe Product está localizada
package com.example.demo.model;

// Declaração da classe Product
public class Product {

    // Atributo privado que armazena o ID do produto
    private Long id;

    // Atributo privado que armazena o nome do produto
    private String name;

    // Construtor vazio (obrigatório para desserialização)
    public Product() {
    }
    

    // Construtor com campos para inicializar os atributos id e name
    public Product(Long id, String name) {
        this.id = id; // Inicializa o atributo id com o valor passado como parâmetro
        this.name = name; // Inicializa o atributo name com o valor passado como parâmetro
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
}
