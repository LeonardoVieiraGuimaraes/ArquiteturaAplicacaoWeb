# 🐳 Guia de Acesso ao PHPMyAdmin via Docker

## 📋 Informações de Acesso

### 🌐 URL do PHPMyAdmin
```
http://localhost:8081
```

### 🔐 Credenciais de Login

| Campo | Valor |
|-------|-------|
| **Servidor** | `mariadb` (ou deixe em branco) |
| **Usuário** | `root` |
| **Senha** | `root` |

---

## 🚀 Como Iniciar

### 1. Navegue até a pasta do exemplo

```powershell
cd D:\GitHub\NewtonPaiva\ArquiteturaAplicacaoWeb\PersistenciaDados\exemplo05
```

### 2. Inicie os containers Docker

```powershell
docker-compose up -d
```

**Saída esperada:**
```
[+] Running 2/2
 ✔ Container exemplo04-mariadb     Started
 ✔ Container exemplo04-phpmyadmin  Started
```

### 3. Verifique se os containers estão rodando

```powershell
docker-compose ps
```

**Saída esperada:**
```
NAME                   STATUS    PORTS
exemplo04-mariadb      Up        0.0.0.0:3307->3306/tcp
exemplo04-phpmyadmin   Up        0.0.0.0:8081->80/tcp
```

### 4. Acesse o PHPMyAdmin

Abra seu navegador e acesse:
```
http://localhost:8081
```

### 5. Faça login

- **Usuário**: `root`
- **Senha**: `root`

---

## 🗂️ O que você verá no PHPMyAdmin

Após fazer login, você poderá ver:

### Database: `productdb`

#### Tabelas (serão criadas automaticamente pelo Hibernate):

1. **`categories`** - Categorias de produtos
   - `id` (PK, AUTO_INCREMENT)
   - `name` (VARCHAR, UNIQUE)
   - `description` (VARCHAR)

2. **`products`** - Produtos
   - `id` (PK, AUTO_INCREMENT)
   - `name` (VARCHAR)
   - `description` (VARCHAR)
   - `price` (DECIMAL)
   - `stock` (INT)
   - `category_id` (FK → categories.id)

3. **`tags`** - Tags/Etiquetas
   - `id` (PK, AUTO_INCREMENT)
   - `name` (VARCHAR, UNIQUE)
   - `color` (VARCHAR)

4. **`product_tags`** - Tabela intermediária (N:N)
   - `product_id` (FK → products.id)
   - `tag_id` (FK → tags.id)
   - **Chave composta**: (product_id, tag_id)

---

## 🛠️ Comandos Úteis

### Parar os containers

```powershell
docker-compose down
```

### Ver logs dos containers

```powershell
# Todos os logs
docker-compose logs

# Apenas MariaDB
docker-compose logs mariadb

# Apenas PHPMyAdmin
docker-compose logs phpmyadmin

# Seguir logs em tempo real
docker-compose logs -f
```

### Reiniciar os containers

```powershell
docker-compose restart
```

### Ver status dos containers

```powershell
docker-compose ps
```

### Entrar no container MariaDB (CLI)

```powershell
docker exec -it exemplo04-mariadb mysql -uroot -proot
```

Depois você pode executar comandos SQL:
```sql
USE productdb;
SHOW TABLES;
SELECT * FROM products;
```

---

## 🔍 Explorando o Banco de Dados no PHPMyAdmin

### 1. Visualizar Estrutura das Tabelas

1. Selecione `productdb` no menu lateral
2. Clique em uma tabela (ex: `products`)
3. Clique na aba **"Estrutura"**

### 2. Ver Dados

1. Selecione a tabela
2. Clique na aba **"Procurar"** (Browse)

### 3. Executar Queries SQL

1. Clique na aba **"SQL"** no topo
2. Digite sua query, exemplo:
```sql
SELECT 
    p.id,
    p.name,
    p.price,
    c.name AS category_name
FROM products p
LEFT JOIN categories c ON p.category_id = c.id;
```
3. Clique em **"Executar"**

### 4. Ver Relacionamentos

1. Selecione `productdb`
2. Clique em **"Designer"** no menu superior
3. Você verá um diagrama visual dos relacionamentos

