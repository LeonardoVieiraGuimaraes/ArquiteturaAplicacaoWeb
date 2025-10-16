# Exemplo 05 - Salvamento em MariaDB com Relacionamentos

## Descrição
Este exemplo demonstra como criar uma API REST utilizando Spring Boot com **relacionamentos entre entidades** em um banco de dados MariaDB. O projeto implementa um sistema completo de gerenciamento de produtos com:

- **Categorias** (relacionamento 1:N com produtos)
- **Tags** (relacionamento N:N com produtos)
- **Produtos** (relacionado a categorias e tags)

### 🎯 Relacionamentos Implementados

| Relacionamento | Descrição | Tipo |
|----------------|-----------|------|
| **Category ↔ Product** | Uma categoria pode ter vários produtos | **One-to-Many** (1:N) |
| **Product ↔ Category** | Cada produto pertence a uma categoria | **Many-to-One** (N:1) |
| **Product ↔ Tag** | Um produto pode ter várias tags | **Many-to-Many** (N:N) |
| **Tag ↔ Product** | Uma tag pode estar em vários produtos | **Many-to-Many** (N:N) |

## Tecnologias Utilizadas
- **Java 23**
- **Spring Boot 3.4.3**
- **Spring Web** - Para criação de APIs REST
- **Spring Data JPA** - Para persistência de dados com relacionamentos
- **MariaDB** - Banco de dados relacional
- **Maven** - Gerenciamento de dependências
- **Docker & Docker Compose** - Containerização do banco de dados

## Estrutura do Projeto

```
exemplo05/demo/
├── src/main/java/com/example/demo/
│   ├── DemoApplication.java          # Classe principal
│   ├── controller/
│   │   ├── CategoryController.java   # Controller de categorias
│   │   ├── TagController.java        # Controller de tags
│   │   ├── ProductController.java    # Controller de produtos
│   │   └── HelloController.java      # Controller simples
│   ├── service/
│   │   ├── CategoryService.java      # Lógica de negócio de categorias
│   │   ├── TagService.java           # Lógica de negócio de tags
│   │   └── ProductService.java       # Lógica de negócio de produtos
│   ├── repository/
│   │   ├── CategoryRepository.java   # Interface JPA para Category
│   │   ├── TagRepository.java        # Interface JPA para Tag
│   │   └── ProductRepository.java    # Interface JPA para Product
│   └── model/
│       ├── Category.java             # Entidade Category
│       ├── Tag.java                  # Entidade Tag
│       └── Product.java              # Entidade Product
└── src/main/resources/
    └── application.properties        # Configurações do MariaDB
```

## Modelo de Dados

### Diagrama de Relacionamentos

```
┌─────────────────┐
│    Category     │
├─────────────────┤
│ id (PK)         │
│ name            │
│ description     │
└────────┬────────┘
         │ 1
         │
         │ N
         │
┌────────┴────────┐         ┌──────────────┐
│    Product      │────────│  product_tags │
├─────────────────┤   N:N   │ (join table) │
│ id (PK)         │────────├──────────────┤
│ name            │         │ product_id   │
│ description     │         │ tag_id       │
│ price           │         └──────────────┘
│ stock           │                │
│ category_id(FK) │                │
└─────────────────┘                │ N:N
         │                         │
         │ N                       │
         │                         │
         │ 1                  ┌────┴────┐
         └────────────────────│   Tag   │
                              ├─────────┤
                              │ id (PK) │
                              │ name    │
                              │ color   │
                              └─────────┘
```

### Tabelas Criadas Automaticamente

O Hibernate criará automaticamente as seguintes tabelas:

1. **categories** - Armazena as categorias de produtos
2. **products** - Armazena os produtos com FK para category
3. **tags** - Armazena as tags/etiquetas
4. **product_tags** - Tabela intermediária do relacionamento N:N

## Modelos (Entities)

### 1. Category (Categoria)

```java
@Entity
@Table(name = "categories")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, unique = true)
    private String name;
    
    @Column(length = 500)
    private String description;
    
    // Relacionamento One-to-Many com Product
    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
    @JsonIgnore  // Evita recursão infinita no JSON
    private List<Product> products = new ArrayList<>();
}
```

**Campos:**
- `id` - ID auto-incrementado (chave primária)
- `name` - Nome da categoria (único, obrigatório)
- `description` - Descrição da categoria
- `products` - Lista de produtos desta categoria

### 2. Tag (Etiqueta)

