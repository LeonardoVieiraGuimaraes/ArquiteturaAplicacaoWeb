# Exemplo 03 - Salvamento em Memória (H2 Database)

## Descrição
Este exemplo demonstra como criar uma API REST utilizando Spring Boot com persistência de dados em **H2 Database**, um banco de dados relacional em memória. Os dados são armazenados temporariamente durante a execução da aplicação e são perdidos ao reiniciar.

## Tecnologias Utilizadas
- **Java 23**
- **Spring Boot 3.4.3**
- **Spring Web** - Para criação de APIs REST
- **Spring Data JPA** - Para persistência de dados com JPA/Hibernate
- **H2 Database** - Banco de dados relacional em memória
- **Maven** - Gerenciamento de dependências

## Estrutura do Projeto
```
exemplo03/demo/
├── src/main/java/com/example/demo/
│   ├── DemoApplication.java          # Classe principal
│   ├── controller/
│   │   ├── HelloController.java      # Controller simples
│   │   └── ProductController.java    # Controller de produtos
│   ├── service/
│   │   └── ProductService.java       # Lógica de negócio
│   ├── repository/
│   │   └── ProductRepository.java    # Interface JPA Repository
│   └── model/
│       └── Product.java              # Entidade JPA Product
└── src/main/resources/
    └── application.properties        # Configurações do H2
```

## Características

### Persistência com H2
- Banco de dados SQL em memória
- Dados armazenados temporariamente (perdidos ao reiniciar)
- IDs auto-incrementados pelo banco de dados
- Suporte completo a SQL e JPA
- Console web integrado para visualização dos dados

### Entidade Product
```java
@Entity
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    // Getters e Setters
}
```

### Repository Pattern
Interface `ProductRepository` que estende `JpaRepository`:
```java
public interface ProductRepository extends JpaRepository<Product, Long> {
    Optional<Product> findByName(String name);
}
```

## Configuração do H2

### application.properties
```properties
spring.application.name=demo

# H2 Database Configuration
spring.datasource.url=jdbc:h2:mem:testdb
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=

# H2 Console (para acessar o banco via navegador)
spring.h2.console.enabled=true
spring.h2.console.path=/h2-console

# JPA/Hibernate
spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

## H2 Console

### Acessando o Console Web
Após iniciar a aplicação, acesse:
```
http://localhost:8080/h2-console
```

### Configurações de Conexão:
- **JDBC URL**: `jdbc:h2:mem:testdb`
- **User Name**: `sa`
- **Password**: (deixe em branco)

## Endpoints Disponíveis

### 1. Listar todos os produtos
```http
GET http://localhost:8080/products
```

### 2. Buscar produto por ID
```http
GET http://localhost:8080/products/{id}
```

### 3. Buscar produto por nome
```http
GET http://localhost:8080/products/name/{name}
```

### 4. Adicionar novo produto
```http
POST http://localhost:8080/products
Content-Type: application/json

{
    "name": "Notebook"
}
```

### 5. Atualizar produto
```http
PUT http://localhost:8080/products/{id}
Content-Type: application/json

{
    "name": "Notebook Gamer"
}
```

### 6. Deletar produto
```http
DELETE http://localhost:8080/products/{id}
```

## Como Executar

### Pré-requisitos
- JDK 23 ou compatível
- Maven 3.6+

### Passos

1. Navegue até a pasta do projeto:
```powershell
cd PersistenciaDados\exemplo03\demo
```

2. Compile o projeto:
```powershell
mvn clean install
```

3. Execute a aplicação:
```powershell
mvn spring-boot:run
```

4. A aplicação estará disponível em: `http://localhost:8080`

## Testando a API

### Usando cURL

**Adicionar produto:**
```powershell
curl -X POST http://localhost:8080/products `
  -H "Content-Type: application/json" `
  -d '{\"name\":\"Mouse\"}'
```

**Listar produtos:**
```powershell
curl http://localhost:8080/products
```

