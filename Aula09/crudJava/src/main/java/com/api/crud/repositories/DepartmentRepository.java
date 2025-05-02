package com.api.crud.repositories;

// Importações necessárias
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.api.crud.models.Department;

// Anotação que indica que esta interface é um repositório Spring
@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long> {
    // Esta interface herda métodos de JpaRepository para operações CRUD
}