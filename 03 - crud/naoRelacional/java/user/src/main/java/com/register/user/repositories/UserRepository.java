package com.register.user.repositories;

// Importa a interface MongoRepository do Spring Data MongoDB, que fornece métodos CRUD para a entidade User
import org.springframework.data.mongodb.repository.MongoRepository;

// Importa a anotação @Repository do Spring Framework, que é usada para indicar que a interface é um repositório
import org.springframework.stereotype.Repository;

// Importa a classe User do pacote models, que representa a entidade User no banco de dados
import com.register.user.models.User;

// A anotação @Repository indica que esta interface é um repositório Spring Data
@Repository
public interface UserRepository extends MongoRepository<User, String> {
    // Esta interface herda os métodos CRUD (Create, Read, Update, Delete) da interface MongoRepository
    // A entidade gerenciada é User e o tipo do ID é String
}