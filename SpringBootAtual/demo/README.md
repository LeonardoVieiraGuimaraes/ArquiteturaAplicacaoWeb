# Roadmap de Segurança Spring Boot

## Separação de Configurações de Segurança por Contexto

**Objetivo:** Centralizar e organizar as regras de segurança em classes específicas, facilitando alternância entre ambientes e manutenção.

### Estrutura Recomendada

- `PublicSecurityConfig`: libera endpoints públicos (ex: ambiente de desenvolvimento).
- `KeycloakSecurityConfig`: protege endpoints via Keycloak (ex: ambiente de produção).
- `MethodSecurityConfig`: ativa segurança por métodos (@PreAuthorize, @Secured).

### Como implementar

1. **Criar classes de configuração separadas:**
   - Cada classe define um `SecurityFilterChain` específico.
2. **Usar `@Order` para prioridade dos filtros:**
   - Define qual configuração é aplicada primeiro.
3. **Usar `@Profile` para alternar conforme ambiente:**
   - `@Profile("dev")` para liberar tudo.
   - `@Profile("prod")` para proteger endpoints.
4. **Ativar segurança por métodos:**
   - Usar `@EnableMethodSecurity` em uma classe dedicada.

### Exemplo Futuro

```java
// PublicSecurityConfig.java
@Configuration
@EnableWebSecurity
@Order(1)
@Profile("dev")
public class PublicSecurityConfig {
    @Bean
    public SecurityFilterChain publicFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth.anyRequest().permitAll());
        return http.build();
    }
}

// KeycloakSecurityConfig.java
@Configuration
@EnableWebSecurity
@Order(2)
@Profile("prod")
public class KeycloakSecurityConfig {
    @Bean
    public SecurityFilterChain keycloakFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/livros/**").hasRole("USER")
                .requestMatchers("/autores/**").hasRole("ADMIN")
                .anyRequest().permitAll());
        return http.build();
    }
}

// MethodSecurityConfig.java
@Configuration
@EnableMethodSecurity(prePostEnabled = true)
public class MethodSecurityConfig {
    // Ativa @PreAuthorize/@Secured nos métodos
}
```

### Vantagens
- Organização clara por contexto.
- Alternância fácil entre ambientes.
- Prioridade de filtros controlada.
- Flexibilidade para crescer o projeto.

---

**Futuro:**
- Implementar as classes acima.
- Testar alternância entre ambientes.
- Documentar roles e permissões no Keycloak.
- Adicionar exemplos de uso de @PreAuthorize nos controllers/services.

---

> **Referência:**
> Este roadmap foi gerado para facilitar a evolução da segurança do projeto, conforme melhores práticas corporativas Spring Boot + Keycloak.
