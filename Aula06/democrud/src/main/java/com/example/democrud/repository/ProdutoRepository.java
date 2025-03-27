package com.example.democrud.repository;

import org.springframework.data.jpa.repository.JpaRepository; // Interface para operações JPA.
import org.springframework.stereotype.Repository;

import com.example.democrud.model.Produto;

@Repository // Indica que esta interface é um repositório Spring.
public interface ProdutoRepository extends JpaRepository<Produto, Long> {
    // Extende JpaRepository para fornecer operações CRUD padrão.
    // CrudModel: Tipo da entidade.
    // Long: Tipo da chave primária.
}
