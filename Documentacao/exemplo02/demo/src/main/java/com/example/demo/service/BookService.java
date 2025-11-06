package com.example.demo.service;

import com.example.demo.dto.BookRequest;
import com.example.demo.dto.BookResponse;
import com.example.demo.exception.NotFoundException;
import com.example.demo.mapper.BookMapper;
import com.example.demo.model.Book;
import com.example.demo.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Camada de serviço de Livro.
 */
@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;
    private final AuthorService authorService; // usado para validar/buscar o autor

    /**
     * Cria um novo livro associado a um autor existente.
     */
    @Transactional
    public BookResponse create(BookRequest request) {
        var author = authorService.getEntity(request.getAuthorId());
        Book entity = Book.builder()
                .title(request.getTitle())
                .author(author)
                .build();
        entity = bookRepository.save(entity);
        return BookMapper.toResponse(entity);
    }

    /**
     * Atualiza dados do livro (título e, opcionalmente, reatribui autor).
     */
    @Transactional
    public BookResponse update(@NonNull Long id, BookRequest request) {
        Book entity = bookRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Livro não encontrado: id=" + id));
        // Atualiza campos mutáveis
        BookMapper.apply(entity, request);
        // Se veio authorId, reatribui
        if (request.getAuthorId() != null) {
            var author = authorService.getEntity(request.getAuthorId());
            entity.setAuthor(author);
        }
        return BookMapper.toResponse(entity);
    }

    /**
     * Busca livro por id.
     */
    @Transactional(readOnly = true)
    public BookResponse getById(@NonNull Long id) {
        return bookRepository.findById(id)
                .map(BookMapper::toResponse)
                .orElseThrow(() -> new NotFoundException("Livro não encontrado: id=" + id));
    }

    /**
     * Lista todos os livros.
     */
    @Transactional(readOnly = true)
    public List<BookResponse> listAll() {
        return bookRepository.findAll().stream()
                .map(BookMapper::toResponse)
                .toList();
    }

    /**
     * Exclui livro por id.
     */
    @Transactional
    public void delete(@NonNull Long id) {
        if (!bookRepository.existsById(id)) {
            throw new NotFoundException("Livro não encontrado: id=" + id);
        }
        bookRepository.deleteById(id);
    }
}
