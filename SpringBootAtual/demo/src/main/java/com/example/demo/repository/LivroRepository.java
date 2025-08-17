
// Repository: interface para operações de acesso a dados do Livro
package com.example.demo.repository;

import com.example.demo.model.Livro;
import org.springframework.data.jpa.repository.JpaRepository;

// JpaRepository fornece métodos prontos para CRUD
public interface LivroRepository extends JpaRepository<Livro, Long> {
	// Não é necessário implementar nada, o Spring Data JPA faz tudo automaticamente
}
