# 🐳 Docker Setup - Exemplo 02 (MongoDB)

## O que está incluído

Este Docker Compose cria dois containers:

1. **MongoDB** (porta 27017) - Banco de dados NoSQL
2. **Mongo Express** (porta 8082) - Interface web para gerenciar o MongoDB

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

### 3. Acessar Mongo Express (Interface Web)
Abra no navegador: **http://localhost:8082**

**Credenciais:**
- Username: (não necessário, acesso direto)
- Database: `productdb`

### 4. Atualizar application.properties
O arquivo já está configurado para usar Docker local!

**Configuração Ativa (Docker Local):**
```properties
spring.data.mongodb.host=localhost
spring.data.mongodb.port=27017
spring.data.mongodb.database=productdb
spring.data.mongodb.username=root
spring.data.mongodb.password=root
```

**Configuração Alternativa (MongoDB Atlas - Comentada):**
```properties
# spring.data.mongodb.uri=mongodb+srv://...
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

## 🔄 Alternando entre Docker e MongoDB Atlas

### Para usar MongoDB LOCAL (Docker):
1. No `application.properties`, mantenha as linhas do Docker descomentadas
2. Comente a linha do MongoDB Atlas
3. Execute: `docker-compose up -d`

### Para usar MongoDB ATLAS (Nuvem):
1. No `application.properties`, comente as linhas do Docker
2. Descomente a linha do MongoDB Atlas
3. Não precisa rodar o Docker

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
| MongoDB | 27017 | localhost:27017 |
| Mongo Express | 8082 | http://localhost:8082 |
| Spring Boot | 8080 | http://localhost:8080 |

---

## 🔧 Comandos úteis

### Ver logs dos containers
```powershell
docker-compose logs -f
```

### Ver logs apenas do MongoDB
```powershell
docker-compose logs -f mongodb
```

### Reiniciar containers
```powershell
docker-compose restart
```

### Conectar diretamente no MongoDB
```powershell
docker exec -it exemplo02-mongodb mongosh -u root -p root
```

Dentro do mongosh:
```javascript
use productdb
db.products.find()
db.products.countDocuments()
```

---

## 🆚 Comparação: Docker vs Atlas

| Aspecto | Docker Local | MongoDB Atlas |
|---------|-------------|---------------|
| **Setup** | `docker-compose up` | Já configurado |
| **Instalação** | Nenhuma | Nenhuma |
| **Internet** | Não necessária | Necessária |
| **Latência** | ~1ms | ~250ms |
| **Armazenamento** | Ilimitado | 512MB (free tier) |
| **Backup** | Manual | Automático |
| **Custo** | Gratuito | Gratuito (tier básico) |
| **Acesso Remoto** | Não | Sim |
| **Melhor para** | Desenvolvimento | Produção/Compartilhamento |

---

## ✅ Vantagens do Docker Local

- ✅ **Mais rápido**: Latência mínima
- ✅ **Offline**: Funciona sem internet
- ✅ **Sem limites**: Armazenamento ilimitado
- ✅ **Privado**: Dados no seu computador
- ✅ **Desenvolvimento**: Ideal para testar localmente

## ✅ Vantagens do MongoDB Atlas

- ✅ **Nuvem**: Acesso de qualquer lugar
- ✅ **Backup**: Backup automático
- ✅ **Escalável**: Fácil aumentar capacidade
- ✅ **Gerenciado**: Sem manutenção
- ✅ **Produção**: Pronto para produção

---

## 🎯 Recomendação

**Use Docker Local para:**
- ✅ Desenvolvimento
- ✅ Testes locais
- ✅ Aprendizado
- ✅ Trabalhar offline

**Use MongoDB Atlas para:**
- ✅ Produção
- ✅ Trabalho em equipe
- ✅ Deploy na nuvem
- ✅ Demonstrações online

---

## 📝 Notas Importantes

1. **Porta 27017**: Certifique-se de que não há outro MongoDB rodando
2. **Dados Persistentes**: Os dados ficam salvos no volume Docker `mongodb_data`
3. **Credenciais**: Usuário `root` / Senha `root` (apenas para desenvolvimento!)
4. **Init Script**: O arquivo `init-mongo.js` é executado na primeira inicialização

---

## ✅ Pronto!

Agora você pode escolher entre MongoDB local (Docker) ou MongoDB Atlas (nuvem) facilmente, apenas comentando/descomentando linhas no `application.properties`!
