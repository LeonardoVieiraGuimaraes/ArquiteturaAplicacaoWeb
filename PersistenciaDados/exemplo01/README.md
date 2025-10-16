# 📚 Exemplo 01 - Persistência em Memória (ArrayList)

## 📑 Índice

1. [🎯 Visão Geral](#-visão-geral)
2. [🚀 Início Rápido](#-início-rápido)
3. [💡 O que é Persistência em Memória?](#-o-que-é-persistência-em-memória)
4. [🗂️ Estrutura do Projeto](#️-estrutura-do-projeto)
5. [🔍 Código Explicado Linha por Linha](#-código-explicado-linha-por-linha)
6. [🌐 Endpoints da API](#-endpoints-da-api)
7. [🧪 Testando a API](#-testando-a-api)
8. [⚖️ Vantagens e Desvantagens](#️-vantagens-e-desvantagens)
9. [📊 Comparação com Outras Formas de Persistência](#-comparação-com-outras-formas-de-persistência)
10. [🎓 Dicas para Aula](#-dicas-para-aula-e-demonstração)
11. [📝 Exercícios Práticos](#-exercícios-práticos-para-alunos)
12. [🆘 Troubleshooting](#-troubleshooting)
13. [🚀 Próximos Passos](#-próximos-passos)

---

## 🎯 Visão Geral

### Descrição

Este exemplo demonstra a forma **mais simples** de criar uma API REST com Spring Boot, armazenando dados em uma **lista em memória (ArrayList)**. 

> ⚠️ **IMPORTANTE:** Não há persistência em banco de dados! Ao reiniciar a aplicação, **todos os dados são perdidos**.

### Por que começar com ArrayList?

- ✅ **Simplicidade Extrema** - Sem banco de dados, sem configuração
- ✅ **Foco no Básico** - Aprenda REST, Controllers, JSON
- ✅ **Feedback Rápido** - Execute e teste em segundos
- ✅ **Sem Dependências** - Só precisa de Java e Spring Boot

### Quando Usar?

| Cenário | Usar ArrayList? |
|---------|-----------------|
| 🎓 **Aprendizado inicial** | ✅ SIM - Ótimo para começar |
| 🧪 **Protótipos rápidos** | ✅ SIM - Teste APIs rapidamente |
| 💼 **Produção** | ❌ NÃO - Use banco de dados |
| 🔄 **Dados permanentes** | ❌ NÃO - Usa H2, MongoDB, MariaDB |

---

## 🚀 Início Rápido

### Executar em 3 Comandos

```powershell
# 1. Navegue até a pasta
cd D:\GitHub\NewtonPaiva\ArquiteturaAplicacaoWeb\PersistenciaDados\exemplo01\demo

# 2. Execute a aplicação
.\mvnw.cmd spring-boot:run

# 3. Teste no navegador
start http://localhost:8080/products
```

### Primeira Requisição

```powershell
# Adicionar um produto
curl -X POST http://localhost:8080/products `
  -H "Content-Type: application/json" `
  -d '{"name": "Notebook"}'

# Listar produtos
curl http://localhost:8080/products
```

---

## 💡 O que é Persistência em Memória?

### Conceito

**Persistência em Memória** = Armazenar dados na **RAM** durante a execução do programa.

```
┌─────────────────────────────────────────┐
│         Memória RAM (Volátil)           │
├─────────────────────────────────────────┤
│                                         │
│  ArrayList<Product> products            │
│  ┌─────────────────────────────────┐   │
│  │ [0] Product(id=1, name="Note")  │   │
│  │ [1] Product(id=2, name="Mouse") │   │
│  │ [2] Product(id=3, name="Teclado")│  │
│  └─────────────────────────────────┘   │
│                                         │
│  ⚠️ Reiniciou? TUDO APAGADO! ⚠️        │
│                                         │
└─────────────────────────────────────────┘
```

### Ciclo de Vida dos Dados

```
Iniciar App → ArrayList vazio → Adicionar dados → Usar dados
                                                       ↓
Reiniciar App ← DADOS PERDIDOS! ← Parar App ← Continuar usando
```

---

## 🗂️ Estrutura do Projeto

### Árvore de Arquivos

```
exemplo01/demo/
├── pom.xml                               # Maven - Dependências
├── mvnw.cmd                              # Maven Wrapper (Windows)
├── src/
│   └── main/
│       ├── java/com/example/demo/
│       │   ├── DemoApplication.java      # 🚀 Classe principal (main)
│       │   ├── controller/
│       │   │   ├── HelloController.java  # 👋 Controller básico (/hello)
│       │   │   └── ProductController.java # 📦 Controller de produtos (FOCO!)
│       │   └── model/
│       │       └── Product.java          # 📋 Modelo Product
│       └── resources/
│           └── application.properties    # Configurações
```

### Tecnologias Utilizadas

| Tecnologia | Versão | Finalidade |
|------------|--------|------------|
| **Java** | 11+ | Linguagem de programação |
| **Spring Boot** | 3.4.3 | Framework para APIs REST |
| **Spring Web** | (incluído) | Anotações REST (@RestController) |
| **Maven** | 3.6+ | Gerenciador de dependências |

---

## 🔍 Código Explicado Linha por Linha

### 📋 Modelo: Product.java

**Localização:** `src/main/java/com/example/demo/model/Product.java`

```java
package com.example.demo.model;

public class Product {
    
    // Atributos
    private Long id;      // ID único do produto
    private String name;  // Nome do produto
    
    // Construtor vazio (necessário para JSON)
    public Product() {}
    
    // Construtor com parâmetros
    public Product(Long id, String name) {
        this.id = id;
        this.name = name;
    }
    
    // Getters e Setters (Spring precisa deles para JSON!)
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
}
```

**Explicação:**

| Linha | O que faz |
|-------|-----------|
| `private Long id;` | Identificador único (1, 2, 3...) |
| `private String name;` | Nome do produto ("Notebook", "Mouse") |
| `public Product() {}` | Construtor vazio para Spring deserializar JSON |
| `getId()`, `setId()` | Getters/Setters para Spring acessar atributos privados |

---

### 📦 Controller: ProductController.java

**Localização:** `src/main/java/com/example/demo/controller/ProductController.java`

#### Estrutura Geral

```java
package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import com.example.demo.model.Product;

@RestController                    // ← 1️⃣ Define como Controller REST
@RequestMapping("/products")       // ← 2️⃣ Base URL: /products
public class ProductController {
    
    // 3️⃣ Armazenamento em memória
    private final List<Product> products = new ArrayList<>();
    
    // 4️⃣ Gerador de IDs
    private long nextId = 1L;
    
    // ... endpoints ...
}
```

**Anotações Importantes:**

| Anotação | Significado |
|----------|-------------|
| `@RestController` | "Esta classe é um controller REST que retorna JSON" |
| `@RequestMapping("/products")` | "Todas as rotas começam com /products" |
| `private final List<Product>` | Lista final (não pode ser reatribuída) |
| `private long nextId = 1L` | Contador de IDs (1, 2, 3...) |

---

#### Endpoint 1: Listar Todos os Produtos (GET)

```java
// GET: Listar todos os produtos
@GetMapping                        // ← GET /products
public List<Product> getAllProducts() {
    return products;               // ← Retorna a lista completa
}
```

**Passo a Passo:**

1. Cliente faz: `GET http://localhost:8080/products`
2. Spring mapeia para `getAllProducts()`
3. Método retorna `products` (ArrayList)
4. Spring converte para JSON:
   ```json
   [
       {"id": 1, "name": "Notebook"},
       {"id": 2, "name": "Mouse"}
   ]
   ```

---

#### Endpoint 2: Adicionar Produto (POST)

```java
// POST: Adicionar um novo produto
@PostMapping                                    // ← POST /products
@ResponseStatus(HttpStatus.CREATED)            // ← Retorna HTTP 201
public Product addProduct(@RequestBody Product product) {
    product.setId(nextId++);                   // ← Define ID e incrementa
    products.add(product);                     // ← Adiciona à lista
    return product;                            // ← Retorna o produto criado
}
```

**Passo a Passo:**

1. Cliente envia JSON:
   ```json
   {
       "name": "Teclado"
   }
   ```

2. `@RequestBody` converte JSON para objeto `Product`

3. `product.setId(nextId++)` → Define ID:
   - `nextId` é 1 → produto recebe ID 1
   - `nextId` vira 2 (para próximo produto)

4. `products.add(product)` → Adiciona à lista

5. Retorna HTTP 201 com JSON:
   ```json
   {
       "id": 1,
       "name": "Teclado"
   }
   ```

---

#### Endpoint 3: Buscar por ID (GET)

```java
// GET: Buscar produto por ID
@GetMapping("/{id}")                           // ← GET /products/1
public Object getProductById(@PathVariable Long id) {
    for (Product product : products) {         // ← Itera pela lista
        if (product.getId().equals(id)) {      // ← Compara IDs
            return product;                    // ← Retorna se encontrou
        }
    }
    return "Produto não encontrado";           // ← Mensagem de erro
}
```

**Passo a Passo:**

1. Cliente faz: `GET /products/2`
2. `@PathVariable Long id` → Captura `2` da URL
3. Loop `for` percorre `products`:
   - Produto 1: ID=1 ≠ 2 → continua
   - Produto 2: ID=2 == 2 → retorna produto
4. Se não encontrar → retorna string de erro

> ⚠️ **Nota:** Em produção, retorne HTTP 404 com `ResponseEntity`!

---

#### Endpoint 4: Atualizar Produto (PUT)

```java
// PUT: Atualizar um produto existente
@PutMapping("/{id}")                           // ← PUT /products/1
public Object updateProduct(
    @PathVariable Long id,                     // ← ID da URL
    @RequestBody Product updatedProduct        // ← Novos dados do JSON
) {
    for (Product product : products) {         // ← Busca na lista
        if (product.getId().equals(id)) {
            product.setName(updatedProduct.getName()); // ← Atualiza nome
            return product;                    // ← Retorna produto atualizado
        }
    }
    return "Produto não encontrado";
}
```

**Exemplo de Uso:**

```powershell
# Atualizar produto ID=1
curl -X PUT http://localhost:8080/products/1 `
  -H "Content-Type: application/json" `
  -d '{"name": "Notebook Gamer"}'
```

---

#### Endpoint 5: Deletar Produto (DELETE)

```java
// DELETE: Remover um produto por ID
@DeleteMapping("/{id}")                        // ← DELETE /products/1
public String deleteProduct(@PathVariable Long id) {
    for (Product product : products) {         // ← Busca na lista
        if (product.getId().equals(id)) {
            products.remove(product);          // ← Remove da lista
            return "Produto removido com sucesso";
        }
    }
    return "Produto não encontrado";
}
```

**Passo a Passo:**

1. Cliente faz: `DELETE /products/1`
2. Loop encontra produto com ID=1
3. `products.remove(product)` → Remove da lista
4. Retorna mensagem de sucesso

---

## 🌐 Endpoints da API

### Tabela Resumo

| Método | Endpoint | Descrição | Body | Resposta |
|--------|----------|-----------|------|----------|
| **GET** | `/products` | Lista todos | - | Array JSON |
| **GET** | `/products/{id}` | Busca por ID | - | Objeto JSON ou erro |
| **POST** | `/products` | Cria novo | `{"name": "..."}` | Objeto JSON (HTTP 201) |
| **PUT** | `/products/{id}` | Atualiza | `{"name": "..."}` | Objeto JSON ou erro |
| **DELETE** | `/products/{id}` | Deleta | - | String de sucesso/erro |

---

## 🧪 Testando a API

### Opção 1: Curl (PowerShell)

**1. Listar produtos (vazio inicialmente):**

```powershell
curl http://localhost:8080/products
# Resposta: []
```

**2. Adicionar produtos:**

```powershell
# Adicionar Notebook
curl -X POST http://localhost:8080/products `
  -H "Content-Type: application/json" `
  -d '{"name": "Notebook"}'

# Adicionar Mouse
curl -X POST http://localhost:8080/products `
  -H "Content-Type: application/json" `
  -d '{"name": "Mouse"}'

# Adicionar Teclado
curl -X POST http://localhost:8080/products `
  -H "Content-Type: application/json" `
  -d '{"name": "Teclado"}'
```

**3. Listar produtos novamente:**

```powershell
curl http://localhost:8080/products
# Resposta:
# [
#   {"id":1,"name":"Notebook"},
#   {"id":2,"name":"Mouse"},
#   {"id":3,"name":"Teclado"}
# ]
```

**4. Buscar por ID:**

```powershell
curl http://localhost:8080/products/2
# Resposta: {"id":2,"name":"Mouse"}
```

**5. Atualizar produto:**

```powershell
curl -X PUT http://localhost:8080/products/1 `
  -H "Content-Type: application/json" `
  -d '{"name": "Notebook Gamer"}'
# Resposta: {"id":1,"name":"Notebook Gamer"}
```

**6. Deletar produto:**

```powershell
curl -X DELETE http://localhost:8080/products/3
# Resposta: "Produto removido com sucesso"
```

---

### Opção 2: Postman / Insomnia

**1. GET - Listar Todos:**
- Método: `GET`
- URL: `http://localhost:8080/products`

**2. POST - Criar Produto:**
- Método: `POST`
- URL: `http://localhost:8080/products`
- Headers: `Content-Type: application/json`
- Body (raw JSON):
  ```json
  {
      "name": "Monitor"
  }
  ```

**3. PUT - Atualizar:**
- Método: `PUT`
- URL: `http://localhost:8080/products/1`
- Headers: `Content-Type: application/json`
- Body (raw JSON):
  ```json
  {
      "name": "Monitor 4K"
  }
  ```

**4. DELETE - Remover:**
- Método: `DELETE`
- URL: `http://localhost:8080/products/1`

---

### Opção 3: Navegador (só GET)

```
http://localhost:8080/products
http://localhost:8080/products/1
http://localhost:8080/hello
```

---

## ⚖️ Vantagens e Desvantagens

### ✅ Vantagens

| Vantagem | Descrição |
|----------|-----------|
| 🚀 **Rapidez** | Zero configuração, executa instantaneamente |
| 🎓 **Didático** | Foco em REST, sem complexidade de BD |
| 🧪 **Prototipagem** | Teste APIs rapidamente |
| 💻 **Leve** | Sem overhead de banco de dados |

### ❌ Desvantagens

| Desvantagem | Descrição |
|-------------|-----------|
| 💾 **Sem Persistência** | Reiniciou? Perdeu tudo! |
| 🔄 **Não Escalável** | Um servidor = uma lista |
| 🔒 **Sem Transações** | Sem controle de concorrência |
| 🐛 **Dados Temporários** | Não serve para produção |

---

## 📊 Comparação com Outras Formas de Persistência

| Aspecto | ArrayList (Ex01) | H2 (Ex03) | MongoDB (Ex02) | MariaDB (Ex04) |
|---------|------------------|-----------|----------------|----------------|
| **Persistência** | ❌ Não | ⚠️ Temporária | ✅ Permanente | ✅ Permanente |
| **Reiniciar API** | 💀 Perde tudo | 💀 Perde tudo | ✅ Mantém | ✅ Mantém |
| **Complexidade** | ⭐ Muito fácil | ⭐⭐ Fácil | ⭐⭐⭐ Média | ⭐⭐⭐ Média |
| **Velocidade** | ⚡⚡⚡ Muito rápido | ⚡⚡⚡ Muito rápido | ⚡⚡ Rápido | ⚡⚡ Rápido |
| **Configuração** | ✅ Zero | ✅ Mínima | 🐳 Docker/Atlas | 🐳 Docker |
| **Uso** | 🎓 Aprendizado | 🧪 Testes | 💼 Produção | 💼 Produção |
| **SQL** | ❌ Não | ✅ Sim | ❌ NoSQL | ✅ Sim |
| **Relacionamentos** | ❌ Manual | ✅ JPA | ⚠️ Referências | ✅ JPA |

---

## 🎓 Dicas para Aula e Demonstração

### 📋 Ordem Recomendada de Ensino (45 min):

**1. Introdução (5 min):**
- "Vamos criar nossa primeira API REST!"
- Mostrar o que vamos construir: CRUD de produtos
- Explicar: "Por enquanto, sem banco de dados"

**2. Explicar o Modelo Product (5 min):**
- Mostrar `Product.java`
- Explicar: ID, nome, getters/setters
- "JSON ↔ Objeto Java"

**3. Demonstração do Controller (15 min):**
- Abrir `ProductController.java`
- Explicar linha por linha:
  - `@RestController`
  - `ArrayList<Product>`
  - `nextId` (gerador de IDs)
- Mostrar cada endpoint (GET, POST, PUT, DELETE)

**4. Executar e Testar Ao Vivo (15 min):**
- `.\mvnw.cmd spring-boot:run`
- Navegador: `http://localhost:8080/products` (vazio)
- Curl: Adicionar 3 produtos
- Navegador: Ver produtos criados
- Curl: Atualizar um produto
- Curl: Deletar um produto
- **Reiniciar API e mostrar que perdeu tudo!**

**5. Discussão (5 min):**
- "Qual o problema dessa abordagem?"
- Alunos respondem: "Perde os dados!"
- "Na próxima aula: banco de dados!"

---

### 🎬 Script de Demonstração (5 Cenas):

#### Cena 1: "Primeira API REST" (5 min)

```
Professor: "Vamos criar a primeira API REST da disciplina!"
[Abrir VS Code em ProductController.java]

"Vejam: só 3 coisas importantes aqui:"
1. ArrayList → armazena produtos
2. nextId → gera IDs únicos
3. Métodos → GET, POST, PUT, DELETE

"Simples assim!"
```

#### Cena 2: "Executando" (3 min)

```
Professor: "Vamos executar!"
[Abrir terminal]

cd exemplo01/demo
.\mvnw.cmd spring-boot:run

[Aguardar start]
"Pronto! API no ar em localhost:8080"
```

#### Cena 3: "Testando - Lista Vazia" (5 min)

```
Professor: "Primeiro GET - o que vai retornar?"
[Navegador: http://localhost:8080/products]

"Vejam: array vazio []"
"Por quê? Ainda não criamos produtos!"
```

#### Cena 4: "Criando Produtos" (10 min)

```
Professor: "Agora vamos criar produtos com curl!"
[Terminal]

# Produto 1
curl -X POST http://localhost:8080/products `
  -H "Content-Type: application/json" `
  -d '{"name": "Notebook"}'

"Vejam a resposta: ID=1 gerado automaticamente!"

[Repetir para Mouse e Teclado]

"Agora GET novamente:"
curl http://localhost:8080/products

"Vejam: 3 produtos! IDs 1, 2, 3!"
```

#### Cena 5: "O Grande Problema" (8 min)

```
Professor: "Agora a parte importante..."
"Vou REINICIAR a API. Prestem atenção!"

[Ctrl+C no terminal]
[mvnw spring-boot:run novamente]

"API reiniciada. E agora?"
[GET /products no navegador]

"Vejam: VAZIO! Perdemos TUDO!"
"Por quê? ArrayList está na MEMÓRIA RAM!"

[Desenhar no quadro:]
Memória RAM ← Reiniciou? APAGOU!
Banco de Dados ← Reiniciou? MANTÉM!

"Por isso precisamos de banco de dados!"
"Próxima aula: exemplo02 com MongoDB!"
```

---

## 📝 Exercícios Práticos para Alunos

### 🎯 Exercício 1: Primeira Requisição (Iniciante)

**Objetivo:** Executar a API e fazer primeira requisição

**Tarefas:**
1. Execute a aplicação: `.\mvnw.cmd spring-boot:run`
2. Acesse `http://localhost:8080/products` no navegador
3. Tire screenshot da tela mostrando `[]`

**Entrega:** Screenshot

---

### 🎯 Exercício 2: CRUD Completo (Intermediário)

**Objetivo:** Realizar todas as operações CRUD

**Tarefas:**
1. Crie 3 produtos (POST) com nomes:
   - Notebook
   - Mouse
   - Teclado

2. Liste todos (GET)
3. Atualize o Notebook para "Notebook Gamer" (PUT)
4. Delete o Mouse (DELETE)
5. Liste novamente para confirmar

**Entrega:**
- Comandos curl usados
- Screenshot do GET final (deve ter 2 produtos)

---

### 🎯 Exercício 3: Adicionar Campo Preço (Avançado)

**Objetivo:** Modificar o modelo Product

**Tarefas:**
1. Adicione campo `price` (Double) em `Product.java`:
   ```java
   private Double price;
   // Getter e Setter
   ```

2. Modifique POST para aceitar preço:
   ```json
   {
       "name": "Notebook",
       "price": 2500.00
   }
   ```

3. Teste criar produto com preço

**Entrega:**
- Código modificado de `Product.java`
- Curl do POST com preço
- Screenshot do GET mostrando preço

---

### 🎯 Exercício 4: Buscar por Nome (Desafio)

**Objetivo:** Criar endpoint para buscar produto por nome

**Tarefas:**
1. Crie endpoint `GET /products/name/{name}` em `ProductController.java`:
   ```java
   @GetMapping("/name/{name}")
   public Object getProductByName(@PathVariable String name) {
       for (Product product : products) {
           if (product.getName().equalsIgnoreCase(name)) {
               return product;
           }
       }
       return "Produto não encontrado";
   }
   ```

2. Teste: `GET /products/name/Mouse`

**Entrega:**
- Código do método
- Curl da requisição
- Screenshot da resposta

---

### 🎯 Exercício 5: Validação de Dados (Expert)

**Objetivo:** Adicionar validações

**Tarefas:**
1. No método `addProduct`, valide se nome não está vazio:
   ```java
   if (product.getName() == null || product.getName().isEmpty()) {
       throw new IllegalArgumentException("Nome não pode ser vazio");
   }
   ```

2. Teste enviar produto sem nome:
   ```json
   {
       "name": ""
   }
   ```

3. Capture o erro e documente

**Entrega:**
- Código com validação
- Print do erro retornado

---

## 🆘 Troubleshooting

### ❌ Problema: Porta 8080 em uso

**Erro:**
```
Web server failed to start. Port 8080 was already in use.
```

**Solução:**

```powershell
# Descobrir processo na porta 8080
netstat -ano | Select-String ":8080"

# Matar processo (substitua PID)
taskkill /PID 12345 /F

# Ou mudar porta em application.properties:
server.port=8081
```

---

### ❌ Problema: Dados não persistem

**Sintoma:**
- Criar produtos, reiniciar API, produtos sumiram

**Solução:**
- ✅ **Isso é ESPERADO!** ArrayList não persiste
- Use H2 (exemplo03) ou MongoDB (exemplo02) para persistência

---

### ❌ Problema: JSON vazio `{}`

**Sintoma:**
```powershell
curl http://localhost:8080/products/1
# Resposta: {}
```

**Causa:**
- Product não tem getters/setters

**Solução:**
```java
// Adicione em Product.java
public Long getId() { return id; }
public void setId(Long id) { this.id = id; }
public String getName() { return name; }
public void setName(String name) { this.name = name; }
```

---

### ❌ Problema: DELETE não funciona

**Erro:**
```
java.util.ConcurrentModificationException
```

**Causa:**
- Modificar lista durante iteração

**Solução:**
```java
// Em vez de:
for (Product product : products) {
    if (product.getId().equals(id)) {
        products.remove(product); // ❌ ERRO!
    }
}

// Use:
products.removeIf(product -> product.getId().equals(id)); // ✅ CORRETO!
```

---

### ❌ Problema: IDs duplicados após reiniciar

**Sintoma:**
- Reinicia API, `nextId` volta para 1
- IDs duplicados se criar produtos novamente

**Solução:**
- ✅ **Isso é normal** em ArrayList
- Use banco de dados com auto-increment real

---

## 🚀 Próximos Passos

### Evolução da Persistência

```
exemplo01 (ArrayList)
    ↓
exemplo03 (H2 - SQL em memória)
    ↓
exemplo02 (MongoDB - NoSQL permanente)
    ↓
exemplo04 (MariaDB - SQL permanente)
    ↓
exemplo05 (MariaDB + Relacionamentos 1:N, N:N)
```

### O que Aprender em Seguida:

1. ✅ **exemplo03** - H2 Database (SQL em memória)
   - Aprenda JPA, Repositories
   - Ainda sem configuração de BD

2. ✅ **exemplo02** - MongoDB (NoSQL permanente)
   - Aprenda NoSQL, documentos
   - Docker ou Atlas cloud

3. ✅ **exemplo04** - MariaDB (SQL permanente)
   - Banco relacional real
   - Docker XAMPP

4. ✅ **exemplo05** - Relacionamentos (1:N, N:N)
   - Category ↔ Product
   - Product ↔ Tags

---

## 📚 Recursos Adicionais

### Documentação Spring Boot

- **Spring Boot Reference:** https://docs.spring.io/spring-boot/docs/current/reference/html/
- **Building REST Services:** https://spring.io/guides/tutorials/rest/
- **Spring Web MVC:** https://docs.spring.io/spring-framework/docs/current/reference/html/web.html

### Tutoriais Recomendados

1. **[REST API with Spring Boot](https://www.baeldung.com/rest-with-spring-series)** - Baeldung
2. **[Building a RESTful Web Service](https://spring.io/guides/gs/rest-service/)** - Spring.io
3. **[Spring Boot Tutorial](https://www.youtube.com/watch?v=vtPkZShrvXQ)** - Vídeo

---

## ✅ Checklist de Aprendizado

Marque conforme for dominando:

- [ ] Entendi o que é persistência em memória
- [ ] Sei a diferença entre ArrayList e Banco de Dados
- [ ] Executei a aplicação com sucesso
- [ ] Criei produtos via POST
- [ ] Listei produtos via GET
- [ ] Atualizei produto via PUT
- [ ] Deletei produto via DELETE
- [ ] Testei com Curl
- [ ] Testei com Postman/Insomnia
- [ ] Entendi por que os dados são perdidos ao reiniciar
- [ ] Completei os 5 exercícios práticos
- [ ] Pronto para aprender H2/MongoDB!

---

**📝 Documentação criada para:**
- Java 11+
- Spring Boot 3.4.3
- Maven 3.6+

**🎯 Objetivo:** Guia completo de persistência em memória com ArrayList, do básico ao avançado, para aulas práticas de Arquitetura de Aplicações Web.

---

**Made with ❤️ for Learning Spring Boot**

## Testando a API

### Usando cURL

**Adicionar produto:**
```bash
curl -X POST http://localhost:8080/products \
  -H "Content-Type: application/json" \
  -d '{"name":"Mouse"}'
```

**Listar produtos:**
```bash
curl http://localhost:8080/products
```

**Buscar produto por ID:**
```bash
curl http://localhost:8080/products/1
```

**Atualizar produto:**
```bash
curl -X PUT http://localhost:8080/products/1 \
  -H "Content-Type: application/json" \
  -d '{"name":"Mouse Gamer"}'
```

**Deletar produto:**
```bash
curl -X DELETE http://localhost:8080/products/1
```

## Vantagens
- ✅ Simples e fácil de entender
- ✅ Não requer configuração de banco de dados
- ✅ Ideal para testes e protótipos rápidos
- ✅ Baixo consumo de recursos

## Desvantagens
- ❌ Dados não persistem após reiniciar a aplicação
- ❌ Não adequado para produção
- ❌ Limitado pela memória RAM disponível
- ❌ Não há concorrência/segurança thread-safe

## Quando Usar
- Protótipos rápidos
- Testes de conceito
- Ambientes de desenvolvimento/estudo
- Aplicações temporárias

## Próximos Passos
- Ver **exemplo02** para persistência com MongoDB
- Ver **exemplo03** para persistência com H2 (banco em memória)
- Ver **exemplo04** para persistência com MariaDB

## Autor
Curso de Arquitetura de Aplicação Web - Newton Paiva

## Licença
Este projeto é livre para uso educacional.
