
package com.example.demo.config;

import org.keycloak.adapters.springboot.KeycloakSpringBootConfigResolver;
import org.keycloak.adapters.springsecurity.authentication.KeycloakAuthenticationProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.authority.mapping.SimpleAuthorityMapper;
import org.springframework.security.web.SecurityFilterChain;

@Configuration // Indica que esta classe contém beans de configuração
@EnableWebSecurity // Ativa a configuração de segurança web do Spring
@EnableMethodSecurity // Permite usar anotações de segurança em métodos (@PreAuthorize)
public class KeycloakSecurityConfig {

    /**
     * Bean para resolver configurações do Keycloak via application.yml
     * Permite usar as propriedades do Keycloak no arquivo application.yml
     */
    @Bean
    public KeycloakSpringBootConfigResolver keycloakConfigResolver() {
        return new KeycloakSpringBootConfigResolver();
    }

    /**
     * Bean para configurar o provedor de autenticação do Keycloak
     * Responsável por mapear as roles do Keycloak para o Spring Security
     */
    @Bean
    public KeycloakAuthenticationProvider keycloakAuthenticationProvider() {
        KeycloakAuthenticationProvider provider = new KeycloakAuthenticationProvider();
        provider.setGrantedAuthoritiesMapper(new SimpleAuthorityMapper());
        return provider;
    }

    /**
     * Configuração moderna de segurança para liberar todos os endpoints (ambiente de teste)
     * Permite acesso irrestrito a todos os endpoints da API
     */
    // @Bean
    // public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    //     http
    //         .csrf(csrf -> csrf.disable()) // Desabilita proteção CSRF para facilitar testes
    //         .authorizeHttpRequests(auth -> auth
    //             .anyRequest().permitAll() // Permite acesso a qualquer endpoint
    //         );
    //     return http.build();
    // }

    /*
     * Configuração para proteger endpoints com Keycloak (produção)
     * Exemplo: protege /livros/** para usuários com role USER e /autores/** para ADMIN
     * Para ativar, descomente este método e comente o de teste acima
     */
    @Bean
    public SecurityFilterChain securityFilterChainKeycloak(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable()) // Desabilita CSRF
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/livros/**").hasRole("USER") // Apenas usuários com role USER acessam livros
                .requestMatchers("/autores/**").hasRole("ADMIN") // Apenas ADMIN acessam autores
                .anyRequest().permitAll() // Outros endpoints liberados
            );
        return http.build();
    }
}
