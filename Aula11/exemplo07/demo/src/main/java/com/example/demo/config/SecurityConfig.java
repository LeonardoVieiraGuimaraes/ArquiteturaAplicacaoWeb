// Pacote da configuração de segurança
package com.example.demo.config;

// Importações necessárias do Spring Security para configuração de segurança
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
<<<<<<< HEAD
import org.springframework.http.HttpMethod;
=======
>>>>>>> f400c99298f0bdfad30d99dc5f226b5b7a51dbc8
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
<<<<<<< HEAD
@EnableMethodSecurity // Adicione esta linha para ativar @PreAuthorize
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
                // POST, PUT, DELETE em /api/books e /api/authors somente para ADMIN
                .requestMatchers(HttpMethod.POST, "/api/books").hasAuthority("ROLE_ADMIN")
                .requestMatchers(HttpMethod.PUT, "/api/books").hasAuthority("ROLE_ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/api/books").hasAuthority("ROLE_ADMIN")
                .requestMatchers(HttpMethod.POST, "/api/authors").hasAuthority("ROLE_ADMIN")
                .requestMatchers(HttpMethod.PUT, "/api/authors").hasAuthority("ROLE_ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/api/authors").hasAuthority("ROLE_ADMIN")
                // Permite POST em /users (MVC) apenas para ADMIN
                .requestMatchers(HttpMethod.POST, "/users").hasAuthority("ROLE_ADMIN")
                // Demais métodos em /api/books e /api/authors são públicos
                .requestMatchers("/api/books").permitAll()
                .requestMatchers("/api/authors").permitAll()
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
=======
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
>>>>>>> f400c99298f0bdfad30d99dc5f226b5b7a51dbc8
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

<<<<<<< HEAD
=======
    // NÃO é necessário declarar um JwtDecoder manualmente para o uso padrão.
    // O Spring Boot cria e configura automaticamente o JwtDecoder usando as propriedades do application.yml.
    // Só crie um bean JwtDecoder se precisar de customização avançada.
>>>>>>> f400c99298f0bdfad30d99dc5f226b5b7a51dbc8
}
