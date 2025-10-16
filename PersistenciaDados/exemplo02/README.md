# Exemplo 02 - Salvamento usando MongoDB

> **🎯 Flexibilidade de Deployment**
> 
> Este projeto oferece **duas formas de usar MongoDB**:
> - **🐳 Docker (Local)**: MongoDB em container - Ideal para desenvolvimento e testes
> - **☁️ Atlas (Cloud)**: MongoDB gerenciado na AWS - Ideal para produção
> 
> Troque entre eles facilmente editando o `application.properties`!
> 
> 📖 **Guia Completo do Docker**: [DOCKER.md](DOCKER.md)

## Descrição
Este exemplo demonstra como criar uma API REST utilizando Spring Boot com persistência de dados em **MongoDB**, um banco de dados NoSQL orientado a documentos. 

Você pode escolher usar MongoDB localmente com Docker ou na nuvem com MongoDB Atlas.

## Tecnologias Utilizadas
- **Java 25**
- **Spring Boot 3.4.3**
- **Spring Web** - Para criação de APIs REST
- **Spring Data MongoDB** - Para integração com MongoDB
- **MongoDB** - Banco de dados NoSQL
  - 🐳 **Local**: MongoDB em Docker container
  - ☁️ **Nuvem**: MongoDB Atlas (AWS)
- **Maven** - Gerenciamento de dependências
- **Docker** - Containerização (opcional)

## Estrutura do Projeto
```
exemplo02/demo/
├── src/main/java/com/example/demo/
│   ├── DemoApplication.java          # Classe principal
│   ├── controller/
│   │   └── ProductController.java    # Controller de produtos
│   ├── service/
│   │   └── ProductService.java       # Lógica de negócio
│   ├── repository/
│   │   └── ProductRepository.java    # Interface de acesso ao MongoDB
│   └── model/
│       └── Product.java              # Entidade Product (Documento MongoDB)
└── src/main/resources/
    └── application.properties        # Configurações do MongoDB
```

## Características

### Persistência com MongoDB
- Dados armazenados permanentemente no MongoDB
- IDs gerados automaticamente pelo MongoDB (ObjectId)
- Utiliza Spring Data MongoDB para operações CRUD
- Suporte a queries customizadas

### Entidade Product
```java
@Document(collection = "products")
public class Product {
    @Id
    private String id;  // MongoDB ObjectId
    private String name;
    // Getters e Setters
}
```

### Repository Pattern
Interface `ProductRepository` que estende `MongoRepository`:
```java
public interface ProductRepository extends MongoRepository<Product, String> {
    Optional<Product> findByName(String name);
}
```

## Configuração do MongoDB

Este projeto oferece **duas opções** de configuração do MongoDB:

### 🐳 Opção 1: MongoDB Docker (Local - Recomendado para Desenvolvimento)

Para usar o MongoDB em container Docker, veja o guia completo em [DOCKER.md](DOCKER.md).

**Vantagens:**
- ✅ Não precisa instalar MongoDB localmente
- ✅ Fácil de iniciar/parar (docker-compose up/down)
- ✅ Inclui Mongo Express (interface web)
- ✅ Dados persistidos em volume
- ✅ Ideal para desenvolvimento e testes

**Configuração em application.properties:**
```properties
spring.application.name=demo

# CONFIGURAÇÃO DOCKER (LOCAL) - ATIVO
spring.data.mongodb.host=localhost
spring.data.mongodb.port=27017
spring.data.mongodb.database=productdb
```

### ☁️ Opção 2: MongoDB Atlas (Cloud)

**Vantagens:**
- ✅ Banco gerenciado na nuvem
- ✅ Backup automático
- ✅ Escalabilidade facilitada
- ✅ Alta disponibilidade (replica set)

**Configuração em application.properties:**
```properties
spring.application.name=demo

# CONFIGURAÇÃO ATLAS (CLOUD) - COMENTADA
# spring.data.mongodb.uri=mongodb+srv://leonardovieiraxy:okHXviB3zYBAOgdf@cluster0.zh78p5f.mongodb.net/productdb?retryWrites=true&w=majority
```

