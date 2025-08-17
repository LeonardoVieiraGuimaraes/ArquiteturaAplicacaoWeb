
// Service: regras de negócio e integração entre Controller e Repository
package com.example.demo.service;

import com.example.demo.model.Livro;
import com.example.demo.dto.LivroDTO;
import com.example.demo.repository.LivroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service // Indica que esta classe é um serviço do Spring
public class LivroService {
    @Autowired // Injeta o repository automaticamente
    private LivroRepository livroRepository;

    // Retorna todos os livros como DTO
    // Usa cache para melhorar performance
    @org.springframework.cache.annotation.Cacheable("livros")
    public List<LivroDTO> listarTodos() {
        return livroRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    // Salva um novo livro a partir do DTO
    public LivroDTO salvar(LivroDTO dto) {
        Livro livro = toEntity(dto);
        Livro salvo = livroRepository.save(livro);
        return toDTO(salvo);
    }

    // Converte entidade Livro para DTO
    private LivroDTO toDTO(Livro livro) {
        LivroDTO dto = new LivroDTO();
        dto.setId(livro.getId());
        dto.setTitulo(livro.getTitulo());
        dto.setAutor(livro.getAutor());
        dto.setIsbn(livro.getIsbn());
        return dto;
    }

    // Converte DTO para entidade Livro
    private Livro toEntity(LivroDTO dto) {
        Livro livro = new Livro();
        livro.setId(dto.getId());
        livro.setTitulo(dto.getTitulo());
        livro.setAutor(dto.getAutor());
        livro.setIsbn(dto.getIsbn());
        return livro;
    }
}
