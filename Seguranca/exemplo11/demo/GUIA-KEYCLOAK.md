# 🔐 Guia de Configuração do Keycloak - Realm Demo

## ✅ PRÉ-REQUISITO: Confirme que está no Realm "demo"
**Olhe no canto superior esquerdo do console do Keycloak - deve estar escrito "demo"**

---

## 📋 PASSO 1: Verificar Roles

1. No menu lateral esquerdo, clique em **"Realm roles"**
2. Você deve ver estas roles:
   - ✅ `admin` - Administrador
   - ✅ `user` - Usuário padrão

**Se as roles NÃO existirem, crie-as:**

### Criar role "admin":
- Clique em **"Create role"**
- **Role name**: `admin`
- **Description**: `Administrador do sistema`
- Clique em **"Save"**

### Criar role "user":
- Clique em **"Create role"**
- **Role name**: `user`
- **Description**: `Usuário padrão`
- Clique em **"Save"**

---

## 📋 PASSO 2: Verificar Client "spring-api"

1. No menu lateral esquerdo, clique em **"Clients"**
2. Procure o client **`spring-api`**
3. Clique nele

### Configurações na aba "Settings":
- ✅ **Client authentication**: `ON` (deve estar ligado)
- ✅ **Authorization**: `OFF`
- ✅ **Authentication flow**:
  - ✅ Standard flow: `ON`
  - ✅ Direct access grants: `ON` ⚠️ **IMPORTANTE para obter token com senha**
  - ❌ Implicit flow: `OFF`
  - ❌ Service accounts roles: `OFF`
- ✅ **Valid redirect URIs**: `http://localhost:8081/*`
- ✅ **Valid post logout redirect URIs**: `+`
- ✅ **Web origins**: `+` (ou `http://localhost:8081`)

### Verificar o Secret na aba "Credentials":
- O **Client Secret** deve ser: `my-spring-api-secret`
- Se for diferente, anote o secret e atualize o script de teste

**Clique em "Save" se mudou algo**

---

## 📋 PASSO 3: Criar Usuário ADMIN

1. No menu lateral esquerdo, clique em **"Users"**
2. Clique no botão azul **"Create new user"** (ou "Add user")

### Dados do usuário:
```
Username: admin
Email: admin@example.com (opcional)
Email verified: ON (opcional)
First name: Admin (opcional)
Last name: User (opcional)
```

3. Clique em **"Create"**

### Definir senha:
1. Após criar, você será redirecionado para a página do usuário
2. Clique na aba **"Credentials"**
3. Clique em **"Set password"**
4. Preencha:
   ```
   Password: admin123
   Password confirmation: admin123
   Temporary: OFF ⚠️ IMPORTANTE! (desligue esta opção)
   ```
5. Clique em **"Save"**
6. Confirme clicando em **"Save password"** no popup

### Atribuir roles:
1. Clique na aba **"Role mappings"**
2. Clique no botão **"Assign role"**
3. Na lista que aparece, marque as checkboxes:
   - ✅ `admin`
   - ✅ `user`
4. Clique em **"Assign"**
5. Você deve ver as duas roles listadas em "Assigned roles"

---

## 📋 PASSO 4: Criar Usuário USER (normal)

1. Volte para **"Users"** no menu lateral
2. Clique em **"Create new user"** novamente

### Dados do usuário:
```
Username: user
Email: user@example.com (opcional)
Email verified: ON (opcional)
First name: Normal (opcional)
Last name: User (opcional)
```

3. Clique em **"Create"**

### Definir senha:
1. Vá na aba **"Credentials"**
2. Clique em **"Set password"**
3. Preencha:
   ```
   Password: user123
   Password confirmation: user123
   Temporary: OFF ⚠️ IMPORTANTE!
   ```
4. Clique em **"Save"** e confirme

### Atribuir role:
1. Clique na aba **"Role mappings"**
2. Clique em **"Assign role"**
3. Marque APENAS:
   - ✅ `user`
   - ❌ NÃO marque `admin`
4. Clique em **"Assign"**

---

## ✅ CHECKLIST FINAL

Antes de testar, verifique:

