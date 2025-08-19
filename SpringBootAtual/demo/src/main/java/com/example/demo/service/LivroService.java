
// Service: regras de negócio e integração entre Controller e Repository
package com.example.demo.service;

import com.example.demo.model.Livro;
import com.example.demo.model.Autor;
import com.example.demo.dto.LivroDTO;
import com.example.demo.dto.AutorDTO;
import com.example.demo.repository.LivroRepository;
import com.example.demo.repository.AutorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service // Indica que esta classe é um serviço do Spring
public class LivroService {
    @Autowired // Injeta o repository automaticamente
    private LivroRepository livroRepository;

    @Autowired
    private AutorRepository autorRepository;

    // Retorna todos os livros como DTO
    // Usa cache para melhorar performance
    @org.springframework.cache.annotation.Cacheable("livros")
    public List<LivroDTO> listarTodos() {
        return livroRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    // Busca um livro por id
    public LivroDTO buscarPorId(Long id) {
        return livroRepository.findById(id)
                .map(this::toDTO)
                .orElse(null);
    }

    // Salva um novo livro a partir do DTO
    public LivroDTO salvar(LivroDTO dto) {
        Livro livro = toEntity(dto);
        Livro salvo = livroRepository.save(livro);
        return toDTO(salvo);
    }

    // Atualiza um livro existente
    public LivroDTO atualizar(Long id, LivroDTO dto) {
        Livro livro = livroRepository.findById(id).orElse(null);
        if (livro == null) return null;
        livro.setTitulo(dto.getTitulo());
        if (dto.getAutor() != null && dto.getAutor().getId() != null) {
            Autor autor = autorRepository.findById(dto.getAutor().getId()).orElse(null);
            livro.setAutor(autor);
        }
        livro.setIsbn(dto.getIsbn());
        Livro atualizado = livroRepository.save(livro);
        return toDTO(atualizado);
    }

    // Deleta um livro por id
    public void deletar(Long id) {
        livroRepository.deleteById(id);
    }

    // Converte entidade Livro para DTO
    private LivroDTO toDTO(Livro livro) {
        LivroDTO dto = new LivroDTO();
        dto.setId(livro.getId());
        dto.setTitulo(livro.getTitulo());
        if (livro.getAutor() != null) {
            AutorDTO autorDTO = new AutorDTO();
            autorDTO.setId(livro.getAutor().getId());
            autorDTO.setNome(livro.getAutor().getNome());
            dto.setAutor(autorDTO);
        }
        dto.setIsbn(livro.getIsbn());
        return dto;
    }

    // Converte DTO para entidade Livro
    private Livro toEntity(LivroDTO dto) {
        Livro livro = new Livro();
        livro.setId(dto.getId());
        livro.setTitulo(dto.getTitulo());
        if (dto.getAutor() != null && dto.getAutor().getId() != null) {
            Autor autor = autorRepository.findById(dto.getAutor().getId()).orElse(null);
            livro.setAutor(autor);
        } else {
            livro.setAutor(null);
        }
        livro.setIsbn(dto.getIsbn());
        return livro;
    }
}
