package com.example.demo.repository;

import com.example.demo.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Camada de acesso a dados do Book.
 */
@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
}
