# Spring Boot CRUD Example

Este projeto é um exemplo de uma aplicação Spring Boot que implementa operações CRUD (Create, Read, Update, Delete) utilizando diferentes bancos de dados: H2, MariaDB e MongoDB.

## Estrutura do Projeto

```
spring-boot-crud
├── src
│   ├── main
│   │   ├── java
│   │   │   └── com
│   │   │       └── example
│   │   │           └── crud
│   │   │               ├── CrudApplication.java
│   │   │               ├── controller
│   │   │               │   └── CrudController.java
│   │   │               ├── model
│   │   │               │   └── CrudModel.java
│   │   │               ├── repository
│   │   │               │   └── CrudRepository.java
│   │   │               └── service
│   │   │                   └── CrudService.java
│   │   └── resources
│   │       ├── application-h2.properties
│   │       ├── application-mariadb.properties
│   │       ├── application-mongodb.properties
│   │       └── data.sql
├── pom.xml
└── README.md
```

## Dependências

Este projeto utiliza Maven como gerenciador de dependências. As principais dependências incluem:

- Spring Boot Starter Web
- Spring Boot Starter Data JPA
- H2 Database
- MariaDB Driver
- Spring Data MongoDB

## Configuração do Banco de Dados

### H2

As configurações para o banco de dados H2 estão localizadas em `src/main/resources/application-h2.properties`. O H2 é um banco de dados em memória, ideal para desenvolvimento e testes.

### MariaDB

As configurações para o banco de dados MariaDB estão em `src/main/resources/application-mariadb.properties`. O MariaDB é um banco de dados relacional que pode ser utilizado em produção.

### MongoDB

As configurações para o banco de dados MongoDB estão em `src/main/resources/application-mongodb.properties`. O MongoDB é um banco de dados NoSQL que permite armazenar dados em formato de documento.

## Inicialização do Projeto

Para iniciar o projeto, você pode usar o comando Maven:

```
mvn spring-boot:run
```

## Uso

Após iniciar a aplicação, você pode acessar os endpoints da API para realizar operações CRUD. Os endpoints estão definidos na classe `CrudController`.

## Exemplos de Comandos CURL

1. **GET - Obter todos os registros**  
   ```bash
   curl -X GET "http://localhost:8080/api/crud"
   ```

2. **GET - Obter um registro por ID**  
   ```bash
   curl -X GET "http://localhost:8080/api/crud/<id>"
   ```
   Substitua `<id>` pelo ID do registro que deseja buscar.

3. **POST - Criar um novo registro**  
   ```bash
   curl -X POST "http://localhost:8080/api/crud" -H "Content-Type: application/json" -d '{"name":"Registro A", "description":"Descrição do Registro A"}'
   ```

4. **PUT - Atualizar um registro existente**  
   ```bash
   curl -X PUT "http://localhost:8080/api/crud/<id>" -H "Content-Type: application/json" -d '{"name":"Registro B", "description":"Descrição atualizada do Registro B"}'
   ```
   Substitua `<id>` pelo ID do registro que deseja atualizar e o JSON pelo corpo atualizado.

5. **DELETE - Excluir um registro por ID**  
   ```bash
   curl -X DELETE "http://localhost:8080/api/crud/<id>"
   ```
   Substitua `<id>` pelo ID do registro que deseja excluir.

## Contribuição

Sinta-se à vontade para contribuir com melhorias ou correções. Para isso, faça um fork do repositório e envie um pull request.

## Licença

Este projeto está licenciado sob a MIT License. Veja o arquivo LICENSE para mais detalhes.

