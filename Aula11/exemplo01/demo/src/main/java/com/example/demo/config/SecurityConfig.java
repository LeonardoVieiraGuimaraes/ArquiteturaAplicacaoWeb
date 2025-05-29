package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // Desabilita CSRF para facilitar testes no Postman
                .authorizeHttpRequests(auth -> auth
                .anyRequest().authenticated() // Exige autenticação para todas as requisições
                )
                .formLogin(form -> form // Habilita o formulário de login padrão do Spring Boot
                .permitAll() // Permite acesso à página de login sem autenticação
                )
                .httpBasic(basic -> {
                }); // Nova forma recomendada para habilitar HTTP Basic

        return http.build();
    }

}
