// Pacote onde a classe Product está localizada
package com.example.demo.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

/**
 * Entidade Product - Representa um produto no sistema
 * 
 * Relacionamentos:
 * - Many-to-One com Category (vários produtos pertencem a uma categoria)
 * - Many-to-Many com Tag (um produto pode ter várias tags e uma tag pode estar em vários produtos)
 */
@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(length = 1000)
    private String description;

    @Column(precision = 10, scale = 2)
    private BigDecimal price;

    private Integer stock;

    /**
     * Relacionamento Many-to-One com Category
     * Vários produtos podem pertencer a uma categoria
     * 
     * @JoinColumn: define a coluna de chave estrangeira
     * FetchType.LAZY: a categoria só é carregada quando acessada
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    /**
     * Relacionamento Many-to-Many com Tag
     * Um produto pode ter várias tags e uma tag pode estar em vários produtos
     * 
     * @JoinTable: define a tabela intermediária do relacionamento
     * - name: nome da tabela intermediária
     * - joinColumns: coluna que referencia Product
     * - inverseJoinColumns: coluna que referencia Tag
     * 
     * CascadeType.PERSIST e MERGE: ao salvar/atualizar um produto, suas tags também são salvas/atualizadas
     */
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
        name = "product_tags",
        joinColumns = @JoinColumn(name = "product_id"),
        inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    private Set<Tag> tags = new HashSet<>();

    // Construtor vazio (obrigatório para JPA e desserialização)
    public Product() {
    }

    // Construtor com campos principais
    public Product(String name, String description, BigDecimal price, Integer stock) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.stock = stock;
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

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Set<Tag> getTags() {
        return tags;
    }

    public void setTags(Set<Tag> tags) {
        this.tags = tags;
    }

    /**
     * Método auxiliar para adicionar uma tag ao produto
     * Mantém a sincronização bidirecional do relacionamento
     */
    public void addTag(Tag tag) {
        this.tags.add(tag);
        tag.getProducts().add(this);
    }

    /**
     * Método auxiliar para remover uma tag do produto
     * Mantém a sincronização bidirecional do relacionamento
     */
    public void removeTag(Tag tag) {
        this.tags.remove(tag);
        tag.getProducts().remove(this);
    }
}
