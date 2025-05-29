package com.example.demo.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.demo.model.Usuario;
import com.example.demo.service.UsuarioService;

@Configuration
public class AdminInitializer {

    private static final Logger logger = LoggerFactory.getLogger(AdminInitializer.class);

    @Bean
    public CommandLineRunner createAdmin(UsuarioService usuarioService) {
        return args -> {
            if (usuarioService.findByUsername("admin").isEmpty()) {
                logger.info("Criando usuário admin padrão...");
                Usuario admin = new Usuario();
                admin.setUsername("admin");
                admin.setPassword("admin123"); // será criptografada pelo service
                admin.setEnabled(true);
                admin.setRoles(new String[]{"ROLE_ADMIN"});
                admin.setNomeCompleto("Administrador do Sistema");
                admin.setCpf("05889763695");
                admin.setEmail("admin@exemplo.com");
                usuarioService.createUser(admin);
                logger.info("Usuário admin criado com sucesso!");
            } else {
                logger.info("Usuário admin já existe. Nenhuma ação realizada.");
            }
        };
    }
}
