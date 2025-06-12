// Este inicializador garante que as roles padrão (ROLE_ADMIN, ROLE_USER, ROLE_GERENTE) existam no banco ao iniciar a aplicação.
// Se já existirem, nada é feito. Se não existirem, são criadas automaticamente.
package com.example.demo.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.demo.model.Role;
import com.example.demo.repository.RoleRepository;

@Configuration
public class RoleInitializer {

    @Bean
    public CommandLineRunner createDefaultRoles(RoleRepository roleRepository) {
        return args -> {
            if (roleRepository.findByName("ROLE_ADMIN").isEmpty()) {
                roleRepository.save(new Role(null, "ROLE_ADMIN", "Administrador"));
            }
            if (roleRepository.findByName("ROLE_USER").isEmpty()) {
                roleRepository.save(new Role(null, "ROLE_USER", "Usuário"));
            }
            if (roleRepository.findByName("ROLE_GERENTE").isEmpty()) {
                roleRepository.save(new Role(null, "ROLE_GERENTE", "Gerente"));
            }
        };
    }
}
