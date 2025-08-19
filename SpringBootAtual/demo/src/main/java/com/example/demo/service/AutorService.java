// Service para Autor
// Service para Autor
package com.example.demo.service;

import com.example.demo.model.Autor;
import com.example.demo.dto.AutorDTO;
import com.example.demo.repository.AutorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AutorService {
    @Autowired
    private AutorRepository autorRepository;

    /**
     * Lista todos os autores cadastrados
     * @return lista de autores
     */
    public List<AutorDTO> listarTodos() {
        return autorRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * Busca um autor por id
     * @param id identificador do autor
     * @return autor encontrado ou null
     */
    public AutorDTO buscarPorId(Long id) {
        return autorRepository.findById(id)
                .map(this::toDTO)
                .orElse(null);
    }

    /**
     * Salva um novo autor
     * @param dto dados do autor
     * @return autor salvo
     */
    public AutorDTO salvar(AutorDTO dto) {
        Autor autor = toEntity(dto);
        Autor salvo = autorRepository.save(autor);
        return toDTO(salvo);
    }

    /**
     * Atualiza um autor existente
     * @param id identificador do autor
     * @param dto dados atualizados
     * @return autor atualizado
     */
    public AutorDTO atualizar(Long id, AutorDTO dto) {
        Autor autor = autorRepository.findById(id).orElse(null);
        if (autor == null) return null;
        autor.setNome(dto.getNome());
        Autor atualizado = autorRepository.save(autor);
        return toDTO(atualizado);
    }

    /**
     * Deleta um autor por id
     * @param id identificador do autor
     */
    public void deletar(Long id) {
        autorRepository.deleteById(id);
    }

    // Métodos auxiliares para conversão entre DTO e entidade
    private AutorDTO toDTO(Autor autor) {
        AutorDTO dto = new AutorDTO();
        dto.setId(autor.getId());
        dto.setNome(autor.getNome());
        return dto;
    }

    private Autor toEntity(AutorDTO dto) {
        Autor autor = new Autor();
        autor.setId(dto.getId());
        autor.setNome(dto.getNome());
        return autor;
    }
}
