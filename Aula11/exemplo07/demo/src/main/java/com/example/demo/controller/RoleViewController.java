package com.example.demo.controller;

<<<<<<< HEAD
import com.example.demo.model.Role;
import com.example.demo.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
=======
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.model.Role;
import com.example.demo.service.RoleService;

import lombok.RequiredArgsConstructor;
>>>>>>> f400c99298f0bdfad30d99dc5f226b5b7a51dbc8

@Controller
@RequiredArgsConstructor
public class RoleViewController {

    private final RoleService roleService;

    @GetMapping("/roles")
    public String listRoles(Model model) {
        model.addAttribute("roles", roleService.getAllRoles());
        return "roles";
    }

    @GetMapping("/roles/form")
    public String showRoleForm(@RequestParam(value = "id", required = false) Long id, Model model) {
        Role role = (id != null) ? roleService.getRoleById(id).orElse(new Role()) : new Role();
        model.addAttribute("role", role);
        return "roleform";
    }

<<<<<<< HEAD
=======
    @PreAuthorize("hasRole('ADMIN')")
>>>>>>> f400c99298f0bdfad30d99dc5f226b5b7a51dbc8
    @PostMapping("/roles")
    public String saveRole(@ModelAttribute("role") Role role, RedirectAttributes redirectAttributes) {
        try {
            roleService.createRole(role);
            redirectAttributes.addFlashAttribute("successMessage", "Função cadastrada com sucesso!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Erro ao cadastrar função: " + e.getMessage());
        }
        return "redirect:/roles";
    }

<<<<<<< HEAD
=======
    @PreAuthorize("hasRole('ADMIN')")
>>>>>>> f400c99298f0bdfad30d99dc5f226b5b7a51dbc8
    @PostMapping("/roles/{id}/delete")
    public String deleteRole(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        boolean deleted = roleService.deleteRole(id);
        if (deleted) {
            redirectAttributes.addFlashAttribute("successMessage", "Função excluída com sucesso!");
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "Função não encontrada.");
        }
        return "redirect:/roles";
    }
}
