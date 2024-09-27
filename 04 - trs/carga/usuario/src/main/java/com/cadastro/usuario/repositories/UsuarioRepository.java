package com.cadastro.usuario.repositories;

// src/main/java/com/exemplo/projeto/repository/UsuarioRepository.java


import org.springframework.data.jpa.repository.JpaRepository;

import com.cadastro.usuario.models.Usuario;


public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
}