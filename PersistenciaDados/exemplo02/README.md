# 🍃 Exemplo 02 - Persistência com MongoDB (NoSQL)

## 📑 Índice

1. [Visão Geral](#-visão-geral)
2. [Início Rápido](#-início-rápido)
3. [O que é MongoDB?](#-o-que-é-mongodb)
4. [Estrutura do Projeto](#-estrutura-do-projeto)
5. [Configuração: Docker vs Atlas](#-configuração-docker-vs-atlas)
6. [Código Explicado Linha por Linha](#-código-explicado-linha-por-linha)
7. [Endpoints da API](#-endpoints-da-api)
8. [Testando a API](#-testando-a-api)
9. [MongoDB vs SQL](#-mongodb-vs-sql-comparação-completa)
10. [Dicas para Aula](#-dicas-para-aula-e-demonstração)
11. [Exercícios Práticos](#-exercícios-práticos-para-alunos)
12. [Troubleshooting](#-troubleshooting)
13. [Recursos Adicionais](#-recursos-adicionais)

---

## 🎯 Visão Geral

### Descrição

Este exemplo demonstra como criar uma API REST com Spring Boot usando **MongoDB**, um banco de dados **NoSQL orientado a documentos**. Os dados são armazenados **permanentemente** e sobrevivem a reinicializações.

> 🌟 **Flexibilidade Total:** Escolha entre **Docker (local)** ou **Atlas (cloud)** - troque com um clique!

### Por que MongoDB?

- ✅ **Schema Flexível** - Adicione campos sem migrar banco
- ✅ **JSON Nativo** - Armazena documentos como JSON
- ✅ **Escalável** - Sharding horizontal automático
- ✅ **Rápido** - Performance em leitura/escrita

### Quando Usar MongoDB?

| Cenário | Usar MongoDB? |
|---------|---------------|
| 🎮 **Dados não-relacionais** | ✅ SIM - Documentos, logs, IoT |
| 📱 **Apps mobile/web** | ✅ SIM - JSON direto do frontend |
| 📊 **BI/Analytics** | ✅ SIM - Agregações poderosas |
| 💳 **Transações complexas** | ⚠️ Use SQL (MariaDB, PostgreSQL) |
| 🔗 **Muitos relacionamentos** | ⚠️ Use SQL (exemplo05) |

---

## 🚀 Início Rápido

### Opção A: Docker (Recomendado para Desenvolvimento)

```powershell
# 1. Inicie MongoDB + Mongo Express
cd D:\GitHub\NewtonPaiva\ArquiteturaAplicacaoWeb\PersistenciaDados\exemplo02
docker-compose up -d

# 2. Execute a API
cd demo
.\mvnw.cmd spring-boot:run

# 3. Acesse
start http://localhost:8080/products      # API
start http://localhost:8082                # Mongo Express (GUI)
```

### Opção B: Atlas (Cloud)

```powershell
# 1. Configure application.properties (descomente linha Atlas)
# 2. Execute
cd D:\GitHub\NewtonPaiva\ArquiteturaAplicacaoWeb\PersistenciaDados\exemplo02\demo
.\mvnw.cmd spring-boot:run

# 3. Dados na nuvem AWS!
```

---

## 💡 O que é MongoDB?

### Conceito

**MongoDB** = Banco de dados **NoSQL** que armazena dados como **documentos JSON**.

```
SQL (Tabelas)              NoSQL (Documentos)
┌─────────────────┐       ┌─────────────────────────┐
│  products       │       │  products (collection)  │
├─────┬───────────┤       ├─────────────────────────┤
│ id  │ name      │       │ {                       │
├─────┼───────────┤       │   "_id": ObjectId(...), │
│ 1   │ Notebook  │       │   "name": "Notebook"    │
│ 2   │ Mouse     │       │ }                       │
└─────┴───────────┘       │ {                       │
                          │   "_id": ObjectId(...), │
                          │   "name": "Mouse",      │
                          │   "color": "Black" ←    │
                          │ }                  Schema │
                          └───────────────────Flexível┘
```

### Terminologia

| SQL | MongoDB | Descrição |
|-----|---------|-----------|
| Database | Database | Banco de dados |
| Table | Collection | Conjunto de documentos |
| Row | Document | Um registro (JSON) |
| Column | Field | Um campo do documento |
| Primary Key | `_id` | Identificador único (ObjectId) |

---

## 🗂️ Estrutura do Projeto

### Árvore de Arquivos

```
exemplo02/
├── docker-compose.yml                    # 🐳 Docker: MongoDB + Mongo Express
├── init-mongo.js                         # 📜 Script de inicialização
├── DOCKER.md                             # 📖 Guia completo Docker
├── TESTE_MONGO_ATLAS.md                  # ☁️ Guia Atlas
│
└── demo/
    ├── pom.xml                           # Maven
    ├── mvnw.cmd                          # Maven Wrapper
    ├── src/main/java/com/example/demo/
    │   ├── DemoApplication.java          # 🚀 Main
    │   ├── controller/
    │   │   └── ProductController.java    # 🎮 REST Controller
    │   ├── service/
    │   │   └── ProductService.java       # 💼 Business Logic
    │   ├── repository/
    │   │   └── ProductRepository.java    # 🗄️ MongoDB Access
    │   └── model/
    │       └── Product.java              # 📄 Document Model
    └── src/main/resources/
        └── application.properties        # ⚙️ Config (Docker ou Atlas)
```

### Tecnologias

| Tecnologia | Versão | Finalidade |
|------------|--------|------------|
| **Java** | 25 | Linguagem |
| **Spring Boot** | 3.4.3 | Framework REST |
| **Spring Data MongoDB** | (incluído) | Integração MongoDB |
| **MongoDB** | 7.0 | Banco NoSQL |
| **Docker** | (opcional) | Container MongoDB |
| **Mongo Express** | 1.0 | GUI Web para MongoDB |
| **MongoDB Atlas** | (opcional) | MongoDB na nuvem AWS |

---

## 🔧 Configuração: Docker vs Atlas

### 📊 Comparação

| Aspecto | 🐳 Docker (Local) | ☁️ Atlas (Cloud) |
|---------|-------------------|------------------|
| **Instalação** | Docker Desktop | Conta gratuita |
| **Internet** | ❌ Não precisa | ✅ Precisa |
| **Dados** | No seu PC | AWS São Paulo |
| **Velocidade** | ⚡⚡⚡ Muito rápido | ⚡⚡ Rápido |
| **Backup** | Manual | Automático |
| **Interface** | Mongo Express | Atlas UI |
| **Ideal para** | Desenvolvimento | Produção/testes |
| **Custo** | Grátis | Grátis (512MB) |

---

### 🐳 Configuração Docker (Opção 1)

#### Pré-requisitos

- Docker Desktop instalado
- Docker Compose (incluído no Desktop)

#### Arquivo: docker-compose.yml

```yaml
version: '3.8'

services:
  mongodb:
    image: mongo:7.0
    container_name: mongodb
    restart: always
    ports:
      - "27017:27017"
    environment:
      MONGO_INITDB_DATABASE: productdb
    volumes:
      - mongodb_data:/data/db
      - ./init-mongo.js:/docker-entrypoint-initdb.d/init-mongo.js:ro
    networks:
      - mongo-network

  mongo-express:
    image: mongo-express:1.0-20
    container_name: mongo-express
    restart: always
    ports:
      - "8082:8081"
    environment:
      ME_CONFIG_MONGODB_SERVER: mongodb
      ME_CONFIG_MONGODB_PORT: 27017
      ME_CONFIG_BASICAUTH: false
    depends_on:
      - mongodb
    networks:
      - mongo-network

volumes:
  mongodb_data:

networks:
  mongo-network:
    driver: bridge
```

#### application.properties (Docker)

```properties
spring.application.name=demo

# 🐳 DOCKER LOCAL (ATIVO)
spring.data.mongodb.host=localhost
spring.data.mongodb.port=27017
spring.data.mongodb.database=productdb

# ☁️ ATLAS CLOUD (COMENTADO)
# spring.data.mongodb.uri=mongodb+srv://user:pass@cluster.mongodb.net/productdb
```

#### Comandos Docker

```powershell
# Iniciar
docker-compose up -d

# Ver logs
docker-compose logs -f mongodb

# Parar
docker-compose down

# Parar e APAGAR dados
docker-compose down -v
```

#### Acessar Mongo Express

```
http://localhost:8082

Database: productdb
Collection: products
```

---

### ☁️ Configuração Atlas (Opção 2)

#### Passo 1: Criar Conta

1. Acesse: https://www.mongodb.com/cloud/atlas/register
2. Crie conta gratuita
3. Crie cluster:
   - Provider: **AWS**
   - Região: **South America (São Paulo)**
   - Tier: **M0 Free**

#### Passo 2: Configurar Acesso

**Network Access:**
- IP: `0.0.0.0/0` (qualquer IP)

**Database Access:**
- Username: `seuusuario`
- Password: `suasenha`

#### Passo 3: Connection String

Cluster → Connect → Drivers → Connection String:

```
mongodb+srv://seuusuario:suasenha@cluster0.xyz123.mongodb.net/productdb?retryWrites=true&w=majority
```

#### application.properties (Atlas)

```properties
spring.application.name=demo

# 🐳 DOCKER LOCAL (COMENTADO)
# spring.data.mongodb.host=localhost
# spring.data.mongodb.port=27017
# spring.data.mongodb.database=productdb

# ☁️ ATLAS CLOUD (ATIVO)
spring.data.mongodb.uri=mongodb+srv://seuusuario:suasenha@cluster0.xyz123.mongodb.net/productdb?retryWrites=true&w=majority
```

#### Vantagens Atlas

- ✅ Backup automático
- ✅ Replica set (3 nodes)
- ✅ SSL/TLS habilitado
- ✅ Monitoramento incluído
- ✅ 512MB grátis permanentemente

---

### 🔄 Como Alternar Entre Docker e Atlas

**Docker → Atlas:**

```properties
# application.properties

# Comente estas linhas
# spring.data.mongodb.host=localhost
# spring.data.mongodb.port=27017
# spring.data.mongodb.database=productdb

# Descomente esta
spring.data.mongodb.uri=mongodb+srv://...
```

```powershell
# Pare o Docker
docker-compose down
```

**Atlas → Docker:**

```properties
# Comente esta
# spring.data.mongodb.uri=mongodb+srv://...

# Descomente estas
spring.data.mongodb.host=localhost
spring.data.mongodb.port=27017
spring.data.mongodb.database=productdb
```

```powershell
# Inicie o Docker
docker-compose up -d
```

---

## 🔍 Código Explicado Linha por Linha

### 📄 Modelo: Product.java

```java
package com.example.demo.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "products")  // ← Collection no MongoDB
public class Product {
    
    @Id                              // ← _id do MongoDB
    private String id;               // ← ObjectId (String)
    
    private String name;
    
    // Construtor vazio
    public Product() {}
    
    // Construtor com parâmetros
    public Product(String id, String name) {
        this.id = id;
        this.name = name;
    }
    
    // Getters e Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
}
```

**Anotações MongoDB:**

| Anotação | Significado |
|----------|-------------|
| `@Document(collection = "products")` | "Armazene na collection 'products'" |
| `@Id` | "Este campo é o _id do documento" |
| `private String id` | ObjectId em formato String |

**Diferença vs SQL:**

| SQL (JPA) | MongoDB |
|-----------|---------|
| `@Entity` | `@Document` |
| `@Table(name = "...")` | `@Document(collection = "...")` |
| `@Id` + `Long id` | `@Id` + `String id` (ObjectId) |
| `@GeneratedValue` | Geração automática pelo MongoDB |

---

### 🗄️ Repository: ProductRepository.java

```java
package com.example.demo.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import com.example.demo.model.Product;
import java.util.Optional;

@Repository
public interface ProductRepository extends MongoRepository<Product, String> {
    
    // Query method customizado
    Optional<Product> findByName(String name);
}
```

**Spring Data MongoDB:**

| Código | O que faz |
|--------|-----------|
| `extends MongoRepository<Product, String>` | Herda CRUD completo para MongoDB |
| `<Product, String>` | Tipo da entidade + Tipo do ID (String = ObjectId) |
| `findByName(String name)` | Spring gera query: `{ "name": "..." }` |

**Métodos Herdados (grátis!):**

```java
save(product)              // Inserir ou atualizar
findById(id)               // Buscar por _id
findAll()                  // Buscar todos
deleteById(id)             // Deletar por _id
count()                    // Contar documentos
existsById(id)             // Verifica se existe
```

---

### 💼 Service: ProductService.java

```java
package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.demo.model.Product;
import com.example.demo.repository.ProductRepository;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    
    @Autowired
    private ProductRepository repository;
    
    // Listar todos
    public List<Product> findAll() {
        return repository.findAll();
    }
    
    // Buscar por ID
    public Optional<Product> findById(String id) {
        return repository.findById(id);
    }
    
    // Buscar por nome
    public Optional<Product> findByName(String name) {
        return repository.findByName(name);
    }
    
    // Salvar (criar ou atualizar)
    public Product save(Product product) {
        return repository.save(product);
    }
    
    // Deletar
    public void deleteById(String id) {
        repository.deleteById(id);
    }
}
```

**Padrão Service:**

- ✅ Controller não fala direto com Repository
- ✅ Service tem lógica de negócio
- ✅ Facilita testes (mock do Service)

---

### 🎮 Controller: ProductController.java

```java
package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.demo.model.Product;
import com.example.demo.service.ProductService;
import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {
    
    @Autowired
    private ProductService service;
    
    // GET: Listar todos
    @GetMapping
    public List<Product> getAll() {
        return service.findAll();
    }
    
    // GET: Buscar por ID
    @GetMapping("/{id}")
    public ResponseEntity<Product> getById(@PathVariable String id) {
        return service.findById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }
    
    // GET: Buscar por nome
    @GetMapping("/name/{name}")
    public ResponseEntity<Product> getByName(@PathVariable String name) {
        return service.findByName(name)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }
    
    // POST: Criar
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Product create(@RequestBody Product product) {
        return service.save(product);
    }
    
    // PUT: Atualizar
    @PutMapping("/{id}")
    public ResponseEntity<Product> update(
        @PathVariable String id,
        @RequestBody Product updatedProduct
    ) {
        return service.findById(id)
            .map(existing -> {
                updatedProduct.setId(id);
                return ResponseEntity.ok(service.save(updatedProduct));
            })
            .orElse(ResponseEntity.notFound().build());
    }
    
    // DELETE: Deletar
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        if (service.findById(id).isPresent()) {
            service.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
```

**ResponseEntity:**

| Código | HTTP Status | Quando usar |
|--------|-------------|-------------|
| `ResponseEntity.ok(product)` | 200 OK | Sucesso com dados |
| `ResponseEntity.notFound().build()` | 404 Not Found | Não encontrou |
| `@ResponseStatus(CREATED)` | 201 Created | Recurso criado |
| `ResponseEntity.noContent()` | 204 No Content | Deletado com sucesso |

---

## 🌐 Endpoints da API

### Tabela Resumo

| Método | Endpoint | Descrição | Body | Resposta |
|--------|----------|-----------|------|----------|
| **GET** | `/products` | Lista todos | - | Array JSON + HTTP 200 |
| **GET** | `/products/{id}` | Busca por ObjectId | - | Objeto JSON + HTTP 200/404 |
| **GET** | `/products/name/{name}` | Busca por nome | - | Objeto JSON + HTTP 200/404 |
| **POST** | `/products` | Cria novo | `{"name": "..."}` | Objeto JSON + HTTP 201 |
| **PUT** | `/products/{id}` | Atualiza | `{"name": "..."}` | Objeto JSON + HTTP 200/404 |
| **DELETE** | `/products/{id}` | Deleta | - | HTTP 204/404 |

---

## 🧪 Testando a API

### Fluxo Completo

**1. Iniciar MongoDB:**

```powershell
docker-compose up -d
```

**2. Iniciar API:**

```powershell
cd demo
.\mvnw.cmd spring-boot:run
```

**3. POST - Criar produtos:**

```powershell
# Produto 1
curl -X POST http://localhost:8080/products `
  -H "Content-Type: application/json" `
  -d '{"name": "Notebook"}'

# Resposta:
# {
#   "id": "67aef123456789abcdef0001",  ← ObjectId gerado!
#   "name": "Notebook"
# }

# Produto 2
curl -X POST http://localhost:8080/products `
  -H "Content-Type: application/json" `
  -d '{"name": "Mouse Gamer"}'

# Produto 3
curl -X POST http://localhost:8080/products `
  -H "Content-Type: application/json" `
  -d '{"name": "Teclado Mecânico"}'
```

**4. GET - Listar todos:**

```powershell
curl http://localhost:8080/products

# Resposta:
# [
#   {"id":"67aef123456789abcdef0001","name":"Notebook"},
#   {"id":"67aef123456789abcdef0002","name":"Mouse Gamer"},
#   {"id":"67aef123456789abcdef0003","name":"Teclado Mecânico"}
# ]
```

**5. GET - Buscar por ID:**

```powershell
curl http://localhost:8080/products/67aef123456789abcdef0001

# Resposta:
# {"id":"67aef123456789abcdef0001","name":"Notebook"}
```

**6. GET - Buscar por nome:**

```powershell
curl http://localhost:8080/products/name/Mouse%20Gamer

# Resposta:
# {"id":"67aef123456789abcdef0002","name":"Mouse Gamer"}
```

**7. PUT - Atualizar:**

```powershell
curl -X PUT http://localhost:8080/products/67aef123456789abcdef0001 `
  -H "Content-Type: application/json" `
  -d '{"name": "Notebook Gamer RTX"}'

# Resposta:
# {"id":"67aef123456789abcdef0001","name":"Notebook Gamer RTX"}
```

**8. DELETE - Deletar:**

```powershell
curl -X DELETE http://localhost:8080/products/67aef123456789abcdef0003

# HTTP 204 No Content
```

**9. Reiniciar API e verificar persistência:**

```powershell
# Ctrl+C na API
# mvnw spring-boot:run novamente
# GET /products

curl http://localhost:8080/products
# Dados ainda estão lá! ✅
```

---

### Testando com Mongo Express

**1. Abra:** http://localhost:8082

**2. Navegue:**
- Database: `productdb`
- Collection: `products`

**3. Ver documentos:**

```json
{
  "_id": ObjectId("67aef123456789abcdef0001"),
  "name": "Notebook Gamer RTX",
  "_class": "com.example.demo.model.Product"
}
```

**4. Editar direto no Mongo Express:**
- Clique em "Edit"
- Modifique o JSON
- Save

---

## 📊 MongoDB vs SQL: Comparação Completa

### Estrutura de Dados

**SQL (MariaDB):**

```sql
CREATE TABLE products (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);

INSERT INTO products (name) VALUES ('Notebook');
INSERT INTO products (name) VALUES ('Mouse');
```

**MongoDB:**

```javascript
// Collection: products
{
  "_id": ObjectId("..."),
  "name": "Notebook"
}

{
  "_id": ObjectId("..."),
  "name": "Mouse",
  "color": "Black"  // ← Schema flexível!
}
```

---

### Tabela Comparativa

| Aspecto | SQL (MariaDB) | MongoDB |
|---------|---------------|---------|
| **Tipo** | Relacional | NoSQL (Documentos) |
| **Schema** | Fixo, migrar para mudar | Flexível, muda na hora |
| **Formato** | Linhas e colunas | Documentos JSON |
| **ID** | BIGINT auto-increment | ObjectId (12 bytes) |
| **Relacionamentos** | Foreign Keys, JOINs | Embedded ou References |
| **Transações** | ACID completo | ACID (desde 4.0) |
| **Escalabilidade** | Vertical (mais RAM) | Horizontal (sharding) |
| **Queries** | SQL | MQL (MongoDB Query Language) |
| **Ideal para** | Dados relacionados | Docs, logs, IoT, mobile |

---

### Exemplos de Queries

**Buscar por nome:**

**SQL:**
```sql
SELECT * FROM products WHERE name = 'Notebook';
```

**MongoDB:**
```javascript
db.products.find({ "name": "Notebook" })
```

**Spring Data:**
```java
// Ambos usam o mesmo código!
productRepository.findByName("Notebook");
```

---

**Buscar com LIKE:**

**SQL:**
```sql
SELECT * FROM products WHERE name LIKE '%book%';
```

**MongoDB:**
```javascript
db.products.find({ "name": /book/i })
```

**Spring Data:**
```java
@Query("{'name': {$regex: ?0, $options: 'i'}}")
List<Product> findByNameContaining(String name);
```

---

### Quando Usar Cada Um?

| Cenário | Use SQL | Use MongoDB |
|---------|---------|-------------|
| **E-commerce com carrinho, pedidos, pagamentos** | ✅ | ❌ |
| **Blog com posts, comentários** | ✅ | ✅ |
| **App mobile com perfis de usuário** | ⚠️ | ✅ |
| **IoT com milhões de sensores** | ❌ | ✅ |
| **Sistema bancário** | ✅ | ❌ |
| **Catálogo de produtos** | ✅ | ✅ |
| **Analytics/Big Data** | ⚠️ | ✅ |

---

## 🎓 Dicas para Aula e Demonstração

### 📋 Ordem Recomendada (60 min):

**1. Introdução (5 min):**
- "Na aula passada: ArrayList (perdeu dados)"
- "Hoje: MongoDB - dados PERMANENTES!"
- Mostrar: SQL vs NoSQL (quadro branco)

**2. Docker vs Atlas (5 min):**
- "2 formas de usar MongoDB:"
  - Docker: PC local
  - Atlas: Nuvem AWS
- Mostrar `application.properties`

**3. Iniciar Docker (5 min):**
```powershell
docker-compose up -d
docker ps
```
- Abrir Mongo Express: http://localhost:8082
- Mostrar: vazio ainda

**4. Código Explicado (15 min):**
- `Product.java` - `@Document`
- `ProductRepository` - extends MongoRepository
- `ProductController` - ResponseEntity

**5. Testes Ao Vivo (20 min):**
- POST: Criar 3 produtos
- Mongo Express: mostrar documentos
- GET: Listar
- PUT: Atualizar
- DELETE: Remover
- **Reiniciar API:** Dados ainda lá! ✅

**6. Comparação vs Exemplo01 (5 min):**
```
exemplo01 (ArrayList)  → Reiniciou? 💀 PERDEU
exemplo02 (MongoDB)    → Reiniciou? ✅ MANTÉM
```

**7. Exercícios (5 min)**

---

### 🎬 Script de Demonstração (5 Cenas):

#### Cena 1: "O Problema do Exemplo01" (3 min)

```
Professor: "Lembram do exemplo01?"
[Mostrar ArrayList em ProductController do ex01]

"Problema: reinicia, perde tudo!"
[Desenhar no quadro:]

RAM (Volátil)        vs    Disco (Permanente)
ArrayList                  MongoDB
💀 Reinicia = perde        ✅ Reinicia = mantém
```

#### Cena 2: "MongoDB: SQL vs NoSQL" (7 min)

```
Professor: "MongoDB é diferente!"

[Quadro branco - lado a lado:]

SQL                    NoSQL (MongoDB)
┌───┬────────┐        {
│id │ name   │          "_id": ...,
├───┼────────┤          "name": "Note",
│ 1 │ Note   │          "price": 2500  ← NOVO!
└───┴────────┘        }

"Adicionei 'price' SEM migração!"
"MongoDB = Schema flexível!"
```

#### Cena 3: "Docker em Ação" (10 min)

```
Professor: "Vamos iniciar o MongoDB!"
[Terminal]

docker-compose up -d

"Vejam: 2 containers!"
docker ps
# mongodb
# mongo-express

"Mongo Express = GUI para MongoDB"
[Abrir http://localhost:8082]

"Vejam: Database 'productdb' criado!"
"Collection 'products' virá quando criarmos!"
```

#### Cena 4: "Testando a API" (25 min)

```
Professor: "Vamos criar produtos!"
[Terminal - API]

cd demo
.\mvnw.cmd spring-boot:run

[Aguardar iniciar]

"API no ar! Vamos criar:"

curl -X POST http://localhost:8080/products `
  -H "Content-Type: application/json" `
  -d '{"name": "Notebook"}'

"Vejam a resposta:"
{
  "id": "67aef123...",  ← ObjectId!
  "name": "Notebook"
}

"Agora no Mongo Express..."
[F5 em http://localhost:8082]

"Vejam: Collection 'products' criada!"
"Documento apareceu!"

[Criar mais 2 produtos]

"Agora GET:"
curl http://localhost:8080/products

[Mostrar 3 produtos]
```

#### Cena 5: "A Grande Diferença" (10 min)

```
Professor: "Momento da verdade!"
"Vou REINICIAR a API como fiz no exemplo01"

[Ctrl+C na API]

"API parada. Dados perdidos?"
[Alunos respondem: "Não!"]

"Vamos confirmar:"
[mvnw spring-boot:run novamente]
[Aguardar start]

curl http://localhost:8080/products

"Vejam: 3 produtos AINDA LÁ!"
"Por quê? MongoDB salvou no DISCO!"

[Quadro:]
exemplo01: RAM → 💀
exemplo02: Disco → ✅

"Essa é a diferença: PERSISTÊNCIA!"
```

---

## 📝 Exercícios Práticos para Alunos

### 🎯 Exercício 1: Docker + Primeira Inserção (Iniciante)

**Objetivo:** Configurar Docker e criar primeiro documento

**Tarefas:**
1. Inicie Docker: `docker-compose up -d`
2. Verifique containers: `docker ps`
3. Inicie API: `.\mvnw.cmd spring-boot:run`
4. Crie um produto:
   ```powershell
   curl -X POST http://localhost:8080/products `
     -H "Content-Type: application/json" `
     -d '{"name": "Meu Produto"}'
   ```
5. Abra Mongo Express: http://localhost:8082
6. Navegue: productdb → products

**Entrega:**
- Screenshot do Mongo Express mostrando o documento

---

### 🎯 Exercício 2: CRUD Completo MongoDB (Intermediário)

**Objetivo:** Realizar todas as operações

**Tarefas:**
1. Crie 5 produtos com nomes diferentes
2. Liste todos (GET /products)
3. Copie um ObjectId do response
4. Busque por ID (GET /products/{id})
5. Atualize esse produto (PUT)
6. Delete outro produto (DELETE)
7. Liste novamente

**Entrega:**
- Comandos curl de cada operação
- Screenshot do GET final mostrando 4 produtos

---

### 🎯 Exercício 3: Persistência vs Memória (Avançado)

**Objetivo:** Comprovar persistência

**Tarefas:**
1. Crie 3 produtos
2. Liste (guarde o response)
3. **PARE a API** (Ctrl+C)
4. **PARE o Docker** (`docker-compose down`)
5. **INICIE o Docker** (`docker-compose up -d`)
6. **INICIE a API** novamente
7. Liste produtos novamente

**Entrega:**
- Screenshot do GET antes de parar
- Screenshot do GET depois de reiniciar
- Explicação: "Os dados persistiram porque..."

---

### 🎯 Exercício 4: Atlas Cloud (Desafio)

**Objetivo:** Usar MongoDB na nuvem

**Tarefas:**
1. Crie conta no MongoDB Atlas
2. Crie cluster free (M0)
3. Configure Network Access (0.0.0.0/0)
4. Crie usuário de banco
5. Obtenha connection string
6. Modifique `application.properties`:
   ```properties
   spring.data.mongodb.uri=mongodb+srv://...
   ```
7. Inicie API e teste

**Entrega:**
- Screenshot do Atlas mostrando cluster
- Screenshot do Atlas mostrando dados inseridos

---

### 🎯 Exercício 5: Query Customizada (Expert)

**Objetivo:** Criar query method personalizado

**Tarefas:**
1. Adicione campo `price` em `Product.java`:
   ```java
   private Double price;
   // Getter e Setter
   ```

2. Adicione método em `ProductRepository`:
   ```java
   List<Product> findByPriceGreaterThan(Double price);
   ```

3. Adicione endpoint em `ProductController`:
   ```java
   @GetMapping("/expensive/{price}")
   public List<Product> getExpensive(@PathVariable Double price) {
       return service.findByPriceGreaterThan(price);
   }
   ```

4. Adicione método no Service

5. Teste:
   ```powershell
   # Criar produtos com preços
   curl -X POST http://localhost:8080/products `
     -d '{"name":"Notebook","price":2500.00}'
   
   # Buscar caros (>2000)
   curl http://localhost:8080/products/expensive/2000
   ```

**Entrega:**
- Código de `Product.java`, `ProductRepository`, `ProductService`, `ProductController`
- Screenshot da query funcionando

---

## 🆘 Troubleshooting

### ❌ Problema: Docker não inicia

**Erro:**
```
Error response from daemon: Ports are not available
```

**Solução:**

```powershell
# Verificar se porta 27017 está em uso
netstat -ano | Select-String ":27017"

# Matar processo
taskkill /PID [PID] /F

# Ou mudar porta no docker-compose.yml:
ports:
  - "27018:27017"  # Porta externa diferente
```

---

### ❌ Problema: Connection refused ao MongoDB

**Erro:**
```
com.mongodb.MongoSocketOpenException: Exception opening socket
```

**Solução:**

```powershell
# 1. Verificar se container está rodando
docker ps

# 2. Ver logs do MongoDB
docker-compose logs mongodb

# 3. Verificar application.properties
# Docker:
spring.data.mongodb.host=localhost  # ✅
spring.data.mongodb.port=27017      # ✅

# Não pode ser:
spring.data.mongodb.host=mongodb    # ❌ (só funciona dentro do Docker)
```

---

### ❌ Problema: "Cast to ObjectId failed"

**Erro:**
```
Cast to ObjectId failed for value "123" at path "_id"
```

**Causa:**
- Tentando buscar com ID inválido

**Solução:**

```powershell
# ❌ ERRADO: ID numérico
curl http://localhost:8080/products/123

# ✅ CERTO: ObjectId completo
curl http://localhost:8080/products/67aef123456789abcdef0001
```

---

### ❌ Problema: Dados sumiram após `docker-compose down -v`

**Sintoma:**
- Criou produtos, fez `down -v`, dados sumiram

**Causa:**
- Flag `-v` **APAGA os volumes** (dados permanentes)

**Solução:**

```powershell
# ✅ Parar SEM apagar dados:
docker-compose down

# ❌ Parar E apagar dados:
docker-compose down -v  # Só use para "resetar tudo"
```

---

### ❌ Problema: Atlas - Authentication failed

**Erro:**
```
Command failed with error 18 (AuthenticationFailed)
```

**Solução:**

1. **Verifique usuário/senha** na connection string
2. **Verifique IP whitelist:** 
   - Atlas → Network Access → `0.0.0.0/0`
3. **Escape caracteres especiais** na senha:
   ```
   Senha: P@ssw0rd!
   Encode: P%40ssw0rd%21
   
   https://www.urlencoder.org/
   ```

---

### ❌ Problema: Mongo Express não abre

**Sintoma:**
- http://localhost:8082 não carrega

**Solução:**

```powershell
# 1. Verificar se container está rodando
docker ps | Select-String "mongo-express"

# 2. Ver logs
docker-compose logs mongo-express

# 3. Verificar porta
# docker-compose.yml:
ports:
  - "8082:8081"  # Externa:Interna

# Acesse na porta EXTERNA: 8082
```

---

## 📚 Recursos Adicionais

### 📖 Documentação Oficial

- **MongoDB:** https://www.mongodb.com/docs/
- **MongoDB University:** https://learn.mongodb.com/ (cursos grátis!)
- **Spring Data MongoDB:** https://docs.spring.io/spring-data/mongodb/reference/
- **Mongo Express:** https://github.com/mongo-express/mongo-express

### 🎓 Tutoriais Recomendados

1. **[MongoDB Tutorial](https://www.mongodb.com/docs/manual/tutorial/)** - Oficial
2. **[Spring Data MongoDB](https://www.baeldung.com/spring-data-mongodb-tutorial)** - Baeldung
3. **[MongoDB University M001](https://learn.mongodb.com/courses/mongodb-basics)** - Curso grátis

### 🔧 Ferramentas Úteis

- **MongoDB Compass** - Cliente desktop oficial (GUI)
- **Studio 3T** - IDE para MongoDB
- **mongosh** - Shell interativo do MongoDB
- **Robo 3T** - Cliente leve e gratuito

---

## ✅ Checklist de Aprendizado

- [ ] Entendi a diferença entre SQL e NoSQL
- [ ] Sei o que é um documento JSON no MongoDB
- [ ] Configurei Docker com MongoDB + Mongo Express
- [ ] Executei a API com sucesso
- [ ] Criei documentos via POST
- [ ] Listei documentos via GET
- [ ] Atualizei documento via PUT
- [ ] Deletei documento via DELETE
- [ ] Visualizei dados no Mongo Express
- [ ] Comprovei persistência (reiniciei e dados permaneceram)
- [ ] Entendi `@Document` e `@Id`
- [ ] Entendi `MongoRepository`
- [ ] Criei conta no MongoDB Atlas (opcional)
- [ ] Testei com Atlas cloud (opcional)
- [ ] Completei os 5 exercícios

---

## 🚀 Próximos Passos

### Comparação de Exemplos

```
✅ exemplo01 - ArrayList (memória, perdida)
✅ exemplo02 - MongoDB (NoSQL, permanente) ← VOCÊ ESTÁ AQUI
➡️ exemplo03 - H2 (SQL em memória)
➡️ exemplo04 - MariaDB (SQL permanente)
➡️ exemplo05 - MariaDB + Relacionamentos
```

### O que Aprender em Seguida:

1. **exemplo03** - H2 Database
   - SQL em memória (como ArrayList mas SQL)
   - Console H2 embutido

2. **exemplo04** - MariaDB
   - SQL permanente (como MongoDB mas SQL)
   - Docker XAMPP

3. **exemplo05** - Relacionamentos
   - 1:N (Category → Products)
   - N:N (Products ↔ Tags)
   - Quando usar SQL vs NoSQL

---

**📝 Documentação criada para:**
- Java 25
- Spring Boot 3.4.3
- MongoDB 7.0
- Docker Compose 3.8
- MongoDB Atlas Free Tier

**🎯 Objetivo:** Guia completo de persistência com MongoDB (NoSQL), do básico ao avançado, para aulas práticas de Arquitetura de Aplicações Web.

---

**Made with ❤️ for Learning NoSQL & MongoDB**
