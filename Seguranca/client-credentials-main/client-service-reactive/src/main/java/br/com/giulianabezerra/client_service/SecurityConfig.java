package br.com.giulianabezerra.client_service;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {
  @Bean
  SecurityWebFilterChain filterChain(ServerHttpSecurity http) throws Exception {
    http.authorizeExchange(authorize -> authorize
        .anyExchange().permitAll());

    return http.build();
  }
}
