package com.example.demo.repository;

import com.example.demo.model.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

/**
 * Repository para a entidade Tag
 * Estende JpaRepository que fornece métodos CRUD prontos
 */
@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {
    
    /**
     * Busca uma tag pelo nome
     * @param name Nome da tag
     * @return Optional contendo a tag encontrada ou vazia
     */
    Optional<Tag> findByName(String name);
    
    /**
     * Busca tags cujos nomes estão na lista fornecida
     * @param names Lista de nomes de tags
     * @return Conjunto de tags encontradas
     */
    Set<Tag> findByNameIn(Set<String> names);
    
    /**
     * Verifica se existe uma tag com o nome fornecido
     * @param name Nome da tag
     * @return true se existir, false caso contrário
     */
    boolean existsByName(String name);
}