- [ ] Você está no Realm **"demo"** (canto superior esquerdo)
- [ ] Roles criadas: `admin` e `user`
- [ ] Client `spring-api` existe com:
  - [ ] Client authentication: ON
  - [ ] Direct access grants: ON
  - [ ] Client secret: `my-spring-api-secret`
- [ ] Usuário `admin` criado:
  - [ ] Password: `admin123` (temporary OFF)
  - [ ] Roles atribuídas: `admin` + `user`
- [ ] Usuário `user` criado:
  - [ ] Password: `user123` (temporary OFF)
  - [ ] Role atribuída: `user` (sem admin)

---

## 🧪 PRÓXIMO PASSO: Testar

### Teste 1: Verificar se a aplicação está rodando

```powershell
# Se a aplicação não estiver rodando, inicie-a:
./mvnw spring-boot:run
```

Aguarde até ver a mensagem: `Started DemoApplication in X seconds`

---

### Teste 2: Obter token do usuário ADMIN

```powershell
$tokenAdmin = (curl -Method POST `
  -Uri "http://localhost:8080/realms/demo/protocol/openid-connect/token" `
  -Headers @{ 'Content-Type' = 'application/x-www-form-urlencoded' } `
  -Body "grant_type=password&client_id=spring-api&client_secret=my-spring-api-secret&username=admin&password=admin123" `
  | ConvertFrom-Json).access_token

Write-Host "Token Admin obtido com sucesso!" -ForegroundColor Green
Write-Host $tokenAdmin
```

**Resultado esperado:** Um token JWT longo (várias linhas de texto codificado)

---

### Teste 3: Obter token do usuário USER

```powershell
$tokenUser = (curl -Method POST `
  -Uri "http://localhost:8080/realms/demo/protocol/openid-connect/token" `
  -Headers @{ 'Content-Type' = 'application/x-www-form-urlencoded' } `
  -Body "grant_type=password&client_id=spring-api&client_secret=my-spring-api-secret&username=user&password=user123" `
  | ConvertFrom-Json).access_token

Write-Host "Token User obtido com sucesso!" -ForegroundColor Green
Write-Host $tokenUser
```

**Resultado esperado:** Um token JWT longo

---

### Teste 4: Endpoint público (sem autenticação)

```powershell
curl http://localhost:8081/public
```

**Resultado esperado:** `Acesso público` ✅

---

### Teste 5: Endpoint /user com usuário autenticado

```powershell
curl -H "Authorization: Bearer $tokenUser" http://localhost:8081/user
```

**Resultado esperado:** `Acesso autenticado` ✅

---

### Teste 6: Endpoint /admin com usuário normal (deve FALHAR)

```powershell
curl -H "Authorization: Bearer $tokenUser" http://localhost:8081/admin
```

**Resultado esperado:** Erro 403 Forbidden ❌ (user não tem role admin)

---

### Teste 7: Endpoint /admin com usuário admin (deve FUNCIONAR)

```powershell
curl -H "Authorization: Bearer $tokenAdmin" http://localhost:8081/admin
```

**Resultado esperado:** `Acesso restrito a admins` ✅

---

### Teste 8: Endpoint /user sem token (deve FALHAR)

```powershell
curl http://localhost:8081/user
```

**Resultado esperado:** Erro 401 Unauthorized ❌

---

### 🎯 Resumo dos resultados esperados:

| Teste | Endpoint | Token | Resultado Esperado |
|-------|----------|-------|-------------------|
| 1 | `/public` | Nenhum | ✅ 200 - "Acesso público" |
| 2 | `/user` | tokenUser | ✅ 200 - "Acesso autenticado" |
| 3 | `/user` | tokenAdmin | ✅ 200 - "Acesso autenticado" |
| 4 | `/admin` | tokenUser | ❌ 403 - Forbidden |
| 5 | `/admin` | tokenAdmin | ✅ 200 - "Acesso restrito a admins" |
| 6 | `/user` | Nenhum | ❌ 401 - Unauthorized |


## 📮 TESTANDO COM POSTMAN

### Passo 1: Obter Token JWT no Postman

