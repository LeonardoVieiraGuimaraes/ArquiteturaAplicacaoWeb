package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // Desabilita CSRF para facilitar testes no Postman
                .authorizeHttpRequests(auth -> auth
                .requestMatchers("/api/hello").permitAll() // Permite acesso sem autenticação ao /api/hello
                .requestMatchers("/api/users/**").permitAll() // Permite acesso irrestrito aos endpoints de usuários
                // .requestMatchers("/api/authors/**").hasRole("ADMIN") // Requer autenticação com a role ADMIN
                // .requestMatchers("/api/books/**").hasAnyRole("USER", "ADMIN") // Requer autenticação com a role USER e ADMIN
                // .requestMatchers(HttpMethod.DELETE, "/api/books/**").hasAnyRole("USER") // Requer autenticação com a role USER
                // .requestMatchers(HttpMethod.POST, "/api/books/**").hasAnyRole("USER") // Requer autenticação com a role USER
                // .requestMatchers(HttpMethod.PUT, "/api/books/**").hasAnyRole("USER") // Requer autenticação com a role USER
                // .requestMatchers(HttpMethod.GET, "/api/books/**").hasAnyRole("USER") // Requer autenticação com a role USER
                // .requestMatchers(HttpMethod.GET, "/api/authors/**").hasAnyRole("USER") // Requer autenticação com a role USER
                // .requestMatchers(HttpMethod.POST, "/api/authors/**").hasAnyRole("USER") // Requer autenticação com a role USER
                // .requestMatchers(HttpMethod.PUT, "/api/authors/**").hasAnyRole("USER") // Requer autenticação com a role USER
                // .requestMatchers(HttpMethod.DELETE, "/api/authors/**").hasAnyRole("USER") // Requer autenticação com a role USER

                .anyRequest().authenticated() // Qualquer outra requisição requer autenticação
                )
                .formLogin(form -> form // Habilita o formulário de login padrão do Spring Boot
                .permitAll() // Permite acesso à página de login sem autenticação
                )
                .httpBasic(_ -> {
                    // Configura autenticação HTTP Basic
                }); // Habilita autenticação HTTP Basic
                // Configura o logout

                // .formLogin(formLoginCustomizer -> formLoginCustomizer
                // .loginPage("/login") // Define a URL da página de login
                // .defaultSuccessUrl("/index", true) // Redireciona para /index após login bem-sucedido
                // .permitAll() // Permite acesso à página de login sem autenticação
                // )
                // .logout(logout -> logout
                // .logoutUrl("/logout") // Define a URL de logout
                // .logoutSuccessUrl("/logout-success") // Redireciona para /logout-success após logout
                // .invalidateHttpSession(true) // Invalida a sessão HTTP
                // .deleteCookies("JSESSIONID") // Remove o cookie de sessão
                // .permitAll() // Permite acesso ao logout sem autenticação
                // )
                // .exceptionHandling(exception -> exception
                // .accessDeniedPage("/access-denied") // Define uma página personalizada para erros de acesso negado
                // );
        return http.build();
    }

    // @Bean
    // public UserDetailsService userDetailsService(PasswordEncoder passwordEncoder) {
    //     UserDetails user = User.builder()
    //             .username("user")
    //             .password(passwordEncoder.encode("123"))
    //             .roles("USER") // Define a role USER para o usuário padrão
    //             .build();

    //     UserDetails admin = User.builder()
    //             .username("admin")
    //             .password(passwordEncoder.encode("123"))
    //             .roles("ADMIN") // Define a role ADMIN para o usuário padrão
    //             .build();
    //     return new InMemoryUserDetailsManager(user, admin);
    // }

    // @Bean
    // public UserDetailsService userDetailsService(DataSource dataSource) {
    //     JdbcUserDetailsManager userDetailsManager = new JdbcUserDetailsManager(dataSource);
    //     userDetailsManager.setUsersByUsernameQuery("SELECT username, password, enabled FROM users WHERE username = ?");
    //     userDetailsManager.setAuthoritiesByUsernameQuery("SELECT username, authority FROM authorities WHERE username = ?");
    //     return userDetailsManager;
    // }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // Define o encoder de senha como BCrypt com força 10
    }
}
