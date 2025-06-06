# Aplicação de Gerenciamento de Produtos

Esta aplicação é um exemplo de um sistema de gerenciamento de produtos utilizando Spring Boot. A aplicação permite listar, buscar, adicionar, atualizar e deletar produtos.

## Estrutura do Projeto

- **Controller**: Lida com as requisições HTTP e define as rotas.
- **Service**: Contém a lógica de negócios.
- **Model**: Define a estrutura dos dados (neste caso, o modelo `Product`).

## Bibliotecas e Códigos Utilizados

### Spring Boot

- **@RestController**: Marca a classe como um controlador que lida com requisições HTTP.
- **@RequestMapping**: Define a rota base para os métodos do controlador.
- **@Autowired**: Injeta automaticamente uma instância de uma classe (neste caso, `ProductService`).
- **@GetMapping, @PostMapping, @PutMapping, @DeleteMapping**: Mapeiam métodos HTTP (GET, POST, PUT, DELETE) para métodos específicos no controlador.
- **@ResponseStatus**: Define o status HTTP padrão para a resposta de um método (neste caso, `HttpStatus.CREATED` para o método `addProduct`).

### Java Util

- **Optional**: Uma classe que pode ou não conter um valor. Evita `NullPointerException`. O NullPointerException é um erro em Java que ocorre quando o código tenta usar uma referência que não aponta para nenhum objeto (ou seja, a referência é null).
  - **map**: Aplica uma função ao valor contido, se presente, e retorna um novo `Optional` com o resultado.
  - **orElse**: Retorna o valor contido, se presente, ou um valor padrão se não estiver presente.
- **Stream API**: Processa coleções de forma funcional.
  - **filter**: Filtra elementos de um stream com base em uma condição.
  - **findFirst**: Retorna o primeiro elemento de um stream, se presente.
- **removeIf**: Remove todos os elementos de uma coleção que atendem a uma condição especificada.

### Exemplo de Uso

#### Controller

```java
@GetMapping("/{id}")
public ResponseEntity<Product> getProductById(@PathVariable Long id) {
    Optional<Product> product = productService.getProductById(id);
    return product.map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
}
```

- **@GetMapping("/{id}")**: Mapeia requisições GET para o método `getProductById`.
- **@PathVariable**: Extrai o valor da variável de caminho `{id}`.
- **Optional.map**: Aplica `ResponseEntity::ok` ao produto, se presente.
- **Optional.orElse**: Retorna `ResponseEntity.notFound().build()` se o produto não estiver presente.

#### Service

```java
public boolean deleteProduct(Long id) {
    return products.removeIf(p -> p.getId().equals(id));
}
```

- **removeIf**: Remove o produto da lista se o ID corresponder.

### Model

```java
public class Product {
    private Long id;
    private String name;

    // Getters e setters
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

- **Product**: Classe que representa o modelo de dados do produto.
- **Long id**: Identificador único do produto.
- **String name**: Nome do produto.
- **Getters e setters**: Métodos para acessar e modificar os atributos do produto.

## Executando a Aplicação

1. Clone o repositório.
2. Navegue até o diretório do projeto.
3. Execute `mvn spring-boot:run` para iniciar a aplicação.

A aplicação estará disponível em `http://localhost:8080/api/products`.

## Endpoints

- **GET /api/products**: Lista todos os produtos.
- **GET /api/products/{id}**: Busca um produto por ID.
- **POST /api/products**: Adiciona um novo produto.
- **PUT /api/products/{id}**: Atualiza um produto existente.
- **DELETE /api/products/{id}**: Remove um produto por ID.

## Tecnologias Utilizadas

- **Spring Boot**: Framework para criação de aplicações Java baseadas em Spring.
- **Maven**: Ferramenta de automação de compilação e gerenciamento de dependências.
- **Java 8+**: Linguagem de programação utilizada para desenvolver a aplicação.

