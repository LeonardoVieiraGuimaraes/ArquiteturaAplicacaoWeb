package com.api.crud.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.api.crud.model.Produto;

@Repository
public interface  ProdutoRepository extends JpaRepository<Produto, Long> {

}
