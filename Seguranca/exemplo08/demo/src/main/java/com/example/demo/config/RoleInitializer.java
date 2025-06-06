package com.example.demo.config;

import com.example.demo.model.Role;
import com.example.demo.repository.RoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RoleInitializer {

    @Bean
    public CommandLineRunner createDefaultRoles(RoleRepository roleRepository) {
        return args -> {
            if (roleRepository.findByName("ROLE_ADMIN").isEmpty()) {
                roleRepository.save(new Role(null, "ROLE_ADMIN", "Administrador", null));
            }
            if (roleRepository.findByName("ROLE_USER").isEmpty()) {
                roleRepository.save(new Role(null, "ROLE_USER", "Usuário", null));
            }
            if (roleRepository.findByName("ROLE_GERENTE").isEmpty()) {
                roleRepository.save(new Role(null, "ROLE_GERENTE", "Gerente", null));
            }
        };
    }
}
