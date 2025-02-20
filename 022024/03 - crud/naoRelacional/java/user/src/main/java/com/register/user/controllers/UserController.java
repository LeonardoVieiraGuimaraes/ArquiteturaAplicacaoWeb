package com.register.user.controllers;

// Importa a anotação @Autowired do Spring Framework, que é usada para injeção de dependência automática
import org.springframework.beans.factory.annotation.Autowired;

// Importa a classe ResponseEntity do Spring Framework, que é usada para representar toda a resposta HTTP
import org.springframework.http.ResponseEntity;

// Importa as anotações @RestController, @RequestMapping, @GetMapping, @PostMapping, @PutMapping e @DeleteMapping do Spring Framework
// Essas anotações são usadas para mapear requisições HTTP para métodos específicos no controlador
import org.springframework.web.bind.annotation.*;

// Importa a classe User do pacote models, que representa a entidade User
import com.register.user.models.User;

// Importa a classe UserService do pacote services, que contém a lógica de negócios para a entidade User
import com.register.user.services.UserService;

// Importa as classes List e Optional da biblioteca Java
// List é usada para representar uma coleção de usuários
// Optional é usada para representar um valor que pode ou não estar presente
import java.util.List;
import java.util.Optional;

// A anotação @RestController indica que esta classe é um controlador REST
@RestController
// A anotação @RequestMapping é usada para mapear requisições HTTP para o caminho "/users"
@RequestMapping("/users")
public class UserController {

    // A anotação @Autowired é usada para injetar automaticamente uma instância de UserService
    @Autowired
    private UserService userService;

    // O método getAllUsers é mapeado para requisições HTTP GET no caminho "/users"
    // Ele retorna uma lista de todos os usuários
    @GetMapping
    public List<User> getAllUsers() {
        return userService.findAll();
    }

    // O método getUserById é mapeado para requisições HTTP GET no caminho "/users/{id}"
    // Ele retorna um usuário específico com base no ID fornecido
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable String id) {
        Optional<User> user = userService.findById(id);
        return user.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // O método createUser é mapeado para requisições HTTP POST no caminho "/users"
    // Ele cria um novo usuário com base nos dados fornecidos no corpo da requisição
    @PostMapping
    public User createUser(@RequestBody User user) {
        return userService.save(user);
    }

    // O método updateUser é mapeado para requisições HTTP PUT no caminho "/users/{id}"
    // Ele atualiza um usuário existente com base no ID e nos dados fornecidos no corpo da requisição
    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable String id, @RequestBody User user) {
        if (!userService.findById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        user.setId(id);
        return ResponseEntity.ok(userService.save(user));
    }

    // O método deleteUser é mapeado para requisições HTTP DELETE no caminho "/users/{id}"
    // Ele exclui um usuário existente com base no ID fornecido
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable String id) {
        if (!userService.findById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        userService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}