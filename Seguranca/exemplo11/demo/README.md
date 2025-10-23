# Spring Boot + JWT + Keycloak (Docker)

Este projeto demonstra uma API Spring Boot protegida como Resource Server (JWT) usando Keycloak como Authorization Server.

## Requisitos

- Java 21+
- Maven 3.9+
- Docker e Docker Compose
- Postman ou curl para testes

## Arquitetura

- Keycloak roda em um container Docker na porta 8080
- A API Spring Boot roda localmente na porta 8081
- A API valida os JWTs emitidos pelo Keycloak (issuer-uri)

## Como subir o Keycloak (Docker)

1. No diretório do projeto `demo`, suba o compose:

```pwsh
# Subir Keycloak
docker compose up -d
# Verificar logs (opcional)
docker compose logs -f keycloak
```

2. Acesse o Admin Console em http://localhost:8080/ → clique em Administration Console e faça login:

- Usuário: `admin`
- Senha: `admin`

O compose importa automaticamente o Realm `demo` com:
- Roles: `user`, `admin`
- Client confidencial: `spring-api`
  - Secret: `my-spring-api-secret`
  - Standard Flow e Direct Access Grants habilitados

3. Crie ao menos um usuário no Realm `demo` e atribua a role desejada (ex.: `user` ou `admin`).
   - Realm: `demo`
   - Menu Users → Add user → defina `username`, salve → Credentials → Set password (desmarque Temporary) → Role Mappings → atribua `user` e/ou `admin`.

## Configuração da API

Arquivo `src/main/resources/application.yml`:

```yaml
spring:
  security:
    oauth2:
      resourceserver:
        jwt:
          issuer-uri: http://localhost:8080/realms/demo
server:
  port: 8081
```

- Veja a classe `com.example.demo.config.SecurityConfig` para as regras de autorização.
- Conversão de roles do Keycloak → `KeycloakRealmRoleConverter` (lê realm_access e resource_access e adiciona prefixo ROLE_).

## Rodando a API

```pwsh
# Build
./mvnw -q clean package

# Executar
./mvnw -q spring-boot:run
```

A API ficará em http://localhost:8081

## Endpoints

- `GET /public` → acesso público
- `GET /user` → exige autenticação (qualquer usuário autenticado)
- `GET /admin` → exige role `admin`

## Obtendo um token (Password Grant)

Substitua `<usuario>` e `<senha>` de um usuário criado no Realm `demo`.

```pwsh
$token = (curl -Method POST `
  -Uri "http://localhost:8080/realms/demo/protocol/openid-connect/token" `
  -Headers @{ 'Content-Type' = 'application/x-www-form-urlencoded' } `
  -Body "grant_type=password&client_id=spring-api&client_secret=my-spring-api-secret&username=<usuario>&password=<senha>" `
  | ConvertFrom-Json).access_token

$token
```

## Testando os endpoints

- Público:

```pwsh
curl http://localhost:8081/public
```

- Autenticado (`/user`):

```pwsh
curl -H "Authorization: Bearer $token" http://localhost:8081/user
```

- Admin (`/admin`):

```pwsh
curl -H "Authorization: Bearer $token" http://localhost:8081/admin
```

Se o usuário não tiver a role `admin`, você receberá 403.

## Observações

- Se preferir outro fluxo (Authorization Code), o client `spring-api` já está com `standardFlowEnabled: true`.
- O conversor de roles considera roles de `realm_access` e de qualquer `resource_access`.

## Estrutura relevante

```
src/main/java/com/example/demo/
  ├── config/
  │   ├── SecurityConfig.java
  │   └── KeycloakRealmRoleConverter.java
  └── controller/
      └── DemoController.java
src/main/resources/
  └── application.yml

docker-compose.yml
/docker/keycloak/realm-demo.json
```

## Licença

Livre para uso educacional.
