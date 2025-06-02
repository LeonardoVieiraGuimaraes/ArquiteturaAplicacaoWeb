package com.example.demo.config;

import java.util.Optional;

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
            // Ajuste: use Optional<Usuario> corretamente
            Optional<Usuario> adminOpt = usuarioService.findByUsername("admin");
            if (!adminOpt.isPresent()) {
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
                Usuario adminExistente = adminOpt.get();
                // Corrige roles do admin existente caso estejam ausentes ou erradas
                boolean precisaAtualizar = false;
                String[] roles = adminExistente.getRoles();
                if (roles == null || roles.length == 0 || !roles[0].equals("ROLE_ADMIN")) {
                    adminExistente.setRoles(new String[]{"ROLE_ADMIN"});
                    precisaAtualizar = true;
                }
                if (!adminExistente.isEnabled()) {
                    adminExistente.setEnabled(true);
                    precisaAtualizar = true;
                }
                if (precisaAtualizar) {
                    usuarioService.createUser(adminExistente);
                    logger.info("Usuário admin já existia, mas foi atualizado (roles e/ou enabled).");
                } else {
                    logger.info("Usuário admin já existe. Nenhuma ação realizada.");
                }
            }
        };
    }
}
