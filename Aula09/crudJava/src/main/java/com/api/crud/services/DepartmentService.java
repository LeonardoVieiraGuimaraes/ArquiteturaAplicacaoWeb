package com.api.crud.services;

// Importações necessárias
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.api.crud.models.Department;
import com.api.crud.repositories.DepartmentRepository;

// Anotação que indica que esta classe é um serviço do Spring
@Service
public class DepartmentService {

    // Injeção de dependência do repositório de departamentos
    @Autowired
    private DepartmentRepository departmentRepository;

    // Método para buscar todos os departamentos
    public List<Department> findAll() {
        // Chama o método findAll do repositório para obter todos os departamentos
        return departmentRepository.findAll();
    }

    // Método para buscar um departamento pelo ID
    public Optional<Department> findById(Long id) {
        // Chama o método findById do repositório para obter um departamento pelo ID
        return departmentRepository.findById(id);
    }

    // Método para salvar um novo departamento ou atualizar um existente
    public Department save(Department department) {
        // Chama o método save do repositório para salvar o departamento
        return departmentRepository.save(department);
    }

    // Método para atualizar um departamento existente
    public Department update(Long id, Department department) {
        // Verifica se o departamento existe pelo ID
        if (departmentRepository.existsById(id)) {
            // Se existir, atualiza o departamento com os novos dados
            department.setId(id);
            return departmentRepository.save(department);
        } else {
            // Se não existir, retorna null ou pode lançar uma exceção
            return null;
        }
    }

    // Método para deletar um departamento pelo ID
    public void deleteById(Long id) {
        // Chama o método deleteById do repositório para deletar o departamento pelo ID
        departmentRepository.deleteById(id);
    }
}