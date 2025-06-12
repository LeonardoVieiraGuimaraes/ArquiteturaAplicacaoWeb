# Exemplo de Arquitetura OAuth2 com Spring Boot

## Visão Geral

Este projeto demonstra uma arquitetura moderna de autenticação e autorização usando OAuth2, JWT e Spring Security. Ele é dividido em dois papéis principais:

- **Authorization Server**: Responsável por autenticar usuários e emitir tokens JWT.
- **Resource Server**: Protege APIs e valida tokens JWT emitidos pelo Authorization Server.

## Dependências Principais

- `spring-boot-starter-oauth2-authorization-server`: Implementa o servidor de autorização OAuth2.
- `spring-security-oauth2-resource-server`: Permite que a API valide tokens JWT.
- `spring-security-oauth2-jose`: Lida com assinatura e criptografia dos tokens JWT.

## Organização do Projeto

- `config/AuthorizationServerConfig.java`: Configuração do Authorization Server.
- `config/SecurityConfig.java`: Configuração do Resource Server.
- `controller/AuthController.java`: Endpoint de login customizado (para autenticação via usuário/senha).
- `controller/*Controller.java`: Endpoints protegidos.
- `model/`, `repository/`, `service/`: Camadas de domínio, persistência e lógica de negócio.

## Fluxo de Autenticação

1. O usuário faz login via `/auth/login` ou pelo fluxo OAuth2 padrão.
2. O Authorization Server autentica o usuário e emite um token JWT.
3. O cliente usa o token JWT para acessar endpoints protegidos do Resource Server.
4. O Resource Server valida o token JWT usando a chave secreta/configuração.

## Como rodar

1. Execute o projeto com `mvn spring-boot:run`.
2. Acesse `/auth/login` para obter um token JWT.
3. Use o token JWT no header `Authorization: Bearer <token>` para acessar as APIs protegidas.

## Observações

- Para produção, recomenda-se usar chaves assimétricas (RSA/ECDSA) e um Authorization Server dedicado.
- O projeto pode ser evoluído para separar Authorization Server e Resource Server em projetos distintos.

# Demo Spring Boot JWT

## Como funciona

- Inicializa roles padrão e usuário admin (admin/431).
- Login customizado em `/auth/login` (POST JSON: username, password).
- Receba um JWT e use em `Authorization: Bearer <token>` para acessar endpoints protegidos.
- Endpoints `/h2-console` e `/public` são livres.

## Funcionalidades

- Cadastro e autenticação de usuários
- Proteção de endpoints por roles (ADMIN, USER, GERENTE)
- Login customizado que retorna JWT
- Validação automática de entidades (Bean Validation)
- Inicialização automática de roles e usuário admin
- Console H2 para debug

## Teste rápido

1. Rode a aplicação (`mvn spring-boot:run`).
2. Faça login:
   ```
   curl -X POST http://localhost:8080/auth/login -H "Content-Type: application/json" -d '{"username":"admin","password":"431"}'
   ```
   O retorno será:
   ```json
   {"token":"<JWT aqui>"}
   ```

3. Use o token retornado para acessar endpoints protegidos:
   ```
   curl -H "Authorization: Bearer <JWT aqui>" http://localhost:8080/api/users
   ```

4. Acesse o endpoint público:
   ```
   curl http://localhost:8080/public
   ```

## Observações

- O JWT é validado automaticamente pelo Resource Server.
- Roles são verificadas via `@PreAuthorize`.
- O banco é H2 em arquivo local (`./data/demo-db`).

---
