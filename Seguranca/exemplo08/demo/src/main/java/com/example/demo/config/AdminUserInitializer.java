package com.example.demo.config;

import com.example.demo.model.Role;
import com.example.demo.model.User;
import com.example.demo.repository.RoleRepository;
import com.example.demo.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@Configuration
public class AdminUserInitializer {

    @Bean
    public CommandLineRunner createAdmin(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            if (userRepository.findByUsername("admin").isEmpty()) {
                User admin = new User();
                admin.setUsername("admin");
                admin.setPassword(passwordEncoder.encode("431")); // senha padrão
                admin.setEnabled(true);
                // Busca a role ROLE_ADMIN no banco
                Role adminRole = roleRepository.findByName("ROLE_ADMIN")
                        .orElseGet(() -> roleRepository.save(new Role(null, "ROLE_ADMIN", "Administrador", null)));
                admin.setRoles(List.of(adminRole));
                admin.setNomeCompleto("Leonardo Vieira Guimarães");
                admin.setCpf("00000000000");
                admin.setEmail("admin@admin.com");
                userRepository.save(admin);
            }
        };
    }
}
