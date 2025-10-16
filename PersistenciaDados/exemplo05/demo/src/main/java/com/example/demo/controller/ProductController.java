package com.example.demo.controller;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.demo.model.Product;
import com.example.demo.service.ProductService;

/**
 * Controller REST para gerenciar produtos
 * Endpoints: /products
 * 
 * Suporta relacionamentos com Category (Many-to-One) e Tags (Many-to-Many)
 */
@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    /**
     * GET /products - Lista todos os produtos
     * @return Lista de todos os produtos com suas categorias e tags
     */
    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts() {
        List<Product> products = productService.getAllProducts();
        return ResponseEntity.ok(products);
    }

    /**
     * GET /products/{id} - Busca um produto por ID
     * @param id ID do produto
     * @return Produto encontrado ou 404 Not Found
     */
    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        return productService.getProductById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * GET /products/name/{name} - Busca um produto pelo nome
     * @param name Nome do produto
     * @return Produto encontrado ou 404 Not Found
     */
    @GetMapping("/name/{name}")
    public ResponseEntity<Product> getProductByName(@PathVariable String name) {
        return productService.getProductByName(name)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * GET /products/category/{categoryId} - Busca produtos por categoria
     * @param categoryId ID da categoria
     * @return Lista de produtos da categoria
     */
    @GetMapping("/category/{categoryId}")
    public ResponseEntity<List<Product>> getProductsByCategory(@PathVariable Long categoryId) {
        List<Product> products = productService.getProductsByCategory(categoryId);
        return ResponseEntity.ok(products);
    }

    /**
     * POST /products - Cria um novo produto
     * 
     * Body JSON exemplo:
     * {
     *   "name": "Notebook Dell",
     *   "description": "Notebook Dell XPS 15",
     *   "price": 5499.99,
     *   "stock": 10,
     *   "categoryId": 1,
     *   "tagIds": [1, 2, 3]
     * }
     * 
     * @param requestBody Mapa contendo os dados do produto e IDs de relacionamentos
     * @return Produto criado com status 201 Created
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Product> addProduct(@RequestBody Map<String, Object> requestBody) {
        // Extrai os dados do produto
        Product product = new Product();
        product.setName((String) requestBody.get("name"));
        product.setDescription((String) requestBody.get("description"));
        
        if (requestBody.get("price") != null) {
            product.setPrice(new java.math.BigDecimal(requestBody.get("price").toString()));
        }
        if (requestBody.get("stock") != null) {
            product.setStock((Integer) requestBody.get("stock"));
        }

        // Extrai o ID da categoria
        Long categoryId = requestBody.get("categoryId") != null 
            ? Long.valueOf(requestBody.get("categoryId").toString()) 
            : null;

        // Extrai os IDs das tags
        @SuppressWarnings("unchecked")
        List<Integer> tagIdsList = (List<Integer>) requestBody.get("tagIds");
        Set<Long> tagIds = tagIdsList != null 
            ? tagIdsList.stream()
                .map(Integer::longValue)
                .collect(java.util.stream.Collectors.toSet())
            : null;

        Product createdProduct = productService.addProduct(product, categoryId, tagIds);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdProduct);
    }

    /**
     * PUT /products/{id} - Atualiza um produto existente
     * 
     * Body JSON exemplo:
     * {
     *   "name": "Notebook Dell Updated",
     *   "description": "Nova descrição",
     *   "price": 5999.99,
     *   "stock": 5,
     *   "categoryId": 2,
     *   "tagIds": [1, 3]
     * }
     * 
     * @param id ID do produto a ser atualizado
     * @param requestBody Mapa contendo os novos dados do produto
     * @return Produto atualizado ou 404 Not Found
     */
    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable Long id, @RequestBody Map<String, Object> requestBody) {
        // Extrai os dados do produto
        Product product = new Product();
        product.setName((String) requestBody.get("name"));
        product.setDescription((String) requestBody.get("description"));
        
        if (requestBody.get("price") != null) {
            product.setPrice(new java.math.BigDecimal(requestBody.get("price").toString()));
        }
        if (requestBody.get("stock") != null) {
            product.setStock((Integer) requestBody.get("stock"));
        }

        // Extrai o ID da categoria
        Long categoryId = requestBody.get("categoryId") != null 
            ? Long.valueOf(requestBody.get("categoryId").toString()) 
            : null;

        // Extrai os IDs das tags
        @SuppressWarnings("unchecked")
        List<Integer> tagIdsList = (List<Integer>) requestBody.get("tagIds");
        Set<Long> tagIds = tagIdsList != null 
            ? tagIdsList.stream()
                .map(Integer::longValue)
                .collect(java.util.stream.Collectors.toSet())
            : null;

        return productService.updateProduct(id, product, categoryId, tagIds)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * POST /products/{productId}/tags/{tagId} - Adiciona uma tag ao produto
     * @param productId ID do produto
     * @param tagId ID da tag
     * @return Produto atualizado ou 404 Not Found
     */
    @PostMapping("/{productId}/tags/{tagId}")
    public ResponseEntity<Product> addTagToProduct(@PathVariable Long productId, @PathVariable Long tagId) {
        return productService.addTagToProduct(productId, tagId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * DELETE /products/{productId}/tags/{tagId} - Remove uma tag do produto
     * @param productId ID do produto
     * @param tagId ID da tag
     * @return Produto atualizado ou 404 Not Found
     */
    @DeleteMapping("/{productId}/tags/{tagId}")
    public ResponseEntity<Product> removeTagFromProduct(@PathVariable Long productId, @PathVariable Long tagId) {
        return productService.removeTagFromProduct(productId, tagId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * DELETE /products/{id} - Deleta um produto
     * @param id ID do produto a ser deletado
     * @return 204 No Content se deletado, 404 Not Found se não encontrado
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        boolean deleted = productService.deleteProduct(id);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    /**
     * GET /products/count - Retorna o total de produtos
     * @return Número total de produtos
     */
    @GetMapping("/count")
    public ResponseEntity<Long> countProducts() {
        return ResponseEntity.ok(productService.count());
    }
}
