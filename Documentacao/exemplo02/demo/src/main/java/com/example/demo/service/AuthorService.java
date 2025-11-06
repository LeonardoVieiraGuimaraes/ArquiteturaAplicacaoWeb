package com.example.demo.service;

import com.example.demo.dto.AuthorRequest;
import com.example.demo.dto.AuthorResponse;
import com.example.demo.exception.NotFoundException;
import com.example.demo.mapper.AuthorMapper;
import com.example.demo.model.Author;
import com.example.demo.repository.AuthorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Camada de serviço de Autor.
 * - Contém regras de negócio e orquestra persistência e mapeamento.
 */
@Service
@RequiredArgsConstructor
public class AuthorService {

    private final AuthorRepository authorRepository;

    /**
     * Cria um novo autor.
     */
    @Transactional
    public AuthorResponse create(AuthorRequest request) {
        Author entity = AuthorMapper.toEntity(request);
        if (entity == null) {
            throw new IllegalArgumentException("AuthorRequest inválido");
        }
        entity = authorRepository.save(entity);
        return AuthorMapper.toResponse(entity);
    }

    /**
     * Atualiza dados do autor existente.
     */
    @Transactional
    public AuthorResponse update(@NonNull Long id, AuthorRequest request) {
        Author entity = authorRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Autor não encontrado: id=" + id));
        entity.setName(request.getName());
        return AuthorMapper.toResponse(entity);
    }

    /**
     * Busca um autor por id.
     */
    @Transactional(readOnly = true)
    public AuthorResponse getById(@NonNull Long id) {
        return authorRepository.findById(id)
                .map(AuthorMapper::toResponse)
                .orElseThrow(() -> new NotFoundException("Autor não encontrado: id=" + id));
    }

    /**
     * Lista todos os autores.
     */
    @Transactional(readOnly = true)
    public List<AuthorResponse> listAll() {
        return authorRepository.findAll().stream()
                .map(AuthorMapper::toResponse)
                .toList();
    }

    /**
     * Exclui um autor por id.
     */
    @Transactional
    public void delete(@NonNull Long id) {
        if (!authorRepository.existsById(id)) {
            throw new NotFoundException("Autor não encontrado: id=" + id);
        }
        authorRepository.deleteById(id);
    }

    /**
     * Busca a entidade Author (uso interno por outros serviços, ex: BookService).
     */
    @Transactional(readOnly = true)
    public Author getEntity(@NonNull Long id) {
        return authorRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Autor não encontrado: id=" + id));
    }
}
