package com.api.crud.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.api.crud.model.Produto;
import com.api.crud.repository.ProdutoRepository;


@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;

    //Listar Produto 
    public List<Produto> findAll() {
        return produtoRepository.findAll();
    }

    //Mostrar Produtor por id
    public Produto findById(Long id) {
        return produtoRepository.findById(id).orElse(null);
    }

    //Novo Produto 
    public Produto save(Produto produto) {
        return produtoRepository.save(produto);
    }

    //Atualizar Produto
    public Produto update(Long id, Produto produto) {
        Produto produtoUpdate = findById(id);
        produtoUpdate.setNome(produto.getNome());
        produtoUpdate.setQuantidade(produto.getQuantidade());
        return produtoRepository.save(produtoUpdate);
    }

    //Deletar Produto   
    public Boolean delete(Long id) {
        Produto produto = findById(id);
        if (produto != null) {
            produtoRepository.delete(produto);
            return true;
        } else {
            return false;
        }
    }
}
