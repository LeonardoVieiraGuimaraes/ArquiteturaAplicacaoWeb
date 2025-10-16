# 📋 Resumo - Exemplo 05: Relacionamentos JPA com Spring Boot

## ✅ O que foi Implementado

### 🎯 Objetivo
Transformar o exemplo simples de produtos em um **sistema completo com relacionamentos entre entidades**, demonstrando os principais tipos de relacionamentos JPA/Hibernate.

---

## 📊 Arquitetura Implementada

### 🗂️ Entidades Criadas

#### 1. **Category** (Categoria)
```
Tabela: categories
Campos: id, name, description
Relacionamento: 1:N com Product
```

#### 2. **Tag** (Etiqueta)
```
Tabela: tags
Campos: id, name, color
Relacionamento: N:N com Product
```

#### 3. **Product** (Produto) - ATUALIZADO
```
Tabela: products
Campos: id, name, description, price, stock, category_id (FK)
Relacionamentos:
  - N:1 com Category
  - N:N com Tag (via tabela product_tags)
```

---

## 🔗 Tipos de Relacionamentos

### 1️⃣ **One-to-Many** (1:N)
**Entidades**: `Category` → `Product`

**Descrição**: Uma categoria pode ter muitos produtos

**Implementação**:
```java
// Em Category
@OneToMany(mappedBy = "category", cascade = CascadeType.ALL, orphanRemoval = true)
@JsonIgnore
private List<Product> products = new ArrayList<>();

// Em Product
@ManyToOne(fetch = FetchType.LAZY)
@JoinColumn(name = "category_id")
private Category category;
```

**SQL Gerado**:
```sql
ALTER TABLE products 
ADD CONSTRAINT fk_category 
FOREIGN KEY (category_id) REFERENCES categories(id);
```

---

### 2️⃣ **Many-to-Many** (N:N)
**Entidades**: `Product` ↔ `Tag`

**Descrição**: 
- Um produto pode ter várias tags
- Uma tag pode estar em vários produtos

**Implementação**:
```java
// Em Product
@ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
@JoinTable(
    name = "product_tags",
    joinColumns = @JoinColumn(name = "product_id"),
    inverseJoinColumns = @JoinColumn(name = "tag_id")
)
private Set<Tag> tags = new HashSet<>();

// Em Tag
@ManyToMany(mappedBy = "tags")
@JsonIgnore
private Set<Product> products = new HashSet<>();
```

**SQL Gerado**:
```sql
CREATE TABLE product_tags (
    product_id BIGINT NOT NULL,
    tag_id BIGINT NOT NULL,
    PRIMARY KEY (product_id, tag_id),
    FOREIGN KEY (product_id) REFERENCES products(id),
    FOREIGN KEY (tag_id) REFERENCES tags(id)
);
```

---

## 📁 Arquivos Criados/Modificados

### ✨ Novos Arquivos

#### Models
- ✅ `Category.java` - Entidade categoria com relacionamento 1:N
- ✅ `Tag.java` - Entidade tag com relacionamento N:N
- ✅ `Product.java` - **ATUALIZADO** com novos campos e relacionamentos

#### Repositories
- ✅ `CategoryRepository.java` - CRUD + queries customizadas
- ✅ `TagRepository.java` - CRUD + busca por conjunto de nomes
- ✅ `ProductRepository.java` - **ATUALIZADO** com novos métodos

#### Services
- ✅ `CategoryService.java` - Lógica de negócio de categorias
- ✅ `TagService.java` - Lógica de negócio de tags
- ✅ `ProductService.java` - **ATUALIZADO** com suporte a relacionamentos

#### Controllers
- ✅ `CategoryController.java` - API REST para categorias
- ✅ `TagController.java` - API REST para tags
- ✅ `ProductController.java` - **ATUALIZADO** com endpoints de relacionamento

#### Documentação
- ✅ `README.md` - Documentação completa com exemplos de relacionamentos

---

## 🌐 Endpoints da API

### 📦 Categories
```
GET    /categories              - Listar todas
GET    /categories/{id}         - Buscar por ID
GET    /categories/name/{name}  - Buscar por nome
POST   /categories              - Criar
PUT    /categories/{id}         - Atualizar
DELETE /categories/{id}         - Deletar
GET    /categories/count        - Contar
```

### 🏷️ Tags
```
GET    /tags              - Listar todas
GET    /tags/{id}         - Buscar por ID
GET    /tags/name/{name}  - Buscar por nome
POST   /tags              - Criar
PUT    /tags/{id}         - Atualizar
DELETE /tags/{id}         - Deletar
GET    /tags/count        - Contar
```

### 📱 Products (Atualizados)
```
GET    /products                        - Listar todos (com relacionamentos)
GET    /products/{id}                   - Buscar por ID (com relacionamentos)
GET    /products/name/{name}            - Buscar por nome
GET    /products/category/{categoryId}  - Buscar por categoria
POST   /products                        - Criar com relacionamentos
PUT    /products/{id}                   - Atualizar com relacionamentos
DELETE /products/{id}                   - Deletar
GET    /products/count                  - Contar
POST   /products/{id}/tags/{tagId}      - Adicionar tag
DELETE /products/{id}/tags/{tagId}      - Remover tag
```

---

## 🚀 Exemplo de Uso

### Criar Produto Completo

