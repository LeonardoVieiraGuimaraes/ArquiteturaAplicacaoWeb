package com.api.crud.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.api.crud.model.Department;



@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long> {
}