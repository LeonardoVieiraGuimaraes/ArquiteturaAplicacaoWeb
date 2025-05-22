package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.model.Book;
import com.example.demo.service.AuthorService;
import com.example.demo.service.BookService;

@Controller // Indica que esta classe é um controlador para renderizar páginas HTML
@RequestMapping("/books") // Define o prefixo para os endpoints deste controlador
public class BookViewController {

    @Autowired // Injeta automaticamente a dependência do serviço BookService
    private BookService bookService;

    @Autowired // Injeta automaticamente a dependência do serviço AuthorService
    private AuthorService authorService;

    @GetMapping // Lista todos os livros e exibe a página de listagem
    public String listBooks(Model model) {
        List<Book> books = bookService.getAllBooks(); // Recupera todos os livros usando o serviço
        model.addAttribute("books", books); // Adiciona a lista de livros ao modelo
        return "books"; // Retorna a página books.html
    }

    @GetMapping("/new") // Exibe o formulário para adicionar um novo livro
    public String showCreateForm(Model model) {
        model.addAttribute("book", new Book()); // Adiciona um novo objeto Book ao modelo
        model.addAttribute("authors", authorService.getAllAuthors()); // Adiciona a lista de autores ao modelo
        return "book-form"; // Retorna a página book-form.html
    }

    @GetMapping("/edit/{id}") // Exibe o formulário para editar um livro existente
    public String showEditForm(@PathVariable Long id, Model model) {
        Book book = bookService.getBookById(id).orElseThrow(() -> new IllegalArgumentException("Invalid book ID: " + id));
        model.addAttribute("book", book); // Adiciona o livro ao modelo
        model.addAttribute("authors", authorService.getAllAuthors()); // Adiciona a lista de autores ao modelo
        return "book-form"; // Retorna a página book-form.html
    }

    @PostMapping("/save") // Salva um novo livro ou atualiza um livro existente
    public String saveBook(@ModelAttribute Book book) {
        bookService.saveBook(book); // Salva o livro no banco de dados
        return "redirect:/books"; // Redireciona para a página de listagem de livros
    }

    @GetMapping("/delete/{id}") // Exclui um livro pelo ID
    public String deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id); // Exclui o livro do banco de dados
        return "redirect:/books"; // Redireciona para a página de listagem de livros
    }
}