1. **Criar Categoria**:
```json
POST /categories
{
    "name": "Eletrônicos",
    "description": "Produtos eletrônicos"
}
// Retorna: {"id": 1, ...}
```

2. **Criar Tags**:
```json
POST /tags
{
    "name": "Novo",
    "color": "#00FF00"
}
// Retorna: {"id": 1, ...}

POST /tags
{
    "name": "Promoção",
    "color": "#FF0000"
}
// Retorna: {"id": 2, ...}
```

3. **Criar Produto com Relacionamentos**:
```json
POST /products
{
    "name": "Notebook Dell XPS 15",
    "description": "Notebook profissional",
    "price": 5499.99,
    "stock": 10,
    "categoryId": 1,      // Relacionamento Many-to-One
    "tagIds": [1, 2]      // Relacionamento Many-to-Many
}
```

4. **Resultado** (GET /products/1):
```json
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
            "name": "Promoção",
            "color": "#FF0000"
        }
    ]
}
```

---

## 🎓 Conceitos JPA Demonstrados

### Anotações Utilizadas

| Anotação | Propósito | Onde Usar |
|----------|-----------|-----------|
| `@Entity` | Marca classe como entidade JPA | Todas as entidades |
| `@Table` | Define nome da tabela | Todas as entidades |
| `@Id` | Define chave primária | Campo id |
| `@GeneratedValue` | Gera IDs automaticamente | Campo id com IDENTITY |
| `@Column` | Configura colunas | Campos com restrições |
| `@OneToMany` | Relacionamento 1:N | Category → Products |
| `@ManyToOne` | Relacionamento N:1 | Product → Category |
| `@ManyToMany` | Relacionamento N:N | Product ↔ Tag |
| `@JoinColumn` | Define FK | Relacionamento N:1 |
| `@JoinTable` | Define tabela intermediária | Relacionamento N:N |
| `@JsonIgnore` | Evita recursão JSON | Lado inverso |

### Cascade Types
```java
CascadeType.ALL          // Propaga todas as operações
CascadeType.PERSIST      // Propaga persist (criar)
CascadeType.MERGE        // Propaga merge (atualizar)
CascadeType.REMOVE       // Propaga remove (deletar)
```

### Fetch Types
```java
FetchType.LAZY           // Carrega sob demanda (padrão para *ToMany)
FetchType.EAGER          // Carrega imediatamente (padrão para *ToOne)
```

---

## 📊 Estrutura do Banco de Dados

### Tabelas Criadas Automaticamente

```
┌─────────────────┐
│   categories    │
├─────────────────┤
│ id (PK)         │
│ name (UNIQUE)   │
│ description     │
└─────────────────┘
         │
         │ 1:N
         ▼
┌─────────────────┐         ┌──────────────┐
│    products     │   N:N   │ product_tags │
├─────────────────┤◄───────►├──────────────┤
│ id (PK)         │         │ product_id   │
│ name            │         │ tag_id       │
│ description     │         └──────────────┘
│ price           │                │
│ stock           │                │ N:N
│ category_id(FK) │                │
└─────────────────┘                ▼
                              ┌─────────┐
                              │   tags  │
                              ├─────────┤
                              │ id (PK) │
                              │ name    │
                              │ color   │
                              └─────────┘
```

---

## ✅ Status do Projeto

### Compilação
```
✅ BUILD SUCCESS
✅ 14 arquivos Java compilados
✅ 0 erros de compilação
✅ JAR gerado: demo-0.0.1-SNAPSHOT.jar
```

### Próximos Passos (Testes)
1. ⏳ Iniciar Docker Compose (MariaDB + PHPMyAdmin)
2. ⏳ Executar a aplicação
3. ⏳ Testar endpoints de Category
4. ⏳ Testar endpoints de Tag
5. ⏳ Testar endpoints de Product com relacionamentos
6. ⏳ Verificar tabelas no PHPMyAdmin

---

## 🎯 Objetivos Alcançados

✅ **Relacionamento 1:N implementado** (Category → Product)  
✅ **Relacionamento N:N implementado** (Product ↔ Tag)  
✅ **CRUD completo para 3 entidades**  
✅ **Endpoints REST documentados**  
✅ **Queries customizadas em Repositories**  
✅ **Services com lógica de negócio**  
✅ **Controllers com tratamento de relacionamentos**  
✅ **Documentação completa no README**  
✅ **Exemplos práticos de uso**  
✅ **Prevenção de recursão JSON com @JsonIgnore**  
✅ **Cascade e OrphanRemoval configurados**  
✅ **Compilação bem-sucedida**  

---

## 📖 Documentação

- ✅ **README.md completo** - 700+ linhas de documentação
- ✅ Diagramas de relacionamento
- ✅ Explicação de cada anotação JPA
- ✅ Exemplos de requisições HTTP
- ✅ Scripts PowerShell de teste
- ✅ Solução de problemas comuns

---

## 🎉 Resultado Final

Um sistema completo de gerenciamento de produtos com:
- **3 entidades relacionadas** (Category, Tag, Product)
- **4 tabelas no banco** (categories, tags, products, product_tags)
- **2 tipos de relacionamentos** (1:N e N:N)
- **20+ endpoints REST**
- **Documentação profissional**

---

**Data de Criação**: 16 de outubro de 2025  
**Curso**: Arquitetura de Aplicação Web - Newton Paiva  
**Exemplo**: 05 - Relacionamentos JPA/Hibernate com MariaDB
