// Controller REST para Autor
package com.example.demo.controller;

import com.example.demo.dto.AutorDTO;
import com.example.demo.service.AutorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/autores")
public class AutorController {
    @Autowired
    private AutorService autorService;

    // GET /autores - lista todos os autores
    @GetMapping
    public List<AutorDTO> listarTodos() {
        return autorService.listarTodos();
    }

    // GET /autores/{id} - busca autor por id
    @GetMapping("/{id}")
    public AutorDTO buscarPorId(@PathVariable Long id) {
        return autorService.buscarPorId(id);
    }

    // POST /autores - cadastra novo autor
    @PostMapping
    public AutorDTO salvar(@RequestBody AutorDTO dto) {
        return autorService.salvar(dto);
    }

    // PUT /autores/{id} - atualiza autor
    @PutMapping("/{id}")
    public AutorDTO atualizar(@PathVariable Long id, @RequestBody AutorDTO dto) {
        return autorService.atualizar(id, dto);
    }

    // DELETE /autores/{id} - remove autor
    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Long id) {
        autorService.deletar(id);
    }
}
