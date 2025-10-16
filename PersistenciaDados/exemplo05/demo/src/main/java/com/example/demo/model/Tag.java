package com.example.demo.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.HashSet;
import java.util.Set;

/**
 * Entidade Tag - Representa uma tag/etiqueta que pode ser associada a vários produtos
 * Relacionamento: Uma tag pode estar em vários produtos e um produto pode ter várias tags (N:N)
 * 
 * Lombok:
 * - @Data: gera getters, setters, toString, equals e hashCode
 * - @NoArgsConstructor: gera construtor vazio (obrigatório para JPA)
 * - @AllArgsConstructor: gera construtor com todos os campos
 * 
 * Bean Validation:
 * - @NotBlank: campo não pode ser nulo ou vazio
 * - @Size: define tamanho mínimo/máximo
 * - @Pattern: valida formato de cor hexadecimal
 */
@Entity
@Table(name = "tags")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Tag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Nome da tag é obrigatório")
    @Size(min = 2, max = 30, message = "Nome deve ter entre 2 e 30 caracteres")
    @Column(nullable = false, unique = true)
    private String name;

    @Pattern(regexp = "^#[0-9A-Fa-f]{6}$", message = "Cor deve estar no formato hexadecimal (#RRGGBB)")
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

    /**
     * Construtor com campos principais (sem ID e relacionamentos)
     */
    public Tag(String name, String color) {
        this.name = name;
        this.color = color;
    }
}
