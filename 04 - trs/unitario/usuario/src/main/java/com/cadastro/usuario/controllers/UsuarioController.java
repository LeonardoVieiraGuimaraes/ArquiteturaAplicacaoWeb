package com.cadastro.usuario.controllers; // Define o pacote onde esta classe está localizada.

import java.util.List; // Importa a interface List, que representa uma lista ordenada de elementos.
import java.util.Optional; // Importa a classe Optional, que é um contêiner para um valor que pode estar presente ou ausente.

import org.springframework.beans.factory.annotation.Autowired; // Importa a anotação Autowired do Spring para injeção de dependências.
import org.springframework.http.ResponseEntity; // Importa a classe ResponseEntity do Spring para representar uma resposta HTTP.
import org.springframework.web.bind.annotation.DeleteMapping; // Importa a anotação DeleteMapping do Spring para mapear requisições HTTP DELETE.
import org.springframework.web.bind.annotation.GetMapping; // Importa a anotação GetMapping do Spring para mapear requisições HTTP GET.
import org.springframework.web.bind.annotation.PathVariable; // Importa a anotação PathVariable do Spring para vincular variáveis de caminho em URLs.
import org.springframework.web.bind.annotation.PostMapping; // Importa a anotação PostMapping do Spring para mapear requisições HTTP POST.
import org.springframework.web.bind.annotation.PutMapping; // Importa a anotação PutMapping do Spring para mapear requisições HTTP PUT.
import org.springframework.web.bind.annotation.RequestBody; // Importa a anotação RequestBody do Spring para vincular o corpo da requisição a um objeto.
import org.springframework.web.bind.annotation.RequestMapping; // Importa a anotação RequestMapping do Spring para mapear requisições HTTP para métodos manipuladores.
import org.springframework.web.bind.annotation.RequestParam; // Importa a anotação RequestParam do Spring para vincular parâmetros de consulta em URLs.
import org.springframework.web.bind.annotation.RestController; // Importa a anotação RestController do Spring para marcar esta classe como um controlador REST.

import com.cadastro.usuario.models.Usuario; // Importa a classe Usuario do pacote com.cadastro.usuario.models, que representa o modelo de dados de um usuário.
import com.cadastro.usuario.services.UsuarioService; // Importa a classe UsuarioService do pacote com.cadastro.usuario.services, que representa o serviço de usuário.

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