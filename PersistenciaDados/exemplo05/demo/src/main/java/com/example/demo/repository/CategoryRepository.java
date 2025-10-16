package com.example.demo.repository;

import com.example.demo.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository para a entidade Category
 * Estende JpaRepository que fornece métodos CRUD prontos
 */
@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    
    /**
     * Busca uma categoria pelo nome
     * @param name Nome da categoria
     * @return Optional contendo a categoria encontrada ou vazio
     */
    Optional<Category> findByName(String name);
    
    /**
     * Verifica se existe uma categoria com o nome fornecido
     * @param name Nome da categoria
     * @return true se existir, false caso contrário
     */
    boolean existsByName(String name);
}
