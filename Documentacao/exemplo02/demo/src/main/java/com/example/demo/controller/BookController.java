package com.example.demo.controller;

import com.example.demo.dto.BookRequest;
import com.example.demo.dto.BookResponse;
import com.example.demo.service.BookService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Objects;

/**
 * Controller REST para recursos de Livros.
 */
@RestController
@RequestMapping("/api/books")
@RequiredArgsConstructor
@Tag(name = "Livros", description = "CRUD de livros")
public class BookController {

    private final BookService bookService;

    @Operation(summary = "Cria um novo livro")
    @PostMapping
    public ResponseEntity<BookResponse> create(@Valid @RequestBody BookRequest request) {
        BookResponse response = bookService.create(request);
        URI location = URI.create("/api/books/" + response.getId());
        Objects.requireNonNull(location);
        return ResponseEntity.created(location).body(response);
    }

    @Operation(summary = "Atualiza um livro pelo id")
    @PutMapping("/{id}")
    public BookResponse update(@PathVariable @NonNull Long id, @Valid @RequestBody BookRequest request) {
        return bookService.update(id, request);
    }

    @Operation(summary = "Busca um livro pelo id")
    @GetMapping("/{id}")
    public BookResponse getById(@PathVariable @NonNull Long id) {
        return bookService.getById(id);
    }

    @Operation(summary = "Lista todos os livros")
    @GetMapping
    public List<BookResponse> listAll() {
        return bookService.listAll();
    }

    @Operation(summary = "Exclui um livro pelo id")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable @NonNull Long id) {
        bookService.delete(id);
    }
}
