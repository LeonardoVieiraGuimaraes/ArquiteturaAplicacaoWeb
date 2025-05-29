package com.example.demo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.model.Usuario;
import com.example.demo.service.UsuarioService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

/**
 * Controller MVC responsável pelas telas de cadastro e listagem de usuários.
 * Utiliza Thymeleaf para renderizar as views.
 */
@Controller
@RequiredArgsConstructor
public class UsuarioViewController {

    private static final Logger logger = LoggerFactory.getLogger(UsuarioViewController.class);

    private final UsuarioService usuarioService;

    /**
     * Exibe a lista de usuários cadastrados.
     *
     * @param model Model para adicionar a lista de usuários.
     * @return Nome do template Thymeleaf para listagem.
     */
    @GetMapping("/users")
    public String users(Model model) {
        model.addAttribute("users", usuarioService.getAllUsers());
        return "users"; // Template de listagem de usuários
    }

    /**
     * Exibe o formulário de cadastro de usuário.
     *
     * @param model Model para adicionar um novo usuário.
     * @return Nome do template Thymeleaf para o formulário.
     */
    @GetMapping("/users/form")
    public String userForm(Model model) {
        model.addAttribute("user", new Usuario());
        return "userform";
    }

    /**
     * Processa o cadastro de um novo usuário. Valida os campos e trata erros de
     * unicidade (CPF, e-mail, username).
     *
     * @param user Usuário preenchido no formulário.
     * @param result Resultado da validação.
     * @param model Model para mensagens de erro.
     * @param redirectAttributes Mensagem de sucesso após cadastro.
     * @return Redireciona para a lista ou retorna ao formulário em caso de
     * erro.
     */
    @PostMapping("/users")
    public String saveUser(@Valid @ModelAttribute("user") Usuario usuario, BindingResult result, Model model, RedirectAttributes redirectAttributes) {
        logger.debug("POST /users chamado. Erros de validação? {}", result.hasErrors());
        if (result.hasErrors()) {
            return "userform";
        }
        try {
            usuarioService.createUser(usuario);
        } catch (DataIntegrityViolationException e) {
            // Mensagem amigável para CPF, e-mail ou username duplicados
            model.addAttribute("uniqueError", "CPF, e-mail ou username já cadastrado.");
            return "userform";
        }
        redirectAttributes.addFlashAttribute("successMessage", "Usuário cadastrado com sucesso!");
        return "redirect:/users";
    }
}
