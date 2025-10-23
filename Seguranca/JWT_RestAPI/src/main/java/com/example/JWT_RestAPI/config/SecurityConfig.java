package com.example.JWT_RestAPI.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
/**
 * Configurações de segurança HTTP com Spring Security.
 * <p>
 * Define as regras de autorização para os endpoints, desativa CSRF para APIs stateless
 * e configura usuários em memória para simplificar o exemplo.
 */
public class SecurityConfig {

    /**
     * Define a cadeia de filtros de segurança (autorização, CSRF, autenticação básica, etc.).
     * @param http builder de segurança HTTP do Spring
     * @return chain de filtros configurada
     * @throws Exception em caso de erro de configuração
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
        // Em APIs REST com JWT, geralmente mantemos CSRF desabilitado (stateless)
        .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(request -> request
            // Endpoints públicos
            .requestMatchers(HttpMethod.POST, "/login/**").permitAll()
            .requestMatchers(HttpMethod.GET, "/username/**").permitAll()
            .requestMatchers(HttpMethod.GET, "/user/**").permitAll()
            // Endpoints restritos
            .requestMatchers("/admin/**").hasRole("ADMIN")
                        .anyRequest()
                        .authenticated()
        )
        // Autenticação HTTP Basic apenas para exemplo; em produção, prefira Bearer JWT
        .httpBasic(Customizer.withDefaults());
        return http.build();
    }

    /**
     * Serviço de usuários em memória para fins de demonstração.
     * Em produção, substitua por UserDetailsService que consulta base de dados.
     */
    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails user = User.builder()
                .username("joao")
                .password(passwordEncoder().encode("4321"))
                .roles("USER")
                .build();
        UserDetails admin = User.builder()
                .username("admin")
                .password(passwordEncoder().encode("1234"))
                .roles("ADMIN")
                .build();
        return new InMemoryUserDetailsManager(user,admin);
    }

    /**
     * PasswordEncoder padrão (BCrypt) para codificar/validar senhas.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}