**Informações do Cluster Atlas:**
- **Provider**: AWS
- **Região**: South America (São Paulo - sa-east-1)
- **Tipo**: Replica Set com 3 nodes
- **Segurança**: SSL/TLS habilitado
- **Database**: productdb

### 🔄 Como Alternar Entre Docker e Atlas

1. **Para usar Docker (Local):**
   - Descomente as linhas do Docker
   - Comente a linha do Atlas
   - Execute: `docker-compose up -d`

2. **Para usar Atlas (Cloud):**
   - Comente as linhas do Docker
   - Descomente a linha do Atlas
   - Pare o Docker: `docker-compose down`

> **📖 Documentação Completa**: Consulte [DOCKER.md](DOCKER.md) para instruções detalhadas sobre o uso do MongoDB com Docker.

## Pré-requisitos

### 🐳 Opção Docker (Recomendada):
- **Docker** e **Docker Compose** instalados
- ✅ **Nenhuma instalação adicional necessária!**

### ☁️ Opção Atlas (Cloud):
- Conta no MongoDB Atlas (gratuita)
- ✅ **Nenhuma instalação local necessária!**

### 🔧 Ambas as opções precisam:
- Java 21 ou superior
- Maven 3.9.8 ou superior

## Como Executar

### 🐳 Usando Docker (Recomendado)

#### 1. Inicie o MongoDB com Docker Compose
```powershell
docker-compose up -d
```

#### 2. Navegue até a pasta do projeto
```powershell
cd demo
```

#### 3. Compile o projeto
```powershell
mvn clean install -DskipTests
```

#### 4. Execute a aplicação
```powershell
java -jar .\target\demo-0.0.1-SNAPSHOT.jar
```

#### 5. Acesse as interfaces
- **API REST**: http://localhost:8080
- **Mongo Express**: http://localhost:8082

#### 6. Para parar os containers
```powershell
docker-compose down
```

> 📖 **Guia Completo**: Veja [DOCKER.md](DOCKER.md) para instruções detalhadas

---

### ☁️ Usando MongoDB Atlas (Cloud)

#### 1. Configure o application.properties
Descomente a linha do Atlas e comente as linhas do Docker.

#### 2. Navegue até a pasta do projeto
```powershell
cd demo
```

#### 3. Compile o projeto
```powershell
mvn clean install -DskipTests
```

#### 4. Execute a aplicação
```powershell
java -jar .\target\demo-0.0.1-SNAPSHOT.jar
```

#### 5. A aplicação estará disponível em
- **API REST**: http://localhost:8080

## Endpoints Disponíveis

### 1. Listar todos os produtos
```http
GET http://localhost:8080/products
```

### 2. Buscar produto por ID (ObjectId)
```http
GET http://localhost:8080/products/{id}
```
Exemplo: `GET http://localhost:8080/products/507f1f77bcf86cd799439011`

### 3. Buscar produto por nome
```http
GET http://localhost:8080/products/name/{name}
```

### 4. Adicionar novo produto
```http
POST http://localhost:8080/products
Content-Type: application/json

{
    "name": "Notebook"
}
```

### 5. Atualizar produto
```http
PUT http://localhost:8080/products/{id}
Content-Type: application/json

{
    "name": "Notebook Gamer"
}
```

### 6. Deletar produto
```http
DELETE http://localhost:8080/products/{id}
```

## Como Executar

### 1. Certifique-se de que o MongoDB está rodando
```powershell
mongod
```

### 2. Navegue até a pasta do projeto
```powershell
cd PersistenciaDados\exemplo02\demo
```

### 3. Compile o projeto
```powershell
mvn clean install
```

### 4. Execute a aplicação
```powershell
mvn spring-boot:run
```

### 5. A aplicação estará disponível em: `http://localhost:8080`

## Testando a API

### Usando PowerShell (Recomendado no Windows)

**Adicionar produto:**
```powershell
$body = @{ name = "Mouse Gamer" } | ConvertTo-Json
Invoke-RestMethod -Uri "http://localhost:8080/products" -Method Post -Body $body -ContentType "application/json"
```

