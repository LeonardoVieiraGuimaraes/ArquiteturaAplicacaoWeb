package com.example.democrud.controller;

// Importações necessárias para o funcionamento do controlador REST.
import java.util.List; // Para trabalhar com listas de objetos.
import org.springframework.beans.factory.annotation.Autowired; // Injeta dependências automaticamente.
import org.springframework.http.ResponseEntity; // Representa respostas HTTP.
import org.springframework.web.bind.annotation.DeleteMapping; // Define endpoints para requisições DELETE.
import org.springframework.web.bind.annotation.GetMapping; // Define endpoints para requisições GET.
import org.springframework.web.bind.annotation.PathVariable; // Captura variáveis de caminho na URL.
import org.springframework.web.bind.annotation.PostMapping; // Define endpoints para requisições POST.
import org.springframework.web.bind.annotation.PutMapping; // Define endpoints para requisições PUT.
import org.springframework.web.bind.annotation.RequestBody; // Captura o corpo da requisição.
import org.springframework.web.bind.annotation.RequestMapping; // Define o caminho base para os endpoints.
import org.springframework.web.bind.annotation.RestController; // Marca a classe como um controlador REST.

import com.example.democrud.model.Produto; // Importa o modelo Produto.
import com.example.democrud.service.ProdutoService; // Importa o serviço ProdutoService para lógica de negócios.

// Indica que esta classe é um controlador REST, permitindo que ela manipule requisições HTTP.
@RestController
// Define o caminho base "/api/crud" para todos os endpoints desta classe.
@RequestMapping("/api/crud")
public class ProdutoController {

    // Injeta automaticamente uma instância de ProdutoService para uso no controlador.
    @Autowired
    private ProdutoService crudService;

    // Endpoint para obter todos os produtos.
    @GetMapping
    public List<Produto> getAll() {
        // Chama o serviço para buscar todos os produtos e retorna a lista.
        return crudService.findAll();
    }

    // Endpoint para obter um produto específico pelo ID.
    @GetMapping("/{id}")
    public ResponseEntity<Produto> getById(@PathVariable Long id) {
        // Busca o produto pelo ID. Se encontrado, retorna 200 OK com o produto.
        // Caso contrário, retorna 404 Not Found.
        return crudService.findById(id)
                .map(ResponseEntity::ok) // Retorna 200 OK com o produto encontrado.
                .orElse(ResponseEntity.notFound().build()); // Retorna 404 se não encontrado.
    }

    // Endpoint para criar um novo produto.
    @PostMapping
    public Produto create(@RequestBody Produto model) {
        // Recebe o produto no corpo da requisição e o salva no banco de dados.
        return crudService.save(model);
    }

    // Endpoint para atualizar um produto existente.
    @PutMapping("/{id}")
    public ResponseEntity<Produto> update(@PathVariable Long id, @RequestBody Produto model) {
        // Atualiza o produto pelo ID. Se encontrado, retorna 200 OK com o produto atualizado.
        // Caso contrário, retorna 404 Not Found.
        return crudService.update(id, model)
                .map(ResponseEntity::ok) // Retorna 200 OK com o produto atualizado.
                .orElse(ResponseEntity.notFound().build()); // Retorna 404 se não encontrado.
    }

    // Endpoint para deletar um produto pelo ID.
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        // Remove o produto pelo ID. Se encontrado e removido, retorna 204 No Content.
        // Caso contrário, retorna 404 Not Found.
        if (crudService.deleteById(id)) {
            return ResponseEntity.noContent().build(); // Retorna 204 No Content se a exclusão for bem-sucedida.
        } else {
            return ResponseEntity.notFound().build(); // Retorna 404 se o produto não for encontrado.
        }
    }
}
