package com.example.demo.service;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import com.example.demo.model.Product;
@Service
public class ProductService {
    // Lista de produtos armazenados em memória
    private final List<Product> products = new ArrayList<>();
    // Variável para gerar IDs únicos para novos produtos
    private long nextId = 1;
    // GET: Retorna todos os produtos
    public List<Product> getAllProducts() {
        return products;
    }
    // GET: Busca um produto por ID
    public Optional<Product> getProductById(Long id) {
        // Filtra a lista de produtos para encontrar o produto com o ID especificado
        return products.stream()
                .filter(p -> p.getId().equals(id))
                .findFirst();
    }
    // GET: Busca um produto por nome
    public Optional<Product> getProductByName(String name) {
        // Filtra a lista de produtos para encontrar o produto com o nome especificado
        return products.stream()
                .filter(p -> p.getName().equals(name))
                .findFirst();
    }
    // POST: Adiciona um novo produto
    public Product addProduct(Product product) {
        // Define um ID único para o novo produto
        product.setId(nextId++);
        // Adiciona o novo produto à lista de produtos
        products.add(product);
        return product;
    }
    // PUT: Atualiza um produto existente
    public Optional<Product> updateProduct(Long id, Product updatedProduct) {
        // Busca o produto existente pelo ID
        Optional<Product> existingProduct = getProductById(id);
        if (existingProduct.isPresent()) {
            // Se o produto existir, atualiza seu nome
            Product product = existingProduct.get();
            product.setName(updatedProduct.getName());
        }
        return existingProduct;
    }
    // DELETE: Remove um produto por ID
    public boolean deleteProduct(Long id) {
        // Remove o produto da lista se o ID corresponder
        return products.removeIf(p -> p.getId().equals(id));
    }
}
