package com.example.demo.mapper;

import com.example.demo.dto.BookRequest;
import com.example.demo.dto.BookResponse;
import com.example.demo.model.Book;

/**
 * Mapeador simples (manual) entre entidade Book e seus DTOs.
 */
public final class BookMapper {

    private BookMapper() {}

    /**
     * Atualiza dados mutáveis do Book a partir do DTO de entrada.
     */
    public static void apply(Book entity, BookRequest dto) {
        if (dto == null) return;
        entity.setTitle(dto.getTitle());
        // a associação Author é tratada no serviço, pois depende de carregar a entidade Author.
    }

    /**
     * Converte Book para DTO de saída incluindo informações do autor.
     */
    public static BookResponse toResponse(Book entity) {
        if (entity == null) return null;
        var author = entity.getAuthor();
        return BookResponse.builder()
                .id(entity.getId())
                .title(entity.getTitle())
                .authorId(author != null ? author.getId() : null)
                .authorName(author != null ? author.getName() : null)
                .build();
    }
}
