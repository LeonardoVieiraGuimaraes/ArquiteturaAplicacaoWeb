package com.example.democrud.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.example.democrud.model.Produto;

@Repository
public interface ProdutoRepository extends MongoRepository<Produto, String> {
    List<Produto> findByName(String name);
    List<Produto> findByDescription(String description);
}