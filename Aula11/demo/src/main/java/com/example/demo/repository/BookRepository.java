package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Book;

/**
 * Repository interface for {@link Book} entity. Provides CRUD operations and
 * custom query methods.
 */
@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    // Interface que fornece métodos prontos para operações CRUD no banco de dados
    // O JpaRepository já implementa métodos como save, findById, findAll e deleteById
}
