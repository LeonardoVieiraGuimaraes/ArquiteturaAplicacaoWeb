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

import com.example.demo.model.Author;
import com.example.demo.service.AuthorService;

@Controller // Indica que esta classe é um controlador para renderizar páginas HTML
@RequestMapping("/authors") // Define o prefixo para os endpoints deste controlador
public class AuthorViewController {

    @Autowired // Injeta automaticamente a dependência do serviço AuthorService
    private AuthorService authorService;

    @GetMapping // Lista todos os autores e exibe a página de listagem
    public String listAuthors(Model model) {
        List<Author> authors = authorService.getAllAuthors(); // Recupera todos os autores usando o serviço
        model.addAttribute("authors", authors); // Adiciona a lista de autores ao modelo
        return "authors"; // Retorna a página authors.html
    }

    @GetMapping("/new") // Exibe o formulário para adicionar um novo autor
    public String showCreateForm(Model model) {
        model.addAttribute("author", new Author()); // Adiciona um novo objeto Author ao modelo
        return "author-form"; // Retorna a página author-form.html
    }

    @GetMapping("/edit/{id}") // Exibe o formulário para editar um autor existente
    public String showEditForm(@PathVariable Long id, Model model) {
        Author author = authorService.getAuthorById(id).orElseThrow(() -> new IllegalArgumentException("Invalid author ID: " + id));
        model.addAttribute("author", author); // Adiciona o autor ao modelo
        return "author-form"; // Retorna a página author-form.html
    }

    @PostMapping("/save") // Salva um novo autor ou atualiza um autor existente
    public String saveAuthor(@ModelAttribute Author author) {
        authorService.saveAuthor(author); // Salva o autor no banco de dados
        return "redirect:/authors"; // Redireciona para a página de listagem de autores
    }

    @GetMapping("/delete/{id}") // Exclui um autor pelo ID
    public String deleteAuthor(@PathVariable Long id) {
        authorService.deleteAuthor(id); // Exclui o autor do banco de dados
        return "redirect:/authors"; // Redireciona para a página de listagem de autores
    }
}
