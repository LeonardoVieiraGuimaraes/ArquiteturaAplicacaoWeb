package com.example.democrud.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired; // Injeta dependências automaticamente.
import org.springframework.http.ResponseEntity; // Representa respostas HTTP.
import org.springframework.web.bind.annotation.DeleteMapping; // Importa anotações para endpoints REST.
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.democrud.model.Produto;
import com.example.democrud.service.ProdutoService;

@RestController // Indica que esta classe é um controlador REST.
@RequestMapping("/api/crud") // Define o caminho base para os endpoints.
public class ProdutoController {

    @Autowired // Injeta automaticamente a instância do CrudService.
    private ProdutoService crudService;

    @GetMapping // Mapeia requisições GET para "/api/crud".
    public List<Produto> getAll() {
        // Retorna todos os registros.
        return crudService.findAll();
    }

    @GetMapping("/{id}") // Mapeia requisições GET para "/api/crud/{id}".
    public ResponseEntity<Produto> getById(@PathVariable Long id) {
        // Retorna um registro pelo ID ou 404 se não encontrado.
        return crudService.findById(id)
                .map(ResponseEntity::ok) // Retorna 200 OK com o registro.
                .orElse(ResponseEntity.notFound().build()); // Retorna 404 Not Found.
    }

    @PostMapping // Mapeia requisições POST para "/api/crud".
    public Produto create(@RequestBody Produto model) {
        // Cria um novo registro.
        return crudService.save(model);
    }

    @PutMapping("/{id}") // Mapeia requisições PUT para "/api/crud/{id}".
    public ResponseEntity<Produto> update(@PathVariable Long id, @RequestBody Produto model) {
        // Atualiza um registro existente ou retorna 404 se não encontrado.
        return crudService.update(id, model)
                .map(ResponseEntity::ok) // Retorna 200 OK com o registro atualizado.
                .orElse(ResponseEntity.notFound().build()); // Retorna 404 Not Found.
    }

    @DeleteMapping("/{id}") // Mapeia requisições DELETE para "/api/crud/{id}".
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        // Remove um registro pelo ID ou retorna 404 se não encontrado.
        if (crudService.deleteById(id)) {
            return ResponseEntity.noContent().build(); // Retorna 204 No Content.
        } else {
            return ResponseEntity.notFound().build(); // Retorna 404 Not Found.
        }
    }
}
