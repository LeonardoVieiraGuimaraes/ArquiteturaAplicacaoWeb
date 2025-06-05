// Pacote da configuração de segurança
package com.example.demo.config;

// Importações necessárias do Spring Security para configuração de segurança
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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

    // ==========================
    // Opção 1: API JWT (Resource Server)
    // ==========================
    @Bean
    // @Order(1) // Pode adicionar @Order se quiser usar múltiplos filtros
    public SecurityFilterChain apiSecurityFilterChain(HttpSecurity http) throws Exception {
        http
                .securityMatcher("/api/**")
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                .requestMatchers("/api/auth/**").permitAll()
                .anyRequest().authenticated()
                )
                .oauth2ResourceServer(oauth2 -> oauth2.jwt(org.springframework.security.config.Customizer.withDefaults()))
                .sessionManagement(session -> session.sessionCreationPolicy(
                org.springframework.security.config.http.SessionCreationPolicy.STATELESS
        ));
        return http.build();
    }

    // ==========================
    // Opção 2: Sessão tradicional (formLogin)
    // ==========================
    /*
    @Bean
    // @Order(2) // Pode remover o @Order se só usar uma configuração
    public SecurityFilterChain webSecurityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                .requestMatchers("/h2-console/**", "/", "/home", "/index", "/login").permitAll()
                .anyRequest().authenticated()
                )
                .formLogin(form -> form
                .loginPage("/login")
                .permitAll()
                )
                .logout(logout -> logout
                    .logoutUrl("/logout") // URL que dispara o logout
                    .logoutSuccessUrl("/login?logout") // Para onde redireciona após logout
                    .permitAll()
                );
        http.headers(headers -> headers.frameOptions(frame -> frame.disable()));
        return http.build();
    }
     */
    // PasswordEncoder padrão para criptografar senhas dos usuários usando BCrypt (recomendado pelo Spring Security).
    // Sempre use passwordEncoder.encode(senha) ao salvar senhas no banco.
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // NÃO é necessário declarar um JwtDecoder manualmente para o uso padrão.
    // O Spring Boot cria e configura automaticamente o JwtDecoder usando as propriedades do application.yml.
    // Só crie um bean JwtDecoder se precisar de customização avançada.
}
