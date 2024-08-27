package com.api.crud.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.crud.model.Produto;
import com.api.crud.service.ProdutoService;

@RestController
@RequestMapping("/produto")
public class ProdutoController {

    @Autowired
    private ProdutoService produtoService;

    @GetMapping("/listar")
    public ResponseEntity<List<Produto>> findAll() {
        List<Produto> produtos = produtoService.findAll();
        return ResponseEntity.ok().body(produtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Produto> findById(@PathVariable Long id) {
        Produto produto = produtoService.findById(id);
        return ResponseEntity.ok().body(produto);
    }

    @PostMapping("/criar")
    public ResponseEntity<Produto> create(@RequestBody Produto produto) {
        Produto novoProduto = produtoService.save(produto);
        return ResponseEntity.ok().body(novoProduto);
    }

    @PutMapping("/atualizar/{id}")
    public ResponseEntity<Produto> update(@PathVariable Long id, @RequestBody Produto produto) {
        Produto produtoAtualizado = produtoService.update(id, produto);
        return ResponseEntity.ok().body(produtoAtualizado);
    }

    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable Long id) {
       Boolean flag = produtoService.delete(id);
       return ResponseEntity.ok().body(flag);

    }
}
