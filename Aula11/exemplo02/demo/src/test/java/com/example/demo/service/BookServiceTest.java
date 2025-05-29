package com.example.demo.service;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;

import com.example.demo.model.Book;
import com.example.demo.repository.BookRepository;

public class BookServiceTest {

    @Mock // Simula o comportamento do repositório para testes
    private BookRepository bookRepository;

    @InjectMocks // Injeta o mock do repositório no serviço
    private BookService bookService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this); // Inicializa os mocks antes de cada teste
    }

    @Test
    public void testSaveBook() {
        // Cria um livro de teste
        Book book = new Book();
        book.setIsbn("123456789");
        book.setTitulo("Test Book");
        book.setDataPublicacao(LocalDate.now());
        book.setGenero("Fiction");
        book.setPreco(19.99);

        // Configura o comportamento do mock para o método save
        when(bookRepository.save(any(Book.class))).thenReturn(book);

        // Chama o método saveBook do serviço
        Book savedBook = bookService.saveBook(book);

        // Verifica se o livro foi salvo corretamente
        assertNotNull(savedBook);
        assertEquals("123456789", savedBook.getIsbn());
        verify(bookRepository, times(1)).save(any(Book.class)); // Verifica se o método save foi chamado uma vez
    }

    @Test
    public void testGetAllBooks() {
        // Chama o método getAllBooks do serviço
        bookService.getAllBooks();

        // Verifica se o método findAll foi chamado uma vez
        verify(bookRepository, times(1)).findAll();
    }

    @Test
    public void testGetBookById() {
        Long id = 1L; // ID do livro
        Book book = new Book();
        book.setId(id);

        // Configura o comportamento do mock para o método findById
        when(bookRepository.findById(id)).thenReturn(Optional.of(book));

        // Chama o método getBookById do serviço
        Optional<Book> foundBook = bookService.getBookById(id);

        // Verifica se o livro foi encontrado
        assertTrue(foundBook.isPresent());
        assertEquals(id, foundBook.get().getId());
        verify(bookRepository, times(1)).findById(id); // Verifica se o método findById foi chamado uma vez
    }

    @Test
    public void testUpdateBook() {
        Long id = 1L; // ID do livro
        Book existingBook = new Book();
        existingBook.setId(id);
        existingBook.setTitulo("Old Title");

        Book updatedBook = new Book();
        updatedBook.setTitulo("New Title");

        // Configura o comportamento do mock para os métodos findById e save
        when(bookRepository.findById(id)).thenReturn(Optional.of(existingBook));
        when(bookRepository.save(any(Book.class))).thenReturn(updatedBook);

        // Chama o método updateBook do serviço
        Optional<Book> result = bookService.updateBook(id, updatedBook);

        // Verifica se o livro foi atualizado corretamente
        assertTrue(result.isPresent());
        assertEquals("New Title", result.get().getTitulo());
        verify(bookRepository, times(1)).findById(id); // Verifica se o método findById foi chamado uma vez
        verify(bookRepository, times(1)).save(any(Book.class)); // Verifica se o método save foi chamado uma vez
    }

    @Test
    public void testDeleteBook() {
        Long id = 1L; // ID do livro

        // Configura o comportamento do mock para o método existsById
        when(bookRepository.existsById(id)).thenReturn(true);

        // Chama o método deleteBook do serviço
        boolean deleted = bookService.deleteBook(id);

        // Verifica se o livro foi deletado
        assertTrue(deleted);
        verify(bookRepository, times(1)).existsById(id); // Verifica se o método existsById foi chamado uma vez
        verify(bookRepository, times(1)).deleteById(id); // Verifica se o método deleteById foi chamado uma vez
    }

    @Test
    public void testDeleteBookNotFound() {
        Long id = 1L; // ID do livro

        // Configura o comportamento do mock para o método existsById
        when(bookRepository.existsById(id)).thenReturn(false);

        // Chama o método deleteBook do serviço
        boolean deleted = bookService.deleteBook(id);

        // Verifica se o livro não foi deletado
        assertFalse(deleted);
        verify(bookRepository, times(1)).existsById(id); // Verifica se o método existsById foi chamado uma vez
        verify(bookRepository, never()).deleteById(id); // Verifica se o método deleteById nunca foi chamado
    }
}
