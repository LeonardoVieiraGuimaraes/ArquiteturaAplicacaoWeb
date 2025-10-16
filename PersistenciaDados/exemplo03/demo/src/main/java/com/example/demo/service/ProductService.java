package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Product;
import com.example.demo.repository.ProductRepository;

@Service
public class ProductService {
    
    @Autowired
    private ProductRepository productRepository;

    // GET: Retorna todos os produtos
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    // GET: Busca um produto por ID
    public Optional<Product> getProductById(Long id) {
        return productRepository.findById(id);
    }

    // GET: Busca um produto por nome
    public Optional<Product> getProductByName(String name) {
        return productRepository.findByName(name);
    }

    // POST: Adiciona um novo produto
    public Product addProduct(Product product) {
        return productRepository.save(product);
    }

    // PUT: Atualiza um produto existente
    public Optional<Product> updateProduct(Long id, Product updatedProduct) {
        Optional<Product> existingProduct = productRepository.findById(id);
        if (existingProduct.isPresent()) {
            Product product = existingProduct.get();
            product.setName(updatedProduct.getName());
            productRepository.save(product);
        }
        return existingProduct;
    }

    // DELETE: Remove um produto por ID
    public boolean deleteProduct(Long id) {
        if (productRepository.existsById(id)) {
            productRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
