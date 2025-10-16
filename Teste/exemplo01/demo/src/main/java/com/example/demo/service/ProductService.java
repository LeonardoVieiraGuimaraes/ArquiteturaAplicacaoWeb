package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Product;
import com.example.demo.repository.ProductRepository;

@Service
public class ProductService implements IProductService {
    
    @Autowired
    private ProductRepository productRepository;

    // Construtor vazio para Spring
    public ProductService() {
    }

    // Construtor para testes que recebem um repository mock
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

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
        return createProduct(product);
    }

    // Nome alternativo usado pelos testes
    public Product createProduct(Product product) {
        return productRepository.save(product);
    }

    // PUT: Atualiza um produto existente
    public Optional<Product> updateProduct(Long id, Product updatedProduct) {
        Optional<Product> existingProduct = productRepository.findById(id);
        if (existingProduct.isPresent()) {
            Product product = existingProduct.get();
            product.setName(updatedProduct.getName());
            // Atualiza campos adicionais, se houver
            if (updatedProduct.getDescription() != null) product.setDescription(updatedProduct.getDescription());
            if (updatedProduct.getPrice() != null) product.setPrice(updatedProduct.getPrice());
            if (updatedProduct.getStock() != null) product.setStock(updatedProduct.getStock());
            productRepository.save(product);
        }
        return existingProduct;
    }

    // Versão utilizada em alguns testes que esperam receber o produto atualizado
    public Product updateProduct(Product product) {
        return productRepository.save(product);
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
