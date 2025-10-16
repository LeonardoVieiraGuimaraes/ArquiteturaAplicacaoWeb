package com.example.demo.controller;

import com.example.demo.model.Tag;
import com.example.demo.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller REST para gerenciar tags
 * Endpoints: /tags
 */
@RestController
@RequestMapping("/tags")
public class TagController {

    @Autowired
    private TagService tagService;

    /**
     * GET /tags - Lista todas as tags
     * @return Lista de todas as tags
     */
    @GetMapping
    public ResponseEntity<List<Tag>> getAllTags() {
        List<Tag> tags = tagService.findAll();
        return ResponseEntity.ok(tags);
    }

    /**
     * GET /tags/{id} - Busca uma tag por ID
     * @param id ID da tag
     * @return Tag encontrada ou 404 Not Found
     */
    @GetMapping("/{id}")
    public ResponseEntity<Tag> getTagById(@PathVariable Long id) {
        return tagService.findById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    /**
     * GET /tags/name/{name} - Busca uma tag pelo nome
     * @param name Nome da tag
     * @return Tag encontrada ou 404 Not Found
     */
    @GetMapping("/name/{name}")
    public ResponseEntity<Tag> getTagByName(@PathVariable String name) {
        return tagService.findByName(name)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    /**
     * POST /tags - Cria uma nova tag
     * @param tag Dados da tag a ser criada
     * @return Tag criada com status 201 Created
     */
    @PostMapping
    public ResponseEntity<Tag> createTag(@RequestBody Tag tag) {
        try {
            Tag createdTag = tagService.create(tag);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdTag);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * PUT /tags/{id} - Atualiza uma tag existente
     * @param id ID da tag a ser atualizada
     * @param tag Novos dados da tag
     * @return Tag atualizada ou 404 Not Found
     */
    @PutMapping("/{id}")
    public ResponseEntity<Tag> updateTag(@PathVariable Long id, @RequestBody Tag tag) {
        return tagService.update(id, tag)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    /**
     * DELETE /tags/{id} - Deleta uma tag
     * @param id ID da tag a ser deletada
     * @return 204 No Content se deletada, 404 Not Found se não encontrada
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTag(@PathVariable Long id) {
        if (tagService.delete(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    /**
     * GET /tags/count - Retorna o total de tags
     * @return Número total de tags
     */
    @GetMapping("/count")
    public ResponseEntity<Long> countTags() {
        return ResponseEntity.ok(tagService.count());
    }
}
