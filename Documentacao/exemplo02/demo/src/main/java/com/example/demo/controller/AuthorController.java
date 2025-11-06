package com.example.demo.controller;

import com.example.demo.dto.AuthorRequest;
import com.example.demo.dto.AuthorResponse;
import com.example.demo.service.AuthorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Objects;
import java.util.List;

/**
 * Controller REST responsável pelos endpoints de Autores.
 * - Camada "C" do padrão MVC, expondo a API HTTP.
 */
@RestController
@RequestMapping("/api/authors")
@RequiredArgsConstructor
@Tag(name = "Autores", description = "CRUD de autores")
public class AuthorController {

    private final AuthorService authorService;

    @Operation(summary = "Cria um novo autor")
    @PostMapping
    public ResponseEntity<AuthorResponse> create(@Valid @RequestBody AuthorRequest request) {
    AuthorResponse response = authorService.create(request);
    URI location = URI.create("/api/authors/" + response.getId());
    Objects.requireNonNull(location);
    return ResponseEntity.created(location).body(response);
    }

    @Operation(summary = "Atualiza um autor pelo id")
    @PutMapping("/{id}")
    public AuthorResponse update(@PathVariable @NonNull Long id, @Valid @RequestBody AuthorRequest request) {
        return authorService.update(id, request);
    }

    @Operation(summary = "Busca um autor pelo id")
    @GetMapping("/{id}")
    public AuthorResponse getById(@PathVariable @NonNull Long id) {
        return authorService.getById(id);
    }

    @Operation(summary = "Lista todos os autores")
    @GetMapping
    public List<AuthorResponse> listAll() {
        return authorService.listAll();
    }

    @Operation(summary = "Exclui um autor pelo id")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable @NonNull Long id) {
        authorService.delete(id);
    }
}
