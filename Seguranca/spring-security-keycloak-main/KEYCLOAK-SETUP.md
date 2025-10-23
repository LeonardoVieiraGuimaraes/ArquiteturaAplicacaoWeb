# 🔐 Configuração do Keycloak com Docker

## 🚀 Opções de Execução

### Opção 1: Desenvolvimento Rápido (sem PostgreSQL)

Ideal para testes e desenvolvimento rápido:

```powershell
docker-compose -f docker-compose.dev.yml up -d
```

### Opção 2: Produção/Persistente (com PostgreSQL)

Recomendado para ambientes mais próximos de produção:

```powershell
docker-compose up -d
```

---

## 📦 O que foi criado?

### `docker-compose.yml` (Produção)
- **PostgreSQL 15**: Banco de dados para persistência do Keycloak
- **Keycloak 23.0.3**: Servidor de autenticação
- Volumes persistentes para dados
- Health checks configurados
- Network isolada

### `docker-compose.dev.yml` (Desenvolvimento)
- **Keycloak 23.0.3**: Standalone sem PostgreSQL
- Mais rápido para subir
- H2 database em memória (reinicia limpo)

---

## 🔧 Configuração Inicial do Keycloak

### 1. Acessar Console Admin

Após subir o container, acesse:

- **URL**: http://localhost:8080
- **Usuário**: `admin`
- **Senha**: `admin`

### 2. Criar um Realm

1. No menu lateral esquerdo, clique no dropdown do realm (inicialmente "master")
2. Clique em **"Create Realm"**
3. Preencha:
   - **Realm name**: `giulianabezerra` (ou outro nome de sua preferência)
4. Clique em **"Create"**

### 3. Criar um Client OAuth2

1. No menu lateral, vá em **"Clients"**
2. Clique em **"Create client"**
3. Na aba **General Settings**:
   - **Client type**: `OpenID Connect`
   - **Client ID**: `spring-security-keycloak`
   - Clique em **"Next"**

4. Na aba **Capability config**:
   - Marque: ✅ **Client authentication**
   - Marque: ✅ **Authorization**
   - Marque: ✅ **Standard flow**
   - Marque: ✅ **Direct access grants**
   - Clique em **"Next"**

5. Na aba **Login settings**:
   - **Valid redirect URIs**: 
     - `http://localhost:9090/*`
     - `http://localhost:9090/login/oauth2/code/keycloak`
   - **Valid post logout redirect URIs**: `http://localhost:9090/*`
   - **Web origins**: `http://localhost:9090`
   - Clique em **"Save"**

### 4. Obter o Client Secret

1. No client criado (`spring-security-keycloak`), vá na aba **"Credentials"**
2. Copie o **Client secret** gerado
3. Cole no arquivo `application.yaml` do projeto Spring Boot:

```yaml
spring:
  security:
    oauth2:
      client:
        registration:
          keycloak:
            client-id: spring-security-keycloak
            client-secret: <COLE_O_SECRET_AQUI>
```

### 5. Criar Usuários

1. No menu lateral, vá em **"Users"**
2. Clique em **"Add user"**
3. Preencha:
   - **Username**: `teste`
   - **Email**: `teste@example.com`
   - **First name**: `Usuario`
   - **Last name**: `Teste`
   - ✅ **Email verified**: marque
   - Clique em **"Create"**

4. Defina a senha:
   - Vá na aba **"Credentials"**
   - Clique em **"Set password"**
   - **Password**: `123456` (ou outra senha)
   - **Password confirmation**: `123456`
   - **Temporary**: Desmarque (para não pedir troca de senha no primeiro login)
   - Clique em **"Save"**

### 6. Configurar Roles (Opcional)

1. No menu lateral, vá em **"Realm roles"**
2. Crie roles como: `USER`, `ADMIN`, `MANAGER`
3. Associe roles aos usuários:
   - Vá em **"Users"** → selecione o usuário
   - Aba **"Role mapping"**
   - Clique em **"Assign role"**
   - Selecione as roles desejadas

---

## 🧪 Testar a Aplicação

### 1. Subir o Keycloak
```powershell
docker-compose -f docker-compose.dev.yml up -d
```

### 2. Aguardar inicialização
```powershell
# Verificar logs
docker logs keycloak-dev -f

# Aguardar até ver: "Keycloak 23.0.3 started"
```

