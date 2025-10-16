# 🧪 Testes Unitários com JUnit 5 e Mockito

## 📑 Índice

1. [🚀 Início Rápido](#-início-rápido)
2. [🗂️ Estrutura do Projeto](#️-estrutura-do-projeto)
3. [🎯 Conceitos Fundamentais](#-conceitos-fundamentais)
4. [🛠️ Criando Testes Passo a Passo](#️-criando-testes-passo-a-passo)
   - [Teste 1: CRUD de Serviço](#-teste-1-crud-de-serviço)
   - [Teste 2: Endpoints do Controller](#-teste-2-endpoints-do-controller)
   - [Teste 3: Teste Completo (ProductFullTest)](#-teste-3-teste-completo-productfulltest)
5. [▶️ Executando os Testes](#️-executando-os-testes)
6. [📊 Analisando Resultados](#-analisando-resultados)
7. [🏷️ Anotações do Mockito Explicadas](#️-anotações-do-mockito-explicadas)
8. [🎯 Padrões de Teste (AAA Pattern)](#-padrões-de-teste-aaa-pattern)
9. [🎓 Dicas para Aula](#-dicas-para-aula-e-demonstração)
10. [📝 Exercícios Práticos](#-exercícios-práticos-para-alunos)
11. [🆘 Troubleshooting](#-troubleshooting)
12. [📚 Recursos Adicionais](#-recursos-adicionais)

---

## 🚀 Início Rápido

Se você já conhece JUnit e Mockito e quer executar os testes imediatamente:

```powershell
# 1. Navegue até a pasta do projeto
cd D:\GitHub\NewtonPaiva\ArquiteturaAplicacaoWeb\Teste\exemplo01\demo

# 2. Execute todos os testes
.\mvnw.cmd clean test

# 3. Veja os resultados no terminal ou abra o relatório HTML
start target\surefire-reports\index.html
```

> 💡 **Primeira vez?** Continue lendo para entender cada conceito detalhadamente.

---

## 🗂️ Estrutura do Projeto

```
Teste/exemplo01/
├── demo/
│   ├── src/
│   │   ├── main/
│   │   │   └── java/
│   │   │       └── com/example/demo/
│   │   │           ├── Product.java              # Entidade (Model)
│   │   │           ├── ProductRepository.java    # Interface JPA
│   │   │           ├── ProductService.java       # Lógica de negócio
│   │   │           └── ProductController.java    # REST Controller
│   │   │
│   │   └── test/
│   │       └── java/
│   │           └── com/example/demo/
│   │               ├── ProductTest.java          # ✅ Testes do Service
│   │               ├── ProductControllerTest.java # ✅ Testes do Controller
│   │               └── ProductFullTest.java      # ✅ TODOS os testes juntos
│   │
│   ├── pom.xml                                   # Dependências (JUnit, Mockito)
│   └── mvnw.cmd                                  # Maven Wrapper (Windows)
│
└── README.md                                     # Este arquivo
```

### 📦 Tecnologias Utilizadas

| Tecnologia | Versão | Finalidade |
|------------|--------|------------|
| **JUnit 5** | 5.x | Framework de testes unitários |
| **Mockito** | 5.x | Framework para criar mocks |
| **Spring Boot** | 3.4.3 | Framework web (testado) |
| **Maven** | 3.x | Gerenciamento de dependências |
| **H2 Database** | In-memory | Banco de dados para testes (opcional) |

---

## 🎯 Conceitos Fundamentais

### O que é Teste Unitário?

> **Teste Unitário** = Testar uma **unidade** isolada do código (método, classe)

**Características:**
- ✅ **Rápido** - Executa em milissegundos
- ✅ **Isolado** - Não depende de banco de dados, APIs externas
- ✅ **Repetível** - Sempre produz o mesmo resultado
- ✅ **Automático** - Roda sem intervenção manual

### Por que usar Mocks?

Imagine que você quer testar o `ProductService`, mas ele depende do `ProductRepository` (que acessa o banco de dados):

```java
// ❌ SEM Mock: Teste dependeria do banco real
ProductRepository repo = new ProductRepository(); // Precisa de BD!
ProductService service = new ProductService(repo);

// ✅ COM Mock: Teste isolado, sem BD
ProductRepository repo = mock(ProductRepository.class); // Mock!
ProductService service = new ProductService(repo);
```

**Mock** = Objeto falso que simula comportamento de dependências

---

## 🛠️ Criando Testes Passo a Passo

### 🔹 Teste 1: CRUD de Serviço

> 🎯 **Objetivo:** Testar as operações CRUD do `ProductService` sem usar banco de dados real

#### Arquivo: `ProductTest.java`

**Passo 1: Criar a Classe de Teste**

```java
package com.example.demo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Optional;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class ProductTest {
    // Declarações aqui
}
```

**Passo 2: Declarar as Dependências**

```java
public class ProductTest {
    private ProductService service;      // ← Classe que vamos TESTAR
    private ProductRepository repository; // ← Dependência que vamos MOCKAR
}
```

> 💡 **service** = Sistema sob teste (SUT - System Under Test)  
> 💡 **repository** = Dependência mockada

**Passo 3: Configurar o Setup (Before Each)**

```java
@BeforeEach
void setup() {
    // Cria um mock do repositório
    repository = mock(ProductRepository.class);
    
    // Injeta o mock no serviço
    service = new ProductService(repository);
}
```

> 💡 **@BeforeEach** = Executa antes de CADA teste (setup fresco)

**Passo 4: Teste CREATE - Criar Produto**

```java
@Test
void testCreateProduct() {
    // --- ARRANGE (Preparar) ---
    Product product = new Product();
    product.setId(1L);
    product.setName("Notebook");
    
    // Configurar comportamento do mock
    when(repository.save(any(Product.class))).thenReturn(product);
    
    // --- ACT (Agir) ---
    Product saved = service.createProduct(product);
    
    // --- ASSERT (Verificar) ---
    assertEquals("Notebook", saved.getName());
    assertEquals(1L, saved.getId());
    
    // Verificar que o método save foi chamado 1 vez
    verify(repository, times(1)).save(product);
}
```

**Explicação linha por linha:**

| Código | O que faz |
|--------|-----------|
| `when(repository.save(...))` | Quando o mock for chamado com save() |
| `.thenReturn(product)` | Retorne este produto |
| `service.createProduct(product)` | Chama o método real do serviço |
| `assertEquals("Notebook", saved.getName())` | Verifica se o nome está correto |
| `verify(repository, times(1)).save(product)` | Confirma que save() foi chamado 1x |

**Passo 5: Teste READ - Buscar Produto**

```java
@Test
void testReadProduct() {
    // ARRANGE
    Product product = new Product();
    product.setId(1L);
    product.setName("Mouse");
    
    // Mock retorna Optional com produto
    when(repository.findById(1L)).thenReturn(Optional.of(product));
    
    // ACT
    Optional<Product> found = service.getProductById(1L);
    
    // ASSERT
    assertTrue(found.isPresent());              // Produto foi encontrado?
    assertEquals(1L, found.get().getId());      // ID correto?
    assertEquals("Mouse", found.get().getName()); // Nome correto?
    
    verify(repository, times(1)).findById(1L);
}
```

**Passo 6: Teste UPDATE - Atualizar Produto**

```java
@Test
void testUpdateProduct() {
    // ARRANGE
    Product product = new Product();
    product.setId(1L);
    product.setName("Teclado Mecânico");
    
    when(repository.save(any(Product.class))).thenReturn(product);
    
    // ACT
    Product updated = service.updateProduct(product);
    
    // ASSERT
    assertEquals("Teclado Mecânico", updated.getName());
    verify(repository, times(1)).save(product);
}
```

**Passo 7: Teste DELETE - Deletar Produto**

```java
@Test
void testDeleteProduct() {
    // ARRANGE
    // doNothing() = método void não faz nada (comportamento padrão)
    doNothing().when(repository).deleteById(1L);
    
    // ACT
    service.deleteProduct(1L);
    
    // ASSERT
    // Verifica que deleteById foi chamado com ID correto
    verify(repository, times(1)).deleteById(1L);
}
```

> 💡 **doNothing()** = Para métodos void (sem retorno)

**Passo 8: Executar os Testes**

```powershell
cd D:\GitHub\NewtonPaiva\ArquiteturaAplicacaoWeb\Teste\exemplo01\demo
.\mvnw.cmd test -Dtest=ProductTest
```

---

### 🔹 Teste 2: Endpoints do Controller

> 🎯 **Objetivo:** Testar os endpoints REST do `ProductController` sem subir servidor

#### Arquivo: `ProductControllerTest.java`

**Passo 1: Criar Classe com Anotações Mockito**

```java
package com.example.demo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import java.util.Optional;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class ProductControllerTest {
    @Mock
    private ProductService productService;  // ← Mock do Service
    
    @InjectMocks
    private ProductController productController; // ← Injeta mock no Controller
}
```

> 💡 **@Mock** = Cria mock automaticamente  
> 💡 **@InjectMocks** = Injeta os mocks nas dependências

**Passo 2: Inicializar Mocks no Setup**

```java
@BeforeEach
void setup() {
    MockitoAnnotations.openMocks(this);
}
```

> 💡 **openMocks(this)** = Inicializa os mocks anotados com @Mock

**Passo 3: Teste GET by ID - Sucesso (200)**

```java
@Test
void testGetProductById_Success() {
    // ARRANGE
    Product product = new Product();
    product.setId(1L);
    product.setName("Monitor 4K");
    
    // Mock retorna produto
    when(productService.getProductById(1L)).thenReturn(Optional.of(product));
    
    // ACT
    ResponseEntity<Product> response = productController.getProductById(1L);
    
    // ASSERT
    assertEquals(200, response.getStatusCodeValue()); // HTTP 200 OK
    assertNotNull(response.getBody());
    assertEquals(1L, response.getBody().getId());
    assertEquals("Monitor 4K", response.getBody().getName());
}
```

**Passo 4: Teste GET by ID - Não Encontrado (404)**

```java
@Test
void testGetProductById_NotFound() {
    // ARRANGE
    when(productService.getProductById(99L)).thenReturn(Optional.empty());
    
    // ACT
    ResponseEntity<Product> response = productController.getProductById(99L);
    
    // ASSERT
    assertEquals(404, response.getStatusCodeValue()); // HTTP 404 Not Found
    assertNull(response.getBody());
}
```

**Passo 5: Teste POST - Criar Produto (201)**

```java
@Test
void testCreateProduct() {
    // ARRANGE
    Product product = new Product();
    product.setName("Cadeira Gamer");
    product.setPrice(899.99);
    
    Product savedProduct = new Product();
    savedProduct.setId(1L);
    savedProduct.setName("Cadeira Gamer");
    savedProduct.setPrice(899.99);
    
    when(productService.createProduct(any(Product.class))).thenReturn(savedProduct);
    
    // ACT
    ResponseEntity<Product> response = productController.createProduct(product);
    
    // ASSERT
    assertEquals(201, response.getStatusCodeValue()); // HTTP 201 Created
    assertEquals(1L, response.getBody().getId());
    assertEquals("Cadeira Gamer", response.getBody().getName());
}
```

**Passo 6: Teste DELETE - Deletar Produto (204)**

```java
@Test
void testDeleteProduct() {
    // ARRANGE
    doNothing().when(productService).deleteProduct(1L);
    
    // ACT
    ResponseEntity<Void> response = productController.deleteProduct(1L);
    
    // ASSERT
    assertEquals(204, response.getStatusCodeValue()); // HTTP 204 No Content
    verify(productService, times(1)).deleteProduct(1L);
}
```

**Passo 7: Executar Testes do Controller**

```powershell
.\mvnw.cmd test -Dtest=ProductControllerTest
```

---

### 🔹 Teste 3: Teste Completo (ProductFullTest)

> 🎯 **Objetivo:** Arquivo único com TODOS os testes (Service + Controller)

#### Arquivo: `ProductFullTest.java` (Referência Principal)

Este é o arquivo **MAIS IMPORTANTE** para aprendizado! Ele demonstra:
- ✅ Testes de Service E Controller no mesmo arquivo
- ✅ Uso de `@Mock` e `@InjectMocks`
- ✅ Setup separado para Service e Controller
- ✅ CRUD completo testado

**Estrutura Completa:**

```java
package com.example.demo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import java.util.*;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class ProductFullTest {
    
    // ==========================================
    // SEÇÃO 1: TESTES DE SERVIÇO
    // ==========================================
    
    private ProductService service;
    private ProductRepository repository;

    @BeforeEach
    void setupService() {
        repository = mock(ProductRepository.class);
        service = new ProductService(repository);
    }

    @Test
    void testCreateProduct() {
        // CREATE - Criar produto
        Product product = new Product();
        product.setId(1L);
        product.setName("Notebook");
        
        when(repository.save(any(Product.class))).thenReturn(product);
        
        Product saved = service.createProduct(product);
        
        assertEquals("Notebook", saved.getName());
        verify(repository, times(1)).save(product);
    }

    @Test
    void testReadProduct() {
        // READ - Buscar produto
        Product product = new Product();
        product.setId(1L);
        
        when(repository.findById(1L)).thenReturn(Optional.of(product));
        
        Optional<Product> found = service.getProductById(1L);
        
        assertTrue(found.isPresent());
        assertEquals(1L, found.get().getId());
        verify(repository, times(1)).findById(1L);
    }

    @Test
    void testUpdateProduct() {
        // UPDATE - Atualizar produto
        Product product = new Product();
        product.setId(1L);
        product.setName("Notebook");
        
        when(repository.save(any(Product.class))).thenReturn(product);
        
        Product updated = service.updateProduct(product);
        
        assertEquals("Notebook", updated.getName());
        verify(repository, times(1)).save(product);
    }

    @Test
    void testDeleteProduct() {
        // DELETE - Deletar produto
        doNothing().when(repository).deleteById(1L);
        
        service.deleteProduct(1L);
        
        verify(repository, times(1)).deleteById(1L);
    }

    // ==========================================
    // SEÇÃO 2: TESTES DE CONTROLADOR
    // ==========================================
    
    @Mock
    private ProductService productService;
    
    @InjectMocks
    private ProductController productController;

    @BeforeEach
    void setupController() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetProductByIdController() {
        // GET by ID - Endpoint REST
        Product product = new Product();
        product.setId(1L);
        
        when(productService.getProductById(1L)).thenReturn(Optional.of(product));
        
        ResponseEntity<Product> response = productController.getProductById(1L);
        
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(1L, response.getBody().getId());
    }

    @Test
    void testCreateProductController() {
        // POST - Criar via endpoint
        Product product = new Product();
        product.setName("Notebook");
        
        when(productService.createProduct(any(Product.class))).thenReturn(product);
        
        ResponseEntity<Product> response = productController.createProduct(product);
        
        assertEquals(201, response.getStatusCodeValue());
        assertEquals("Notebook", response.getBody().getName());
    }

    @Test
    void testDeleteProductController() {
        // DELETE - Deletar via endpoint
        doNothing().when(productService).deleteProduct(1L);
        
        ResponseEntity<Void> response = productController.deleteProduct(1L);
        
        assertEquals(204, response.getStatusCodeValue());
    }
}
```

**Executar ProductFullTest:**

```powershell
.\mvnw.cmd test -Dtest=ProductFullTest
```

**Saída Esperada:**

```
[INFO] Tests run: 7, Failures: 0, Errors: 0, Skipped: 0
[INFO] BUILD SUCCESS
```

---

## 🚀 Executando os Testes

### Método 1: Executar TODOS os Testes

```powershell
cd D:\GitHub\NewtonPaiva\ArquiteturaAplicacaoWeb\Teste\exemplo01\demo

# Executar todos os testes do projeto
.\mvnw.cmd clean test
```

### Método 2: Executar UM Arquivo de Teste Específico

```powershell
# Apenas ProductTest
.\mvnw.cmd test -Dtest=ProductTest

# Apenas ProductControllerTest
.\mvnw.cmd test -Dtest=ProductControllerTest

# Apenas ProductFullTest (RECOMENDADO para aula)
.\mvnw.cmd test -Dtest=ProductFullTest
```

### Método 3: Executar UM Método Específico

```powershell
# Executar apenas o teste testCreateProduct
.\mvnw.cmd test -Dtest=ProductFullTest#testCreateProduct

# Executar apenas o teste testGetProductByIdController
.\mvnw.cmd test -Dtest=ProductFullTest#testGetProductByIdController
```

### Método 4: Executar com Relatório HTML

```powershell
.\mvnw.cmd clean test site

# Abrir relatório no navegador
start target\site\surefire-report.html
```

---

## 📊 Analisando Resultados

### Saída no Terminal (Surefire)

```
-------------------------------------------------------
 T E S T S
-------------------------------------------------------
Running com.example.demo.ProductFullTest
Tests run: 7, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.234 s

Results :

Tests run: 7, Failures: 0, Errors: 0, Skipped: 0

[INFO] BUILD SUCCESS
```

### Métricas Importantes:

| Métrica | Descrição | Ideal |
|---------|-----------|-------|
| **Tests run** | Total de testes executados | 7 |
| **Failures** | Testes que falharam (assertion) | 0 ✅ |
| **Errors** | Testes com erro de execução | 0 ✅ |
| **Skipped** | Testes pulados (@Disabled) | 0 |
| **Time elapsed** | Tempo de execução | < 1s ✅ |

### Relatórios Gerados:

```
target/
├── surefire-reports/
│   ├── com.example.demo.ProductFullTest.txt
│   ├── TEST-com.example.demo.ProductFullTest.xml
│   └── index.html (se usar 'site')
```

**Abrir relatório:**

```powershell
start target\surefire-reports\com.example.demo.ProductFullTest.txt
```

---

## 🔍 Anotações do Mockito Explicadas

### Tabela Completa de Anotações:

| Anotação | Uso | Exemplo |
|----------|-----|---------|
| `@Mock` | Cria um mock automaticamente | `@Mock ProductRepository repo;` |
| `@InjectMocks` | Injeta mocks nas dependências | `@InjectMocks ProductService service;` |
| `@BeforeEach` | Executa antes de cada teste | `@BeforeEach void setup() { ... }` |
| `@Test` | Marca método como teste | `@Test void testCreate() { ... }` |
| `@Disabled` | Desabilita um teste | `@Disabled("Em desenvolvimento")` |
| `@DisplayName` | Nome customizado do teste | `@DisplayName("Deve criar produto")` |

### Métodos do Mockito:

| Método | Finalidade | Sintaxe |
|--------|------------|---------|
| `mock()` | Cria mock manualmente | `mock(ProductRepository.class)` |
| `when()...thenReturn()` | Define retorno do mock | `when(repo.save(p)).thenReturn(p)` |
| `doNothing()` | Mock para método void | `doNothing().when(repo).delete(1L)` |
| `verify()` | Verifica chamadas | `verify(repo, times(1)).save(p)` |
| `any()` | Qualquer argumento | `when(repo.save(any())).thenReturn(p)` |
| `anyLong()` | Qualquer Long | `when(repo.findById(anyLong()))...` |
| `times()` | Quantas vezes chamou | `verify(repo, times(2)).save(...)` |
| `never()` | Nunca foi chamado | `verify(repo, never()).delete(...)` |

---

## 🎨 Padrões de Teste (AAA Pattern)

### O que é AAA?

**AAA** = **Arrange, Act, Assert**

```java
@Test
void exemploAAA() {
    // 1️⃣ ARRANGE (Preparar)
    // Configurar dados, mocks, expectativas
    Product product = new Product();
    product.setName("Mouse");
    when(repository.save(any())).thenReturn(product);
    
    // 2️⃣ ACT (Agir)
    // Executar o método sob teste
    Product result = service.createProduct(product);
    
    // 3️⃣ ASSERT (Verificar)
    // Validar resultado
    assertEquals("Mouse", result.getName());
    verify(repository).save(product);
}
```

### Por que usar AAA?

- ✅ **Legibilidade** - Fácil entender o que o teste faz
- ✅ **Organização** - Estrutura clara
- ✅ **Manutenção** - Fácil modificar depois

---

## 🎓 Dicas para Aula e Demonstração

### 📋 Ordem Recomendada de Ensino (60 min):

**1. Introdução Teórica (10 min):**
- O que é teste unitário?
- Por que testar?
- Pirâmide de testes (Unit → Integration → E2E)
- JUnit vs Mockito

**2. Demonstração - Primeiro Teste (15 min):**
- Abrir `ProductFullTest.java`
- Explicar estrutura AAA
- Executar `testCreateProduct`
- Mostrar saída no terminal

**3. Mocks e Assertions (15 min):**
- O que é mock?
- `when()...thenReturn()`
- `verify()`
- `assertEquals()`, `assertTrue()`

**4. Testes de Controller (10 min):**
- ResponseEntity e HTTP Status
- Testar sem subir servidor
- Validar JSON responses

**5. Execução e Relatórios (5 min):**
- Maven wrapper (`mvnw.cmd`)
- Relatórios Surefire
- TDD (opcional)

**6. Exercícios Práticos (15 min):**
- Alunos criam testes

---

### 💡 Dicas de Demonstração em Aula:

#### ✅ DO (Faça):

1. **Prepare exemplos com erro:**
   ```java
   // Exemplo de assertion ERRADA (para mostrar)
   @Test
   void exemploErro() {
       Product p = new Product();
       p.setName("Mouse");
       assertEquals("Teclado", p.getName()); // ❌ FALHA!
   }
   ```

2. **Mostre o ciclo Red-Green-Refactor (TDD):**
   - 🔴 RED: Escreva teste que falha
   - 🟢 GREEN: Faça passar (código mínimo)
   - 🔵 REFACTOR: Melhore o código

3. **Use println para debug:**
   ```java
   @Test
   void debug() {
       Product p = service.createProduct(new Product());
       System.out.println("Produto criado: " + p.getName());
       assertEquals("Notebook", p.getName());
   }
   ```

4. **Compare com teste real (sem mock):**
   ```java
   // ❌ Teste que depende de BD real (ruim)
   @Test
   void testSemMock() {
       ProductRepository repo = new ProductRepository();
       // Erro: precisa de BD rodando!
   }
   ```

#### ❌ DON'T (Não Faça):

1. ❌ Não pule a explicação de `@Mock` vs `mock()`
2. ❌ Não assuma que alunos sabem AAA Pattern
3. ❌ Não execute testes sem mostrar o código antes
4. ❌ Não use `@SpringBootTest` em testes unitários (é teste de integração!)

---

## 📝 Exercícios Práticos para Alunos

### 🎯 Exercício 1: Primeiro Teste (Iniciante)

**Objetivo:** Criar seu primeiro teste unitário

**Tarefas:**
1. Crie arquivo `MeuPrimeiroTest.java` em `src/test/java/com/example/demo/`
2. Teste o método `setName()` e `getName()` da classe `Product`:

```java
@Test
void testProductName() {
    Product p = new Product();
    p.setName("Mouse Gamer");
    assertEquals("Mouse Gamer", p.getName());
}
```

3. Execute: `.\mvnw.cmd test -Dtest=MeuPrimeiroTest`

**Entrega:** Screenshot do terminal com "BUILD SUCCESS" ✅

---

### 🎯 Exercício 2: Mock Simples (Intermediário)

**Objetivo:** Entender como criar e usar mocks

**Tarefas:**
1. Crie `MeuServiceTest.java`
2. Mocke o `ProductRepository`
3. Teste o método `createProduct`:

```java
@Test
void testCreate() {
    ProductRepository repo = mock(ProductRepository.class);
    ProductService service = new ProductService(repo);
    
    Product p = new Product();
    p.setId(1L);
    p.setName("Teclado");
    
    when(repo.save(any(Product.class))).thenReturn(p);
    
    Product result = service.createProduct(p);
    
    assertEquals("Teclado", result.getName());
    verify(repo, times(1)).save(p);
}
```

**Entrega:** 
- Código do teste
- Print do terminal

---

### 🎯 Exercício 3: Teste de Controller (Avançado)

**Objetivo:** Testar endpoints REST

**Tarefas:**
1. Crie `MeuControllerTest.java`
2. Use `@Mock` e `@InjectMocks`
3. Teste GET e POST:

```java
@Mock
private ProductService service;

@InjectMocks
private ProductController controller;

@BeforeEach
void setup() {
    MockitoAnnotations.openMocks(this);
}

@Test
void testGetById() {
    Product p = new Product();
    p.setId(1L);
    when(service.getProductById(1L)).thenReturn(Optional.of(p));
    
    ResponseEntity<Product> response = controller.getProductById(1L);
    
    assertEquals(200, response.getStatusCodeValue());
}
```

**Entrega:**
- Teste GET (200)
- Teste GET Not Found (404)
- Teste POST (201)

---

### 🎯 Exercício 4: TDD - Test Driven Development (Desafio)

**Objetivo:** Escrever teste ANTES do código

**Tarefas:**
1. Escreva teste para método que ainda NÃO existe:

```java
@Test
void testGetProductsByPriceRange() {
    // Teste para método que você VAI criar
    List<Product> products = service.getProductsByPriceRange(100.0, 500.0);
    assertEquals(3, products.size());
}
```

2. Execute - deve FALHAR (🔴 RED)
3. Crie o método `getProductsByPriceRange()` no Service
4. Execute - deve PASSAR (🟢 GREEN)
5. Refatore o código (🔵 REFACTOR)

**Entrega:**
- Código do teste
- Código do método implementado
- Explicação do processo TDD

---

### 🎯 Exercício 5: Cobertura de Código (Expert)

**Objetivo:** Atingir 100% de cobertura

**Tarefas:**
1. Execute com cobertura:

```powershell
.\mvnw.cmd clean test jacoco:report
```

2. Abra relatório:

```powershell
start target\site\jacoco\index.html
```

3. Identifique linhas NÃO cobertas (vermelho)
4. Crie testes para cobrir 100%

**Entrega:**
- Screenshot do JaCoCo com 100% coverage

---

## 🎬 Script de Demonstração (Para Professores)

### Cena 1: Por que Testar? (5 min)

```
Professor: "Imaginem que vocês criaram este método:"

public int dividir(int a, int b) {
    return a / b;
}

"O que acontece se b = 0?"
[Mostrar erro: ArithmeticException: / by zero]

"Com testes, descobrimos isso ANTES de ir pra produção!"

@Test
void testDivisaoPorZero() {
    assertThrows(ArithmeticException.class, () -> {
        dividir(10, 0);
    });
}
```

### Cena 2: Primeiro Teste ao Vivo (10 min)

```
Professor: "Vamos criar nosso primeiro teste!"
[Abrir ProductFullTest.java]
"Vejam a estrutura AAA:"

@Test
void testCreateProduct() {
    // ARRANGE - Preparar
    Product product = new Product();
    product.setName("Notebook");
    
    // ACT - Agir
    product.setId(1L);
    
    // ASSERT - Verificar
    assertEquals("Notebook", product.getName());
    assertEquals(1L, product.getId());
}

[Executar teste]
"Verde! ✅ Teste passou!"
```

### Cena 3: Mocks Explicados (10 min)

```
Professor: "Agora o desafio: testar SEM banco de dados!"
[Mostrar ProductService que depende de ProductRepository]

"Solução: MOCK!"
[Escrever ao vivo:]

ProductRepository repo = mock(ProductRepository.class);
when(repo.save(any())).thenReturn(product);

"Estamos FINGINDO que o repository funciona!"
"Mock = dublê de teste"
```

### Cena 4: Erro Proposital (5 min)

```
Professor: "Vou causar um erro de propósito..."
[Mudar assertion:]

assertEquals("Mouse", product.getName()); // Mas setou "Notebook"!

[Executar]
"Vejam o erro:"

Expected :Mouse
Actual   :Notebook

"Isso é BOM! O teste pegou o bug!"
```

### Cena 5: Relatório Surefire (5 min)

```
Professor: "Executem comigo:"
.\mvnw.cmd clean test

[Mostrar saída:]
Tests run: 7, Failures: 0, Errors: 0

"7 testes, 0 falhas - perfeito!"
[Abrir target/surefire-reports/]
"Aqui ficam os relatórios detalhados"
```

---

## 🆘 Troubleshooting

### ❌ Problema: Package não encontrado

**Erro:**
```
[ERROR] package com.example.demo does not exist
```

**Solução:**
```java
// Verifique o package no topo do arquivo
package com.example.demo; // ✅ Deve ser exatamente igual
```

---

### ❌ Problema: Mock retorna null

**Erro:**
```java
Product result = service.createProduct(p);
// result é null!
```

**Solução:**
```java
// Esqueceu de configurar o mock!
when(repository.save(any(Product.class))).thenReturn(p); // ✅ Adicione isso
```

---

### ❌ Problema: Verify falha

**Erro:**
```
Wanted but not invoked:
repository.save(...)
```

**Solução:**
```java
// 1. Verifique se realmente chamou o método
service.createProduct(p); // Esqueceu de chamar?

// 2. Verifique o argumento
verify(repository).save(p); // Passou o objeto correto?

// 3. Use any() se não importa o argumento
verify(repository).save(any(Product.class)); // ✅
```

---

### ❌ Problema: @InjectMocks não funciona

**Erro:**
```
NullPointerException ao chamar productController.getProductById(...)
```

**Solução:**
```java
@BeforeEach
void setup() {
    MockitoAnnotations.openMocks(this); // ✅ ESSENCIAL!
}
```

---

### ❌ Problema: Teste passa mas não deveria

**Possível causa:**
```java
@Test
void testeErrado() {
    Product p = new Product();
    // Esqueceu de fazer assertions!
    // Teste passa mas não testa nada!
}
```

**Solução:**
```java
@Test
void testeCorreto() {
    Product p = new Product();
    p.setName("Mouse");
    assertEquals("Mouse", p.getName()); // ✅ Sempre tenha assertions!
}
```

---

### ❌ Problema: OutOfMemoryError em testes

**Solução:**
```powershell
# Aumentar memória do Maven
set MAVEN_OPTS=-Xmx1024m
.\mvnw.cmd clean test
```

---

### ❌ Problema: Testes lentos

**Causa:** Usando banco de dados real ou Spring Context

**Solução:**
```java
// ❌ Evite:
@SpringBootTest // Sobe todo o Spring (lento!)

// ✅ Use:
public class ProductTest { // Teste unitário puro (rápido!)
    @Mock
    private ProductRepository repository;
}
```

---

## 📚 Recursos Adicionais

### 📖 Documentação Oficial

- **JUnit 5:** https://junit.org/junit5/docs/current/user-guide/
- **Mockito:** https://javadoc.io/doc/org.mockito/mockito-core/latest/org/mockito/Mockito.html
- **Spring Testing:** https://docs.spring.io/spring-framework/reference/testing.html
- **Maven Surefire:** https://maven.apache.org/surefire/maven-surefire-plugin/

### 🎓 Tutoriais Recomendados

1. **[JUnit 5 User Guide](https://junit.org/junit5/docs/current/user-guide/)** - Guia completo
2. **[Mockito Tutorial](https://www.baeldung.com/mockito-series)** - Série Baeldung
3. **[Testing in Spring Boot](https://spring.io/guides/gs/testing-web/)** - Spring Guides
4. **[TDD by Example](https://www.amazon.com/Test-Driven-Development-Kent-Beck/dp/0321146530)** - Livro Kent Beck

### 🔌 Ferramentas Úteis

**JaCoCo (Cobertura de Código):**

Adicione no `pom.xml`:

```xml
<plugin>
    <groupId>org.jacoco</groupId>
    <artifactId>jacoco-maven-plugin</artifactId>
    <version>0.8.11</version>
    <executions>
        <execution>
            <goals>
                <goal>prepare-agent</goal>
            </goals>
        </execution>
        <execution>
            <id>report</id>
            <phase>test</phase>
            <goals>
                <goal>report</goal>
            </goals>
        </execution>
    </executions>
</plugin>
```

Executar:

```powershell
.\mvnw.cmd clean test jacoco:report
start target\site\jacoco\index.html
```

**AssertJ (Assertions Fluentes):**

```xml
<dependency>
    <groupId>org.assertj</groupId>
    <artifactId>assertj-core</artifactId>
    <version>3.24.2</version>
    <scope>test</scope>
</dependency>
```

Uso:

```java
import static org.assertj.core.api.Assertions.*;

@Test
void testComAssertJ() {
    Product p = new Product();
    p.setName("Mouse");
    p.setPrice(50.0);
    
    assertThat(p.getName()).isEqualTo("Mouse");
    assertThat(p.getPrice()).isGreaterThan(0).isLessThan(100);
}
```

---

## 📊 Comparação: Teste Unitário vs Integração vs E2E

| Tipo | O que testa | Velocidade | Uso de Mock | Quando usar |
|------|-------------|------------|-------------|-------------|
| **Unitário** | Método/classe isolada | ⚡ Muito rápido (ms) | ✅ Sim, muito | Sempre! |
| **Integração** | Múltiplas camadas | 🐌 Médio (segundos) | ⚠️ Pouco | Testar BD, APIs |
| **E2E (End-to-End)** | Sistema completo | 🐢 Lento (minutos) | ❌ Não | Fluxos críticos |

**Pirâmide de Testes:**

```
       /\
      /E2E\       ← Poucos (5%)
     /------\
    /Integr.\    ← Alguns (15%)
   /----------\
  / Unitários \  ← Muitos (80%)
 /--------------\
```

---

## 🎯 Checklist para Aula Bem-Sucedida

### Antes da Aula:
- [ ] Java 17+ instalado
- [ ] Maven configurado
- [ ] Projeto demo compilando
- [ ] Testes executando sem erros
- [ ] Slides/apresentação prontos
- [ ] Exemplos de código preparados

### Durante a Aula:
- [ ] Explicar conceitos antes de codificar
- [ ] Mostrar AAA Pattern em TODOS os testes
- [ ] Executar testes ao vivo (não só slides!)
- [ ] Provocar erros para ensinar debug
- [ ] Circular pela sala ajudando alunos
- [ ] Tirar dúvidas em tempo real

### Depois da Aula:
- [ ] Compartilhar código dos exemplos
- [ ] Disponibilizar este README
- [ ] Criar lista de exercícios para casa
- [ ] Abrir fórum/grupo para dúvidas
- [ ] Pedir feedback dos alunos

---

## 🚀 Próximos Passos

1. ✅ **Pratique TDD** - Escreva testes primeiro
2. ✅ **Explore AssertJ** - Assertions mais legíveis
3. ✅ **Use JaCoCo** - Medir cobertura de código
4. ✅ **Aprenda @ParameterizedTest** - Testes com múltiplos dados
5. ✅ **Estude Testes de Integração** - `@SpringBootTest`
6. ✅ **Configure CI/CD** - Executar testes automaticamente
7. ✅ **Leia "Clean Code"** - Capítulo sobre testes

---

## 🎓 Glossário de Conceitos

| Termo | Significado |
|-------|-------------|
| **Unit Test** | Teste de uma unidade isolada (método/classe) |
| **Mock** | Objeto falso que simula dependência |
| **Stub** | Mock que retorna valores pré-definidos |
| **Spy** | Mock parcial (alguns métodos reais, outros mockados) |
| **Assertion** | Verificação de resultado esperado |
| **AAA Pattern** | Arrange, Act, Assert |
| **TDD** | Test-Driven Development (teste primeiro) |
| **SUT** | System Under Test (classe sendo testada) |
| **Code Coverage** | % do código coberto por testes |
| **Red-Green-Refactor** | Ciclo TDD (falha → passa → melhora) |

---

**📝 Documentação criada com base em:**
- JUnit 5.10.x
- Mockito 5.x
- Spring Boot 3.4.3
- Maven 3.9.x
- Java 23

**🎯 Objetivo:** Guia completo de testes unitários para aulas práticas, com foco em `ProductFullTest.java` como referência principal.

---

**Made with ❤️ for Testing Excellence**
