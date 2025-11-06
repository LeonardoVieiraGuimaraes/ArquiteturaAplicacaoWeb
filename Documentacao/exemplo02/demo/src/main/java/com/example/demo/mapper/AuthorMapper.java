package com.example.demo.mapper;

import com.example.demo.dto.AuthorRequest;
import com.example.demo.dto.AuthorResponse;
import com.example.demo.model.Author;

/**
 * Mapeador simples (manual) entre entidades e DTOs de Autor.
 */
public final class AuthorMapper {

    private AuthorMapper() {}

    /**
     * Converte um DTO de entrada em uma entidade Author (para criar/atualizar).
     */
    public static Author toEntity(AuthorRequest dto) {
        if (dto == null) return null;
        return Author.builder()
                .name(dto.getName())
                .build();
    }

    /**
     * Converte uma entidade Author em DTO de saída.
     */
    public static AuthorResponse toResponse(Author entity) {
        if (entity == null) return null;
        return AuthorResponse.builder()
                .id(entity.getId())
                .name(entity.getName())
                .build();
    }
}