```java
@Entity
@Table(name = "tags")
public class Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, unique = true)
    private String name;
    
    @Column(length = 7)
    private String color;  // Cor hexadecimal (#FF5733)
    
    // Relacionamento Many-to-Many com Product
    @ManyToMany(mappedBy = "tags")
    @JsonIgnore  // Evita recursão infinita no JSON
    private Set<Product> products = new HashSet<>();
}
```

**Campos:**
- `id` - ID auto-incrementado (chave primária)
- `name` - Nome da tag (único, obrigatório)
- `color` - Cor da tag em hexadecimal
- `products` - Conjunto de produtos com esta tag

### 3. Product (Produto)

```java
@Entity
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String name;
    
    @Column(length = 1000)
    private String description;
    
    @Column(precision = 10, scale = 2)
    private BigDecimal price;
    
    private Integer stock;
    
    // Relacionamento Many-to-One com Category
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;
    
    // Relacionamento Many-to-Many com Tag
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
        name = "product_tags",
        joinColumns = @JoinColumn(name = "product_id"),
        inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    private Set<Tag> tags = new HashSet<>();
}
```

**Campos:**
- `id` - ID auto-incrementado (chave primária)
- `name` - Nome do produto (obrigatório)
- `description` - Descrição detalhada
- `price` - Preço com 2 casas decimais
- `stock` - Quantidade em estoque
- `category` - Categoria do produto (FK)
- `tags` - Conjunto de tags do produto

## Configuração do MariaDB

### application.properties
```properties
spring.application.name=demo

# MariaDB Configuration (Docker na porta 3307)
spring.datasource.url=jdbc:mariadb://localhost:3307/productdb
spring.datasource.username=root
spring.datasource.password=root
spring.datasource.driver-class-name=org.mariadb.jdbc.Driver

# JPA/Hibernate
spring.jpa.database-platform=org.hibernate.dialect.MariaDBDialect
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
```

## Pré-requisitos

### 🐳 Docker (Recomendado)
- Docker Desktop instalado
- Docker Compose (incluído no Docker Desktop)

### 🔧 Software
- Java 21 ou superior
- Maven 3.9.8 ou superior

## Como Executar

### 1. Inicie o MariaDB com Docker

```powershell
docker-compose up -d
```

Isso iniciará:
- **MariaDB** na porta 3307
- **PHPMyAdmin** em http://localhost:8081

### 2. Navegue até a pasta do projeto

```powershell
cd demo
```

### 3. Compile o projeto

```powershell
mvn clean install -DskipTests
```

### 4. Execute a aplicação

```powershell
mvn spring-boot:run
```

Ou execute o JAR diretamente:

```powershell
java -jar .\target\demo-0.0.1-SNAPSHOT.jar
```

### 5. Acesse as interfaces

- **API REST**: http://localhost:8080
- **PHPMyAdmin**: http://localhost:8081
  - Servidor: `mariadb`
  - Usuário: `root`
  - Senha: `root`

## Endpoints da API

### 📦 Categories (Categorias)

#### Listar todas as categorias
```http
GET http://localhost:8080/categories
```

#### Buscar categoria por ID
```http
GET http://localhost:8080/categories/{id}
```

#### Buscar categoria por nome
```http
GET http://localhost:8080/categories/name/{name}
```

#### Criar nova categoria
```http
POST http://localhost:8080/categories
Content-Type: application/json

{
    "name": "Eletrônicos",
    "description": "Produtos eletrônicos e tecnologia"
}
```

#### Atualizar categoria
```http
PUT http://localhost:8080/categories/{id}
Content-Type: application/json

{
    "name": "Eletrônicos Premium",
    "description": "Produtos eletrônicos de alta qualidade"
}
```

#### Deletar categoria
```http
DELETE http://localhost:8080/categories/{id}
```

#### Contar categorias
```http
GET http://localhost:8080/categories/count
```

---

### 🏷️ Tags (Etiquetas)

#### Listar todas as tags
```http
GET http://localhost:8080/tags
```

#### Buscar tag por ID
```http
GET http://localhost:8080/tags/{id}
```

#### Buscar tag por nome
```http
GET http://localhost:8080/tags/name/{name}
```

#### Criar nova tag
```http
POST http://localhost:8080/tags
Content-Type: application/json

{
    "name": "Novo",
    "color": "#00FF00"
}
```

#### Atualizar tag
```http
PUT http://localhost:8080/tags/{id}
Content-Type: application/json

{
    "name": "Promoção",
    "color": "#FF0000"
}
```

#### Deletar tag
```http
DELETE http://localhost:8080/tags/{id}
```

#### Contar tags
```http
GET http://localhost:8080/tags/count
```

---

### 📱 Products (Produtos)

#### Listar todos os produtos
```http
GET http://localhost:8080/products
```