**Buscar produto por ID:**
```powershell
curl http://localhost:8080/products/1
```

**Buscar produto por nome:**
```powershell
curl http://localhost:8080/products/name/Mouse
```

**Atualizar produto:**
```powershell
curl -X PUT http://localhost:8080/products/1 `
  -H "Content-Type: application/json" `
  -d '{\"name\":\"Mouse Gamer\"}'
```

**Deletar produto:**
```powershell
curl -X DELETE http://localhost:8080/products/1
```

## Usando o H2 Console

### 1. Acesse o console
```
http://localhost:8080/h2-console
```

### 2. Consultas SQL úteis

**Ver todos os produtos:**
```sql
SELECT * FROM PRODUCTS;
```

**Inserir produto manualmente:**
```sql
INSERT INTO PRODUCTS (NAME) VALUES ('Teclado Mecânico');
```

**Atualizar produto:**
```sql
UPDATE PRODUCTS SET NAME = 'Teclado RGB' WHERE ID = 1;
```

**Deletar produto:**
```sql
DELETE FROM PRODUCTS WHERE ID = 1;
```

**Contar produtos:**
```sql
SELECT COUNT(*) FROM PRODUCTS;
```

## Vantagens do H2
- ✅ Banco de dados SQL completo em memória
- ✅ Não requer instalação externa
- ✅ Console web integrado para visualização
- ✅ Compatível com SQL padrão
- ✅ Ideal para desenvolvimento e testes
- ✅ Suporte a JPA/Hibernate
- ✅ Alto desempenho

## Desvantagens
- ❌ Dados não persistem após reiniciar
- ❌ Não adequado para produção
- ❌ Limitado pela memória RAM
- ❌ Dados perdidos em caso de crash

## Quando Usar H2
- Desenvolvimento e testes locais
- Testes unitários e de integração
- Protótipos rápidos
- Aprendizado de JPA/Hibernate
- Quando não quer configurar banco externo

## Diferenças dos Exemplos Anteriores

| Aspecto | Exemplo 01 | Exemplo 02 | Exemplo 03 |
|---------|-----------|-----------|-----------|
| Tipo | Lista | NoSQL | SQL em memória |
| Persistência | Não | Sim | Não |
| ID | Long | String (ObjectId) | Long (auto-increment) |
| Banco | Nenhum | MongoDB | H2 |
| Repository | Não | MongoRepository | JpaRepository |
| SQL | Não | Não | Sim |
| Console | Não | CLI | Web |

## Estrutura de Dados no H2

### Tabela PRODUCTS
```
ID (BIGINT, AUTO_INCREMENT, PRIMARY KEY)
NAME (VARCHAR)
```

### Exemplo de dados:
```
ID  | NAME
----|----------------
1   | Notebook
2   | Mouse
3   | Teclado
```

## Migrando para Produção

Para usar em produção, você pode facilmente trocar o H2 por outro banco SQL alterando apenas o `application.properties`:

### MySQL/MariaDB:
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/productdb
spring.datasource.username=root
spring.datasource.password=senha
```

### PostgreSQL:
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/productdb
spring.datasource.username=postgres
spring.datasource.password=senha
```

O código Java permanece o mesmo! (Graças ao JPA)

## Próximos Passos
- Ver **exemplo04** para persistência com MariaDB (banco relacional permanente)
- Implementar validações com Bean Validation
- Adicionar paginação e ordenação
- Criar testes unitários

## Troubleshooting

### Erro: "Table not found"
- O Hibernate cria automaticamente as tabelas
- Verifique se `spring.jpa.hibernate.ddl-auto=update` está configurado

### Erro: "Console H2 não abre"
- Verifique se `spring.h2.console.enabled=true`
- Acesse `http://localhost:8080/h2-console`

## Autor
Curso de Arquitetura de Aplicação Web - Newton Paiva

## Licença
Este projeto é livre para uso educacional.
