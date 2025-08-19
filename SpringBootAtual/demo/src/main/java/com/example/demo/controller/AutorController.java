// Controller REST para Autor
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

    /**
     * GET /autores - lista todos os autores cadastrados
     * @return lista de autores
     */
    @GetMapping
    public List<AutorDTO> listarTodos() {
        return autorService.listarTodos();
    }

    /**
     * GET /autores/{id} - busca autor pelo id
     * @param id identificador do autor
     * @return autor encontrado ou null
     */
    @GetMapping("/{id}")
    public AutorDTO buscarPorId(@PathVariable Long id) {
        return autorService.buscarPorId(id);
    }

    /**
     * POST /autores - cadastra novo autor
     * @param dto dados do autor
     * @return autor cadastrado
     */
    @PostMapping
    public AutorDTO salvar(@RequestBody AutorDTO dto) {
        return autorService.salvar(dto);
    }

    /**
     * PUT /autores/{id} - atualiza autor existente
     * @param id identificador do autor
     * @param dto dados atualizados
     * @return autor atualizado
     */
    @PutMapping("/{id}")
    public AutorDTO atualizar(@PathVariable Long id, @RequestBody AutorDTO dto) {
        return autorService.atualizar(id, dto);
    }

    /**
     * DELETE /autores/{id} - remove autor pelo id
     * @param id identificador do autor
     */
    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Long id) {
        autorService.deletar(id);
    }
}
