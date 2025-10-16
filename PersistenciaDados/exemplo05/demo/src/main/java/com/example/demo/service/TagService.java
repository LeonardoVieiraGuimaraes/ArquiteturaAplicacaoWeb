package com.example.demo.service;

import com.example.demo.model.Tag;
import com.example.demo.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * Service para gerenciar a lógica de negócio relacionada a tags
 */
@Service
@Transactional
public class TagService {

    @Autowired
    private TagRepository tagRepository;

    /**
     * Lista todas as tags
     * @return Lista de todas as tags
     */
    public List<Tag> findAll() {
        return tagRepository.findAll();
    }

    /**
     * Busca uma tag por ID
     * @param id ID da tag
     * @return Optional contendo a tag encontrada ou vazio
     */
    public Optional<Tag> findById(Long id) {
        return tagRepository.findById(id);
    }

    /**
     * Busca uma tag pelo nome
     * @param name Nome da tag
     * @return Optional contendo a tag encontrada ou vazio
     */
    public Optional<Tag> findByName(String name) {
        return tagRepository.findByName(name);
    }

    /**
     * Busca tags por uma lista de nomes
     * @param names Conjunto de nomes de tags
     * @return Conjunto de tags encontradas
     */
    public Set<Tag> findByNames(Set<String> names) {
        return tagRepository.findByNameIn(names);
    }

    /**
     * Cria uma nova tag
     * @param tag Tag a ser criada
     * @return Tag criada
     * @throws IllegalArgumentException se já existir uma tag com o mesmo nome
     */
    public Tag create(Tag tag) {
        if (tagRepository.existsByName(tag.getName())) {
            throw new IllegalArgumentException("Já existe uma tag com o nome: " + tag.getName());
        }
        return tagRepository.save(tag);
    }

    /**
     * Atualiza uma tag existente
     * @param id ID da tag a ser atualizada
     * @param tagDetails Novos dados da tag
     * @return Optional contendo a tag atualizada ou vazio se não encontrada
     */
    public Optional<Tag> update(Long id, Tag tagDetails) {
        return tagRepository.findById(id)
            .map(tag -> {
                tag.setName(tagDetails.getName());
                tag.setColor(tagDetails.getColor());
                return tagRepository.save(tag);
            });
    }

    /**
     * Deleta uma tag
     * @param id ID da tag a ser deletada
     * @return true se a tag foi deletada, false se não foi encontrada
     */
    public boolean delete(Long id) {
        return tagRepository.findById(id)
            .map(tag -> {
                tagRepository.delete(tag);
                return true;
            })
            .orElse(false);
    }

    /**
     * Conta o total de tags
     * @return Número total de tags
     */
    public long count() {
        return tagRepository.count();
    }
}
