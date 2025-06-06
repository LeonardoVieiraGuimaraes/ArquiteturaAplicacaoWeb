package com.api.crud.services;

// Importações necessárias
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.api.crud.models.Employee;
import com.api.crud.repositories.EmployeeRepository;

// Anotação que indica que esta classe é um serviço do Spring
@Service
public class EmployeeService {

    // Injeção de dependência do repositório de funcionários
    @Autowired
    private EmployeeRepository employeeRepository;

    // Método para buscar todos os funcionários
    public List<Employee> findAll() {
        // Chama o método findAll do repositório para obter todos os funcionários
        return employeeRepository.findAll();
    }

    // Método para buscar um funcionário pelo ID
    public Optional<Employee> findById(Long id) {
        // Chama o método findById do repositório para obter um funcionário pelo ID
        return employeeRepository.findById(id);
    }

    // Método para salvar um novo funcionário ou atualizar um existente
    public Employee save(Employee employee) {
        // Chama o método save do repositório para salvar o funcionário
        return employeeRepository.save(employee);
    }

    // Método para deletar um funcionário pelo ID
    public void deleteById(Long id) {
        // Chama o método deleteById do repositório para deletar o funcionário pelo ID
        employeeRepository.deleteById(id);
    }
}