### 3. Configurar o Keycloak
Siga os passos da seção anterior.

### 4. Rodar a aplicação Spring Boot
```powershell
./mvnw spring-boot:run
```

### 5. Testar Endpoints

#### Endpoint Público (sem autenticação)
```powershell
curl http://localhost:9090/public
```

#### Endpoint Privado (com autenticação)

**Via Browser**: Acesse http://localhost:9090/private e será redirecionado para o Keycloak

**Via curl** (obter token JWT):

1. Obter token:
```powershell
$response = Invoke-RestMethod -Uri "http://localhost:8080/realms/giulianabezerra/protocol/openid-connect/token" `
  -Method POST `
  -ContentType "application/x-www-form-urlencoded" `
  -Body @{
    grant_type = "password"
    client_id = "spring-security-keycloak"
    client_secret = "<SEU_CLIENT_SECRET>"
    username = "teste"
    password = "123456"
  }

$token = $response.access_token
Write-Host "Token: $token"
```

2. Usar o token:
```powershell
curl -H "Authorization: Bearer $token" http://localhost:9090/jwt
```

---

## 🛑 Parar e Remover Containers

### Parar containers
```powershell
# Dev
docker-compose -f docker-compose.dev.yml down

# Produção
docker-compose down
```

### Remover volumes (limpar dados)
```powershell
# Dev
docker-compose -f docker-compose.dev.yml down -v

# Produção
docker-compose down -v
```

---

## 🔍 Comandos Úteis

### Ver logs do Keycloak
```powershell
# Dev
docker logs keycloak-dev -f

# Produção
docker logs keycloak-server -f
```

### Verificar status dos containers
```powershell
docker ps
```

### Entrar no container do Keycloak
```powershell
# Dev
docker exec -it keycloak-dev bash

# Produção
docker exec -it keycloak-server bash
```

### Ver consumo de recursos
```powershell
docker stats
```

---

## 📚 URLs Importantes

### Keycloak Admin Console
- **URL**: http://localhost:8080
- **User**: admin
- **Password**: admin

### Aplicação Spring Boot
- **URL**: http://localhost:9090

### Endpoints da Aplicação
- `GET /public` → Aberto
- `GET /private` → Requer autenticação (cookie)
- `GET /cookie` → Requer autenticação (cookie)
- `GET /jwt` → Requer autenticação (JWT no header)

### Keycloak Endpoints (OpenID Connect)
- **Token**: http://localhost:8080/realms/giulianabezerra/protocol/openid-connect/token
- **Auth**: http://localhost:8080/realms/giulianabezerra/protocol/openid-connect/auth
- **UserInfo**: http://localhost:8080/realms/giulianabezerra/protocol/openid-connect/userinfo
- **Logout**: http://localhost:8080/realms/giulianabezerra/protocol/openid-connect/logout
- **JWKS**: http://localhost:8080/realms/giulianabezerra/protocol/openid-connect/certs

---

## 🔐 Troubleshooting

### Erro: "Port 8080 is already allocated"
- Já existe algo rodando na porta 8080
- Solução: Pare o processo ou altere a porta no `docker-compose.yml`:
```yaml
ports:
  - "8081:8080"  # Muda para 8081
```

### Erro: "Invalid redirect_uri"
- Verifique se o `redirect_uri` está configurado corretamente no client do Keycloak
- Deve incluir: `http://localhost:9090/*`

### Token inválido ou expirado
- Tokens JWT do Keycloak expiram em 5 minutos por padrão
- Obtenha um novo token pelo endpoint `/token`

### Container não inicia
```powershell
# Ver logs de erro
docker logs keycloak-dev

# Remover e recriar
docker-compose -f docker-compose.dev.yml down -v
docker-compose -f docker-compose.dev.yml up -d
```

---

## 📖 Referências

- [Keycloak Documentation](https://www.keycloak.org/documentation)
- [Spring Security OAuth2 Client](https://docs.spring.io/spring-security/reference/servlet/oauth2/client/index.html)
- [OAuth 2.0 RFC 6749](https://datatracker.ietf.org/doc/html/rfc6749)
- [OpenID Connect](https://openid.net/connect/)

---

**Criado para o curso de Arquitetura de Aplicação Web - Newton Paiva** 🚀
