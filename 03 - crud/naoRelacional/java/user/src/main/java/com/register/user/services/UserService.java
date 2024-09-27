package com.register.user.services;

// Importa a anotação @Autowired do Spring Framework, que é usada para injeção de dependência automática
import org.springframework.beans.factory.annotation.Autowired;

// Importa a anotação @Service do Spring Framework, que é usada para marcar a classe como um serviço Spring
import org.springframework.stereotype.Service;

// Importa a classe User do pacote models, que representa a entidade User
import com.register.user.models.User;

// Importa a interface UserRepository do pacote repositories, que fornece métodos CRUD para a entidade User
import com.register.user.repositories.UserRepository;

// Importa as classes List e Optional da biblioteca Java
// List é usada para representar uma coleção de usuários
// Optional é usada para representar um valor que pode ou não estar presente
import java.util.List;
import java.util.Optional;

// A anotação @Service indica que esta classe é um serviço Spring, que contém a lógica de negócios
@Service
public class UserService {

    // A anotação @Autowired é usada para injetar automaticamente uma instância de UserRepository
    @Autowired
    private UserRepository userRepository;

    // Método para encontrar todos os usuários
    public List<User> findAll() {
        return userRepository.findAll();
    }

    // Método para encontrar um usuário pelo ID
    public Optional<User> findById(String id) {
        return userRepository.findById(id);
    }

    // Método para salvar um usuário
    public User save(User user) {
        return userRepository.save(user);
    }

    // Método para deletar um usuário pelo ID
    public void deleteById(String id) {
        userRepository.deleteById(id);
    }
}