package com.example.demo.controller;

import java.util.List;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
<<<<<<< HEAD
import org.springframework.web.bind.annotation.*;
=======
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
>>>>>>> f400c99298f0bdfad30d99dc5f226b5b7a51dbc8

import com.example.demo.model.User;
import com.example.demo.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
<<<<<<< HEAD
=======
@PreAuthorize("hasRole('ADMIN')")
>>>>>>> f400c99298f0bdfad30d99dc5f226b5b7a51dbc8
public class UserController {

    private final UserService userService;

    @GetMapping
    public List<User> index() {
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        return ResponseEntity.of(userService.getUserById(id));
    }

    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody User user) {
        try {
            User created = userService.createUser(user);
            return ResponseEntity.ok(created);
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.badRequest().body(
<<<<<<< HEAD
                java.util.Collections.singletonMap("message", "CPF, e-mail ou username já cadastrado.")
=======
                    java.util.Collections.singletonMap("message", "CPF, e-mail ou username já cadastrado.")
>>>>>>> f400c99298f0bdfad30d99dc5f226b5b7a51dbc8
            );
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Long id, @RequestBody User user) {
        try {
            return ResponseEntity.of(userService.updateUser(id, user));
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.badRequest().body(
<<<<<<< HEAD
                java.util.Collections.singletonMap("message", "CPF, e-mail ou username já cadastrado.")
=======
                    java.util.Collections.singletonMap("message", "CPF, e-mail ou username já cadastrado.")
>>>>>>> f400c99298f0bdfad30d99dc5f226b5b7a51dbc8
            );
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        if (userService.deleteUser(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/search")
    public ResponseEntity<User> findByUsername(@RequestParam String username) {
        return ResponseEntity.of(userService.findByUsername(username));
    }
}