**Listar produtos:**
```powershell
Invoke-RestMethod -Uri "http://localhost:8080/products" -Method Get
```

**Buscar produto por nome:**
```powershell
Invoke-RestMethod -Uri "http://localhost:8080/products/name/Mouse Gamer" -Method Get
```

**Atualizar produto:**
```powershell
$body = @{ name = "Mouse Gamer RGB" } | ConvertTo-Json
Invoke-RestMethod -Uri "http://localhost:8080/products/{id}" -Method Put -Body $body -ContentType "application/json"
```

**Deletar produto:**
```powershell
Invoke-RestMethod -Uri "http://localhost:8080/products/{id}" -Method Delete
```

### Usando cURL

**Adicionar produto:**
```powershell
curl -X POST http://localhost:8080/products `
  -H "Content-Type: application/json" `
  -d '{\"name\":\"Mouse\"}'
```

**Listar produtos:**
```powershell
curl http://localhost:8080/products
```

**Buscar produto por nome:**
```powershell
curl http://localhost:8080/products/name/Mouse
```

**Atualizar produto:**
```powershell
curl -X PUT http://localhost:8080/products/{id} `
  -H "Content-Type: application/json" `
  -d '{\"name\":\"Mouse Gamer\"}'
```

**Deletar produto:**
```powershell
curl -X DELETE http://localhost:8080/products/{id}
```

## Comandos MongoDB Atlas Úteis

### Acessar MongoDB Atlas Web Console
1. Acesse: https://cloud.mongodb.com/
2. Faça login com suas credenciais
3. Selecione seu cluster
4. Clique em "Browse Collections"
5. Navegue até o database `productdb` > collection `products`

### Ver dados diretamente no Atlas
Você pode visualizar, editar e deletar documentos diretamente na interface web do MongoDB Atlas.

## Vantagens do MongoDB Atlas
- ✅ **Sem instalação local**: Funciona direto na nuvem
- ✅ **Persistência permanente**: Dados não se perdem ao reiniciar
- ✅ **Escalabilidade**: Fácil aumentar capacidade
- ✅ **Alta disponibilidade**: Replica Set com 3 nodes
- ✅ **Backup automático**: MongoDB gerencia backups
- ✅ **Segurança SSL/TLS**: Conexões criptografadas
- ✅ **Monitoramento**: Dashboard de métricas incluído
- ✅ **Multi-região**: Possibilidade de deploy global

## Vantagens do MongoDB (NoSQL)
- ✅ Persistência permanente de dados
- ✅ Escalabilidade horizontal
- ✅ Flexibilidade de esquema (schema-less)
- ✅ Alto desempenho para operações de leitura/escrita
- ✅ Suporte a documentos JSON/BSON
- ✅ Ideal para dados não estruturados

## Desvantagens
- ❌ Requer conexão com internet (para MongoDB Atlas)
- ❌ Tier gratuito tem limitações de armazenamento (512MB)
- ❌ Latência maior que banco local (conexão de rede)

## Quando Usar MongoDB Atlas
- Aplicações que precisam de persistência permanente na nuvem
- Projetos sem infraestrutura própria de servidores
- Prototipagem rápida (tier gratuito disponível)
- Aplicações com dados não estruturados ou variáveis
- Sistemas que precisam de alta disponibilidade
- Desenvolvimento distribuído (acesso de qualquer lugar)

## Estrutura de Dados no MongoDB Atlas
```json
{
  "_id": "68f0fb032c95f448538e25c6",
  "name": "Notebook Dell XPS 15",
  "_class": "com.example.demo.model.Product"
}
```
**Nota**: O `_id` é gerado automaticamente pelo MongoDB como ObjectId.

## Diferenças do Exemplo 01
| Aspecto | Exemplo 01 (Lista) | Exemplo 02 (MongoDB Atlas) |
|---------|-------------------|---------------------------|
| Persistência | Não | Sim (Nuvem) |
| ID | Long sequencial | String (ObjectId) |
| Repository | Não usa | MongoRepository |
| Service | Não usa | ProductService |
| Banco de Dados | Memória | MongoDB Atlas (AWS) |
| Infraestrutura | Nenhuma | Cloud (sem instalação) |

