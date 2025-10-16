# Exemplo 01 - Salvamento em Lista (Memória)

## Descrição
Este exemplo demonstra como criar uma API REST utilizando Spring Boot com armazenamento de dados em uma **lista em memória** (ArrayList). Não há persistência em banco de dados - os dados são mantidos apenas durante a execução da aplicação.

## Tecnologias Utilizadas
- **Java 11+**
- **Spring Boot 3.x**
- **Spring Web** - Para criação de APIs REST
- **Maven** - Gerenciamento de dependências

## Estrutura do Projeto
```
exemplo01/demo/
├── src/main/java/com/example/demo/
│   ├── DemoApplication.java          # Classe principal
│   ├── controller/
│   │   ├── HelloController.java      # Controller simples
│   │   └── ProductController.java    # Controller de produtos
│   └── model/
│       └── Product.java              # Entidade Product
```

## Características

### Armazenamento em Lista
- Os dados são armazenados em um `ArrayList<Product>` no próprio controller
- Não há persistência - ao reiniciar a aplicação, os dados são perdidos
- IDs são gerados sequencialmente usando uma variável `nextId`

### Entidade Product
```java
public class Product {
    private Long id;
    private String name;
    // Getters e Setters
}
```

## Endpoints Disponíveis

### 1. Listar todos os produtos
```http
GET http://localhost:8080/products
```

### 2. Buscar produto por ID
```http
GET http://localhost:8080/products/{id}
```

### 3. Adicionar novo produto
```http
POST http://localhost:8080/products
Content-Type: application/json

{
    "name": "Notebook"
}
```

### 4. Atualizar produto
```http
PUT http://localhost:8080/products/{id}
Content-Type: application/json

{
    "name": "Notebook Gamer"
}
```

### 5. Deletar produto
```http
DELETE http://localhost:8080/products/{id}
```

## Como Executar

### Pré-requisitos
- JDK 11 ou superior
- Maven 3.6+

### Passos

1. Navegue até a pasta do projeto:
```bash
cd PersistenciaDados/exemplo01/demo
```

2. Compile o projeto:
```bash
mvn clean install
```

3. Execute a aplicação:
```bash
mvn spring-boot:run
```

4. A aplicação estará disponível em: `http://localhost:8080`

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
