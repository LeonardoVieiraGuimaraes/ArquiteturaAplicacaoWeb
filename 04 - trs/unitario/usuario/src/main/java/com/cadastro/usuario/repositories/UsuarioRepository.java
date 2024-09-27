package com.cadastro.usuario.repositories;

import org.springframework.data.jpa.repository.JpaRepository; // Importa a interface JpaRepository do Spring Data JPA, que fornece métodos padrão para operações CRUD.
import org.springframework.stereotype.Repository; // Importa a anotação Repository do Spring para marcar esta interface como um repositório.

import com.cadastro.usuario.models.Usuario; // Importa a classe Usuario do pacote com.cadastro.usuario.models, que representa o modelo de dados de um usuário.

@Repository // Marca esta interface como um repositório do Spring, permitindo que seja gerenciada pelo contêiner de injeção de dependências do Spring.
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    // Esta interface herda os métodos padrão de JpaRepository para operações CRUD (Create, Read, Update, Delete).
    // JpaRepository<Usuario, Long> indica que esta interface gerencia a entidade Usuario e que o tipo do ID da entidade é Long.
}