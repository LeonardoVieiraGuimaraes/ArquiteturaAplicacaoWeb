package com.example.demo.model;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.HashSet;
import java.util.Set;

/**
 * Entidade Tag - Representa uma tag/etiqueta que pode ser associada a vários produtos
 * Relacionamento: Uma tag pode estar em vários produtos e um produto pode ter várias tags (N:N)
 */
@Entity
@Table(name = "tags")
public class Tag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(length = 7)
    private String color; // Cor hexadecimal para exibição (ex: #FF5733)

    /**
     * Relacionamento Many-to-Many com Product
     * mappedBy: indica que o relacionamento é mapeado pelo atributo "tags" em Product
     * 
     * @JsonIgnore: evita recursão infinita na serialização JSON
     */
    @ManyToMany(mappedBy = "tags")
    @JsonIgnore
    private Set<Product> products = new HashSet<>();

    // Construtor vazio (obrigatório para JPA)
    public Tag() {
    }

    // Construtor com parâmetros
    public Tag(String name, String color) {
        this.name = name;
        this.color = color;
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

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Set<Product> getProducts() {
        return products;
    }

    public void setProducts(Set<Product> products) {
        this.products = products;
    }
}
