package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.model.Author;
import com.example.demo.service.AuthorService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

/**
 * Controller MVC responsável pelas telas de cadastro e listagem de autores.
 * Utiliza Thymeleaf para renderizar as views.
 */
@Controller
@RequiredArgsConstructor
public class AuthorViewController {

    private final AuthorService authorService;

    /**
     * Exibe a lista de autores cadastrados.
     */
    @GetMapping("/authors")
    public String authors(Model model) {
        model.addAttribute("authors", authorService.getAllAuthors());
        return "authors";
    }

    /**
     * Exibe o formulário de cadastro de autor.
     */
    @GetMapping("/authors/form")
    public String authorForm(Model model) {
        model.addAttribute("author", new Author());
        return "authorform";
    }

    /**
     * Processa o cadastro de um novo autor.
     */
    @PostMapping("/authors")
    public String saveAuthor(@Valid @ModelAttribute("author") Author author, BindingResult result, Model model, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "authorform";
        }
        authorService.saveAuthor(author);
        redirectAttributes.addFlashAttribute("successMessage", "Autor cadastrado com sucesso!");
        return "redirect:/authors";
    }
}
