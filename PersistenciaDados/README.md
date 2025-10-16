# Persistência de Dados - Exemplos Práticos

## Descrição Geral
Esta pasta contém 4 exemplos práticos de persistência de dados usando Spring Boot, demonstrando diferentes abordagens para armazenamento de informações em aplicações Java.

## Estrutura dos Exemplos

### 📂 Exemplo 01 - Salvamento em Lista (Memória)
**Descrição**: Armazenamento simples usando `ArrayList` em memória.

**Características**:
- ✅ Mais simples
- ✅ Não requer configuração
- ❌ Dados não persistem
- ❌ Apenas para testes/protótipos

**Quando usar**: Protótipos rápidos, testes, aprendizado inicial.

[📖 Ver documentação completa](./exemplo01/README.md)

---

### 📂 Exemplo 02 - Salvamento com MongoDB
**Descrição**: Persistência permanente usando MongoDB (NoSQL).

**Características**:
- ✅ Persistência permanente
- ✅ Esquema flexível
- ✅ Alto desempenho
- ❌ Requer instalação do MongoDB

**Quando usar**: Dados não estruturados, aplicações escaláveis, Big Data.

[📖 Ver documentação completa](./exemplo02/README.md)

---

### 📂 Exemplo 03 - Salvamento em Memória (H2 Database)
**Descrição**: Banco de dados SQL em memória usando H2.

**Características**:
- ✅ SQL completo em memória
- ✅ Console web integrado
- ✅ Não requer instalação
- ❌ Dados não persistem ao reiniciar

**Quando usar**: Desenvolvimento, testes unitários, prototipação com SQL.

[📖 Ver documentação completa](./exemplo03/README.md)

---

### 📂 Exemplo 04 - Salvamento com MariaDB
**Descrição**: Persistência permanente usando MariaDB (SQL relacional).

**Características**:
- ✅ Persistência permanente
- ✅ Transações ACID
- ✅ Adequado para produção
- ❌ Requer instalação e configuração

**Quando usar**: Aplicações em produção, dados relacionais, transações complexas.

[📖 Ver documentação completa](./exemplo04/README.md)

---

## Comparação Rápida

| Exemplo | Tipo | Persistência | Banco | Complexidade | Produção |
|---------|------|-------------|-------|--------------|----------|
| **01** | Lista | ❌ Não | Nenhum | ⭐ Baixa | ❌ |
| **02** | NoSQL | ✅ Sim | MongoDB | ⭐⭐ Média | ✅ |
| **03** | SQL | ❌ Não | H2 | ⭐ Baixa | ❌ |
| **04** | SQL | ✅ Sim | MariaDB | ⭐⭐ Média | ✅ |

## Tecnologias Utilizadas

### Comum a Todos:
- Java 11+ (exemplo01) / Java 21-23 (demais)
- Spring Boot 3.4.3
- Spring Web
- Maven

### Específicas:
- **Exemplo 02**: Spring Data MongoDB, MongoDB
- **Exemplo 03**: Spring Data JPA, H2 Database
- **Exemplo 04**: Spring Data JPA, MariaDB

## Pré-requisitos

### Para todos os exemplos:
- JDK 11 ou superior (recomendado JDK 21+)
- Maven 3.6+

### Instalações adicionais:
- **Exemplo 02**: MongoDB
- **Exemplo 04**: MariaDB

## Como Usar Este Repositório

### 1. Clone o repositório
```bash
git clone https://github.com/LeonardoVieiraGuimaraes/ArquiteturaAplicacaoWeb.git
cd ArquiteturaAplicacaoWeb/PersistenciaDados
```

### 2. Escolha um exemplo
```bash
cd exemplo01/demo  # ou exemplo02, exemplo03, exemplo04
```

### 3. Execute o exemplo
```bash
mvn clean install
mvn spring-boot:run
```

### 4. Teste a API
```bash
curl http://localhost:8080/products
```

## Endpoints Comuns a Todos

Todos os exemplos implementam os mesmos endpoints REST:

### Listar produtos
```http
GET http://localhost:8080/products
```

### Buscar por ID
```http
GET http://localhost:8080/products/{id}
```

### Adicionar produto
```http
POST http://localhost:8080/products
Content-Type: application/json

{
    "name": "Produto Teste"
}
```

### Atualizar produto
```http
PUT http://localhost:8080/products/{id}
Content-Type: application/json

{
    "name": "Produto Atualizado"
}
```

### Deletar produto
```http
DELETE http://localhost:8080/products/{id}
```

**Nota**: Exemplo02 usa ID do tipo String (ObjectId do MongoDB), os demais usam Long.

## Padrões de Projeto Utilizados

### Repository Pattern
Utilizado nos exemplos 02, 03 e 04:
```java
public interface ProductRepository extends JpaRepository<Product, Long> {
    Optional<Product> findByName(String name);
}
```

### Service Layer
Utilizado nos exemplos 02, 03 e 04:
```java
@Service
public class ProductService {
    @Autowired
    private ProductRepository repository;
    // ... métodos de negócio
}
```

