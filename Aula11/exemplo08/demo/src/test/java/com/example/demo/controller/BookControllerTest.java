package com.example.demo.controller;

import com.example.demo.model.Book;
import com.example.demo.service.BookService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
public class BookControllerTest {

    private MockMvc mockMvc;

    @Mock
    private BookService bookService;

    @InjectMocks
    private BookController bookController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(bookController).build();
    }

    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    public void testSaveBook() throws Exception {
        Book book = new Book();
        book.setId(1L); // Alterado para Long
        book.setIsbn("123456789");
        book.setTitulo("Test Book");
        book.setDataPublicacao(LocalDate.of(2023, 1, 1));
        book.setGenero("Fiction");
        book.setPreco(19.99);

        when(bookService.saveBook(any(Book.class))).thenReturn(book);

        mockMvc.perform(post("/api/books")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                            "isbn": "123456789",
                            "titulo": "Test Book",
                            "dataPublicacao": "2023-01-01",
                            "genero": "Fiction",
                            "preco": 19.99,
                            "autor": {
                                "id": 1
                            }
                        }
                        """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.isbn").value("123456789"))
                .andExpect(jsonPath("$.titulo").value("Test Book"));
    }

    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    public void testGetAllBooks() throws Exception {
        when(bookService.getAllBooks()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/api/books"))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));
    }

    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    public void testGetBookById() throws Exception {
        Book book = new Book();
        book.setId(1L); // Alterado para Long
        book.setIsbn("123456789");
        book.setTitulo("Test Book");

        when(bookService.getBookById(1L)).thenReturn(Optional.of(book));

        mockMvc.perform(get("/api/books/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.isbn").value("123456789"))
                .andExpect(jsonPath("$.titulo").value("Test Book"));
    }
}
