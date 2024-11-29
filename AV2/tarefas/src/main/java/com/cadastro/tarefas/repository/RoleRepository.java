package com.cadastro.tarefas.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cadastro.tarefas.model.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String name);
}