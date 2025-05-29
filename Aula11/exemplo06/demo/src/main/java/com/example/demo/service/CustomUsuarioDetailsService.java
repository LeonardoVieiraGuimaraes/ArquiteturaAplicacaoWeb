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
        Usuario usuario = usuarioService.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado"));
        if (!usuario.isEnabled()) {
            throw new UsernameNotFoundException("Usuário desabilitado");
        }
        // Se usuario.getRoles() retorna String[], use diretamente cada string como nome da role
        return User.builder()
                .username(usuario.getUsername())
                .password(usuario.getPassword())
                .authorities(
                        Arrays.stream(usuario.getRoles())
                                .map(SimpleGrantedAuthority::new)
                                .toList()
                )
                .build();
    }
}
