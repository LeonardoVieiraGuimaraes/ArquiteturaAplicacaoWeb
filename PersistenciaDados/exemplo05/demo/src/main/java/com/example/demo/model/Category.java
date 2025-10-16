package com.example.demo.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.ArrayList;
import java.util.List;

/**
 * Entidade Category - Representa uma categoria de produtos
 * Relacionamento: Uma categoria pode ter vários produtos (1:N)
 * 
 * Lombok:
 * - @Data: gera getters, setters, toString, equals e hashCode
 * - @NoArgsConstructor: gera construtor vazio (obrigatório para JPA)
 * - @AllArgsConstructor: gera construtor com todos os campos
 * 
 * Bean Validation:
 * - @NotBlank: campo não pode ser nulo ou vazio
 * - @Size: define tamanho mínimo/máximo
 */
@Entity
@Table(name = "categories")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Nome da categoria é obrigatório")
    @Size(min = 3, max = 50, message = "Nome deve ter entre 3 e 50 caracteres")
    @Column(nullable = false, unique = true)
    private String name;

    @Size(max = 500, message = "Descrição não pode ter mais de 500 caracteres")
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

    /**
     * Construtor com campos principais (sem ID e relacionamentos)
     */
    public Category(String name, String description) {
        this.name = name;
        this.description = description;
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
