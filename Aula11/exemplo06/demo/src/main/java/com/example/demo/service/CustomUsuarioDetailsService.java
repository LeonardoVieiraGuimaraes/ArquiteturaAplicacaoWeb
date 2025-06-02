package com.example.demo.service;

import java.util.Arrays;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.demo.model.Usuario;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomUsuarioDetailsService implements UserDetailsService {

    private final UsuarioService usuarioService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Busca Optional<Usuario> e trata corretamente
        return usuarioService.findByUsername(username)
                .filter(Usuario::isEnabled)
                .map(usuario -> User.builder()
                .username(usuario.getUsername())
                .password(usuario.getPassword())
                .authorities(
                        Arrays.stream(usuario.getRoles())
                                .map(SimpleGrantedAuthority::new)
                                .toList()
                )
                .build())
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado ou desabilitado"));
    }
}
