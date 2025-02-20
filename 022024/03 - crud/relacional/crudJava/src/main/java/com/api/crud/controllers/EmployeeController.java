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

import com.api.crud.models.Employee;
import com.api.crud.services.EmployeeService;

// Anotação que indica que esta classe é um controlador REST
@RestController
// Define a rota base para todas as requisições deste controlador
@RequestMapping("/employees")
public class EmployeeController {
    // Injeção de dependência do serviço de funcionários
    @Autowired
    private EmployeeService employeeService;

    // Método para obter todos os funcionários
    @GetMapping
    public List<Employee> getAllEmployees() {
        // Chama o serviço para obter todos os funcionários
        return employeeService.findAll();
    }

    // Método para obter um funcionário pelo ID
    @GetMapping("/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable Long id) {
        // Chama o serviço para obter um funcionário pelo ID
        Optional<Employee> employee = employeeService.findById(id);
        // Retorna o funcionário se encontrado, caso contrário, retorna 404 Not Found
        return employee.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Método para criar um novo funcionário
    @PostMapping
    public Employee createEmployee(@RequestBody Employee employee) {
        // Chama o serviço para salvar o novo funcionário
        return employeeService.save(employee);
    }

    // Método para atualizar um funcionário existente
    @PutMapping("/{id}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable Long id, @RequestBody Employee employeeDetails) {
        // Chama o serviço para obter um funcionário pelo ID
        Optional<Employee> employee = employeeService.findById(id);
        if (employee.isPresent()) {
            // Atualiza os detalhes do funcionário
            Employee updatedEmployee = employee.get();
            updatedEmployee.setFirstName(employeeDetails.getFirstName());
            updatedEmployee.setLastName(employeeDetails.getLastName());
            updatedEmployee.setDepartment(employeeDetails.getDepartment());
            updatedEmployee.setBirthdate(employeeDetails.getBirthdate());
            updatedEmployee.setCv(employeeDetails.getCv());
            // Salva o funcionário atualizado e retorna 200 OK
            return ResponseEntity.ok(employeeService.save(updatedEmployee));
        } else {
            // Retorna 404 Not Found se o funcionário não for encontrado
            return ResponseEntity.notFound().build();
        }
    }

    // Método para deletar um funcionário pelo ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable Long id) {
        // Chama o serviço para deletar o funcionário pelo ID
        employeeService.deleteById(id);
        // Retorna 204 No Content
        return ResponseEntity.noContent().build();
    }
}