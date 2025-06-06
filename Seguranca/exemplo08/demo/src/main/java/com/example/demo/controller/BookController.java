package com.example.demo.controller;

// As autorizações para este controlador são feitas diretamente com @PreAuthorize nos métodos.
// Exemplo: apenas ADMIN pode criar, atualizar ou deletar livros, enquanto leitura é pública.
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Book;
import com.example.demo.service.BookService;

import jakarta.validation.Valid;

@RestController // Indica que esta classe é um controlador REST
@RequestMapping("/api/books") // Define o prefixo para os endpoints deste controlador
@PreAuthorize("isAuthenticated()")
public class BookController {

    @Autowired // Injeta automaticamente a dependência do serviço BookService
    private BookService bookService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping // Define o endpoint para criar um novo livro
    public ResponseEntity<Book> saveBook(@Valid @RequestBody Book book) {
        // Exemplo: String username = authentication.getName();
        Book savedBook = bookService.saveBook(book); // Salva o livro usando o serviço
        return ResponseEntity.ok(savedBook); // Retorna o livro salvo com status 200 (OK)
    }

    @GetMapping // Define o endpoint para listar todos os livros
    public ResponseEntity<List<Book>> getAllBooks() {
        // Exemplo: String username = authentication.getName();
        List<Book> books = bookService.getAllBooks(); // Recupera todos os livros usando o serviço
        return ResponseEntity.ok(books); // Retorna a lista de livros com status 200 (OK)
    }

    @GetMapping("/{id}") // Define o endpoint para buscar um livro pelo ID
    public ResponseEntity<Book> getBookById(@PathVariable Long id) {
        // Exemplo: String username = authentication.getName();
        Optional<Book> book = bookService.getBookById(id); // Busca o livro pelo ID usando o serviço
        return book.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build()); // Retorna o livro ou 404 (Not Found)
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}") // Define o endpoint para atualizar um livro pelo ID
    public ResponseEntity<Book> updateBook(@PathVariable Long id, @Valid @RequestBody Book updatedBook) {
        // Exemplo: String username = authentication.getName();
        Optional<Book> book = bookService.updateBook(id, updatedBook); // Atualiza o livro usando o serviço
        return book.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build()); // Retorna o livro atualizado ou 404 (Not Found)
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}") // Define o endpoint para deletar um livro pelo ID
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        // Exemplo: String username = authentication.getName();
        boolean deleted = bookService.deleteBook(id); // Deleta o livro usando o serviço
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build(); // Retorna 204 (No Content) ou 404 (Not Found)
    }
}
