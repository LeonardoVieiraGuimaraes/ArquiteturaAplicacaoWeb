package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import com.example.demo.model.Product;

public interface IProductService {
    List<Product> getAllProducts();
    Optional<Product> getProductById(Long id);
    Optional<Product> getProductByName(String name);
    Product addProduct(Product product);
    Product createProduct(Product product);
    Optional<Product> updateProduct(Long id, Product updatedProduct);
    Product updateProduct(Product product);
    boolean deleteProduct(Long id);
}
