package com.example.demo.controller;

import com.example.demo.model.Category;
import com.example.demo.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller REST para gerenciar categorias
 * Endpoints: /categories
 */
@RestController
@RequestMapping("/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    /**
     * GET /categories - Lista todas as categorias
     * @return Lista de todas as categorias
     */
    @GetMapping
    public ResponseEntity<List<Category>> getAllCategories() {
        List<Category> categories = categoryService.findAll();
        return ResponseEntity.ok(categories);
    }

    /**
     * GET /categories/{id} - Busca uma categoria por ID
     * @param id ID da categoria
     * @return Categoria encontrada ou 404 Not Found
     */
    @GetMapping("/{id}")
    public ResponseEntity<Category> getCategoryById(@PathVariable Long id) {
        return categoryService.findById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    /**
     * GET /categories/name/{name} - Busca uma categoria pelo nome
     * @param name Nome da categoria
     * @return Categoria encontrada ou 404 Not Found
     */
    @GetMapping("/name/{name}")
    public ResponseEntity<Category> getCategoryByName(@PathVariable String name) {
        return categoryService.findByName(name)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    /**
     * POST /categories - Cria uma nova categoria
     * @param category Dados da categoria a ser criada (validados com @Valid)
     * @return Categoria criada com status 201 Created
     */
    @PostMapping
    public ResponseEntity<Category> createCategory(@Valid @RequestBody Category category) {
        try {
            Category createdCategory = categoryService.create(category);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdCategory);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * PUT /categories/{id} - Atualiza uma categoria existente
     * @param id ID da categoria a ser atualizada
     * @param category Novos dados da categoria (validados com @Valid)
     * @return Categoria atualizada ou 404 Not Found
     */
    @PutMapping("/{id}")
    public ResponseEntity<Category> updateCategory(@PathVariable Long id, @Valid @RequestBody Category category) {
        return categoryService.update(id, category)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    /**
     * DELETE /categories/{id} - Deleta uma categoria
     * @param id ID da categoria a ser deletada
     * @return 204 No Content se deletada, 404 Not Found se não encontrada
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
        if (categoryService.delete(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    /**
     * GET /categories/count - Retorna o total de categorias
     * @return Número total de categorias
     */
    @GetMapping("/count")
    public ResponseEntity<Long> countCategories() {
        return ResponseEntity.ok(categoryService.count());
    }
}
