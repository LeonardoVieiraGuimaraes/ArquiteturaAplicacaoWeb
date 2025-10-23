<h1 align="center">
  Spring Security + Keycloak
</h1>

<p align="center">
 <img src="https://img.shields.io/static/v1?label=Youtube&message=@giulianabezerra&color=8257E5&labelColor=000000" alt="@giulianabezerra" />
 <img src="https://img.shields.io/static/v1?label=Tipo&message=Tutorial&color=8257E5&labelColor=000000" alt="Tutorial" />
</p>

Projeto apresentado [nesse vídeo](https://youtu.be/vV2NdanynpA) que ilustra o uso do Keycloak com Spring Security utilizando o Oauth 2.0 + OpenID.

## 🚀 Quick Start com Docker

### Opção 1: Desenvolvimento Rápido (Recomendado)
```powershell
docker-compose -f docker-compose.dev.yml up -d
```

### Opção 2: Produção (com PostgreSQL)
```powershell
docker-compose up -d
```

Aguarde ~60 segundos para o Keycloak inicializar completamente.

## 📖 Configuração do Keycloak

### 🎯 Script PowerShell para Gerenciamento

Use o script `keycloak-manager.ps1` para facilitar o gerenciamento:

```powershell
# Ver todos os comandos disponíveis
.\keycloak-manager.ps1 help

# Iniciar Keycloak
.\keycloak-manager.ps1 start-dev

# Abrir console admin no navegador
.\keycloak-manager.ps1 open-admin

# Obter token JWT interativo
.\keycloak-manager.ps1 get-token

# Ver logs
.\keycloak-manager.ps1 logs-dev

# Parar Keycloak
.\keycloak-manager.ps1 stop-dev
```

### 🔧 Configuração Manual Passo a Passo

#### 1. Criar Realm

1. Acesse http://localhost:8080 (usuário: `admin`, senha: `admin`)
2. No menu superior esquerdo, clique em **"Keycloak"** → **"Create Realm"**
3. Configure:
   - **Realm name**: `giulianabezerra`
   - **Enabled**: ON
4. Clique em **"Create"**

#### 2. Criar Client OAuth2

1. No menu lateral, vá em **"Clients"** → **"Create client"**
2. **General Settings**:
   - **Client type**: `OpenID Connect`
   - **Client ID**: `spring-security-keycloak`
3. Clique em **"Next"**
4. **Capability config**:
   - ✅ **Client authentication**: ON (para habilitar client secret)
   - ✅ **Authorization**: OFF
   - ✅ **Authentication flow**: 
     - Standard flow
     - Direct access grants
5. Clique em **"Next"**
6. **Login settings**:
   - **Valid redirect URIs**: `http://localhost:9090/*`
   - **Valid post logout redirect URIs**: `http://localhost:9090/*`
   - **Web origins**: `+` (permite todas as origens válidas)
7. Clique em **"Save"**

#### 3. Obter Client Secret

1. No client criado, vá na aba **"Credentials"**
2. Copie o valor do **"Client secret"**
3. Cole no arquivo `src/main/resources/application.yaml`:
   ```yaml
   spring:
     security:
       oauth2:
         resourceserver:
           jwt:
             issuer-uri: http://localhost:8080/realms/giulianabezerra
         client:
           registration:
             keycloak:
               client-id: spring-security-keycloak
               client-secret: SEU_CLIENT_SECRET_AQUI  # ← Cole aqui
               scope: openid
           provider:
             keycloak:
               issuer-uri: http://localhost:8080/realms/giulianabezerra
   ```

#### 4. Criar Usuários

1. No menu lateral, vá em **"Users"** → **"Create new user"**
2. Configure:
   - **Username**: `teste`
   - **Email**: `teste@example.com` (opcional)
   - **First name**: `Teste` (opcional)
   - **Last name**: `Usuario` (opcional)
   - ✅ **Email verified**: ON
3. Clique em **"Create"**
4. Na aba **"Credentials"**:
   - Clique em **"Set password"**
   - **Password**: `123456`
   - **Password confirmation**: `123456`
   - ❌ **Temporary**: OFF (para não pedir troca na primeira vez)
   - Clique em **"Save"** → **"Save password"**

#### 5. Criar e Atribuir Roles (Opcional)

1. No menu lateral, vá em **"Realm roles"** → **"Create role"**
2. Crie as roles:
   - **Role name**: `ADMIN`
   - **Role name**: `USER`
3. Volte em **"Users"** → selecione o usuário criado
4. Vá na aba **"Role mapping"** → **"Assign role"**
5. Selecione as roles desejadas e clique em **"Assign"**

### 📝 Guia Completo

Para instruções mais detalhadas, troubleshooting e exemplos de código, consulte: **[KEYCLOAK-SETUP.md](./KEYCLOAK-SETUP.md)**

O guia completo inclui:
- ✅ Comandos curl/PowerShell para obter tokens
- ✅ Como testar todas as rotas da aplicação
- ✅ Troubleshooting de erros comuns
- ✅ Exemplos com Postman
- ✅ Configurações avançadas

## Testando a Aplicação

As seguintes rotas podem ser acessadas para testar:

- GET /public: Rota aberta
- GET /private: Rota que pede autenticação
- GET /cookie: Rota que pede autenticação com cookie de sessão
- GET /jwt: Rota que pede autenticação com JWT