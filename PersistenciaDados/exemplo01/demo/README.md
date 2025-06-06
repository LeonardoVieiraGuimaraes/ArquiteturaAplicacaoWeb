# Spring Boot Application

Este projeto demonstra como criar uma aplicação Spring Boot com um exemplo de HelloWorld e um exemplo de Product usando Model e Controller.

## Requisitos

- JDK 11 ou superior
- Maven

## Passos para criar a aplicação

### 1. Criar a estrutura do projeto

Use o Spring Initializr para gerar a estrutura básica do projeto Spring Boot.

### 2. Exemplo HelloWorld

#### Criar o Controller

Crie um arquivo `HelloWorldController.java` no pacote `com.example.demo.controller` com o seguinte conteúdo:

```java
// filepath: d:\GitHub\NewtonPaiva\ArquiteturaAplicacaoWeb\Aula03\demo\src\main\java\com\example\demo\controller\HelloWorldController.java
// ...existing code...
```

### 3. Exemplo Product

#### Criar o Model

Crie um arquivo `Product.java` no pacote `com.example.demo.model` com o seguinte conteúdo:

```java
// filepath: d:\GitHub\NewtonPaiva\ArquiteturaAplicacaoWeb\Aula03\demo\src\main\java\com\example\demo\model\Product.java
// ...existing code...
```

#### Criar o Controller

Crie um arquivo `ProductController.java` no pacote `com.example.demo.controller` com o seguinte conteúdo:

```java
// filepath: d:\GitHub\NewtonPaiva\ArquiteturaAplicacaoWeb\Aula03\demo\src\main\java\com\example\demo\controller\ProductController.java
// ...existing code...
```

### 4. Testar a aplicação

#### Testar HelloWorld

Para testar o endpoint HelloWorld, execute o seguinte comando `curl`:

```
curl http://localhost:8080/hello
```

Você deve ver a resposta:

```
Hello, World!
```

#### Testar Product

Para testar os endpoints Product, execute os seguintes comandos `curl`:

- **POST** (Criar um produto):

```
curl -X POST http://localhost:8080/products -H "Content-Type: application/json" -d '{"name": "Product1"}'
```

- **GET** (Listar todos os produtos):

```
curl http://localhost:8080/products
```

- **GET por ID** (Obter um produto pelo ID):

```
curl http://localhost:8080/products/1
```

- **PUT** (Atualizar um produto):

```
curl -X PUT http://localhost:8080/products/1 -H "Content-Type: application/json" -d '{"name": "UpdatedProduct"}'
```

- **DELETE** (Deletar um produto):

```
curl -X DELETE http://localhost:8080/products/1
```

## Executar a aplicação

Para executar a aplicação, use o seguinte comando Maven:

```
mvn spring-boot:run
```

Isso iniciará a aplicação Spring Boot no endereço `http://localhost:8080`.