### Controller (MVC)
Utilizado em todos os exemplos:
```java
@RestController
@RequestMapping("/products")
public class ProductController {
    // ... endpoints REST
}
```

## Evolução do Aprendizado

### Nível Iniciante
1. **Comece pelo Exemplo 01**: Entenda o básico de REST API
2. **Passe para o Exemplo 03**: Aprenda SQL e JPA com H2

### Nível Intermediário
3. **Explore o Exemplo 04**: Configure um banco real (MariaDB)
4. **Estude o Exemplo 02**: Conheça NoSQL com MongoDB

### Nível Avançado
5. Compare as abordagens e escolha a melhor para cada caso
6. Implemente validações, testes e segurança
7. Adicione features como paginação e cache

## Conceitos Importantes

### Persistência
- **Temporária** (Exemplo 01, 03): Dados existem apenas na memória RAM
- **Permanente** (Exemplo 02, 04): Dados salvos no disco

### Tipos de Banco
- **Relacional** (SQL): Exemplo 03 e 04 - Dados em tabelas relacionadas
- **Não-Relacional** (NoSQL): Exemplo 02 - Documentos JSON

### JPA/Hibernate
- Framework de mapeamento objeto-relacional (ORM)
- Usado nos exemplos 03 e 04
- Facilita trabalhar com bancos SQL em Java

### Spring Data
- Abstração para acesso a dados
- **Spring Data JPA**: Exemplos 03 e 04
- **Spring Data MongoDB**: Exemplo 02

## Arquitetura das Aplicações

```
┌─────────────────────────────────────────┐
│          Cliente (Browser/App)          │
└───────────────┬─────────────────────────┘
                │ HTTP REST
                ▼
┌─────────────────────────────────────────┐
│         Controller (REST API)           │
│  - Recebe requisições HTTP              │
│  - Valida entrada                       │
│  - Retorna respostas JSON               │
└───────────────┬─────────────────────────┘
                │
                ▼
┌─────────────────────────────────────────┐
│         Service (Lógica de Negócio)     │
│  - Regras de negócio                    │
│  - Transações                           │
│  - Validações complexas                 │
└───────────────┬─────────────────────────┘
                │
                ▼
┌─────────────────────────────────────────┐
│      Repository (Acesso a Dados)        │
│  - Abstração do banco                   │
│  - Queries customizadas                 │
└───────────────┬─────────────────────────┘
                │
                ▼
┌─────────────────────────────────────────┐
│    Banco de Dados / Armazenamento       │
│  Exemplo 01: ArrayList                  │
│  Exemplo 02: MongoDB                    │
│  Exemplo 03: H2 (memória)               │
│  Exemplo 04: MariaDB                    │
└─────────────────────────────────────────┘
```

## Dicas e Boas Práticas

### Segurança
- ⚠️ Nunca commite senhas no código
- ✅ Use variáveis de ambiente para credenciais
- ✅ Implemente validação de entrada
- ✅ Use HTTPS em produção

### Performance
- ✅ Use índices no banco de dados
- ✅ Implemente cache quando apropriado
- ✅ Use paginação para listas grandes
- ✅ Otimize queries N+1

### Manutenibilidade
- ✅ Separe responsabilidades (Controller, Service, Repository)
- ✅ Use nomes descritivos
- ✅ Documente código complexo
- ✅ Escreva testes

## Próximos Passos

Após dominar estes exemplos, explore:

1. **Validação**: Bean Validation (@Valid, @NotNull, @Size)
2. **Tratamento de Erros**: @ExceptionHandler, @ControllerAdvice
3. **Paginação**: Pageable, Page<T>
4. **Testes**: JUnit, Mockito, @SpringBootTest
5. **Segurança**: Spring Security, JWT
6. **Documentação**: Swagger/OpenAPI
7. **Cache**: Redis, Caffeine
8. **Monitoramento**: Spring Actuator, Prometheus

## Recursos Adicionais

### Documentação Oficial
- [Spring Boot](https://spring.io/projects/spring-boot)
- [Spring Data JPA](https://spring.io/projects/spring-data-jpa)
- [Spring Data MongoDB](https://spring.io/projects/spring-data-mongodb)
- [H2 Database](https://www.h2database.com/)
- [MariaDB](https://mariadb.org/)
- [MongoDB](https://www.mongodb.com/)

### Tutoriais
- [Spring Guides](https://spring.io/guides)
- [Baeldung Spring Tutorials](https://www.baeldung.com/spring-tutorial)

## Contribuindo

Este é um projeto educacional. Sinta-se livre para:
- Reportar bugs
- Sugerir melhorias
- Adicionar novos exemplos
- Melhorar documentação

## Autor
**Curso de Arquitetura de Aplicação Web**  
Newton Paiva - 2025

## Licença
Este projeto é livre para uso educacional.

---

**📚 Bons estudos!** 🚀
