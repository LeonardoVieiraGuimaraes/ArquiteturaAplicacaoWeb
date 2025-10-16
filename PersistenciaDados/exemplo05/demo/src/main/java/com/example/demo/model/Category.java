package com.example.demo.model;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.ArrayList;
import java.util.List;

/**
 * Entidade Category - Representa uma categoria de produtos
 * Relacionamento: Uma categoria pode ter vários produtos (1:N)
 */
@Entity
@Table(name = "categories")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(length = 500)
    private String description;

    /**
     * Relacionamento One-to-Many com Product
     * mappedBy: indica que o relacionamento é mapeado pelo atributo "category" em Product
     * cascade: operações em Category serão propagadas para os Products
     * orphanRemoval: se um produto for removido da lista, ele será deletado do banco
     * 
     * @JsonIgnore: evita recursão infinita na serialização JSON
     */
    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Product> products = new ArrayList<>();

    // Construtor vazio (obrigatório para JPA)
    public Category() {
    }

    // Construtor com parâmetros
    public Category(String name, String description) {
        this.name = name;
        this.description = description;
    }

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    /**
     * Método auxiliar para adicionar um produto à categoria
     * Mantém a sincronização bidirecional do relacionamento
     */
    public void addProduct(Product product) {
        products.add(product);
        product.setCategory(this);
    }

    /**
     * Método auxiliar para remover um produto da categoria
     * Mantém a sincronização bidirecional do relacionamento
     */
    public void removeProduct(Product product) {
        products.remove(product);
        product.setCategory(null);
    }
}
