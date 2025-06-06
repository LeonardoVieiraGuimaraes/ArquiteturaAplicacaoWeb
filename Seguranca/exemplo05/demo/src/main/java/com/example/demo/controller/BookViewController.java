package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.service.AuthorService;
import com.example.demo.service.BookService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

/**
 * Controller MVC responsável pelas telas de cadastro e listagem de livros.
 * Utiliza Thymeleaf para renderizar as views.
 */
@Controller
@RequiredArgsConstructor
public class BookViewController {

    private final BookService bookService;
    private final AuthorService authorService;

    /**
     * Exibe a lista de livros cadastrados.
     */
    @GetMapping("/books")
    public String books(Model model) {
        model.addAttribute("books", bookService.getAllBooks());
        return "books";
    }

    /**
     * Exibe o formulário de cadastro de livro.
     */
    @GetMapping("/books/form")
    public String bookForm(Model model) {
        model.addAttribute("book", new com.example.demo.model.Book());
        model.addAttribute("autores", authorService.getAllAuthors());
        return "bookform";
    }

    /**
     * Processa o cadastro de um novo livro.
     */
    @PostMapping("/books")
    public String saveBook(@Valid @ModelAttribute("book") com.example.demo.model.Book book, BindingResult result, Model model, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            model.addAttribute("autores", authorService.getAllAuthors());
            return "bookform";
        }
        bookService.saveBook(book);
        redirectAttributes.addFlashAttribute("successMessage", "Livro cadastrado com sucesso!");
        return "redirect:/books";
    }
}
