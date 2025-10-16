# 🐳 Docker Setup - Exemplo 04

## O que está incluído

Este Docker Compose cria dois containers:

1. **MariaDB** (porta 3306) - Banco de dados
2. **PHPMyAdmin** (porta 8081) - Interface web para gerenciar o banco

---

## 🚀 Como usar

### 1. Iniciar os containers
```powershell
docker-compose up -d
```

### 2. Verificar se está rodando
```powershell
docker-compose ps
```

### 3. Acessar PHPMyAdmin
Abra no navegador: **http://localhost:8081**

**Credenciais:**
- Servidor: `mariadb`
- Usuário: `root`
- Senha: `root`

### 4. Criar a tabela (opcional)
O Spring Boot cria automaticamente, mas se quiser criar manualmente:

```sql
CREATE TABLE IF NOT EXISTS products (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);
```

### 5. Executar a aplicação Spring Boot
```powershell
cd demo
mvn clean install -DskipTests
java -jar .\target\demo-0.0.1-SNAPSHOT.jar
```

### 6. Testar a API
```powershell
# Adicionar produto
$body = @{ name = "Notebook" } | ConvertTo-Json
Invoke-RestMethod -Uri "http://localhost:8080/products" -Method Post -Body $body -ContentType "application/json"

# Listar produtos
Invoke-RestMethod -Uri "http://localhost:8080/products" -Method Get
```

---

## 🛑 Parar os containers

```powershell
docker-compose down
```

**Para parar E deletar os dados:**
```powershell
docker-compose down -v
```

---

## 📊 Portas utilizadas

| Serviço | Porta | URL |
|---------|-------|-----|
| MariaDB | 3306 | localhost:3306 |
| PHPMyAdmin | 8081 | http://localhost:8081 |
| Spring Boot | 8080 | http://localhost:8080 |

---

## 🔧 Comandos úteis

### Ver logs dos containers
```powershell
docker-compose logs -f
```

### Reiniciar containers
```powershell
docker-compose restart
```

### Parar apenas um serviço
```powershell
docker-compose stop mariadb
docker-compose stop phpmyadmin
```

### Iniciar apenas um serviço
```powershell
docker-compose start mariadb
docker-compose start phpmyadmin
```

---

## ✅ Pronto!

Agora você tem:
- ✅ MariaDB rodando no Docker
- ✅ PHPMyAdmin para gerenciar o banco
- ✅ Database `productdb` criado automaticamente
- ✅ Sem necessidade de instalar nada localmente