## Próximos Passos
- Ver **exemplo03** para persistência com H2 (banco em memória SQL)
- Ver **exemplo04** para persistência com MariaDB (banco relacional)
- Implementar validações e tratamento de erros
- Adicionar paginação e ordenação

## Troubleshooting

### 🐳 Erros com Docker

**Erro: "Connection refused" ao conectar no MongoDB**
- Verifique se os containers estão rodando: `docker-compose ps`
- Inicie os containers: `docker-compose up -d`
- Verifique os logs: `docker-compose logs mongodb`

**Erro: "Port already in use"**
- Porta 27017 (MongoDB) ou 8082 (Mongo Express) já está em uso
- Pare outros serviços usando essas portas
- Ou altere as portas no `docker-compose.yml`

**Erro: "Cannot connect to Docker daemon"**
- Verifique se o Docker Desktop está rodando
- No Windows, certifique-se que o Docker Desktop está inicializado

### ☁️ Erros com MongoDB Atlas

**Erro: "Connection refused" ou "Timeout"**
- Verifique sua conexão com a internet
- Confirme se as credenciais do MongoDB Atlas estão corretas
- Verifique se o IP está na whitelist do MongoDB Atlas (0.0.0.0/0 permite todos)

**Erro: "Authentication failed"**
- Verifique o usuário e senha no `application.properties`
- Confirme que o usuário tem permissões no database `productdb`

**Erro: "Database not found"**
- O banco `productdb` é criado automaticamente na primeira inserção
- Não é necessário criar manualmente no Atlas

> 💡 **Dica**: Para mais detalhes sobre Docker, consulte [DOCKER.md](DOCKER.md)

## Testado e Funcionando ✅

### 🐳 Testes com Docker (Local)
- ✅ Containers iniciados com sucesso
- ✅ MongoDB rodando na porta 27017
- ✅ Mongo Express acessível em http://localhost:8082
- ✅ CRUD completo funcionando
- ✅ IDs gerados como ObjectId
- ✅ Dados persistidos em volume Docker
- ✅ Fácil start/stop com docker-compose

### ☁️ Testes com MongoDB Atlas (Cloud)
Este exemplo foi **testado com sucesso** em 16/10/2025 com os seguintes resultados:
- ✅ Conexão com MongoDB Atlas estabelecida
- ✅ Replica Set com 3 nodes conectado (SA-EAST-1)
- ✅ CRUD completo funcionando
- ✅ IDs gerados como ObjectId
- ✅ Dados persistidos permanentemente na nuvem
- ✅ SSL/TLS habilitado

### Exemplos de IDs Reais Gerados:
```
68f0fb032c95f448538e25c6 - Notebook Dell XPS 15 (2025)
68f0fb0a2c95f448538e25c7 - Mouse Logitech MX Master 3
```

> 📖 **Documentação Adicional**: Veja [DOCKER.md](DOCKER.md) para guia completo do Docker

## Autor
Curso de Arquitetura de Aplicação Web - Newton Paiva

## Licença
Este projeto é livre para uso educacional.

---

## 📊 Relatório de Testes - Exemplo 02

### ✅ Status: TESTADO E APROVADO

**Data do Teste**: 16 de outubro de 2025

### Operações Testadas:
1. ✅ **POST** - Criar 3 produtos
2. ✅ **GET** - Listar todos os produtos
3. ✅ **GET** - Buscar produto por nome
4. ✅ **PUT** - Atualizar produto existente
5. ✅ **DELETE** - Deletar produto
6. ✅ **Conexão SSL** - Conectado ao cluster MongoDB Atlas

### Cluster MongoDB Atlas Conectado:
```
Cluster: cluster0.zh78p5f.mongodb.net
Replica Set: atlas-2kktqy-shard-0
Provider: AWS
Região: South America (São Paulo)
Nodes: 3 (1 Primary + 2 Secondary)
Status: ✅ Todos os nodes conectados
```

### Conclusão:
**Todos os endpoints funcionando perfeitamente com MongoDB Atlas!** 🎉