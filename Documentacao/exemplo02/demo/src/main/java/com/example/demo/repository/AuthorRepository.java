package com.example.demo.repository;

import com.example.demo.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Camada de acesso a dados do Author.
 * - Estende JpaRepository para operações CRUD prontas.
 */
@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {
}
