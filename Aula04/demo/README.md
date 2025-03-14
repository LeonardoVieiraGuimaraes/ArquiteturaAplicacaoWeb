



# API de Produtos

## Descrição do Problema

O objetivo deste projeto é criar uma API RESTful para gerenciar produtos. A API deve permitir a criação, atualização, listagem, busca por ID e remoção de produtos. Cada produto possui um identificador único, um nome e uma descrição.

## Passos para Construção do Projeto

1. **Configuração do Ambiente de Desenvolvimento**:
   - Instale o JDK (Java Development Kit).
   - Instale o Maven para gerenciamento de dependências.
   - Configure uma IDE de sua escolha (Eclipse, IntelliJ, etc.).

2. **Criação do Projeto Spring Boot**:
   - Use o Spring Initializr para gerar a estrutura básica do projeto Spring Boot.
   - Inclua dependências como Spring Web e Spring Data JPA.

3. **Definição do Modelo**:
   - Crie a classe `Product` no pacote `com.example.demo.model` com os atributos `id`, `name` e `description`.

4. **Criação do Repositório**:
   - Crie a interface `ProductRepository` no pacote `com.example.demo.repository` estendendo `JpaRepository`.

5. **Implementação do Serviço**:
   - Crie a classe `ProductService` no pacote `com.example.demo.service` para encapsular a lógica de negócios.

6. **Criação do Controlador**:
   - Crie a classe `ProductController` no pacote `com.example.demo.controller` para expor os endpoints da API.

7. **Testes**:
   - Implemente testes unitários e de integração para garantir o funcionamento correto da API.

8. **Documentação**:
   - Atualize o arquivo README.md com instruções de uso e exemplos de chamadas de API.

9. **Execução do Projeto**:
   - Execute a aplicação Spring Boot e teste os endpoints usando ferramentas como Postman ou curl.

Seguindo esses passos, você será capaz de construir uma API RESTful completa para gerenciar produtos.

## Endpoints

### 1. POST (Criar Produto)

Usando curl:
```powershell
curl -X POST -H "Content-Type: application/json" -d '{"name": "Notebook"}' http://localhost:8080/api/products
```

Usando Invoke-RestMethod (PowerShell):
```powershell
Invoke-RestMethod -Uri http://localhost:8080/api/products -Method Post -ContentType "application/json" -Body '{"name": "Notebook"}'
```

### 2. PUT (Atualizar Produto)

Usando curl:
```powershell
curl -X PUT -H "Content-Type: application/json" -d '{"name": "Notebook Gamer"}' http://localhost:8080/api/products/1
```

Usando Invoke-RestMethod (PowerShell):
```powershell
Invoke-RestMethod -Uri http://localhost:8080/api/products/1 -Method Put -ContentType "application/json" -Body '{"name": "Notebook Gamer"}'
```

### 3. GET (Listar Produtos)

Usando curl:
```powershell
curl http://localhost:8080/api/products
```

Usando Invoke-RestMethod (PowerShell):
```powershell
Invoke-RestMethod -Uri http://localhost:8080/api/products -Method Get
```

### 4. GET (Buscar Produto por ID)

Usando curl:
```powershell
curl http://localhost:8080/api/products/1
```

Usando Invoke-RestMethod (PowerShell):
```powershell
Invoke-RestMethod -Uri http://localhost:8080/api/products/1 -Method Get
```

### 5. DELETE (Remover Produto)

Usando curl:
```powershell
curl -X DELETE http://localhost:8080/api/products/1
```

