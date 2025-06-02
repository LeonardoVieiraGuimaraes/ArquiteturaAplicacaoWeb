package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.model.Usuario;
import com.example.demo.repository.UsuarioRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public List<Usuario> getAllUsers() {
        return usuarioRepository.findAll();
    }

    public Optional<Usuario> getUserById(Long id) {
        return usuarioRepository.findById(id);
    }

    public Usuario createUser(Usuario usuario) {
        String senha = usuario.getPassword();
        if (!senha.startsWith("$2a$")) { // só criptografa se não estiver criptografada
            usuario.setPassword(passwordEncoder.encode(senha));
        }
        return usuarioRepository.save(usuario);
    }

    public Optional<Usuario> updateUser(Long id, Usuario usuario) {
        return usuarioRepository.findById(id).map(existingUser -> {
            existingUser.setUsername(usuario.getUsername());
            existingUser.setPassword(passwordEncoder.encode(usuario.getPassword()));
            existingUser.setEnabled(usuario.isEnabled());
            existingUser.setRoles(usuario.getRoles());
            return usuarioRepository.save(existingUser);
        });
    }

    public boolean deleteUser(Long id) {
        if (usuarioRepository.existsById(id)) {
            usuarioRepository.deleteById(id);
            return true;
        }
        return false;
    }

    // Retorna Optional<Usuario> para uso seguro no service de autenticação
    public Optional<Usuario> findByUsername(String username) {
        return usuarioRepository.findByUsername(username);
    }
}
