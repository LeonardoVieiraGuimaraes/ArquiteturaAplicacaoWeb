# 🔐 Segurança em Aplicações Spring Boot

Guia completo sobre implementação de segurança em aplicações Java com Spring Security, cobrindo desde autenticação básica até OAuth2, JWT e integração com Keycloak.

## 📑 Índice

- [🎯 Visão Geral](#-visão-geral)
- [📁 Estrutura do Módulo](#-estrutura-do-módulo)
- [🗺️ Trilha de Aprendizagem](#️-trilha-de-aprendizagem)
- [📚 Exemplos Práticos](#-exemplos-práticos)
  - [Nível 1: Fundamentos](#nível-1-fundamentos-exemplo00-exemplo03)
  - [Nível 2: Autenticação Avançada](#nível-2-autenticação-avançada-exemplo04-exemplo07)
  - [Nível 3: OAuth2 e JWT](#nível-3-oauth2-e-jwt-exemplo08-exemplo11)
  - [Projetos Completos](#projetos-completos)
- [🔑 Conceitos Fundamentais](#-conceitos-fundamentais)
- [🛠️ Ferramentas e Tecnologias](#️-ferramentas-e-tecnologias)
- [🚀 Início Rápido](#-início-rápido)
- [📋 Pré-requisitos](#-pré-requisitos)
- [🧪 Testando Aplicações Seguras](#-testando-aplicações-seguras)
- [🆘 Troubleshooting Comum](#-troubleshooting-comum)
- [💡 Boas Práticas de Segurança](#-boas-práticas-de-segurança)
- [📖 Referências e Recursos](#-referências-e-recursos)

## 🎯 Visão Geral

Este módulo apresenta uma progressão didática completa sobre segurança em aplicações Spring Boot, desde conceitos básicos até implementações enterprise com OAuth2, JWT e Keycloak.

### O que você vai aprender:

- ✅ **Autenticação** básica (HTTP Basic, Form Login)
- ✅ **Autorização** baseada em roles (RBAC)
- ✅ **Spring Security** configuration e filter chain
- ✅ **JWT** (JSON Web Tokens) para APIs stateless
- ✅ **OAuth2** Resource Server e Authorization Server
- ✅ **Keycloak** integração e configuração
- ✅ **Criptografia** de senhas (BCrypt)
- ✅ **Proteção** contra CSRF, XSS, SQL Injection
- ✅ **Code Quality** com SonarQube e JaCoCo

### Público-Alvo:

- Desenvolvedores iniciantes em Spring Security
- Estudantes de arquitetura de aplicações web
- Profissionais buscando implementar autenticação/autorização
- Equipes migrando para OAuth2/JWT

## 📁 Estrutura do Módulo

```
Seguranca/
│
├── README.md                          # Este arquivo - Índice geral
│
├── exemplo00/                         # 🟢 Hello World + Security
│   └── demo/                          # Primeiro contato com Spring Security
│
├── exemplo01/                         # 🟢 HTTP Basic Authentication
│   └── demo/                          # Autenticação básica com usuários em memória
│
├── exemplo02/                         # 🟢 Form Login + Roles
│   └── demo/                          # Login por formulário e autorização
│
├── exemplo03/                         # 🟢 Custom UserDetailsService
│   └── demo/                          # Usuários do banco de dados (H2)
│
├── exemplo04/                         # 🟡 Method Security (@PreAuthorize)
│   └── demo/                          # Segurança em nível de método
│
├── exemplo05/                         # 🟡 Custom Security Filter
│   └── demo/                          # Criando filtros de segurança customizados
│
├── exemplo06/                         # 🟡 Password Encoding
│   └── demo/                          # BCrypt e gestão de senhas
│
├── exemplo07/                         # 🟡 CORS Configuration
│   └── demo/                          # Cross-Origin Resource Sharing
│
├── exemplo08/                         # 🔴 JWT Introduction
│   └── demo/                          # Tokens JWT básicos
│
├── exemplo09/                         # 🔴 JWT + Custom Login
│   └── demo/                          # Autenticação JWT completa
│
├── exemplo10/                         # 🔴 OAuth2 Resource + Auth Server
│   └── demo/                          # Servidor de autorização integrado
│
├── exemplo11/                         # 🔴 Keycloak Integration
│   └── demo/                          # Resource Server com Keycloak
│
├── JWT_RestAPI/                       # 🔵 Projeto JWT Completo
│   └── README.md                      # API REST com JWT documentada
│
├── spring-security-keycloak-main/     # 🔵 Keycloak OAuth2 + OIDC
│   └── README.md                      # Integração completa Keycloak
│
├── aulao-spring-security-main/        # 🔵 Material de Aula
│   └── README.md                      # Exemplos didáticos consolidados
│
└── client-credentials-main/           # 🔵 OAuth2 Client Credentials Flow
    └── README.md                      # Comunicação serviço-a-serviço
```

### Legenda de Dificuldade:
- 🟢 **Iniciante**: Conceitos básicos, ideal para começar
- 🟡 **Intermediário**: Requer conhecimento dos fundamentos
- 🔴 **Avançado**: JWT, OAuth2, tokens complexos
- 🔵 **Projetos Completos**: Aplicações prontas para estudo

## 🗺️ Trilha de Aprendizagem

### 📅 Semana 1: Fundamentos de Segurança
**Foco**: Entender autenticação e autorização básica

| Dia | Exemplo | Tópico | Tempo |
|-----|---------|--------|-------|
| 1 | exemplo00 | Hello World + Spring Security | 2h |
| 2 | exemplo01 | HTTP Basic Authentication | 2h |
| 3 | exemplo02 | Form Login + Roles | 3h |
| 4 | exemplo03 | UserDetailsService + Database | 3h |
| 5 | Revisão | Exercícios práticos e testes | 2h |

**Entregável**: API REST com login e controle de acesso por role

### 📅 Semana 2: Recursos Avançados
**Foco**: Filtros, encoding e CORS

| Dia | Exemplo | Tópico | Tempo |
|-----|---------|--------|-------|
| 1 | exemplo04 | Method Security (@PreAuthorize) | 2h |
| 2 | exemplo05 | Custom Security Filters | 3h |
| 3 | exemplo06 | Password Encoding (BCrypt) | 2h |
| 4 | exemplo07 | CORS Configuration | 2h |
| 5 | Projeto | Integrar todos os conceitos | 3h |

**Entregável**: API com autenticação completa e CORS configurado

### 📅 Semana 3: JWT e OAuth2
**Foco**: Tokens e autenticação stateless

| Dia | Exemplo | Tópico | Tempo |
|-----|---------|--------|-------|
| 1 | exemplo08 | JWT Básico | 3h |
| 2 | exemplo09 | JWT + Custom Login | 4h |
| 3 | exemplo10 | OAuth2 Auth Server | 4h |
| 4 | JWT_RestAPI | Projeto JWT Completo | 3h |
| 5 | Revisão | Testes e debugging | 2h |

**Entregável**: API stateless com JWT

### 📅 Semana 4: Keycloak e Produção
**Foco**: Identity Provider enterprise

| Dia | Exemplo | Tópico | Tempo |
|-----|---------|--------|-------|
| 1 | exemplo11 | Keycloak Resource Server | 4h |
| 2 | spring-security-keycloak | OAuth2 + OIDC | 3h |
| 3 | client-credentials | Service-to-Service | 3h |
| 4 | Qualidade | SonarQube + JaCoCo | 2h |
| 5 | Deploy | Preparação para produção | 4h |

**Entregável**: Sistema completo com Keycloak

## 📚 Exemplos Práticos

### Nível 1: Fundamentos (exemplo00-exemplo03)

#### 🟢 [Exemplo00: Hello World + Security](exemplo00/README.md)
**Objetivo**: Primeiro contato com Spring Security

**O que você vai aprender**:
- Como adicionar Spring Security ao projeto
- Configuração básica de segurança
- Endpoint protegido vs público
- Usuário padrão do Spring Security

**Tecnologias**:
- Spring Boot 3.4.3
- Spring Security
- Spring Web

**Como executar**:
```powershell
cd exemplo00\demo
.\mvnw.cmd spring-boot:run
# Acesse: http://localhost:8080/hello
# User: user / Password: (check console)
```

---

#### 🟢 [Exemplo01: HTTP Basic Authentication](exemplo01/README.md)
**Objetivo**: Implementar autenticação HTTP Basic

**O que você vai aprender**:
- Configurar usuários em memória
- HTTP Basic Auth headers
- Role-based access control (RBAC)
- SecurityFilterChain customizada

**Tecnologias**:
- Spring Security (InMemoryUserDetailsManager)
- BCryptPasswordEncoder
- Postman/curl para testes

**Endpoints**:
- `GET /api/hello` - Requer ADMIN ou USER
- `GET /public` - Público

**Como testar**:
```powershell
# Com credenciais
curl -u joao:4321 http://localhost:8080/api/hello

# Admin only
curl -u admin:1234 http://localhost:8080/admin
```

---

#### 🟢 [Exemplo02: Form Login + Roles](exemplo02/README.md)
**Objetivo**: Login via formulário web

**O que você vai aprender**:
- Form-based authentication
- Página de login customizada
- Logout handling
- Session management
- Authorization por role

**Tecnologias**:
- Spring Security Form Login
- Thymeleaf (opcional)
- Session cookies

**Recursos**:
- Login page: `/login`
- Logout: `/logout`
- Home protegida: `/home`

---

#### 🟢 [Exemplo03: Custom UserDetailsService](exemplo03/README.md)
**Objetivo**: Carregar usuários do banco de dados

**O que você vai aprender**:
- Implementar `UserDetailsService`
- Entity User + UserRepository
- Integração com H2 Database
- Password encoding
- UserDetails custom

**Tecnologias**:
- Spring Data JPA
- H2 Database
- BCryptPasswordEncoder
- Lombok

**Modelo de dados**:
```sql
User:
- id (Long)
- username (String)
- password (String) -- hashed
- roles (String) -- comma-separated
```

---

### Nível 2: Autenticação Avançada (exemplo04-exemplo07)

#### 🟡 [Exemplo04: Method Security](exemplo04/README.md)
**Objetivo**: Segurança em nível de método

**O que você vai aprender**:
- Anotação `@PreAuthorize`
- `@PostAuthorize` e `@Secured`
- `@EnableMethodSecurity`
- SpEL expressions para autorização
- Testes de método seguro

**Exemplo de código**:
```java
@PreAuthorize("hasRole('ADMIN')")
public void deleteUser(Long id) {
    userRepository.deleteById(id);
}

@PreAuthorize("#user.username == authentication.name")
public void updateProfile(User user) {
    userRepository.save(user);
}
```

---

#### 🟡 [Exemplo05: Custom Security Filter](exemplo05/README.md)
**Objetivo**: Criar filtros de segurança customizados

**O que você vai aprender**:
- Implementar `Filter` interface
- Adicionar filtros à filter chain
- Request/Response interceptação
- Logging de acessos
- Rate limiting básico

**Casos de uso**:
- API key validation
- Token extraction
- Request logging
- IP blocking

---

#### 🟡 [Exemplo06: Password Encoding](exemplo06/README.md)
**Objetivo**: Gestão segura de senhas

**O que você vai aprender**:
- BCryptPasswordEncoder
- Comparação de senhas
- Password strength validation
- Migration de senhas
- Testes de encoding

**Boas práticas**:
- Nunca armazenar plaintext
- Usar salt automático (BCrypt)
- Validar força da senha
- Permitir reset seguro

---

#### 🟡 [Exemplo07: CORS Configuration](exemplo07/README.md)
**Objetivo**: Configurar Cross-Origin Resource Sharing

**O que você vai aprender**:
- CORS headers
- CorsConfigurationSource
- Global vs endpoint-specific CORS
- Preflight requests (OPTIONS)
- Security implications

**Configuração exemplo**:
```java
@Bean
public CorsConfigurationSource corsConfigurationSource() {
    CorsConfiguration config = new CorsConfiguration();
    config.setAllowedOrigins(List.of("http://localhost:3000"));
    config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE"));
    config.setAllowedHeaders(List.of("*"));
    config.setAllowCredentials(true);
    
    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", config);
    return source;
}
```

---

### Nível 3: OAuth2 e JWT (exemplo08-exemplo11)

#### 🔴 [Exemplo08: JWT Introduction](exemplo08/README.md)
**Objetivo**: Entender JSON Web Tokens

**O que você vai aprender**:
- Estrutura do JWT (Header.Payload.Signature)
- Geração de tokens
- Validação de tokens
- Claims customizados
- Expiração e refresh

**Fluxo JWT**:
```
1. Cliente envia credenciais → POST /auth/login
2. Servidor valida e gera JWT
3. Cliente recebe token
4. Cliente envia token em header: Authorization: Bearer <token>
5. Servidor valida token e autoriza
```

**Tecnologias**:
- io.jsonwebtoken (jjwt)
- HS256 signing
- Spring Security JWT

---

#### 🔴 [Exemplo09: JWT + Custom Login](exemplo09/README.md)
**Objetivo**: API completa com JWT

**O que você vai aprender**:
- Endpoint de login customizado
- Token generation service
- JWT authentication filter
- Resource server configuration
- Stateless authentication

**Endpoints**:
- `POST /auth/login` - Retorna JWT
- `GET /user/profile` - Requer JWT válido
- `GET /admin/users` - Requer JWT + role ADMIN

**Request/Response**:
```json
// POST /auth/login
{
  "username": "joao",
  "password": "4321"
}

// Response 200 OK
{
  "token": "eyJhbGciOiJIUzI1NiIs...",
  "expiresIn": 14400
}
```

---

#### 🔴 [Exemplo10: OAuth2 Authorization Server](exemplo10/README.md)
**Objetivo**: Servidor de autorização próprio

**O que você vai aprender**:
- OAuth2 Authorization Server
- Resource Server configuration
- Token introspection
- Client credentials
- Authorization code flow

**Arquitetura**:
```
┌─────────────────┐
│  Auth Server    │ ← Emite tokens
│  (Port 8080)    │
└────────┬────────┘
         │ JWT
         ▼
┌─────────────────┐
│ Resource Server │ ← Valida tokens
│  (same app)     │
└─────────────────┘
```

**Tecnologias**:
- Spring Authorization Server
- OAuth2 Resource Server
- JWT with HS256/RS256

---

#### 🔴 [Exemplo11: Keycloak Integration](exemplo11/README.md)
**Objetivo**: Resource Server com Keycloak externo

**O que você vai aprender**:
- Keycloak setup (Docker)
- Realm e client configuration
- Role mapping
- Token validation
- OIDC integration

**Arquitetura**:
```
┌──────────────────┐
│   Keycloak       │ ← Authorization Server
│  (Port 8080)     │    (Docker)
└────────┬─────────┘
         │ JWT
         ▼
┌──────────────────┐
│ Spring Boot API  │ ← Resource Server
│  (Port 8081)     │
└──────────────────┘
```

**Como executar**:
```powershell
# 1. Subir Keycloak
cd exemplo11\demo
docker compose up -d

# 2. Configurar realm (automático via import)
# Acesse: http://localhost:8080
# Admin: admin/admin

# 3. Rodar API
.\mvnw.cmd spring-boot:run

# 4. Obter token
curl -X POST "http://localhost:8080/realms/demo/protocol/openid-connect/token" \
  -d "client_id=spring-api" \
  -d "client_secret=my-spring-api-secret" \
  -d "grant_type=password" \
  -d "username=usuario" \
  -d "password=senha123"

# 5. Testar endpoint
curl -H "Authorization: Bearer <TOKEN>" http://localhost:8081/user
```

---

### Projetos Completos

#### 🔵 [JWT_RestAPI](JWT_RestAPI/README.md)
**Descrição**: API REST completa com autenticação JWT

**Recursos**:
- ✅ CRUD de usuários
- ✅ Login e geração de token
- ✅ Refresh token
- ✅ Roles e permissões
- ✅ Swagger/OpenAPI
- ✅ Testes unitários

**Stack**:
- Spring Boot 3.4.3
- Spring Security + JWT
- Spring Data JPA
- H2/PostgreSQL
- Lombok + Validation

---

#### 🔵 [spring-security-keycloak](spring-security-keycloak-main/README.md)
**Descrição**: Integração completa Spring Security + Keycloak

**Recursos**:
- ✅ OAuth2 Login
- ✅ Resource Server (JWT)
- ✅ OIDC Support
- ✅ Role mapping
- ✅ Docker Compose para Keycloak

**Endpoints**:
- `/public` - Público
- `/private` - Requer autenticação
- `/cookie` - OAuth2 com session cookie
- `/jwt` - OAuth2 com JWT

---

#### 🔵 [aulao-spring-security](aulao-spring-security-main/README.md)
**Descrição**: Material consolidado de aula

**Conteúdo**:
- Slides e apresentações
- Exemplos comentados
- Exercícios práticos
- Testes e soluções

---

#### 🔵 [client-credentials](client-credentials-main/README.md)
**Descrição**: OAuth2 Client Credentials Flow

**Cenário**: Comunicação service-to-service

**Arquitetura**:
```
┌─────────────────┐       ┌─────────────────┐
│ Client Service  │──────▶│ Resource Server │
│  (Port 8081)    │ token │   (Port 8080)   │
└─────────────────┘       └─────────────────┘
         │
         └──────▶ Authorization Server
                  (Keycloak/Auth0)
```

**Projetos incluídos**:
- `resource-service` - API protegida
- `client-service-restclient` - Cliente com RestClient
- `client-service-reactive` - Cliente com WebClient (Reactive)

---

## 🔑 Conceitos Fundamentais

### 1. Autenticação vs Autorização

**Autenticação** (Authentication)
- "Quem você é?"
- Processo de verificar identidade
- Exemplos: usuário/senha, JWT, OAuth2

**Autorização** (Authorization)
- "O que você pode fazer?"
- Processo de verificar permissões
- Exemplos: roles, permissions, ACLs

### 2. Spring Security Architecture

```
HTTP Request
    │
    ▼
┌───────────────────────┐
│   Filter Chain        │
│  ┌─────────────────┐  │
│  │ CSRF Filter     │  │
│  ├─────────────────┤  │
│  │ Auth Filter     │  │
│  ├─────────────────┤  │
│  │ Session Filter  │  │
│  ├─────────────────┤  │
│  │ Exception      │  │
│  │ Filter         │  │
│  └─────────────────┘  │
└───────────┬───────────┘
            │
            ▼
    DispatcherServlet
            │
            ▼
      Controllers
```

### 3. SecurityFilterChain

**Principais Componentes**:
- **HttpSecurity**: Configuração de segurança HTTP
- **AuthenticationManager**: Gerencia autenticação
- **UserDetailsService**: Carrega dados do usuário
- **PasswordEncoder**: Codifica/valida senhas
- **SecurityContext**: Armazena autenticação atual

### 4. JWT (JSON Web Token)

**Estrutura**:
```
eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9  ← Header
.
eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ  ← Payload
.
SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c  ← Signature
```

**Header** (decoded):
```json
{
  "alg": "HS256",
  "typ": "JWT"
}
```

**Payload** (decoded):
```json
{
  "sub": "1234567890",
  "name": "John Doe",
  "iat": 1516239022,
  "exp": 1516242622,
  "roles": ["USER", "ADMIN"]
}
```

**Vantagens**:
- ✅ Stateless (sem sessão no servidor)
- ✅ Escalável (não precisa de shared session)
- ✅ Cross-domain (CORS-friendly)
- ✅ Mobile-friendly

**Desvantagens**:
- ⚠️ Não pode ser revogado facilmente
- ⚠️ Tamanho maior que session ID
- ⚠️ Precisa de refresh token strategy

### 5. OAuth2 Flows

**Authorization Code Flow** (Web Apps):
```
User → Browser → Authorization Server → Code
Browser → Client App → Exchange Code → Token
```

**Client Credentials Flow** (Service-to-Service):
```
Service → Authorization Server → Token (sem usuário)
```

**Password Grant Flow** (Mobile/Trusted Apps):
```
User → App → Send credentials → Token
```

**Refresh Token Flow**:
```
Client → Send refresh token → New access token
```

### 6. CORS (Cross-Origin Resource Sharing)

**Problema**:
```
http://localhost:3000 (Frontend React)
    │
    └──X──▶ http://localhost:8080/api (Backend)
            CORS Error!
```

**Solução**:
```java
config.setAllowedOrigins(List.of("http://localhost:3000"));
config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE"));
config.setAllowedHeaders(List.of("*"));
config.setAllowCredentials(true);
```

**Headers Resultantes**:
```
Access-Control-Allow-Origin: http://localhost:3000
Access-Control-Allow-Methods: GET, POST, PUT, DELETE
Access-Control-Allow-Headers: *
Access-Control-Allow-Credentials: true
```

## 🛠️ Ferramentas e Tecnologias

### Dependências Maven

```xml
<!-- Spring Security -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-security</artifactId>
</dependency>

<!-- OAuth2 Resource Server (JWT) -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-oauth2-resource-server</artifactId>
</dependency>

<!-- OAuth2 Authorization Server -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-oauth2-authorization-server</artifactId>
</dependency>

<!-- JWT Library -->
<dependency>
    <groupId>io.jsonwebtoken</groupId>
    <artifactId>jjwt-api</artifactId>
    <version>0.11.5</version>
</dependency>

<!-- Validation -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-validation</artifactId>
</dependency>
```

### Ferramentas de Análise

#### SonarQube
**Propósito**: Análise estática de código

**Instalação (Docker)**:
```powershell
docker run -d --name sonarqube -p 9000:9000 sonarqube:lts-community
```

**Análise do projeto**:
```powershell
.\mvnw.cmd clean verify sonar:sonar `
  -Dsonar.projectKey=meu-projeto `
  -Dsonar.host.url=http://localhost:9000 `
  -Dsonar.login=seu-token
```

**Métricas analisadas**:
- Bugs
- Vulnerabilidades
- Code smells
- Duplicação de código
- Cobertura de testes

#### JaCoCo
**Propósito**: Cobertura de testes

**Configuração Maven**:
```xml
<plugin>
    <groupId>org.jacoco</groupId>
    <artifactId>jacoco-maven-plugin</artifactId>
    <version>0.8.11</version>
    <executions>
        <execution>
            <goals>
                <goal>prepare-agent</goal>
            </goals>
        </execution>
        <execution>
            <id>report</id>
            <phase>test</phase>
            <goals>
                <goal>report</goal>
            </goals>
        </execution>
    </executions>
</plugin>
```

**Gerar relatório**:
```powershell
.\mvnw.cmd clean test
# Relatório em: target/site/jacoco/index.html
```

### Keycloak

**Instalação (Docker)**:
```yaml
version: '3.8'
services:
  keycloak:
    image: quay.io/keycloak/keycloak:23.0
    environment:
      KEYCLOAK_ADMIN: admin
      KEYCLOAK_ADMIN_PASSWORD: admin
    ports:
      - "8080:8080"
    command: start-dev
```

**Conceitos**:
- **Realm**: Namespace isolado (ex: "demo")
- **Client**: Aplicação que usa Keycloak
- **User**: Usuário do sistema
- **Role**: Permissão/papel
- **Group**: Agrupamento de usuários

## 🚀 Início Rápido

### Opção 1: Exemplo Mais Simples (exemplo00)

```powershell
# 1. Clone e navegue
cd Seguranca\exemplo00\demo

# 2. Execute
.\mvnw.cmd spring-boot:run

# 3. Acesse
# URL: http://localhost:8080/hello
# User: user
# Password: (veja no console)
```

### Opção 2: JWT Completo (JWT_RestAPI)

```powershell
# 1. Navegue
cd Seguranca\JWT_RestAPI

# 2. Execute
.\mvnw.cmd spring-boot:run

# 3. Teste login
curl -X POST http://localhost:8080/login/joao \
  -H "Content-Type: application/json"

# 4. Use o token retornado
curl -H "Authorization: Bearer <SEU_TOKEN>" \
  http://localhost:8080/user
```

### Opção 3: Keycloak (exemplo11)

```powershell
# 1. Suba Keycloak
cd Seguranca\exemplo11\demo
docker compose up -d

# 2. Configure realm (automático)
# Acesse: http://localhost:8080
# Login: admin/admin

# 3. Rode API
.\mvnw.cmd spring-boot:run

# 4. Obtenha token via Keycloak
# (veja exemplo11/README.md)
```

## 📋 Pré-requisitos

### Software Necessário

- **JDK 23** ou superior
- **Maven 3.9.x** (ou use wrapper incluído)
- **Docker Desktop** (para Keycloak/SonarQube)
- **Postman** ou **Insomnia** (testes de API)
- **IDE**: IntelliJ IDEA, Eclipse, ou VS Code

### Conhecimentos Recomendados

**Essenciais**:
- Java básico
- Spring Boot fundamentos
- HTTP/REST APIs
- JSON

**Desejáveis**:
- Spring Data JPA
- SQL básico
- Git
- Docker básico

### Verificar Instalação

```powershell
# Java
java -version
# Esperado: openjdk version "23" ou superior

# Maven
mvn -version
# Esperado: Apache Maven 3.9.x

# Docker
docker --version
docker compose version
```

## 🧪 Testando Aplicações Seguras

### 1. Teste com curl

**HTTP Basic**:
```powershell
curl -u joao:4321 http://localhost:8080/api/hello
```

**Bearer Token (JWT)**:
```powershell
curl -H "Authorization: Bearer eyJhbGciOiJIUzI1NiIs..." \
  http://localhost:8080/user/profile
```

**POST com JSON**:
```powershell
curl -X POST http://localhost:8080/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username":"joao","password":"4321"}'
```

### 2. Teste com Postman

**Configurar Auth**:
1. Aba "Authorization"
2. Type: "Bearer Token"
3. Token: `<COLE_SEU_JWT_AQUI>`

**Variáveis de Ambiente**:
```json
{
  "base_url": "http://localhost:8080",
  "token": "eyJhbGciOiJIUzI1NiIs..."
}
```

**Scripts de Teste**:
```javascript
// Pre-request Script
pm.request.headers.add({
    key: 'Authorization',
    value: 'Bearer ' + pm.environment.get('token')
});

// Tests
pm.test("Status code is 200", () => {
    pm.response.to.have.status(200);
});

pm.test("Response has token", () => {
    pm.expect(pm.response.json()).to.have.property('token');
});
```

### 3. Testes Automatizados

```java
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class SecurityTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser(roles = "ADMIN")
    void testAdminEndpoint() throws Exception {
        mockMvc.perform(get("/admin"))
               .andExpect(status().isOk());
    }

    @Test
    void testUnauthorized() throws Exception {
        mockMvc.perform(get("/admin"))
               .andExpect(status().isUnauthorized());
    }
}
```

## 🆘 Troubleshooting Comum

### Erro: "401 Unauthorized"

**Causa**: Credenciais inválidas ou token expirado

**Soluções**:
```powershell
# 1. Verificar credenciais
curl -v -u joao:SENHA_ERRADA http://localhost:8080/api/hello

# 2. Verificar token (decode em jwt.io)
# - Token expirado? (claim "exp")
# - Assinatura válida?

# 3. Gerar novo token
curl -X POST http://localhost:8080/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username":"joao","password":"4321"}'
```

### Erro: "403 Forbidden"

**Causa**: Usuário autenticado mas sem permissão

**Solução**:
```java
// Verificar roles necessárias
@PreAuthorize("hasRole('ADMIN')")
public void deleteUser(Long id) { ... }

// Usuário atual tem role ADMIN?
// Verificar no token (claim "roles" ou "authorities")
```

### Erro: "CORS Policy Blocked"

**Causa**: Frontend em origem diferente

**Solução**:
```java
@Bean
public CorsConfigurationSource corsConfigurationSource() {
    CorsConfiguration config = new CorsConfiguration();
    config.setAllowedOrigins(List.of(
        "http://localhost:3000",
        "http://localhost:4200"
    ));
    config.setAllowedMethods(List.of("*"));
    config.setAllowedHeaders(List.of("*"));
    config.setAllowCredentials(true);
    
    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", config);
    return source;
}
```

### Erro: "CSRF Token Invalid"

**Causa**: CSRF habilitado em API stateless

**Solução**:
```java
@Bean
public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http
        .csrf(csrf -> csrf.disable()) // Para APIs REST com JWT
        // ... resto da configuração
    return http.build();
}
```

### Keycloak: "Failed to connect"

**Causa**: Keycloak não iniciou ou porta errada

**Verificar**:
```powershell
# Container rodando?
docker ps | findstr keycloak

# Logs
docker compose logs keycloak

# Testar conectividade
curl http://localhost:8080/realms/demo/.well-known/openid-configuration
```

### JWT: "Invalid Signature"

**Causa**: Chave secreta diferente entre geração e validação

**Verificar**:
```properties
# application.yml (deve ser IGUAL em auth e resource server)
spring:
  security:
    oauth2:
      resourceserver:
        jwt:
          secret-key: minha-chave-secreta-super-longa
```

## 💡 Boas Práticas de Segurança

### 1. Gestão de Senhas

✅ **FAZER**:
- Usar BCryptPasswordEncoder (salt automático)
- Validar força da senha (8+ chars, letras, números, símbolos)
- Permitir reset seguro (token temporário)
- Nunca logar senhas

❌ **NÃO FAZER**:
- Armazenar senha em plaintext
- Usar MD5/SHA1 (inseguros)
- Enviar senha por email
- Reutilizar senhas de exemplo

### 2. Tokens JWT

✅ **FAZER**:
- Usar expiração curta (15-60 min)
- Implementar refresh tokens
- Assinar com HS256 (symmetric) ou RS256 (asymmetric)
- Validar issuer e audience
- Armazenar em httpOnly cookie (web)

❌ **NÃO FAZER**:
- Tokens sem expiração
- Incluir dados sensíveis no payload
- Usar "none" algorithm
- Armazenar em localStorage (XSS vulnerability)

### 3. Autenticação

✅ **FAZER**:
- Rate limiting (prevenir brute force)
- Account lockout após N tentativas
- 2FA/MFA quando possível
- Log de tentativas de login
- Usar HTTPS sempre

❌ **NÃO FAZER**:
- Permitir senhas fracas
- Logar credenciais
- Aceitar HTTP em produção
- Confiar só em client-side validation

### 4. Autorização

✅ **FAZER**:
- Princípio do menor privilégio
- Validar permissões server-side
- Usar roles granulares
- Revisar permissões periodicamente

❌ **NÃO FAZER**:
- Confiar em claims do token sem validar
- Hardcoded roles no código
- Autorização só no frontend
- Dar acesso admin por padrão

### 5. Configuração

✅ **FAZER**:
- Externalizar secrets (environment variables)
- Usar vaults (AWS Secrets Manager, Azure Key Vault)
- Diferentes configurações por ambiente
- Rodar análise de segurança (SonarQube)

❌ **NÃO FAZER**:
- Commitar secrets no Git
- Usar mesma chave em dev/prod
- Deixar endpoints debug em produção
- Ignorar vulnerabilidades reportadas

### 6. OWASP Top 10

**Proteções Implementadas**:

1. **Broken Access Control** → Roles + @PreAuthorize
2. **Cryptographic Failures** → BCrypt + HTTPS
3. **Injection** → PreparedStatement + Validation
4. **Insecure Design** → Security by design
5. **Security Misconfiguration** → Default deny, configs revisadas
6. **Vulnerable Components** → Dependabot, updates regulares
7. **Identification/Auth Failures** → Spring Security + JWT
8. **Software/Data Integrity** → JWT signature validation
9. **Logging/Monitoring** → Logs estruturados
10. **SSRF** → Whitelist de URLs externas

## 📖 Referências e Recursos

### Documentação Oficial

- [Spring Security Reference](https://docs.spring.io/spring-security/reference/index.html)
- [Spring Authorization Server](https://spring.io/projects/spring-authorization-server)
- [OAuth2 Resource Server](https://docs.spring.io/spring-security/reference/servlet/oauth2/resource-server/index.html)
- [JWT.io](https://jwt.io/) - Decode e debug de tokens
- [Keycloak Documentation](https://www.keycloak.org/documentation)

### Tutoriais e Guias

- [Spring Security Architecture](https://spring.io/guides/topicals/spring-security-architecture)
- [Securing a Web Application](https://spring.io/guides/gs/securing-web/)
- [OAuth2 Boot Guide](https://spring.io/guides/tutorials/spring-boot-oauth2/)
- [Baeldung Spring Security](https://www.baeldung.com/security-spring)

### OWASP

- [OWASP Top 10](https://owasp.org/www-project-top-ten/)
- [OWASP Cheat Sheet Series](https://cheatsheetseries.owasp.org/)
- [OWASP Dependency-Check](https://owasp.org/www-project-dependency-check/)

### Ferramentas

- [SonarQube](https://www.sonarqube.org/)
- [JaCoCo](https://www.jacoco.org/)
- [Snyk](https://snyk.io/) - Vulnerability scanning
- [Dependabot](https://github.com/dependabot) - Automated updates

### Livros Recomendados

- "Spring Security in Action" por Laurențiu Spilcă
- "OAuth 2 in Action" por Justin Richer e Antonio Sanso
- "Web Application Security" por Andrew Hoffman
- "Secure by Design" por Dan Bergh Johnsson

### Vídeos e Cursos

- [Spring Security Zero to Master](https://www.udemy.com/course/spring-security-zero-to-master/)
- [OAuth2 and OpenID Connect](https://www.youtube.com/watch?v=996OiexHze0)
- [Keycloak Tutorial](https://www.youtube.com/results?search_query=keycloak+tutorial)

### Comunidade

- [Stack Overflow - Spring Security](https://stackoverflow.com/questions/tagged/spring-security)
- [Spring Community Forums](https://community.spring.io/)
- [Reddit - r/java](https://www.reddit.com/r/java/)
- [Discord - Spring Developers](https://discord.gg/spring)

---

## 🎓 Contribuindo

Este material é usado em sala de aula e está em constante evolução. Sugestões e melhorias são bem-vindas!

**Como contribuir**:
1. Reporte bugs ou sugestões via Issues
2. Proponha melhorias via Pull Requests
3. Compartilhe seus projetos baseados nestes exemplos
4. Ajude outros estudantes nos fóruns

---

## 📝 Licença

Este material é de uso educacional. Sinta-se livre para usar, modificar e compartilhar, desde que mantenha os créditos.

---

## 👨‍🏫 Contato

**Professor**: Leonardo Vieira Guimarães  
**Instituição**: Newton Paiva  
**Disciplina**: Arquitetura de Aplicações Web

---

📚 **Última atualização**: Outubro 2025  
🔐 **Versão**: 2.0  
⭐ **Status**: Completo e em uso
