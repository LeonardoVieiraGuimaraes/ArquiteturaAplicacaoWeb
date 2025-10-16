package com.example.demo.service;

import com.example.demo.model.Category;
import com.example.demo.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service para gerenciar a lógica de negócio relacionada a categorias
 */
@Service
@Transactional
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    /**
     * Lista todas as categorias
     * @return Lista de todas as categorias
     */
    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    /**
     * Busca uma categoria por ID
     * @param id ID da categoria
     * @return Optional contendo a categoria encontrada ou vazio
     */
    public Optional<Category> findById(Long id) {
        return categoryRepository.findById(id);
    }

    /**
     * Busca uma categoria pelo nome
     * @param name Nome da categoria
     * @return Optional contendo a categoria encontrada ou vazio
     */
    public Optional<Category> findByName(String name) {
        return categoryRepository.findByName(name);
    }

    /**
     * Cria uma nova categoria
     * @param category Categoria a ser criada
     * @return Categoria criada
     * @throws IllegalArgumentException se já existir uma categoria com o mesmo nome
     */
    public Category create(Category category) {
        if (categoryRepository.existsByName(category.getName())) {
            throw new IllegalArgumentException("Já existe uma categoria com o nome: " + category.getName());
        }
        return categoryRepository.save(category);
    }

    /**
     * Atualiza uma categoria existente
     * @param id ID da categoria a ser atualizada
     * @param categoryDetails Novos dados da categoria
     * @return Optional contendo a categoria atualizada ou vazio se não encontrada
     */
    public Optional<Category> update(Long id, Category categoryDetails) {
        return categoryRepository.findById(id)
            .map(category -> {
                category.setName(categoryDetails.getName());
                category.setDescription(categoryDetails.getDescription());
                return categoryRepository.save(category);
            });
    }

    /**
     * Deleta uma categoria
     * @param id ID da categoria a ser deletada
     * @return true se a categoria foi deletada, false se não foi encontrada
     */
    public boolean delete(Long id) {
        return categoryRepository.findById(id)
            .map(category -> {
                categoryRepository.delete(category);
                return true;
            })
            .orElse(false);
    }

    /**
     * Conta o total de categorias
     * @return Número total de categorias
     */
    public long count() {
        return categoryRepository.count();
    }
}
