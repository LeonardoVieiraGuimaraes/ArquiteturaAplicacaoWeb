package com.example.demo.config;

import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.demo.model.Role;
import com.example.demo.model.User;
import com.example.demo.repository.RoleRepository;
import com.example.demo.repository.UserRepository;

@Configuration
public class AdminUserInitializer {

    @Bean
    public CommandLineRunner createAdmin(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            Role adminRole = roleRepository.findByName("ROLE_ADMIN")
                    .orElseGet(() -> roleRepository.save(new Role(null, "ROLE_ADMIN", "Administrador")));

            if (userRepository.findByUsername("admin").isEmpty()) {
                User admin = new User(null, "admin", passwordEncoder.encode("admin431"), true, List.of(adminRole),
                        "Administrador do Sistema", "00000000000", "admin@admin.com");
                userRepository.save(admin);
            } else {
                User admin = userRepository.findByUsername("admin").get();
                if (admin.getRoles().stream().noneMatch(r -> "ROLE_ADMIN".equals(r.getName()))) {
                    admin.getRoles().add(adminRole);
                    userRepository.save(admin);
                }
            }
        };
    }
}
