# JWT_RestAPI — Guia de uso com Postman e curl

Este projeto é uma API simples com autenticação JWT de exemplo usando Spring Boot.

- Porta padrão: 8081 (configurada em `src/main/resources/application.properties`)
- Requisitos: Java 21 (JDK), Maven Wrapper (incluído: `mvnw`/`mvnw.cmd`)
- Usuários em memória (para testes):
  - admin / 1234 (ROLE_ADMIN)
  - joao / 4321 (ROLE_USER)

## Como executar

No Windows (PowerShell):

1) Garanta que o Java 21 está configurado para esta sessão

```pwsh
$Env:JAVA_HOME = 'C:\\Program Files\\Java\\jdk-21'
$Env:Path = "$Env:JAVA_HOME\\bin;$Env:Path"
```

2) Rode a aplicação

```pwsh
.\mvnw clean spring-boot:run
```

A API deve subir em: http://localhost:8081

> Observação: se você preferir usar a IDE, basta rodar a classe `com.example.JWT_RestAPI.JwtRestApiApplication`.

## Endpoints

- POST `/login` (público)
  - Body (JSON): `{ "username": "joao", "password": "4321" }`
  - Retorna: token JWT (string)

- GET `/username/{token}` (público)
  - Retorna: o `username` extraído do token

- GET `/user` (público)
  - Retorna: `User: <nome>` (sem autenticação, pode retornar `anonymousUser`)

- GET `/admin` (restrito)
  - Requer HTTP Basic Auth → `admin:1234`

---

## Usando com o Postman

### 1) Login (obter token)

- Método: POST
- URL: `http://localhost:8081/login`
- Headers: `Content-Type: application/json`
- Body (raw → JSON):
```json
{
  "username": "joao",
  "password": "4321"
}
```
- Clique em Send. A resposta será um token (string). Copie o token.

Dica: você pode salvar o token em uma variável de ambiente/coleção no Postman. Na aba `Tests` da requisição de login, adicione:
```javascript
pm.collectionVariables.set('token', pm.response.text());
```

### 2) Extrair username a partir do token

- Método: GET
- URL: `http://localhost:8081/username/{{token}}`
  - Se você não salvou a variável `token`, substitua `{{token}}` pelo token copiado.
- Clique em Send. A resposta deve ser `joao`.

### 3) Acessar endpoint admin (HTTP Basic)

- Método: GET
- URL: `http://localhost:8081/admin`
- Na aba `Authorization`: escolha `Basic Auth`
  - Username: `admin`
  - Password: `1234`
- Clique em Send. Resposta esperada: `Admin: admin`

> Observação: Esta API demonstra JWT apenas retornando/extraindo o token em endpoints públicos. O endpoint `/admin` usa Basic Auth apenas para exemplo de autorização com papel ADMIN.

---

## Usando com curl (PowerShell)

Você pode usar o `curl` no PowerShell do Windows. Abaixo há dois formatos: (A) one-liner com escaping e (B) usando variável de corpo (mais legível).

### A) One-liners

1) Login → obter token (string):
```pwsh
curl -X POST "http://localhost:8081/login" -H "Content-Type: application/json" -d "{\"username\":\"joao\",\"password\":\"4321\"}"
```

2) Extrair username (substitua `<TOKEN>`):
```pwsh
curl "http://localhost:8081/username/<TOKEN>"
```

3) Endpoint admin com Basic Auth:
```pwsh
curl -u admin:1234 "http://localhost:8081/admin"
```

### B) Usando variável de corpo (mais prático no PowerShell)

1) Login → obter token:
```pwsh
$body = @'
{
  "username": "joao",
  "password": "4321"
}
'@

$token = (curl -X POST "http://localhost:8081/login" -H "Content-Type: application/json" -d $body).Content
$token
```

2) Extrair username usando o token:
```pwsh
curl "http://localhost:8081/username/$token"
```

3) Chamar `/admin` com Basic Auth:
```pwsh
curl -u admin:1234 "http://localhost:8081/admin"
```

---

## Erros comuns e soluções

- Porta 8081 ocupada: altere `server.port` em `src/main/resources/application.properties` (ex.: `server.port=8082`) ou libere a porta.
- JAVA_HOME não configurado: defina para a pasta do JDK 21 (ex.: `C:\\Program Files\\Java\\jdk-21`) e adicione `bin` ao `Path`.
- Docker/WSL usando 8080: se quiser usar 8080, encerre processos que escutam nessa porta (pode afetar Docker Desktop/WSL). Esta API já está configurada para 8081 para evitar conflito.

---

## Estrutura relevante

- `src/main/java/com/example/JWT_RestAPI/controller/AuthController.java` → Endpoints
- `src/main/java/com/example/JWT_RestAPI/config/SecurityConfig.java` → Regras de segurança e usuários em memória
- `src/main/resources/application.properties` → Porta do servidor (`server.port=8081`)

---

## Testes automatizados (MockMvc)

Há testes de fumaça em `src/test/java/com/example/JWT_RestAPI/AuthControllerTest.java`.

Para executar:
```pwsh
.\mvnw clean test
```

---

Se precisar, posso fornecer uma coleção do Postman pronta para importação ou adaptar a API para usar Bearer Token no header `Authorization` (padrão mais comum) em vez de passar o token na URL.
