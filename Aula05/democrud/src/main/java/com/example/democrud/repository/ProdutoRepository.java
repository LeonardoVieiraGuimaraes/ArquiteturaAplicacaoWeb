package com.example.democrud.repository;

import org.springframework.data.jpa.repository.JpaRepository; // Interface para operações JPA.
// import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.example.democrud.model.Produto;

@Repository // Indica que esta interface é um repositório Spring.
public interface ProdutoRepository extends JpaRepository<Produto, Long> {
  
}
