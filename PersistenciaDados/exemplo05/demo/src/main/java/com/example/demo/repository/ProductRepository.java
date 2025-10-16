package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Product;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

/**
 * Repository para a entidade Product
 * Estende JpaRepository que fornece métodos CRUD prontos
 */
@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    
    /**
     * Busca um produto pelo nome
     * @param name Nome do produto
     * @return Optional contendo o produto encontrado ou vazio
     */
    Optional<Product> findByName(String name);

    /**
     * Busca produtos por categoria
     * @param categoryId ID da categoria
     * @return Lista de produtos da categoria
     */
    List<Product> findByCategoryId(Long categoryId);

    /**
     * Busca produtos com preço menor ou igual ao especificado
     * @param price Preço máximo
     * @return Lista de produtos com preço <= price
     */
    List<Product> findByPriceLessThanEqual(BigDecimal price);

    /**
     * Busca produtos com estoque menor ou igual ao especificado
     * @param stock Estoque máximo
     * @return Lista de produtos com estoque <= stock
     */
    List<Product> findByStockLessThanEqual(Integer stock);

    /**
     * Busca produtos que contenham o texto no nome (case insensitive)
     * @param name Texto a buscar no nome
     * @return Lista de produtos que contenham o texto
     */
    List<Product> findByNameContainingIgnoreCase(String name);

    /**
     * Busca produtos por uma tag específica usando query JPQL
     * @param tagId ID da tag
     * @return Lista de produtos que possuem a tag
     */
    @Query("SELECT p FROM Product p JOIN p.tags t WHERE t.id = :tagId")
    List<Product> findByTagId(Long tagId);
}
