package com.example.demo.service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.model.Product;
import com.example.demo.model.Category;
import com.example.demo.model.Tag;
import com.example.demo.repository.ProductRepository;
import com.example.demo.repository.CategoryRepository;
import com.example.demo.repository.TagRepository;

/**
 * Service para gerenciar a lógica de negócio relacionada a produtos
 * Inclui gerenciamento de relacionamentos com Category e Tags
 */
@Service
@Transactional
public class ProductService {
    
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private TagRepository tagRepository;

    /**
     * Lista todos os produtos
     * @return Lista de todos os produtos
     */
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    /**
     * Busca um produto por ID
     * @param id ID do produto
     * @return Optional contendo o produto encontrado ou vazio
     */
    public Optional<Product> getProductById(Long id) {
        return productRepository.findById(id);
    }

    /**
     * Busca um produto pelo nome
     * @param name Nome do produto
     * @return Optional contendo o produto encontrado ou vazio
     */
    public Optional<Product> getProductByName(String name) {
        return productRepository.findByName(name);
    }

    /**
     * Busca produtos por categoria
     * @param categoryId ID da categoria
     * @return Lista de produtos da categoria
     */
    public List<Product> getProductsByCategory(Long categoryId) {
        return productRepository.findByCategoryId(categoryId);
    }

    /**
     * Cria um novo produto
     * @param product Produto a ser criado
     * @param categoryId ID da categoria (opcional)
     * @param tagIds IDs das tags (opcional)
     * @return Produto criado
     */
    public Product addProduct(Product product, Long categoryId, Set<Long> tagIds) {
        // Associa a categoria se fornecida
        if (categoryId != null) {
            Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new IllegalArgumentException("Categoria não encontrada com ID: " + categoryId));
            product.setCategory(category);
        }

        // Associa as tags se fornecidas
        if (tagIds != null && !tagIds.isEmpty()) {
            Set<Tag> tags = Set.copyOf(tagRepository.findAllById(tagIds));
            product.setTags(tags);
        }

        return productRepository.save(product);
    }

    /**
     * Atualiza um produto existente
     * @param id ID do produto a ser atualizado
     * @param updatedProduct Novos dados do produto
     * @param categoryId ID da nova categoria (opcional)
     * @param tagIds IDs das novas tags (opcional)
     * @return Optional contendo o produto atualizado ou vazio se não encontrado
     */
    public Optional<Product> updateProduct(Long id, Product updatedProduct, Long categoryId, Set<Long> tagIds) {
        return productRepository.findById(id)
            .map(product -> {
                product.setName(updatedProduct.getName());
                product.setDescription(updatedProduct.getDescription());
                product.setPrice(updatedProduct.getPrice());
                product.setStock(updatedProduct.getStock());

                // Atualiza a categoria se fornecida
                if (categoryId != null) {
                    Category category = categoryRepository.findById(categoryId)
                        .orElseThrow(() -> new IllegalArgumentException("Categoria não encontrada com ID: " + categoryId));
                    product.setCategory(category);
                }

                // Atualiza as tags se fornecidas
                if (tagIds != null) {
                    product.getTags().clear();
                    if (!tagIds.isEmpty()) {
                        Set<Tag> tags = Set.copyOf(tagRepository.findAllById(tagIds));
                        product.setTags(tags);
                    }
                }

                return productRepository.save(product);
            });
    }

    /**
     * Adiciona uma tag a um produto
     * @param productId ID do produto
     * @param tagId ID da tag
     * @return Optional contendo o produto atualizado ou vazio se não encontrado
     */
    public Optional<Product> addTagToProduct(Long productId, Long tagId) {
        return productRepository.findById(productId)
            .map(product -> {
                Tag tag = tagRepository.findById(tagId)
                    .orElseThrow(() -> new IllegalArgumentException("Tag não encontrada com ID: " + tagId));
                product.addTag(tag);
                return productRepository.save(product);
            });
    }

    /**
     * Remove uma tag de um produto
     * @param productId ID do produto
     * @param tagId ID da tag
     * @return Optional contendo o produto atualizado ou vazio se não encontrado
     */
    public Optional<Product> removeTagFromProduct(Long productId, Long tagId) {
        return productRepository.findById(productId)
            .map(product -> {
                Tag tag = tagRepository.findById(tagId)
                    .orElseThrow(() -> new IllegalArgumentException("Tag não encontrada com ID: " + tagId));
                product.removeTag(tag);
                return productRepository.save(product);
            });
    }

    /**
     * Deleta um produto
     * @param id ID do produto a ser deletado
     * @return true se o produto foi deletado, false se não foi encontrado
     */
    public boolean deleteProduct(Long id) {
        if (productRepository.existsById(id)) {
            productRepository.deleteById(id);
            return true;
        }
        return false;
    }

    /**
     * Conta o total de produtos
     * @return Número total de produtos
     */
    public long count() {
        return productRepository.count();
    }
}
