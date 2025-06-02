package com.example.demo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.example.demo.model.User;
import com.example.demo.service.RoleService;
import com.example.demo.service.UserService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class UserViewController {

    private static final Logger logger = LoggerFactory.getLogger(UserViewController.class);

    private final UserService userService;
    private final RoleService roleService;

    @GetMapping("/users")
    public String users(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        return "users";
    }

    @GetMapping("/users/form")
    public String userForm(@RequestParam(value = "id", required = false) Long id, Model model) {
        User user = (id != null) ? userService.getUserById(id).orElse(new User()) : new User();
        model.addAttribute("user", user);
        model.addAttribute("rolesList", roleService.getAllRoles());
        return "userform";
    }

    // Não processa mais POST /users, pois o formulário envia para a API REST
}
