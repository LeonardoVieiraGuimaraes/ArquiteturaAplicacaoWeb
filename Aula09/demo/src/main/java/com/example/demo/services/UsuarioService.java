package com.example.demo.services; // Pacote onde a classe UsuarioService está localizada

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.models.Usuario;
import com.example.demo.repositories.UsuarioRepository;

@Service // Marca esta classe como um serviço do Spring, permitindo que seja gerenciada pelo contêiner de injeção de dependências do Spring.
public class UsuarioService {

    @Autowired // Injeta automaticamente uma instância de UsuarioRepository nesta classe.
    private UsuarioRepository usuarioRepository;

    // Método para obter todos os usuários
    public List<Usuario> findAll() {
        return usuarioRepository.findAll();
    }

    // Método para obter um usuário pelo seu ID
    public Optional<Usuario> findById(Long id) {
        return usuarioRepository.findById(id);
    }

    // Método para salvar um novo usuário
    public Usuario save(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    // Método para atualizar um usuário existente
    public Optional<Usuario> update(Long id, Usuario usuario) {
        return usuarioRepository.findById(id).map(existingUsuario -> {
            // Atualiza os campos do usuário existente com os valores do usuário fornecido
            existingUsuario.setNome(usuario.getNome());
            existingUsuario.setEmail(usuario.getEmail());
            // Salva o usuário atualizado no repositório
            return usuarioRepository.save(existingUsuario);
        });
    }

    // Método para excluir um usuário pelo seu ID
    public void deleteById(Long id) {
        usuarioRepository.deleteById(id);
    }

    // Método para somar dois números inteiros
    public int somar(int a, int b) {
        return a + b;
    }
}
