// Pacote da configuração de segurança
package com.example.demo.config;

// Importações necessárias do Spring Security para configuração de segurança
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

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
                // Desabilita a proteção contra CSRF (Cross-Site Request Forgery).
                // Útil para facilitar testes via ferramentas como Postman, mas não recomendado em produção.
                .csrf(csrf -> csrf.disable())
                // Define as regras de autorização para as requisições HTTP.
                .authorizeHttpRequests(auth -> auth
                // Permite acesso a /login para todos
                .requestMatchers("/login").permitAll()
                // POST, PUT, DELETE em /api/books e /api/authors somente para ADMIN
                .requestMatchers(HttpMethod.POST, "/api/books").hasAuthority("ROLE_ADMIN").
                requestMatchers(HttpMethod.GET, "/api/books").hasAuthority("ROLE_ADMIN")
                .requestMatchers(HttpMethod.PUT, "/api/books").hasAuthority("ROLE_ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/api/books").hasAuthority("ROLE_ADMIN")
                .requestMatchers(HttpMethod.POST, "/api/authors").hasAuthority("ROLE_ADMIN")
                .requestMatchers(HttpMethod.PUT, "/api/authors").hasAuthority("ROLE_ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/api/authors").hasAuthority("ROLE_ADMIN")
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

    // Este método define os usuários que poderão acessar a aplicação.
    // Os usuários são armazenados em memória (InMemoryUserDetailsManager).
    // O PasswordEncoder é injetado para garantir que as senhas sejam codificadas corretamente.
    @Bean
    public UserDetailsService userDetailsService(PasswordEncoder encoder) {
        // Cria um usuário chamado "user" com a senha "senha" e papel "USER".
        // A senha é codificada usando o encoder (BCrypt).
        UserDetails user = User.builder()
                .username("user")
                .password(encoder.encode("123"))
                .roles("USER")
                .build();

        // Cria um usuário chamado "admin" com a senha "senha" e papel "ADMIN".
        // Também utiliza o encoder para codificar a senha.
        UserDetails admin = User.builder()
                .username("admin")
                .password(encoder.encode("431"))
                .roles("ADMIN")
                .build();

        // Retorna um gerenciador de usuários em memória contendo ambos os usuários.
        // Isso permite autenticação sem necessidade de banco de dados.
        return new InMemoryUserDetailsManager(user, admin);
    }

}
