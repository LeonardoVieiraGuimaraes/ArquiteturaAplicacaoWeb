// Repository para Autor
package com.example.demo.repository;

import com.example.demo.model.Autor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AutorRepository extends JpaRepository<Autor, Long> {
    // Métodos CRUD automáticos pelo Spring Data JPA
}
