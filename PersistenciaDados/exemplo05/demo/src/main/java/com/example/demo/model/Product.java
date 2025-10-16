// Pacote onde a classe Product está localizada
package com.example.demo.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

/**
 * Entidade Product - Representa um produto no sistema
 * 
 * Relacionamentos:
 * - Many-to-One com Category (vários produtos pertencem a uma categoria)
 * - Many-to-Many com Tag (um produto pode ter várias tags e uma tag pode estar em vários produtos)
 * 
 * Lombok:
 * - @Data: gera getters, setters, toString, equals e hashCode
 * - @NoArgsConstructor: gera construtor vazio (obrigatório para JPA)
 * - @AllArgsConstructor: gera construtor com todos os campos
 * 
 * Bean Validation:
 * - @NotBlank: campo não pode ser nulo ou vazio
 * - @Size: define tamanho mínimo/máximo
 * - @DecimalMin: valor mínimo para BigDecimal
 * - @Min: valor mínimo para Integer
 */
@Entity
@Table(name = "products")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Nome é obrigatório")
    @Size(min = 3, max = 100, message = "Nome deve ter entre 3 e 100 caracteres")
    @Column(nullable = false)
    private String name;

    @Size(max = 1000, message = "Descrição não pode ter mais de 1000 caracteres")
    @Column(length = 1000)
    private String description;

    @NotNull(message = "Preço é obrigatório")
    @DecimalMin(value = "0.01", message = "Preço deve ser maior que zero")
    @Column(precision = 10, scale = 2)
    private BigDecimal price;

    @NotNull(message = "Estoque é obrigatório")
    @Min(value = 0, message = "Estoque não pode ser negativo")
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

    /**
     * Construtor com campos principais (sem ID e relacionamentos)
     */
    public Product(String name, String description, BigDecimal price, Integer stock) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.stock = stock;
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
