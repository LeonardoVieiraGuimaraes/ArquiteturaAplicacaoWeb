# 🔐 Projeto de Segurança - Spring Security

Bem-vindo ao repositório de exemplos progressivos de **Spring Security**, parte do curso de Arquitetura de Aplicação Web da Newton Paiva. Este repositório demonstra conceitos fundamentais e avançados de segurança em aplicações Spring Boot, desde autenticação básica até OAuth2/JWT.

---

## 📋 Índice

1. [Visão Geral](#-visão-geral)
2. [Stack Tecnológica](#-stack-tecnológica)
3. [Pré-requisitos](#-pré-requisitos)
4. [Como Rodar os Exemplos](#-como-rodar-os-exemplos)
5. [Estrutura do Repositório](#-estrutura-do-repositório)
6. [Exemplos por Ordem de Complexidade](#-exemplos-por-ordem-de-complexidade)
   - [Exemplo 00 - CRUD Público com Endpoints Liberados](#exemplo-00---crud-público-com-endpoints-liberados)
   - [Exemplo 01 - Autenticação Básica com Spring Security Padrão](#exemplo-01---autenticação-básica-com-spring-security-padrão)
   - [Exemplo 02 - Login Customizado com Página Própria](#exemplo-02---login-customizado-com-página-própria)
   - [Exemplo 03 - Usuários em Memória com BCrypt](#exemplo-03---usuários-em-memória-com-bcrypt)
   - [Exemplo 04 - Autorização por Roles (RBAC)](#exemplo-04---autorização-por-roles-rbac)
   - [Exemplo 05 - Integração com H2 Console](#exemplo-05---integração-com-h2-console)
   - [Exemplo 06 - Persistência de Usuários com JPA](#exemplo-06---persistência-de-usuários-com-jpa)
   - [Exemplo 07 - UserDetailsService Customizado + Swagger/OpenAPI](#exemplo-07---userdetailsservice-customizado--swaggeropenapi)
   - [Exemplo 08 - Autorização Granular com @PreAuthorize](#exemplo-08---autorização-granular-com-preauthorize)
   - [Exemplo 09 - OAuth2 Resource Server (JWT stateless)](#exemplo-09---oauth2-resource-server-jwt-stateless)
   - [Exemplo 10 - OAuth2 Authorization Server](#exemplo-10---oauth2-authorization-server)
   - [JWT_RestAPI - API REST com JWT (JJWT)](#jwt_restapi---api-rest-com-jwt-jjwt)
7. [Conceitos Importantes](#-conceitos-importantes)
8. [Referências](#-referências)

---

## 🎯 Visão Geral

Este repositório contém uma série de **exemplos progressivos** que evoluem desde configurações básicas de Spring Security até implementações avançadas com OAuth2 e JWT. Cada exemplo adiciona novos conceitos, mantendo os anteriores como base, permitindo um aprendizado incremental.

**Objetivo pedagógico**: 
- Demonstrar a configuração e uso do Spring Security em diferentes cenários
- Evoluir de forma gradual do mais simples ao mais complexo
- Apresentar boas práticas de segurança em aplicações web modernas
- Comparar autenticação stateful (sessão) vs stateless (JWT/OAuth2)

---

## 🛠 Stack Tecnológica

### Comum a todos os exemplos:
- **Spring Boot**: 3.4.5 / 3.5.0 (variando por exemplo)
- **Java**: 24 (recomendado usar Java 21 LTS em produção)
- **Spring Security**: 6.x
- **Spring Data JPA**: Para persistência
- **H2 Database**: Banco em memória/arquivo (dev/testes)
- **Lombok**: Redução de boilerplate
- **Spring Boot DevTools**: Reload automático
- **Thymeleaf**: Renderização de templates (alguns exemplos)
- **Maven**: Gerenciamento de dependências

### Bibliotecas específicas por exemplo:
- **Spring Actuator** (exemplo01): Monitoramento de endpoints
- **SpringDoc OpenAPI** (exemplo07/08): Documentação Swagger
- **OAuth2 Resource Server** (exemplo09/10): Validação de JWT
- **OAuth2 Authorization Server** (exemplo10): Emissão de tokens OAuth2
- **JJWT (JWT_RestAPI)**: Biblioteca Auth0/jjwt para JWT customizado

---

## ✅ Pré-requisitos

Antes de rodar qualquer exemplo, certifique-se de ter:

- **JDK 21+** instalado ([Adoptium](https://adoptium.net/) ou [Oracle JDK](https://www.oracle.com/java/technologies/downloads/))
- **Maven 3.8+** (ou use o wrapper `mvnw` incluído em cada projeto)
- **IDE recomendada**: IntelliJ IDEA, Eclipse ou VS Code com extensões Java
- **Postman/Insomnia** (opcional, para testar APIs REST)

---

## 🚀 Como Rodar os Exemplos

Todos os exemplos seguem o mesmo padrão de execução:

### Via Maven Wrapper (recomendado):

```powershell
# Navegue até o diretório do exemplo desejado
cd exemplo00/demo

# Execute a aplicação
./mvnw spring-boot:run
```

### Via Maven instalado:

```powershell
cd exemplo00/demo
mvn spring-boot:run
```

### Via IDE:

1. Importe o projeto Maven na sua IDE
2. Localize a classe principal `DemoApplication.java`
3. Execute como "Java Application" ou "Spring Boot App"

### Acesso padrão:

- Aplicação: http://localhost:8080
- Console H2 (quando habilitado): http://localhost:8080/h2-console
  - JDBC URL: `jdbc:h2:mem:testdb` (ou `jdbc:h2:file:./data/demo-db` quando persistente)
  - Username: `sa`
  - Password: `password`

---

## 📁 Estrutura do Repositório

```
Seguranca/
├── exemplo00/          → CRUD público (sem segurança ativa)
├── exemplo01/          → Autenticação básica padrão
├── exemplo02/          → Login customizado
├── exemplo03/          → Usuários em memória
├── exemplo04/          → Autorização por roles
├── exemplo05/          → H2 Console habilitado
├── exemplo06/          → Persistência com JPA
├── exemplo07/          → UserDetailsService + Swagger
├── exemplo08/          → @PreAuthorize + Swagger
├── exemplo09/          → OAuth2 Resource Server (JWT)
├── exemplo10/          → OAuth2 Authorization Server
├── demo/               → Projeto de referência completo
├── JWT_RestAPI/        → JWT customizado com JJWT
├── hello/              → (Projeto auxiliar)
└── README.md           → Este arquivo
```

Cada subpasta contém um projeto Spring Boot independente com sua própria estrutura Maven.

---

## 📚 Exemplos por Ordem de Complexidade

### Exemplo 00 - CRUD Público com Endpoints Liberados

**📂 Diretório**: `exemplo00/demo`

**🎓 Conceitos**:
- CRUD básico de usuários, autores e livros
- Spring Security **presente** mas **endpoints públicos**
- BCryptPasswordEncoder configurado
- HTTP Basic e Form Login habilitados

**🔑 Características de Segurança**:
- `/api/hello` → público
- `/api/users/**` → público (CRUD completo de usuários)
- Qualquer outra rota → autenticada
- `UserDetailsService` em memória **comentado** (não ativo)

**🧪 Como Testar**:
```powershell
# Rodar aplicação
cd exemplo00/demo
./mvnw spring-boot:run

# Testar endpoint público
curl http://localhost:8080/api/hello

# Listar usuários (público)
curl http://localhost:8080/api/users

# Criar usuário (público, senha será criptografada)
curl -X POST http://localhost:8080/api/users -H "Content-Type: application/json" \
  -d '{"username":"joao","password":"senha123","enabled":true,"roles":["ROLE_USER"]}'
```

**📝 Observações**:
- Serve como baseline para adicionar segurança nos próximos exemplos
- UserRepository pronto para uso com JPA
- Modelo User com roles em `@ElementCollection`

---

### Exemplo 01 - Autenticação Básica com Spring Security Padrão

**📂 Diretório**: `exemplo01/demo`

**🎓 Conceitos**:
- Spring Security **sem customizações**
- Usuário/senha gerados automaticamente pelo Boot
- HTTP Basic e Form Login ativos
- **Spring Boot Actuator** habilitado

**🔑 Características de Segurança**:
- CSRF desabilitado (para testes)
- Todas as rotas exigem autenticação
- Credenciais padrão:
  - Username: `user`
  - Password: gerada automaticamente no console (procure `Using generated security password: ...`)

**🧪 Como Testar**:
```powershell
cd exemplo01/demo
./mvnw spring-boot:run

# Veja a senha no console:
# "Using generated security password: abc123xyz..."

# Testar com HTTP Basic
curl -u user:abc123xyz http://localhost:8080/api/hello

# Ou acesse no navegador (popup de login aparecerá)
# http://localhost:8080
```

**📝 Observações**:
- Exemplo mínimo de Spring Security "zero config"
- Útil para entender comportamento padrão
- Actuator endpoints disponíveis em `/actuator`

---

### Exemplo 02 - Login Customizado com Página Própria

**📂 Diretório**: `exemplo02/demo`

**🎓 Conceitos**:
- Página de login customizada (`/login`)
- Template Thymeleaf `login.html`
- Configuração de `.loginPage()` no SecurityFilterChain

**🔑 Características de Segurança**:
- Página de login personalizada
- `/login` → público
- Demais rotas → autenticadas
- Credenciais ainda geradas automaticamente (ou configuradas via `application.properties`)

**🧪 Como Testar**:
```powershell
cd exemplo02/demo
./mvnw spring-boot:run

# Acesse no navegador
http://localhost:8080/login
```

**📝 Observações**:
- Introduz customização visual de login
- Base para adicionar usuários fixos no próximo exemplo

---

### Exemplo 03 - Usuários em Memória com BCrypt

**📂 Diretório**: `exemplo03/demo`

**🎓 Conceitos**:
- `InMemoryUserDetailsManager` com usuários fixos
- `BCryptPasswordEncoder` para criptografia de senhas
- Criação de usuários via `User.builder()`

**🔑 Características de Segurança**:
- **Usuários configurados**:
  - `user` / `123` → ROLE_USER
  - `admin` / `4321` → ROLE_ADMIN
- Senhas criptografadas com BCrypt
- Login customizado ativo

**🧪 Como Testar**:
```powershell
cd exemplo03/demo
./mvnw spring-boot:run

# Login com HTTP Basic
curl -u user:123 http://localhost:8080/api/hello
curl -u admin:4321 http://localhost:8080/api/hello

# Ou pelo navegador: http://localhost:8080/login
```

**📝 Observações**:
- Primeira implementação de usuários "reais" (não gerados)
- Senhas hardcoded, não recomendado para produção
- Base para autorização por roles

---

### Exemplo 04 - Autorização por Roles (RBAC)

**📂 Diretório**: `exemplo04/demo`

**🎓 Conceitos**:
- **Role-Based Access Control (RBAC)**
- Regras específicas por HTTP method (GET, POST, PUT, DELETE)
- `@EnableMethodSecurity` para uso de anotações

**🔑 Características de Segurança**:
- **Usuários**:
  - `user` / `123` → ROLE_USER
  - `admin` / `431` → ROLE_ADMIN
- **Regras de autorização**:
  - POST/PUT/DELETE em `/api/books` e `/api/authors` → ROLE_ADMIN
  - GET em `/api/books` e `/api/authors` → público
  - `/api/hello` → ROLE_ADMIN ou ROLE_USER
  - `/api/users/**` → ROLE_ADMIN
  - `/login` → público

**🧪 Como Testar**:
```powershell
cd exemplo04/demo
./mvnw spring-boot:run

# Como USER (403 Forbidden ao tentar criar livro)
curl -u user:123 -X POST http://localhost:8080/api/books \
  -H "Content-Type: application/json" \
  -d '{"title":"Java 101","author":"John Doe"}'

# Como ADMIN (sucesso)
curl -u admin:431 -X POST http://localhost:8080/api/books \
  -H "Content-Type: application/json" \
  -d '{"title":"Java 101","author":"John Doe"}'

# Listar livros (público)
curl http://localhost:8080/api/books
```

**📝 Observações**:
- Introduz `.hasAuthority()` e `.hasAnyAuthority()`
- Distinção entre roles USER e ADMIN
- Base para controle granular

---

### Exemplo 05 - Integração com H2 Console

**📂 Diretório**: `exemplo05/demo`

**🎓 Conceitos**:
- Console H2 habilitado e liberado na configuração de segurança
- Framesets permitidos (`frameOptions().disable()`)
- Persistência em memória

**🔑 Características de Segurança**:
- `/h2-console/**` → público
- Demais regras idênticas ao exemplo04
- Headers de frame desabilitados para permitir iframe do H2

**🧪 Como Testar**:
```powershell
cd exemplo05/demo
./mvnw spring-boot:run

# Acesse o console H2
http://localhost:8080/h2-console

# Conexão:
# JDBC URL: jdbc:h2:mem:testdb
# Username: sa
# Password: password
```

**📝 Observações**:
- Console H2 útil para debug de dados
- **Não habilitar em produção** (risco de segurança)
- Adiciona regra específica para `/h2-console/**`

---

### Exemplo 06 - Persistência de Usuários com JPA

**📂 Diretório**: `exemplo06/demo`

**🎓 Conceitos**:
- `UserRepository` JPA ativo
- Entidade `User` com `@ElementCollection` para roles
- `CustomUserDetailsService` implementando `UserDetailsService`
- Usuários armazenados no banco H2

**🔑 Características de Segurança**:
- **Autenticação**: via `CustomUserDetailsService` que consulta o banco
- **Persistência**: usuários salvos em H2 (memória ou arquivo)
- **BCryptPasswordEncoder**: senhas criptografadas no banco
- Possibilidade de cadastrar novos usuários via API

**🧪 Como Testar**:
```powershell
cd exemplo06/demo
./mvnw spring-boot:run

# Criar usuário via API (ADMIN pode criar)
curl -u admin:431 -X POST http://localhost:8080/api/users \
  -H "Content-Type: application/json" \
  -d '{"username":"maria","password":"senha","enabled":true,"roles":["ROLE_USER"]}'

# Login com o novo usuário
curl -u maria:senha http://localhost:8080/api/hello
```

**📝 Observações**:
- Primeira implementação com banco de dados real
- `CustomUserDetailsService` carrega usuários do banco
- Roles armazenadas na tabela `authorities`

---

### Exemplo 07 - UserDetailsService Customizado + Swagger/OpenAPI

**📂 Diretório**: `exemplo07/demo`

**🎓 Conceitos**:
- **SpringDoc OpenAPI** (Swagger UI)
- Documentação automática de APIs REST
- `CustomUserDetailsService` refinado
- **README.md** com tabela Stateful vs Stateless

**🔑 Características de Segurança**:
- Autenticação via `CustomUserDetailsService`
- Mesmas regras de autorização do exemplo06
- Swagger UI documentando endpoints protegidos

**🧪 Como Testar**:
```powershell
cd exemplo07/demo
./mvnw spring-boot:run

# Acessar Swagger UI
http://localhost:8080/swagger-ui.html

# OpenAPI JSON
http://localhost:8080/v3/api-docs
```

**📝 Observações**:
- Swagger útil para testar APIs interativamente
- README.md local explica diferença entre stateful/stateless
- Adiciona `springdoc-openapi-starter-webmvc-ui` (versão 2.8.8)

---

### Exemplo 08 - Autorização Granular com @PreAuthorize

**📂 Diretório**: `exemplo08/demo`

**🎓 Conceitos**:
- **@PreAuthorize** nos controllers (autorização em nível de método)
- `@EnableMethodSecurity` na config
- SpEL (Spring Expression Language) para regras complexas
- Swagger UI ativo

**🔑 Características de Segurança**:
- Autorização movida para anotações nos métodos dos controllers
- Exemplo de `@PreAuthorize("hasRole('ADMIN')")` ou `@PreAuthorize("hasAnyRole('USER','ADMIN')")`
- Regras mais expressivas e centralizadas no próprio método

**🧪 Como Testar**:
```powershell
cd exemplo08/demo
./mvnw spring-boot:run

# Testar endpoint protegido com @PreAuthorize
curl -u admin:431 http://localhost:8080/api/books/1
curl -u user:123 http://localhost:8080/api/books/1
# (resultado depende da anotação aplicada)
```

**📝 Observações**:
- Autorização mais declarativa e legível
- Facilita manutenção de regras complexas
- Swagger mostra quais endpoints existem (mas não mostra roles)

---

### Exemplo 09 - OAuth2 Resource Server (JWT stateless)

**📂 Diretório**: `exemplo09/demo`

**🎓 Conceitos**:
- **OAuth2 Resource Server**
- Validação de **JWT (JSON Web Token)**
- Autenticação **stateless** (sem sessão HTTP)
- `spring-boot-starter-oauth2-resource-server` e `spring-security-oauth2-jose`
- **README.md** com documentação de fluxo OAuth2

**🔑 Características de Segurança**:
- `.oauth2ResourceServer(oauth2 -> oauth2.jwt(Customizer.withDefaults()))`
- Tokens JWT validados automaticamente
- Chave secreta/pública configurada para validação
- Roles extraídas do JWT

**🧪 Como Testar**:
```powershell
cd exemplo09/demo
./mvnw spring-boot:run

# 1. Obter JWT via /auth/login (endpoint customizado)
curl -X POST http://localhost:8080/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username":"admin","password":"431"}'

# Resposta: {"token":"eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."}

# 2. Usar o token para acessar endpoints protegidos
curl -H "Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..." \
  http://localhost:8080/api/users
```

**📝 Observações**:
- **Stateless**: servidor não mantém sessão
- Escalabilidade: qualquer instância pode validar o JWT
- JWT contém roles/claims do usuário
- Banco: H2 em arquivo (`./data/demo-db`) para persistência entre reinicializações
- `AuthController` implementa login customizado retornando JWT
- Requer configuração de chave secreta (via `application.yml`)

---

### Exemplo 10 - OAuth2 Authorization Server

**📂 Diretório**: `exemplo10/demo`

**🎓 Conceitos**:
- **OAuth2 Authorization Server**
- Emissão de tokens JWT
- Fluxo completo: autenticação → token → validação
- `spring-boot-starter-oauth2-authorization-server`
- Suporte a **Client Credentials**, **Authorization Code**, etc.

**🔑 Características de Segurança**:
- Servidor age como **Authorization Server** e **Resource Server** simultaneamente
- Emite JWT assinados
- Configura clientes OAuth2 (client_id, client_secret, scopes)
- Valida tokens emitidos por ele mesmo

**🧪 Como Testar**:
```powershell
cd exemplo10/demo
./mvnw spring-boot:run

# 1. Login via endpoint customizado (retorna JWT)
curl -X POST http://localhost:8080/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username":"admin","password":"431"}'

# Resposta: {"token":"eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9..."}

# 2. Usar o token
curl -H "Authorization: Bearer <TOKEN>" http://localhost:8080/api/users

# 3. (Opcional) Fluxo OAuth2 padrão com curl
# (requer configuração de client_id/client_secret)
```

**📝 Observações**:
- Mais complexo: ideal para microsserviços
- Pode ser separado em Authorization Server e Resource Server distintos
- Usa chaves assimétricas (RSA/ECDSA) em produção
- `AuthorizationServerConfig` define clientes OAuth2
- Banco: H2 em arquivo para persistir usuários e roles

---

### Demo - Projeto Completo de Referência

**📂 Diretório**: `demo`

**🎓 Conceitos**:
- Projeto de referência **mais completo**
- Integra conceitos de vários exemplos
- CRUD de usuários, autores e livros
- Views Thymeleaf + API REST
- Configuração comentada (código didático)

**🔑 Características de Segurança**:
- `SecurityConfig` com várias opções comentadas
- `/api/hello` → público
- `/api/users/**` → público (para fins de teste)
- HTTP Basic e Form Login
- BCryptPasswordEncoder
- UserDetailsService em memória **comentado**
- JPA pronto para uso

**🧪 Como Testar**:
```powershell
cd demo
./mvnw spring-boot:run

# Endpoints públicos
curl http://localhost:8080/api/hello
curl http://localhost:8080/api/users
```

**📝 Observações**:
- Código altamente comentado para fins didáticos
- Serve como template para projetos reais
- Mistura REST + MVC (Thymeleaf)
- Console H2 habilitado

---

### JWT_RestAPI - API REST com JWT (JJWT)

**📂 Diretório**: `JWT_RestAPI`

**🎓 Conceitos**:
- JWT implementado com **JJWT (io.jsonwebtoken)**
- Filtro customizado (`JwtAuthenticationFilter`)
- Geração e validação manual de tokens
- API REST pura (sem views)

**🔑 Características de Segurança**:
- **Biblioteca**: JJWT 0.12.3
- `JwtUtil` para criar e validar tokens
- Filtro intercepta requisições e valida JWT no header `Authorization: Bearer <token>`
- Configuração manual de chave secreta

**🧪 Como Testar**:
```powershell
cd JWT_RestAPI
./mvnw spring-boot:run

# 1. Login (obter token)
curl -X POST http://localhost:8080/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username":"admin","password":"password"}'

# Resposta: {"token":"eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."}

# 2. Usar token
curl -H "Authorization: Bearer <TOKEN>" http://localhost:8080/api/secure
```

**📝 Observações**:
- Abordagem **alternativa** ao OAuth2 Resource Server
- Mais controle sobre geração/validação de tokens
- Spring Boot 3.2.1 (mais antigo que os outros exemplos)
- Java 17 (ao invés de 24)
- Útil para entender "como funciona por baixo" antes de usar OAuth2

---

## 💡 Conceitos Importantes

### Stateful vs Stateless

| Característica                | **Stateful** (Sessão)                        | **Stateless** (JWT/Token)                     |
|-------------------------------|----------------------------------------------|-----------------------------------------------|
| Armazenamento de estado       | Servidor (sessão HTTP)                       | Cliente (JWT no header)                       |
| Escalabilidade                | Menor (sticky sessions)                      | Maior (qualquer servidor valida)              |
| Performance                   | Overhead de gerenciar sessões                | Leve, sem sessões                             |
| Controle de logout            | Fácil (invalidar sessão)                     | Difícil (blacklist ou expiração)              |
| Exemplo                       | Spring Security com HttpSession              | OAuth2 Resource Server, JWT                   |
| Uso recomendado               | Apps monolíticas, admin internos             | APIs REST, microsserviços, SPAs               |

**Exemplos Stateful**: 00 a 08  
**Exemplos Stateless**: 09, 10, JWT_RestAPI

---

### Fluxo de Autenticação/Autorização

1. **Autenticação**: Verificar identidade (quem é o usuário?)
   - Form Login, HTTP Basic, OAuth2, JWT
2. **Autorização**: Verificar permissões (o que o usuário pode fazer?)
   - Roles, authorities, `@PreAuthorize`, `.hasRole()`, `.hasAuthority()`

---

### Boas Práticas

✅ **Use BCrypt ou Argon2** para criptografar senhas  
✅ **Habilite HTTPS** em produção (TLS/SSL)  
✅ **Não exponha console H2** em produção  
✅ **Use JWT stateless** para APIs REST escaláveis  
✅ **Valide expiração de tokens** JWT  
✅ **Não armazene senhas em plain text** (nem no código)  
✅ **Use Java 21 LTS** em produção (ao invés de 24)  
✅ **Habilite CSRF** se usar sessões (stateful)  
✅ **Audite logs de autenticação/autorização**

---

## 🔧 Troubleshooting

### Erro: "Using generated security password"
- Isso ocorre quando não há `UserDetailsService` configurado. Configure usuários em memória ou no banco.

### Erro 403 Forbidden
- Verifique se o usuário tem a role necessária
- Verifique se o endpoint está configurado corretamente no `SecurityFilterChain`

### Console H2 não abre (blank page)
- Adicione `http.headers(headers -> headers.frameOptions(frame -> frame.disable()));` na config

### JWT inválido ou expirado
- Verifique a chave secreta (`jwt.secret`) no `application.yml`
- Verifique se o token não expirou (claim `exp`)

### Maven build falha
- Certifique-se de usar Java 21+ (alguns exemplos usam Java 24, mas 21 LTS é mais estável)
- Rode `./mvnw clean install` para limpar e recompilar

---

## 📖 Referências

### Documentação Oficial:
- [Spring Security Reference](https://docs.spring.io/spring-security/reference/index.html)
- [Spring Boot Security](https://spring.io/guides/gs/securing-web/)
- [OAuth2 Resource Server](https://docs.spring.io/spring-security/reference/servlet/oauth2/resource-server/index.html)
- [JWT Introduction](https://jwt.io/introduction)
- [SpringDoc OpenAPI](https://springdoc.org/)

### Artigos e Tutoriais:
- [Baeldung - Spring Security](https://www.baeldung.com/spring-security)
- [Baeldung - JWT with Spring](https://www.baeldung.com/spring-security-oauth-jwt)
- [Spring Security Architecture](https://spring.io/guides/topicals/spring-security-architecture/)

### Livros:
- **Spring Security in Action** (Laurențiu Spilcă)
- **OAuth 2 in Action** (Justin Richer)

---

## 👨‍💻 Autor

**Professor Leonardo Vieira Guimarães**  
Newton Paiva - Arquitetura de Aplicação Web  
📧 leonardo.guimaraes@newtonpaiva.br  
📅 Última atualização: Outubro de 2025

---

## 📄 Licença

Este repositório é de uso **educacional** e está disponível para alunos da Newton Paiva e comunidade acadêmica.  
Sinta-se livre para clonar, modificar e aprender! 🚀

---

## 🎓 Como Navegar neste Repositório

**Iniciante?** Comece pelo [Exemplo 01](#exemplo-01---autenticação-básica-com-spring-security-padrão) e avance sequencialmente.

**Procurando JWT?** Vá direto para [Exemplo 09](#exemplo-09---oauth2-resource-server-jwt-stateless) ou [JWT_RestAPI](#jwt_restapi---api-rest-com-jwt-jjwt).

**Quer OAuth2 completo?** Veja [Exemplo 10](#exemplo-10---oauth2-authorization-server).

**Precisa de referência?** Use o [Demo](#demo---projeto-completo-de-referência) como template.

---

**Bons estudos e bons códigos! 🚀🔐**

Exemplo mínimo `sonar-project.properties` (quando usar scanner standalone):

```
sonar.projectKey=meu-projeto
sonar.projectName=Meu Projeto
sonar.sources=src/main/java
sonar.tests=src/test/java
sonar.java.binaries=target/classes
sonar.host.url=https://seu-sonarqube
sonar.login=SEU_TOKEN
```

Recomendações SonarQube:
- Configure Quality Gates (p.ex. cobertura mínima, zero vulnerabilidades críticas).
- Integre a análise na pipeline CI (executar após testes e geração dos relatórios de cobertura).

---

## JaCoCo (Cobertura de testes)

JaCoCo é um plugin de cobertura para JVM que gera relatórios (HTML e XML). O SonarQube consome o relatório XML para calcular métricas de cobertura.

Maven (exemplo no `pom.xml` — snippet):

```xml
<build>
  <plugins>
    <plugin>
      <groupId>org.jacoco</groupId>
      <artifactId>jacoco-maven-plugin</artifactId>
      <version>0.8.8</version>
      <executions>
        <execution>
          <goals>
            <goal>prepare-agent</goal>
          </goals>
        </execution>
        <execution>
          <id>report</id>
          <phase>verify</phase>
          <goals>
            <goal>report</goal>
          </goals>
        </execution>
        <!-- Gera o XML para SonarQube -->
        <execution>
          <id>report-xml</id>
          <phase>verify</phase>
          <goals>
            <goal>report</goal>
          </goals>
          <configuration>
            <reports>
              <report>xml</report>
            </reports>
          </configuration>
        </execution>
      </executions>
    </plugin>
  </plugins>
</build>
```

Comando (Windows PowerShell):

```
mvn clean verify
```

O relatório XML normalmente fica em `target/site/jacoco/jacoco.xml` ou `target/jacoco.exec` dependendo da configuração; configure o Sonar para apontar para o arquivo XML.

Gradle (exemplo `build.gradle`):

```groovy
plugins {
  id 'java'
  id 'jacoco'
}

jacoco {

  toolVersion = '0.8.8'

---

## Testes de carga — Gatling

Gatling é uma ferramenta popular para testes de carga e performance em JVM. Use Gatling para validar comportamento sob alto tráfego, medir tempos de resposta e detectar gargalos.

Pontos principais:
- Crie simulações que representem cenários reais (autenticação, caminhos críticos, picos).
- Execute Gatling em um job separado na pipeline de CI para não atrasar o build principal.
- Armazene os relatórios HTML/CSV/JSON como artefatos do job para investigação.

Exemplo (Maven) — plugin mínimo no `pom.xml`:

```xml
<plugin>
  <groupId>io.gatling</groupId>
  <artifactId>gatling-maven-plugin</artifactId>
  <version>3.9.6</version>
  <executions>
    <execution>
      <goals>
        <goal>test</goal>
      </goals>
    </execution>
  </executions>
</plugin>
```

Comando (Windows PowerShell):

```
mvn -Dgatling.simulationClass=com.meuprojeto.simulations.BasicSimulation gatling:test
```

Os relatórios normalmente ficam em `target/gatling/<simulation-name>-<timestamp>`.

Gradle (plugin):

```groovy
plugins {
  id 'io.gatling.gradle' version '3.9.6'
}

// Exemplo de execução
// ./gradlew gatlingRun -DgatlingSimulation=com.meuprojeto.simulations.BasicSimulation
```

Integração com Sonar/JaCoCo:
- Gatling não gera métricas de cobertura de código; use-o junto com testes unitários para cobertura. Use Gatling para performance e JaCoCo para coverage.
- Execute Gatling após a fase de testes unitários/integrados; guarde relatórios para análise de performance.

---

## Testes unitários e mocks — JUnit + Mockito

JUnit (preferencialmente JUnit 5 - Jupiter) e Mockito são a base para testes unitários em projetos Java.

Pontos principais:
- Escreva testes unitários rápidos e determinísticos com JUnit.
- Use Mockito para mocks e stubs (serviços externos, repositórios, clientes HTTP).
- Execute os testes em cada build; JaCoCo capturará a cobertura.

Exemplo (Maven) — dependências mínimas no `pom.xml`:

```xml
<dependencies>
  <!-- JUnit 5 -->
  <dependency>
    <groupId>org.junit.jupiter</groupId>
    <artifactId>junit-jupiter</artifactId>
    <version>5.9.3</version>
    <scope>test</scope>
  </dependency>
  <!-- Mockito -->
  <dependency>
    <groupId>org.mockito</groupId>
    <artifactId>mockito-core</artifactId>
    <version>5.2.0</version>
    <scope>test</scope>
  </dependency>
</dependencies>
```

Exemplo simples de teste com Mockito (JUnit 5):

```java
@ExtendWith(MockitoExtension.class)
class UsuarioServiceTest {

  @Mock
  private UsuarioRepository repo;

  @InjectMocks
  private UsuarioService service;

  @Test
  void deveRetornarUsuarioQuandoExistir() {
    when(repo.findById(1L)).thenReturn(Optional.of(new Usuario(1L, "Leo")));
    var u = service.findById(1L);
    assertNotNull(u);
    assertEquals("Leo", u.getNome());
  }
}
```

Comando (Windows PowerShell) para rodar testes e gerar cobertura:

```
mvn clean verify
```

JaCoCo e Sonar
- JaCoCo instrumenta testes e gera o XML que o Sonar consome para métricas de cobertura.
- Configure o Sonar para apontar ao `jacoco.xml` conforme mostrado na seção do JaCoCo.

---

## Como combinar tudo numa pipeline (sugestão)

Job 1 — Build & Unit Tests
- mvn clean verify (executa testes JUnit/Mockito e gera relatório JaCoCo)

Job 2 — Análise estática
- Executa SonarQube scanner apontando para o relatório JaCoCo XML.

Job 3 — Testes de carga (opcional)
- Executa Gatling em uma máquina dedicada ou runner; armazene relatórios como artefatos.

Exemplo resumido de execução Sonar após cobertura (Maven):

```
mvn clean verify sonar:sonar -Dsonar.host.url=https://seu-sonarqube -Dsonar.login=SEU_TOKEN -Dsonar.coverage.jacoco.xmlReportPaths=target/site/jacoco/jacoco.xml
```

---

Se quiser, eu posso:
- Gerar um `pom.xml` exemplo que junte JaCoCo, dependências de teste (JUnit/Mockito) e o plugin do Gatling.
- Criar um workflow GitHub Actions que execute os 3 jobs (build/test -> sonar -> gatling) e publique artefatos.

---

Fim do documento.

````
}

tasks.test {
  finalizedBy tasks.jacocoTestReport
}

jacocoTestReport {
  reports {
    xml.required = true
    html.required = true
  }
}
```

Comando (Windows PowerShell):

```
./gradlew clean test jacocoTestReport
```

Integração JaCoCo → SonarQube

- Configure Sonar para ler o relatório XML gerado pelo JaCoCo. Propriedade comum:

```
-Dsonar.coverage.jacoco.xmlReportPaths=target/site/jacoco/jacoco.xml
```

ou, para Gradle, o caminho para `build/reports/jacoco/test/jacocoTestReport.xml`.

Exemplo de execução Sonar após gerar cobertura (Maven):

```
mvn clean verify sonar:sonar -Dsonar.host.url=https://seu-sonarqube -Dsonar.login=SEU_TOKEN -Dsonar.coverage.jacoco.xmlReportPaths=target/site/jacoco/jacoco.xml
```

Observação: em versões mais novas do SonarQube, a propriedade pode ser `sonar.coverage.jacoco.xmlReportPaths` ou `sonar.java.coveragePlugin=jacoco`; confira a documentação da versão que você usa.

---

## Exemplo de pipeline CI mínima (conceito)

1. Checkout do código
2. Build + testes (mvn clean verify ou ./gradlew test)
3. Gerar relatório JaCoCo (XML)
4. Executar SonarQube Scanner apontando para o relatório XML
5. Falhar o pipeline se o Quality Gate do Sonar falhar

## Boas práticas de integração

- Execute a análise Sonar com um token de CI (não commitado). Use variáveis de ambiente no CI.
- Defina limites razoáveis de cobertura (ex.: 80%) e vá melhorando gradualmente.
- Bloqueie merges com Quality Gate falhando.
- Priorize correções de vulnerabilidades e bugs críticos.

---

## Recursos e leitura adicional

- OWASP Top 10: https://owasp.org/www-project-top-ten/
- SonarQube: https://www.sonarqube.org/
- JaCoCo: https://www.jacoco.org/jacoco/

---

## Observações finais

Este documento é um guia rápido. Para cada projeto, ajuste as versões dos plugins, caminhos dos relatórios e políticas de qualidade conforme a necessidade. Se quiser, eu adapto este README para incluir exemplos específicos do seu build (pom.xml ou build.gradle) e para gerar um exemplo de pipeline (GitHub Actions / Azure DevOps / GitLab CI).