1. Abra o **Postman**
2. Crie uma nova requisição **POST**
3. Cole a URL:
  ```
  http://localhost:8080/realms/demo/protocol/openid-connect/token
  ```

4. Vá na aba **"Body"**
5. Selecione **"x-www-form-urlencoded"**
6. Adicione os seguintes campos (key-value):

#### Para obter token do ADMIN:

| Key | Value |
|-----|-------|
| `grant_type` | `password` |
| `client_id` | `spring-api` |
| `client_secret` | `my-spring-api-secret` |
| `username` | `admin` |
| `password` | `admin123` |

#### Para obter token do USER:

| Key | Value |
|-----|-------|
| `grant_type` | `password` |
| `client_id` | `spring-api` |
| `client_secret` | `my-spring-api-secret` |
| `username` | `user` |
| `password` | `user123` |

7. Clique em **"Send"**

8. **Na resposta**, copie o valor do campo `access_token` (é um texto longo)

---

### Passo 2: Testar Endpoint Público

1. Crie uma nova requisição **GET**
2. URL: `http://localhost:8081/public`
3. Não precisa adicionar Authorization
4. Clique em **"Send"**
5. **Resultado esperado:** `Acesso público`

---

### Passo 3: Testar Endpoint /user (com token)

1. Crie uma nova requisição **GET**
2. URL: `http://localhost:8081/user`
3. Vá na aba **"Authorization"**
4. Selecione **Type**: `Bearer Token`
5. Cole o `access_token` que você copiou no campo **"Token"**
6. Clique em **"Send"**
7. **Resultado esperado:** `Acesso autenticado`

---

### Passo 4: Testar Endpoint /admin (com token USER - deve falhar)

1. Crie uma nova requisição **GET**
2. URL: `http://localhost:8081/admin`
3. Vá na aba **"Authorization"**
4. Selecione **Type**: `Bearer Token`
5. Cole o token do **usuário "user"** no campo **"Token"**
6. Clique em **"Send"**
7. **Resultado esperado:** Status **403 Forbidden** ❌

---

### Passo 5: Testar Endpoint /admin (com token ADMIN - deve funcionar)

1. Use a mesma requisição do passo anterior
2. Na aba **"Authorization"**
3. Cole o token do **usuário "admin"** no campo **"Token"**
4. Clique em **"Send"**
5. **Resultado esperado:** `Acesso restrito a admins` ✅

---

### Passo 6: Testar Endpoint /user (sem token - deve falhar)

1. Crie uma nova requisição **GET**
2. URL: `http://localhost:8081/user`
3. Vá na aba **"Authorization"**
4. Selecione **Type**: `No Auth`
5. Clique em **"Send"**
6. **Resultado esperado:** Status **401 Unauthorized** ❌

---

### 💡 Dicas do Postman:

#### Salvar tokens como variáveis:
1. Após obter o token, vá em **"Tests"** na requisição de token
2. Adicione este script:
  ```javascript
  var jsonData = pm.response.json();
  pm.environment.set("token_admin", jsonData.access_token);
  ```
3. Depois você pode usar `{{token_admin}}` no campo Token

#### Criar uma Collection:
1. Crie uma Collection chamada "Spring Boot + Keycloak"
2. Adicione todas as requisições acima
3. Organize em pastas: "Autenticação", "Endpoints Públicos", "Endpoints Protegidos"

#### Verificar o conteúdo do JWT:
1. Copie o token
2. Vá em https://jwt.io/
3. Cole o token no campo "Encoded"
4. Veja as roles em `realm_access.roles`

---

## 🆘 Problemas Comuns

### Não consigo obter token (erro 401):
- Verifique se "Direct access grants" está ON no client spring-api
- Verifique se a senha do usuário está definida como "temporary: OFF"
- Verifique se o client secret está correto

### Token obtido mas endpoint retorna 401:
- Verifique se a aplicação Spring Boot está rodando (porta 8081)
- Verifique o issuer-uri no application.yml
- Verifique se o Keycloak está acessível em http://localhost:8080

### Endpoint /admin retorna 403:
- Verifique se o usuário tem a role "admin" atribuída
- Certifique-se de estar usando o token do usuário admin, não do user

---

**Configuração concluída! 🎉**
