package com.api.crud.controllers;

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

/**
 * Controlador REST para gerenciar funcionários.
 */
@RestController
@RequestMapping("/employees")
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;

    /**
     * Obtém todos os funcionários.
     *
     * @return uma lista de todos os funcionários.
     */
    @GetMapping
    public List<Employee> getAllEmployees() {
        return employeeService.findAll();
    }

    /**
     * Obtém um funcionário pelo ID.
     *
     * @param id o ID do funcionário.
     * @return o funcionário correspondente ao ID fornecido.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable Long id) {
        Optional<Employee> employee = employeeService.findById(id);
        return employee.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Cria um novo funcionário.
     *
     * @param employee o funcionário a ser criado.
     * @return o funcionário criado.
     */
    @PostMapping
    public Employee createEmployee(@RequestBody Employee employee) {
        return employeeService.save(employee);
    }

    /**
     * Atualiza um funcionário existente.
     *
     * @param id o ID do funcionário a ser atualizado.
     * @param employeeDetails os novos detalhes do funcionário.
     * @return o funcionário atualizado.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable Long id, @RequestBody Employee employeeDetails) {
        Optional<Employee> employee = employeeService.findById(id);
        if (employee.isPresent()) {
            Employee updatedEmployee = employee.get();
            updatedEmployee.setFirstName(employeeDetails.getFirstName());
            updatedEmployee.setLastName(employeeDetails.getLastName());
            updatedEmployee.setDepartment(employeeDetails.getDepartment());
            updatedEmployee.setBirthdate(employeeDetails.getBirthdate());
            updatedEmployee.setCv(employeeDetails.getCv());
            return ResponseEntity.ok(employeeService.save(updatedEmployee));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Deleta um funcionário pelo ID.
     *
     * @param id o ID do funcionário a ser deletado.
     * @return uma resposta sem conteúdo.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable Long id) {
        employeeService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}