// Pacote da configuração de segurança
package com.example.demo.config;

// Importações necessárias do Spring Security para configuração de segurança
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.example.demo.service.CustomUsuarioDetailsService;

// Indica que esta classe contém configurações do Spring
@Configuration
// Ativa as configurações de segurança web do Spring Security
@EnableWebSecurity
@EnableMethodSecurity // Adicione esta linha para ativar @PreAuthorize
public class SecurityConfig {

    // Este método define a cadeia de filtros de segurança da aplicação.
    // Ele configura como as requisições HTTP serão protegidas.
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                .requestMatchers("/h2-console/**").permitAll()
                .requestMatchers("/login").permitAll()
                .requestMatchers("/", "/home", "/index").permitAll()
                // Permite acesso público ao cadastro e listagem de usuários (MVC e API)
                .requestMatchers("/users", "/users/**").permitAll()
                .requestMatchers("/api/users", "/api/users/**").permitAll()
                // Remova restrições duplicadas/confusas para evitar conflito:
                // .requestMatchers(HttpMethod.POST, "/api/users").permitAll()
                // .requestMatchers(HttpMethod.POST, "/users").hasAuthority("ROLE_ADMIN")
                // .requestMatchers("/api/users/**").hasAuthority("ROLE_ADMIN")
                // ...existing code...
                .requestMatchers(HttpMethod.POST, "/api/books").hasAuthority("ROLE_ADMIN")
                .requestMatchers(HttpMethod.PUT, "/api/books").hasAuthority("ROLE_ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/api/books").hasAuthority("ROLE_ADMIN")
                .requestMatchers(HttpMethod.POST, "/api/authors").hasAuthority("ROLE_ADMIN")
                .requestMatchers(HttpMethod.PUT, "/api/authors").hasAuthority("ROLE_ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/api/authors").hasAuthority("ROLE_ADMIN")
                .requestMatchers("/api/books").permitAll()
                .requestMatchers("/api/authors").permitAll()
                .requestMatchers("/api/hello").hasAnyAuthority("ROLE_ADMIN", "ROLE_USER")
                .anyRequest().authenticated()
                )
                .formLogin(form -> form
                .loginPage("/login")
                .permitAll()
                .defaultSuccessUrl("/", true)
                .failureUrl("/login?error=true")
                )
                .httpBasic(_ -> {
                });
        http.headers(headers -> headers.frameOptions(frame -> frame.disable()));
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // Injete o CustomUsuarioDetailsService já como bean, não crie manualmente!
    @Bean
    public UserDetailsService userDetailsService(CustomUsuarioDetailsService customUsuarioDetailsService) {
        return customUsuarioDetailsService;
    }

}
