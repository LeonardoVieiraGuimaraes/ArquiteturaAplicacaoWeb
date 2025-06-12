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
            // Garante que a role ROLE_ADMIN exista antes de criar o usuário admin
            Role adminRole = roleRepository.findByName("ROLE_ADMIN")
                .orElseGet(() -> roleRepository.save(new Role(null, "ROLE_ADMIN", "Administrador")));

            // Cria o usuário admin apenas se não existir
            if (userRepository.findByUsername("admin").isEmpty()) {
                User admin = new User(null, "admin", passwordEncoder.encode("431"), true, List.of(adminRole),
                        "Administrador do Sistema", "00000000000", "admin@admin.com");
                userRepository.save(admin);
            } else {
                // Garante que o admin sempre tenha a role ROLE_ADMIN associada
                User admin = userRepository.findByUsername("admin").get();
                if (admin.getRoles().stream().noneMatch(r -> "ROLE_ADMIN".equals(r.getName()))) {
                    admin.getRoles().add(adminRole);
                    userRepository.save(admin);
                }
            }
        };
    }
}
