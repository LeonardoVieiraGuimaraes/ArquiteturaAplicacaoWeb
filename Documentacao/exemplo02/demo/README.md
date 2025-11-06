# Demo: CRUD em memória com H2, JPA, MVC, DTO, Validação e Swagger

Exemplo simples de uma API REST com relacionamento 1:N (Autor -> Livro) usando:
- Spring Boot 3 (Web, Data JPA, Validation)
- Banco H2 em memória (com console web)
- Lombok para reduzir boilerplate
- DTOs e validação com Jakarta Validation (@Valid)
- Swagger/OpenAPI (springdoc) com informações customizadas
- Padrão MVC (Controllers -> Services -> Repositories -> Entities)

## Como rodar

Requisitos: JDK 21+.

No Windows (PowerShell):

```powershell
./mvnw.cmd spring-boot:run
```

A aplicação iniciará em `http://localhost:8080`.

## Documentação Swagger

- UI: http://localhost:8080/swagger-ui.html
- OpenAPI JSON: http://localhost:8080/v3/api-docs

As informações (título, descrição, contato e licença) são configuradas em `com.example.demo.config.OpenApiConfig`.

## Console do H2

- URL: http://localhost:8080/h2-console
- JDBC URL: `jdbc:h2:mem:demo`
- Usuário: `sa`
- Senha: (vazio)

## Endpoints principais

### Autores
- POST `/api/authors` — cria autor
- GET `/api/authors` — lista autores
- GET `/api/authors/{id}` — detalhe do autor
- PUT `/api/authors/{id}` — atualiza autor
- DELETE `/api/authors/{id}` — remove autor

Body (POST/PUT):
```json
{
  "name": "Machado de Assis"
}
```

Resposta:
```json
{
  "id": 1,
  "name": "Machado de Assis"
}
```

### Livros
- POST `/api/books` — cria livro (requer `authorId` existente)
- GET `/api/books` — lista livros
- GET `/api/books/{id}` — detalhe do livro
- PUT `/api/books/{id}` — atualiza livro
- DELETE `/api/books/{id}` — remove livro

Body (POST/PUT):
```json
{
  "title": "Dom Casmurro",
  "authorId": 1
}
```

Resposta:
```json
{
  "id": 10,
  "title": "Dom Casmurro",
  "authorId": 1,
  "authorName": "Machado de Assis"
}
```

## Estrutura do projeto

- `model/` — Entidades JPA (`Author`, `Book`)
- `repository/` — Repositórios Spring Data JPA
- `service/` — Regras de negócio e transações
- `controller/` — Camada web (REST), validação de entrada
- `dto/` — DTOs de entrada/saída
- `mapper/` — Conversão entidade <-> DTO
- `exception/` — Exceções e handler global
- `config/OpenApiConfig` — Config do Swagger; `config/DataLoader` — dados iniciais

## Dados iniciais

Ao subir, alguns autores e livros são populados automaticamente (veja `DataLoader`).

## Observações

- Todas as classes foram comentadas em português explicando o que fazem.
- A validação usa Jakarta Validation (@Valid, @NotBlank, @Size, @NotNull) nas DTOs.
- Erros de validação retornam HTTP 400 com os campos e mensagens; recursos não encontrados retornam HTTP 404.
