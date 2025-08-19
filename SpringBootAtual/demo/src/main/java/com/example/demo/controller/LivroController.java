
// Controller: camada responsável por receber requisições HTTP e retornar respostas
package com.example.demo.controller;

import com.example.demo.dto.LivroDTO;
import com.example.demo.service.LivroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController // Indica que esta classe é um controller REST
@RequestMapping("/livros") // Define o endpoint base para livros
public class LivroController {
    @Autowired // Injeta o serviço automaticamente
    private LivroService livroService;

    // Endpoint GET /livros - retorna todos os livros
    @GetMapping
    public List<LivroDTO> listarTodos() {
        return livroService.listarTodos();
    }

    // Endpoint GET /livros/{id} - retorna um livro por id
    @GetMapping("/{id}")
    public LivroDTO buscarPorId(@PathVariable Long id) {
        return livroService.buscarPorId(id);
    }

    // Endpoint POST /livros - salva um novo livro
    @PostMapping
    public LivroDTO salvar(@RequestBody LivroDTO dto) {
        return livroService.salvar(dto);
    }

    // Endpoint PUT /livros/{id} - atualiza um livro
    @PutMapping("/{id}")
    public LivroDTO atualizar(@PathVariable Long id, @RequestBody LivroDTO dto) {
        return livroService.atualizar(id, dto);
    }

    // Endpoint DELETE /livros/{id} - remove um livro
    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Long id) {
        livroService.deletar(id);
    }
}
