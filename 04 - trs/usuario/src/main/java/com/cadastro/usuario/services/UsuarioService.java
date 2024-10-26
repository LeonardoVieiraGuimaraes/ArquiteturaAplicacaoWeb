package com.cadastro.usuario.services; // Define o pacote onde esta classe está localizada.

import java.util.List; // Importa a interface List, que representa uma lista ordenada de elementos.
import java.util.Optional; // Importa a classe Optional, que é um contêiner para um valor que pode estar presente ou ausente.

import org.springframework.beans.factory.annotation.Autowired; // Importa a anotação Autowired do Spring para injeção de dependências.
import org.springframework.stereotype.Service; // Importa a anotação Service do Spring para marcar esta classe como um serviço.

import com.cadastro.usuario.models.Usuario; // Importa a classe Usuario do pacote com.cadastro.usuario.models, que representa o modelo de dados de um usuário.
import com.cadastro.usuario.repositories.UsuarioRepository; // Importa a interface UsuarioRepository do pacote com.cadastro.usuario.repositories, que representa o repositório de usuários.

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
    public int somar(int a, int b){
        return a + b;
    }
}