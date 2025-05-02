package com.example.demo.controllers;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.models.Usuario;
import com.example.demo.services.UsuarioService;

@RestController // Marca esta classe como um controlador REST do Spring.
@RequestMapping("/usuarios") // Define o mapeamento base para todas as requisições HTTP para "/usuarios".
public class UsuarioController {

    @Autowired // Injeta automaticamente uma instância de UsuarioService nesta classe.
    private UsuarioService usuarioService;

    @GetMapping // Mapeia requisições HTTP GET para este método.
    public List<Usuario> obterTodos() {
        return usuarioService.findAll(); // Retorna a lista de todos os usuários.
    }

    @GetMapping("/{id}") // Mapeia requisições HTTP GET para este método, com um parâmetro de caminho "id".
    public ResponseEntity<Usuario> obterPorId(@PathVariable Long id) {
        Optional<Usuario> usuario = usuarioService.findById(id); // Busca o usuário pelo ID.
        return usuario.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build()); // Retorna o usuário encontrado ou uma resposta 404.
    }

    @PostMapping // Mapeia requisições HTTP POST para este método.
    public Usuario inserir(@RequestBody Usuario usuario) {
        return usuarioService.save(usuario); // Salva o novo usuário e retorna o usuário salvo.
    }

    @PutMapping("/{id}") // Mapeia requisições HTTP PUT para este método, com um parâmetro de caminho "id".
    public ResponseEntity<Usuario> atualizar(@PathVariable Long id, @RequestBody Usuario usuario) {
        Optional<Usuario> updatedUsuario = usuarioService.update(id, usuario); // Atualiza o usuário pelo ID.
        return updatedUsuario.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build()); // Retorna o usuário atualizado ou uma resposta 404.
    }

    @DeleteMapping("/{id}") // Mapeia requisições HTTP DELETE para este método, com um parâmetro de caminho "id".
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        if (!usuarioService.findById(id).isPresent()) { // Verifica se o usuário existe.
            return ResponseEntity.notFound().build(); // Retorna uma resposta 404 se o usuário não for encontrado.
        }
        usuarioService.deleteById(id); // Exclui o usuário pelo ID.
        return ResponseEntity.noContent().build(); // Retorna uma resposta 204 (No Content) após a exclusão.
    }

    @GetMapping("/somar") // Mapeia requisições HTTP GET para este método, com parâmetros de consulta "a" e "b".
    public int somar(@RequestParam int a, @RequestParam int b) {
        return usuarioService.somar(a, b); // Retorna a soma dos dois números fornecidos.
    }
}
