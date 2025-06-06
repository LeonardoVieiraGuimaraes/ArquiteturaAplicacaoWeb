package com.example.demo.config;

// Importa as anotações e classes necessárias do Spring Security
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

// Indica que esta classe é uma configuração do Spring
@Configuration
// Habilita a configuração de segurança web do Spring Security
@EnableWebSecurity
public class SecurityConfig {

    // Para definir um usuário e senha padrão, adicione um bean de UserDetailsService ou configure no application.properties:
    // spring.security.user.name=usuario
    // spring.security.user.password=senha
    // Define um bean do tipo SecurityFilterChain, responsável por configurar a cadeia de filtros de segurança
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // Desabilita a proteção CSRF (Cross-Site Request Forgery) para facilitar testes em ferramentas como Postman
                .csrf(csrf -> csrf.disable())
                // Configura as regras de autorização para as requisições HTTP
                .authorizeHttpRequests(auth -> auth
                // Exige autenticação para qualquer requisição feita à aplicação
                .anyRequest().authenticated()
                )
                // Habilita o formulário de login personalizado em /login (login.html)
                .formLogin(form -> form
                .loginPage("/login") // Define a página de login customizada (caso queira o default, pode remover esta linha)
                .permitAll() // Permite que todos acessem a página de login sem precisar estar autenticado
                )
                // Habilita autenticação HTTP Basic (usuário e senha via cabeçalho HTTP)
                .httpBasic(_ -> {
                }); // Nova forma recomendada para habilitar HTTP Basic

        // Retorna o objeto configurado de SecurityFilterChain
        return http.build();
    }

}