**Resposta com relacionamentos:**
```json
[
    {
        "id": 1,
        "name": "Notebook Dell XPS 15",
        "description": "Notebook profissional",
        "price": 5499.99,
        "stock": 10,
        "category": {
            "id": 1,
            "name": "Eletrônicos",
            "description": "Produtos eletrônicos"
        },
        "tags": [
            {
                "id": 1,
                "name": "Novo",
                "color": "#00FF00"
            },
            {
                "id": 2,
                "name": "Destaque",
                "color": "#FFD700"
            }
        ]
    }
]
```

#### Buscar produto por ID
```http
GET http://localhost:8080/products/{id}
```

#### Buscar produto por nome
```http
GET http://localhost:8080/products/name/{name}
```

#### Buscar produtos por categoria
```http
GET http://localhost:8080/products/category/{categoryId}
```

#### Criar novo produto (com relacionamentos)
```http
POST http://localhost:8080/products
Content-Type: application/json

{
    "name": "Notebook Dell XPS 15",
    "description": "Notebook profissional com processador Intel i7",
    "price": 5499.99,
    "stock": 10,
    "categoryId": 1,
    "tagIds": [1, 2, 3]
}
```

**Explicação dos campos:**
- `name`, `description`, `price`, `stock` - Dados do produto
- `categoryId` - ID da categoria (relacionamento Many-to-One)
- `tagIds` - Array de IDs das tags (relacionamento Many-to-Many)

#### Atualizar produto (com relacionamentos)
```http
PUT http://localhost:8080/products/{id}
Content-Type: application/json

{
    "name": "Notebook Dell XPS 15 2025",
    "description": "Versão atualizada",
    "price": 5999.99,
    "stock": 5,
    "categoryId": 2,
    "tagIds": [1, 4]
}
```

#### Adicionar tag a um produto
```http
POST http://localhost:8080/products/{productId}/tags/{tagId}
```

#### Remover tag de um produto
```http
DELETE http://localhost:8080/products/{productId}/tags/{tagId}
```

#### Deletar produto
```http
DELETE http://localhost:8080/products/{id}
```

#### Contar produtos
```http
GET http://localhost:8080/products/count
```

## Exemplos de Uso Completo

### Cenário 1: Criar um produto completo com categoria e tags

1. **Criar uma categoria:**
```powershell
Invoke-RestMethod -Uri "http://localhost:8080/categories" -Method Post -ContentType "application/json" -Body '{"name":"Notebooks","description":"Notebooks e laptops"}'
```
Resposta: `{"id": 1, "name": "Notebooks", ...}`

2. **Criar tags:**
```powershell
Invoke-RestMethod -Uri "http://localhost:8080/tags" -Method Post -ContentType "application/json" -Body '{"name":"Novo","color":"#00FF00"}'
Invoke-RestMethod -Uri "http://localhost:8080/tags" -Method Post -ContentType "application/json" -Body '{"name":"Promoção","color":"#FF0000"}'
```

3. **Criar produto com relacionamentos:**
```powershell
Invoke-RestMethod -Uri "http://localhost:8080/products" -Method Post -ContentType "application/json" -Body '{"name":"Dell XPS 15","description":"Notebook premium","price":5499.99,"stock":10,"categoryId":1,"tagIds":[1,2]}'
```

### Cenário 2: Buscar todos os produtos de uma categoria

```powershell
Invoke-RestMethod -Uri "http://localhost:8080/products/category/1" -Method Get
```

### Cenário 3: Adicionar uma tag a um produto existente

```powershell
Invoke-RestMethod -Uri "http://localhost:8080/products/1/tags/3" -Method Post
```

## Características dos Relacionamentos

### 1️⃣ One-to-Many (Category → Products)

**Características:**
- Uma categoria pode ter vários produtos
- Cada produto pertence a apenas uma categoria
- Chave estrangeira `category_id` na tabela `products`
- Cascade: operações em Category são propagadas para Products
- OrphanRemoval: produtos removidos da lista são deletados

**Exemplo SQL gerado:**
```sql
ALTER TABLE products ADD CONSTRAINT fk_category 
FOREIGN KEY (category_id) REFERENCES categories(id);
```

### 2️⃣ Many-to-Many (Products ↔ Tags)

**Características:**
- Um produto pode ter várias tags
- Uma tag pode estar em vários produtos
- Tabela intermediária `product_tags` gerenciada pelo JPA
- Não há exclusão em cascata das tags ao deletar produtos

**Exemplo SQL gerado:**
```sql
CREATE TABLE product_tags (
    product_id BIGINT NOT NULL,
    tag_id BIGINT NOT NULL,
    PRIMARY KEY (product_id, tag_id),
    FOREIGN KEY (product_id) REFERENCES products(id),
    FOREIGN KEY (tag_id) REFERENCES tags(id)
);
```