---

## 🎯 Exemplos de Queries Úteis

### Ver produtos com suas categorias

```sql
SELECT 
    p.name AS produto,
    p.price AS preco,
    c.name AS categoria
FROM products p
LEFT JOIN categories c ON p.category_id = c.id;
```

### Ver produtos com suas tags

```sql
SELECT 
    p.name AS produto,
    GROUP_CONCAT(t.name SEPARATOR ', ') AS tags
FROM products p
LEFT JOIN product_tags pt ON p.id = pt.product_id
LEFT JOIN tags t ON pt.tag_id = t.id
GROUP BY p.id, p.name;
```

### Contar produtos por categoria

```sql
SELECT 
    c.name AS categoria,
    COUNT(p.id) AS total_produtos
FROM categories c
LEFT JOIN products p ON c.id = p.category_id
GROUP BY c.id, c.name;
```

---

## ⚠️ Solução de Problemas

### Erro: "Conflict. The container name is already in use"

**Problema**: O exemplo04 ainda está rodando

**Solução**:
```powershell
cd D:\GitHub\NewtonPaiva\ArquiteturaAplicacaoWeb\PersistenciaDados\exemplo04
docker-compose down

cd D:\GitHub\NewtonPaiva\ArquiteturaAplicacaoWeb\PersistenciaDados\exemplo05
docker-compose up -d
```

### Erro: "Cannot connect to database"

**Problema**: O MariaDB ainda está inicializando

**Solução**: Aguarde 10-15 segundos e tente novamente

### Erro: "Access denied for user 'root'"

**Problema**: Senha incorreta

**Solução**: Use a senha `root`

### Porta 8081 já está em uso

**Problema**: Outro serviço está usando a porta 8081

**Solução 1** - Pare o outro serviço

**Solução 2** - Altere a porta no `docker-compose.yml`:
```yaml
phpmyadmin:
  ports:
    - "8082:80"  # Mude de 8081 para 8082
```

---

## 📊 Fluxo Completo de Trabalho

### 1. Inicie o Docker

```powershell
cd D:\GitHub\NewtonPaiva\ArquiteturaAplicacaoWeb\PersistenciaDados\exemplo05
docker-compose up -d
```

### 2. Inicie a Aplicação Spring Boot

```powershell
cd demo
mvn spring-boot:run
```

### 3. Abra o PHPMyAdmin

Navegador: http://localhost:8081

### 4. Teste a API

```powershell
# Criar categoria
Invoke-RestMethod -Uri "http://localhost:8080/categories" -Method Post -ContentType "application/json" -Body '{"name":"Eletrônicos","description":"Produtos eletrônicos"}'

# Ver no PHPMyAdmin
# Vá para: productdb → categories → Procurar
```

### 5. Acompanhe em Tempo Real

- **Terminal 1**: Logs da aplicação Spring Boot
- **Terminal 2**: Logs do Docker (`docker-compose logs -f`)
- **Navegador 1**: API (http://localhost:8080)
- **Navegador 2**: PHPMyAdmin (http://localhost:8081)

---

## 🎓 Dicas Profissionais

### 1. Backup do Banco de Dados

No PHPMyAdmin:
1. Selecione `productdb`
2. Clique em **"Exportar"**
3. Escolha o formato (SQL recomendado)
4. Clique em **"Executar"**

### 2. Importar Dados

1. Clique em **"Importar"**
2. Escolha o arquivo SQL
3. Clique em **"Executar"**

### 3. Ver Índices

1. Selecione a tabela
2. Clique em **"Estrutura"**
3. Role até **"Índices"**

### 4. Ver Constraints (Chaves Estrangeiras)

1. Selecione a tabela
2. Clique em **"Estrutura"**
3. Role até **"Relacionamentos"**

---

## 📝 Resumo Rápido

```powershell
# Iniciar
docker-compose up -d

# Acessar
# Navegador: http://localhost:8081
# Usuário: root
# Senha: root

# Parar
docker-compose down
```

---

**Pronto! Agora você pode acessar e gerenciar seu banco de dados MariaDB visualmente pelo PHPMyAdmin!** 🎉
