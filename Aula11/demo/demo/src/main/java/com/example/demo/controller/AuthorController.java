package com.example.demo.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Author;
import com.example.demo.service.AuthorService;

import jakarta.validation.Valid;

@RestController // Indica que esta classe é um controlador REST
@RequestMapping("/api/authors") // Define o prefixo para os endpoints deste controlador
public class AuthorController {

    @Autowired // Injeta automaticamente a dependência do serviço AuthorService
    private AuthorService authorService;

    @PostMapping // Define o endpoint para criar um novo autor
    public ResponseEntity<Author> saveAuthor(@Valid @RequestBody Author author) {
        Author savedAuthor = authorService.saveAuthor(author); // Salva o autor usando o serviço
        return ResponseEntity.ok(savedAuthor); // Retorna o autor salvo com status 200 (OK)
    }

    @GetMapping // Define o endpoint para listar todos os autores
    public ResponseEntity<List<Author>> getAllAuthors() {
        List<Author> authors = authorService.getAllAuthors(); // Recupera todos os autores usando o serviço
        return ResponseEntity.ok(authors); // Retorna a lista de autores com status 200 (OK)
    }

    @GetMapping("/{id}") // Define o endpoint para buscar um autor pelo ID
    public ResponseEntity<Author> getAuthorById(@PathVariable Long id) {
        Optional<Author> author = authorService.getAuthorById(id); // Busca o autor pelo ID usando o serviço
        return author.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build()); // Retorna o autor ou 404 (Not Found)
    }

    @PutMapping("/{id}") // Define o endpoint para atualizar um autor pelo ID
    public ResponseEntity<Author> updateAuthor(@PathVariable Long id, @Valid @RequestBody Author updatedAuthor) {
        Optional<Author> author = authorService.updateAuthor(id, updatedAuthor); // Atualiza o autor usando o serviço
        return author.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build()); // Retorna o autor atualizado ou 404 (Not Found)
    }

    @DeleteMapping("/{id}") // Define o endpoint para deletar um autor pelo ID
    public ResponseEntity<Void> deleteAuthor(@PathVariable Long id) {
        boolean deleted = authorService.deleteAuthor(id); // Deleta o autor usando o serviço
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build(); // Retorna 204 (No Content) ou 404 (Not Found)
    }
}
