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

### Como customizar a documentação do Swagger/OpenAPI

A documentação é gerada automaticamente pelo springdoc. Você pode customizar de várias formas:

1) Metadados globais (título, descrição, versão, contato, licença, servidores)

- Arquivo/classe: `com.example.demo.config.OpenApiConfig`
- Edite o bean `OpenAPI` para ajustar título, descrição, contato, licença e servidores:

```java
@Bean
public OpenAPI customOpenAPI() {
  return new OpenAPI()
    .info(new Info()
      .title("Demo API — Autores e Livros")
      .description("Exemplo de API com JPA, DTOs, validação e Swagger")
      .version("v1")
      .contact(new Contact().name("Seu Nome").email("seu@email").url("https://seusite"))
      .license(new License().name("MIT").url("https://opensource.org/licenses/MIT")))
    .externalDocs(new ExternalDocumentation()
      .description("Docs Extras")
      .url("https://link-externo"))
    .servers(List.of(
      new Server().url("http://localhost:8080").description("Local"),
      new Server().url("https://api.exemplo.com").description("Produção")
    ));
}
```

2) Agrupar endpoints por contexto (menus no Swagger UI)

- Adicione beans `GroupedOpenApi` para criar grupos por caminho ou pacote:

```java
@Bean
public GroupedOpenApi autoresGroup() {
  return GroupedOpenApi.builder()
    .group("Autores")
    .pathsToMatch("/api/authors/**")
    .build();
}

@Bean
public GroupedOpenApi livrosGroup() {
  return GroupedOpenApi.builder()
    .group("Livros")
    .pathsToMatch("/api/books/**")
    .build();
}
```

3) Anotações nos Controllers/DTOs

- Em classes controller, use `@Tag(name, description)` para descrever o grupo.
- Em métodos, use `@Operation(summary, description)` e `@ApiResponses` para status e exemplos.
- Em parâmetros, use `@Parameter` para descrição/constraints.
- Em DTOs/campos, use `@Schema` para descrever tipos, formatos, exemplos.
- Para esconder endpoints da doc, use `@Hidden` (ou `@Operation(hidden = true)`).

Exemplo de método documentado:

```java
@Operation(summary = "Cria um livro", description = "Cria um livro vinculado a um autor existente")
@ApiResponses({
  @ApiResponse(responseCode = "201", description = "Criado",
    content = @Content(
      mediaType = "application/json",
      schema = @Schema(implementation = BookResponse.class),
      examples = @ExampleObject(
        name = "Exemplo de retorno",
        value = "{\n  \"id\": 1,\n  \"title\": \"Dom Casmurro\",\n  \"authorId\": 1,\n  \"authorName\": \"Machado de Assis\"\n}"
      )
    )
  ),
  @ApiResponse(responseCode = "400", description = "Requisição inválida"),
  @ApiResponse(responseCode = "404", description = "Autor não encontrado")
})
public ResponseEntity<BookResponse> create(@Valid @RequestBody BookRequest request) { ... }
```

4) Opções do Swagger UI via `application.properties`

```properties
# Caminhos
springdoc.swagger-ui.path=/swagger-ui.html
springdoc.api-docs.path=/v3/api-docs

# UI/UX
springdoc.swagger-ui.doc-expansion=none           # none | list | full
springdoc.swagger-ui.tagsSorter=alpha             # alpha | method | none
springdoc.swagger-ui.operationsSorter=alpha       # alpha | method | none
springdoc.swagger-ui.display-request-duration=true
springdoc.swagger-ui.tryItOutEnabled=true

# Tipos padrão
springdoc.default-produces-media-type=application/json
springdoc.default-consumes-media-type=application/json
```

5) Segurança (opcional)

- Você pode definir `SecurityScheme` (ex.: Bearer JWT) e referenciar com `@SecurityRequirement` nos métodos. Isso exibe o cadeado "Authorize" no Swagger UI.

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

- Raiz do projeto:
  - `pom.xml` — dependências e configuração do Maven/Spring Boot.
  - `mvnw`, `mvnw.cmd` — wrappers do Maven (não precisa instalar Maven).
  - `HELP.md` — ajuda padrão do Spring Initializr.

- `src/main/java/com/example/demo/`
  - `DemoApplication.java` — classe principal que inicia o Spring Boot.
  - `config/`
    - `OpenApiConfig` — metadados do Swagger/OpenAPI e (opcional) grupos `GroupedOpenApi`.
    - `DataLoader` — popula dados iniciais em memória no startup.
  - `controller/` — REST Controllers: recebem requests, validam `@Valid` e orquestram services.
  - `service/` — regras de negócio; transações `@Transactional`; chama repositórios e mappers.
  - `repository/` — interfaces Spring Data JPA (`JpaRepository`) para acesso ao banco.
  - `model/` — entidades JPA mapeadas (`@Entity`) e relacionamentos (Author 1:N Book).
  - `dto/` — objetos de transporte (Request/Response) com validação (Jakarta Validation).
  - `mapper/` — conversão entre entidades e DTOs (isola formatação/transformação).
  - `exception/` — exceções e handler global (`@ControllerAdvice`) para 400/404/500.

- `src/main/resources/`
  - `application.properties` — configurações (H2, JPA, springdoc, etc.).
  - `static/` — arquivos estáticos (HTML/CSS/JS) servidos diretamente (não usado aqui).
  - `templates/` — templates de view (Thymeleaf/Freemarker) caso fosse MVC server-side (não usado aqui).

- `src/test/java/com/example/demo/`
  - `DemoApplicationTests` — teste básico de contexto.
  - `ApiDocsIT` — teste de integração para `/v3/api-docs` (Swagger funcionando).

- `target/` — arquivos gerados de build (classes compiladas, jar, etc.).

### Camadas e fluxo (para aula)

Request HTTP → `controller` (valida DTO) → `service` (negócio/transação) → `repository` (JPA/H2)

Resposta: entidade → `mapper` → DTO de saída → JSON para o cliente.

## Dados iniciais

Ao subir, alguns autores e livros são populados automaticamente (veja `DataLoader`).

## Observações

- Todas as classes foram comentadas em português explicando o que fazem.
- A validação usa Jakarta Validation (@Valid, @NotBlank, @Size, @NotNull) nas DTOs.
- Erros de validação retornam HTTP 400 com os campos e mensagens; recursos não encontrados retornam HTTP 404.
