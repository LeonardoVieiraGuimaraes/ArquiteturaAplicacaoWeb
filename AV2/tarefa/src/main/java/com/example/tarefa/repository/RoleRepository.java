package com.example.tarefa.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.tarefa.model.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String name);
}