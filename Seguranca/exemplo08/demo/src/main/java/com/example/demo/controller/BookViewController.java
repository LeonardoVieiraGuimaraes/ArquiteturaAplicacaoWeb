package com.example.demo.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/books/form")
    public String bookForm(@RequestParam(value = "id", required = false) Long id, Model model) {
        com.example.demo.model.Book book = (id != null) ? bookService.getBookById(id).orElse(new com.example.demo.model.Book()) : new com.example.demo.model.Book();
        model.addAttribute("book", book);
        model.addAttribute("autores", authorService.getAllAuthors());
        return "bookform";
    }

    /**
     * Processa o cadastro de um novo livro.
     */
    @PreAuthorize("hasRole('ADMIN')")
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

    /**
     * Exibe os detalhes de um livro.
     */
    @GetMapping("/books/{id}")
    public String bookDetails(@PathVariable Long id, Model model) {
        com.example.demo.model.Book book = bookService.getBookById(id).orElse(null);
        if (book == null) {
            model.addAttribute("errorMessage", "Livro não encontrado.");
            return "bookdetails";
        }
        model.addAttribute("book", book);
        return "bookdetails";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/books/{id}/delete")
    public String deleteBook(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        boolean deleted = bookService.deleteBook(id);
        if (deleted) {
            redirectAttributes.addFlashAttribute("successMessage", "Livro excluído com sucesso!");
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "Livro não encontrado.");
        }
        return "redirect:/books";
    }
}
