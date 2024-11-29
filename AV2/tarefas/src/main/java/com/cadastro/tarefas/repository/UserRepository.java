package com.cadastro.tarefas.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cadastro.tarefas.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    // Usuario findByUsername(String username);
    Optional<User> findByUsername(String username);

}
