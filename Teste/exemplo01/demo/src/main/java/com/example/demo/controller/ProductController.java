package com.example.demo.controller;

// Importa as classes necessárias
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import com.example.demo.model.Product;
import com.example.demo.service.ProductService;

// Indica que esta classe é um controlador REST
@RestController
// Define a rota base para os endpoints deste controlador
@RequestMapping("/products")
public class ProductController {
    // Injeta uma instância de ProductService
    @Autowired
    private ProductService productService;

    // GET: Listar todos os produtos
    @GetMapping
    public List<Product> getAllProducts() {
        // Chama o serviço para obter todos os produtos
        return productService.getAllProducts();
    }

    // GET: Buscar produto por ID
    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        // Chama o serviço para obter um produto pelo ID
        Optional<Product> product = productService.getProductById(id);
        // Retorna o produto encontrado ou um status 404 se não encontrado
        return product.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<Product> getProductByName(@PathVariable String name) {
        // Chama o serviço para obter um produto pelo nome
        Optional<Product> product = productService.getProductByName(name);
        // Retorna o produto encontrado ou um status 404 se não encontrado
        return product.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // POST: Adicionar um novo produto
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Product addProduct(@RequestBody Product product) {
        // Chama o serviço para adicionar um novo produto
        return productService.addProduct(product);
    }

    // PUT: Atualizar um produto existente
    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable Long id, @RequestBody Product updatedProduct) {
        // Chama o serviço para atualizar um produto existente
        Optional<Product> product = productService.updateProduct(id, updatedProduct);
        // Retorna o produto atualizado ou um status 404 se não encontrado
        return product.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // DELETE: Remover um produto por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        // Chama o serviço para deletar um produto pelo ID
        boolean deleted = productService.deleteProduct(id);
        // Retorna um status 204 se deletado ou 404 se não encontrado
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