## Anotações JPA Utilizadas

| Anotação | Descrição | Uso |
|----------|-----------|-----|
| `@Entity` | Define uma classe como entidade JPA | Todas as entidades |
| `@Table` | Define o nome da tabela | Todas as entidades |
| `@Id` | Define a chave primária | Campo id |
| `@GeneratedValue` | Gera valores automaticamente | IDs auto-increment |
| `@Column` | Configura propriedades da coluna | Campos específicos |
| `@OneToMany` | Define relacionamento 1:N | Category → Products |
| `@ManyToOne` | Define relacionamento N:1 | Product → Category |
| `@ManyToMany` | Define relacionamento N:N | Products ↔ Tags |
| `@JoinColumn` | Define coluna de chave estrangeira | Relacionamentos |
| `@JoinTable` | Define tabela intermediária | N:N |
| `@JsonIgnore` | Ignora campo na serialização JSON | Evita recursão |

## Script Completo de Testes

```powershell
# 1. Criar categorias
$cat1 = Invoke-RestMethod -Uri "http://localhost:8080/categories" -Method Post -ContentType "application/json" -Body '{"name":"Eletrônicos","description":"Produtos eletrônicos"}'
$cat2 = Invoke-RestMethod -Uri "http://localhost:8080/categories" -Method Post -ContentType "application/json" -Body '{"name":"Livros","description":"Livros e publicações"}'

# 2. Criar tags
$tag1 = Invoke-RestMethod -Uri "http://localhost:8080/tags" -Method Post -ContentType "application/json" -Body '{"name":"Novo","color":"#00FF00"}'
$tag2 = Invoke-RestMethod -Uri "http://localhost:8080/tags" -Method Post -ContentType "application/json" -Body '{"name":"Promoção","color":"#FF0000"}'
$tag3 = Invoke-RestMethod -Uri "http://localhost:8080/tags" -Method Post -ContentType "application/json" -Body '{"name":"Destaque","color":"#FFD700"}'

# 3. Criar produtos com relacionamentos
$prod1 = Invoke-RestMethod -Uri "http://localhost:8080/products" -Method Post -ContentType "application/json" -Body '{"name":"Notebook Dell XPS","description":"Notebook profissional","price":5499.99,"stock":10,"categoryId":1,"tagIds":[1,2]}'

$prod2 = Invoke-RestMethod -Uri "http://localhost:8080/products" -Method Post -ContentType "application/json" -Body '{"name":"Mouse Logitech MX","description":"Mouse ergonômico","price":299.99,"stock":50,"categoryId":1,"tagIds":[1,3]}'

# 4. Listar todos os produtos com relacionamentos
Invoke-RestMethod -Uri "http://localhost:8080/products" -Method Get

# 5. Buscar produtos por categoria
Invoke-RestMethod -Uri "http://localhost:8080/products/category/1" -Method Get

# 6. Adicionar uma tag a um produto
Invoke-RestMethod -Uri "http://localhost:8080/products/1/tags/3" -Method Post

# 7. Atualizar produto
Invoke-RestMethod -Uri "http://localhost:8080/products/1" -Method Put -ContentType "application/json" -Body '{"name":"Notebook Dell XPS 15 2025","description":"Versão atualizada","price":5999.99,"stock":8,"categoryId":1,"tagIds":[1,2,3]}'

# 8. Listar contagens
Invoke-RestMethod -Uri "http://localhost:8080/categories/count" -Method Get
Invoke-RestMethod -Uri "http://localhost:8080/tags/count" -Method Get
Invoke-RestMethod -Uri "http://localhost:8080/products/count" -Method Get
```

## Solução de Problemas

### Erro: "Duplicate entry" ao criar categoria/tag
**Causa**: Tentando criar uma categoria ou tag com nome que já existe.
**Solução**: Os nomes de categorias e tags devem ser únicos.

### Erro: "Foreign key constraint fails"
**Causa**: Tentando associar um produto a uma categoria/tag que não existe.
**Solução**: Certifique-se de que os IDs fornecidos existem no banco.

### Erro: Recursão infinita no JSON
**Causa**: Relacionamentos bidirecionais causam loop na serialização.
**Solução**: Já implementado com `@JsonIgnore` nas coleções inversas.

### Produtos sem categoria aparecem como null
**Comportamento esperado**: A categoria é opcional. Se não fornecida, será `null`.

## Autor
Curso de Arquitetura de Aplicação Web - Newton Paiva

## Licença
Este projeto é livre para uso educacional.
