// Pacote da configuração de segurança
package com.example.demo.config;

// Importações necessárias do Spring Security para configuração de segurança
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

// Indica que esta classe contém configurações do Spring
@Configuration
// Ativa as configurações de segurança web do Spring Security
@EnableWebSecurity
@EnableMethodSecurity // Ativa o uso de @PreAuthorize nos controladores
public class SecurityConfig {

    // Este método define a cadeia de filtros de segurança da aplicação.
    // Ele configura como as requisições HTTP serão protegidas.
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // Desabilita a proteção contra CSRF (Cross-Site Request Forgery).
                // Útil para facilitar testes via ferramentas como Postman, mas não recomendado em produção.
                .csrf(csrf -> csrf.disable())
                // Define as regras de autorização para as requisições HTTP.
                .authorizeHttpRequests(auth -> auth
                // Permite acesso ao console H2 para todos
                .requestMatchers("/h2-console/**").permitAll()
                // Permite acesso a /login para todos
                .requestMatchers("/login").permitAll()
                // Permite POST em /users (MVC) apenas para ADMIN
                .requestMatchers(HttpMethod.POST, "/users").hasAuthority("ROLE_ADMIN")
                // /api/hello apenas para ADMIN ou USER
                .requestMatchers("/api/hello").hasAnyAuthority("ROLE_ADMIN", "ROLE_USER")
                // Somente ADMIN pode acessar /api/users/**
                .requestMatchers("/api/users/**").hasAuthority("ROLE_ADMIN")
                // Qualquer outra requisição precisa estar autenticada (deixe por último)
                .anyRequest().authenticated()
                )
                // Habilita o formulário de login customizado.
                .formLogin(form -> form
                // Define a página de login customizada ("/login").
                .loginPage("/login")
                // Permite que todos acessem a página de login sem autenticação.
                .permitAll()
                )
                // Habilita autenticação HTTP Basic (usuário e senha via cabeçalho HTTP).
                .httpBasic(_ -> {
                }); // Forma recomendada para habilitar HTTP Basic

        // Permite o uso de frames para o H2 Console
        http.headers(headers -> headers.frameOptions(frame -> frame.disable()));

        // Retorna o objeto configurado de SecurityFilterChain.
        return http.build();
    }

    // Bean para o PasswordEncoder usando BCrypt.
    // O PasswordEncoder é responsável por codificar as senhas dos usuários.
    // BCrypt é um algoritmo de hash seguro recomendado para senhas.
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
