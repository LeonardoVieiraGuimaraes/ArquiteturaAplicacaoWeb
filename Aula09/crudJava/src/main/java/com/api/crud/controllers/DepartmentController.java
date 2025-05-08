package com.api.crud.controllers;

// Importações necessárias
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.crud.models.Department;
import com.api.crud.services.DepartmentService;

// Anotação que indica que esta classe é um controlador REST
@RestController
// Define a rota base para todas as requisições deste controlador
@RequestMapping("/departments")
public class DepartmentController {
    // Injeção de dependência do serviço de departamentos
    @Autowired
    private DepartmentService departmentService;

    // Método para obter todos os departamentos
    @GetMapping
    public List<Department> getAllDepartments() {
        // Chama o serviço para buscar todos os departamentos
        return departmentService.findAll();
    }

    // Método para obter um departamento pelo ID
    @GetMapping("/{id}")
    public ResponseEntity<Department> getDepartmentById(@PathVariable Long id) {
        // Chama o serviço para buscar o departamento pelo ID
        Optional<Department> department = departmentService.findById(id);
        // Retorna o departamento se encontrado, caso contrário retorna 404 Not Found
        return department.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Método para criar um novo departamento
    @PostMapping
    public Department createDepartment(@RequestBody Department department) {
        // Chama o serviço para salvar o novo departamento
        return departmentService.save(department);
    }

    // Método para atualizar um departamento existente
    @PutMapping("/{id}")
    public ResponseEntity<Department> updateDepartment(@PathVariable Long id, @RequestBody Department departmentDetails) {
        // Chama o serviço para buscar o departamento pelo ID
        Optional<Department> department = departmentService.findById(id);
        if (department.isPresent()) {
            // Atualiza os detalhes do departamento
            Department updatedDepartment = department.get();
            updatedDepartment.setTitle(departmentDetails.getTitle());
            // Salva o departamento atualizado e retorna 200 OK
            return ResponseEntity.ok(departmentService.save(updatedDepartment));
        } else {
            // Retorna 404 Not Found se o departamento não for encontrado
            return ResponseEntity.notFound().build();
        }
    }

    // Método para deletar um departamento pelo ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDepartment(@PathVariable Long id) {
        // Chama o serviço para deletar o departamento pelo ID
        departmentService.deleteById(id);
        // Retorna 204 No Content após a exclusão
        return ResponseEntity.noContent().build();
    }
}