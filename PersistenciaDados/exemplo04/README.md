# Exemplo 04 - Salvamento em MariaDB (XAMPP Docker)

## Descrição
Este exemplo demonstra como criar uma API REST utilizando Spring Boot com persistência permanente de dados em **MariaDB**, um banco de dados relacional robusto e de código aberto. Os dados são armazenados permanentemente e podem ser consultados mesmo após reiniciar a aplicação.

**🐳 Novidade**: Agora com **XAMPP em Docker** - o pacote completo Apache + MariaDB + PHP + PHPMyAdmin em um único container!

## Tecnologias Utilizadas
- **Java 23**
- **Spring Boot 3.4.3**
- **Spring Web** - Para criação de APIs REST
- **Spring Data JPA** - Para persistência de dados com JPA/Hibernate
- **MariaDB** - Banco de dados relacional
- **Maven** - Gerenciamento de dependências

## Estrutura do Projeto
```
exemplo04/demo/
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
    └── application.properties        # Configurações do MariaDB
```

## Características

### Persistência com MariaDB
- Banco de dados SQL relacional permanente
- Dados armazenados de forma durável no disco
- IDs auto-incrementados pelo banco de dados
- Suporte completo a transações ACID
- Escalável e adequado para produção

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

## Configuração do MariaDB

### application.properties
```properties
spring.application.name=demo

# MariaDB Configuration
spring.datasource.url=jdbc:mariadb://localhost:3306/productdb
spring.datasource.username=root
spring.datasource.password=root
spring.datasource.driver-class-name=org.mariadb.jdbc.Driver

# JPA/Hibernate
spring.jpa.database-platform=org.hibernate.dialect.MariaDBDialect
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
```

## Pré-requisitos

### 🐳 Opção 1: Usando Docker (Recomendado - Mais Fácil!)

**Requisitos**:
- Docker Desktop instalado
- Docker Compose (incluído no Docker Desktop)

**Vantagens**:
- ✅ Não precisa instalar MariaDB manualmente
- ✅ Configuração automática do banco de dados
- ✅ Inclui PHPMyAdmin para gerenciamento visual
- ✅ Isolado do sistema (não interfere com outros bancos)
- ✅ Fácil de iniciar e parar

**Instalação do Docker**:
- Windows: https://docs.docker.com/desktop/install/windows-install/
- Ou via Chocolatey: `choco install docker-desktop`

---

### 📦 Opção 2: Instalação Manual do MariaDB

#### Windows (usando Chocolatey):
```powershell
choco install mariadb
```

#### Ou baixe do site oficial:
- https://mariadb.org/download/

#### Linux (Ubuntu/Debian):
```bash
sudo apt update
sudo apt install mariadb-server
```

#### macOS (usando Homebrew):
```bash
brew install mariadb
```

### 2. Iniciar MariaDB (Apenas se não usar Docker)

#### Windows:
O serviço inicia automaticamente após a instalação.

#### Linux:
```bash
sudo systemctl start mariadb
sudo systemctl enable mariadb
```

#### macOS:
```bash
brew services start mariadb
```

### 3. Configurar MariaDB (Apenas se não usar Docker)

#### Acessar o console:
```bash
mysql -u root -p
```

#### Criar o banco de dados:
```sql
CREATE DATABASE productdb;
```

#### Criar usuário (opcional):
```sql
CREATE USER 'appuser'@'localhost' IDENTIFIED BY 'senha123';
GRANT ALL PRIVILEGES ON productdb.* TO 'appuser'@'localhost';
FLUSH PRIVILEGES;
```

#### Verificar banco criado:
```sql
SHOW DATABASES;
USE productdb;
```

---

## 🐳 Como Executar com Docker (Recomendado)

### 1. Iniciar o MariaDB e PHPMyAdmin com Docker Compose
```powershell
cd exemplo04
docker-compose up -d
```

**O que acontece:**
- ✅ MariaDB será iniciado na porta `3306`
- ✅ PHPMyAdmin será iniciado na porta `8081`
- ✅ Database `productdb` será criado automaticamente
- ✅ Script `init.sql` será executado automaticamente

### 2. Verificar se os containers estão rodando
```powershell
docker-compose ps
```

**Você deve ver:**
```
NAME                     STATUS
exemplo04-mariadb        Up (healthy)
exemplo04-phpmyadmin     Up
```

### 3. Acessar o PHPMyAdmin (Gerenciador Visual)
Abra o navegador em: **http://localhost:8081**

**Credenciais:**
- Servidor: `mariadb`
- Usuário: `root`
- Senha: `root`

### 4. Navegar até o projeto Spring Boot
```powershell
cd demo
```

### 5. Compilar o projeto
```powershell
mvn clean install -DskipTests
```

### 6. Executar a aplicação
```powershell
java -jar .\target\demo-0.0.1-SNAPSHOT.jar
```

### 7. A aplicação estará disponível em: `http://localhost:8080`

### 8. Parar os containers (quando terminar)
```powershell
cd ..
docker-compose down
```

**Para parar E deletar os dados:**
```powershell
docker-compose down -v
```

---

## 📦 Como Executar com Instalação Manual

### 1. Certifique-se de que o MariaDB está rodando
(Veja seção "Iniciar MariaDB" acima)

### 2. Crie o banco de dados
(Veja seção "Configurar MariaDB" acima)

### 3. Navegue até o projeto
```powershell
cd PersistenciaDados\exemplo04\demo
```

### 4. Compile o projeto
```powershell
mvn clean install
```

### 5. Execute a aplicação
```powershell
java -jar .\target\demo-0.0.1-SNAPSHOT.jar
```

### 6. A aplicação estará disponível em: `http://localhost:8080`

---

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

### 1. Certifique-se de que o MariaDB está rodando
```powershell
# Windows - Verificar serviço
Get-Service -Name MariaDB

# Linux
sudo systemctl status mariadb
```

### 2. Crie o banco de dados
```sql
CREATE DATABASE IF NOT EXISTS productdb;
```

### 3. Navegue até a pasta do projeto
```powershell
cd PersistenciaDados\exemplo04\demo
```

### 4. Configure as credenciais no application.properties
Edite o arquivo e ajuste usuário/senha conforme sua instalação:
```properties
spring.datasource.username=root
spring.datasource.password=sua_senha
```

### 5. Compile o projeto
```powershell
mvn clean install
```

### 6. Execute a aplicação
```powershell
mvn spring-boot:run
```

### 7. A aplicação estará disponível em: `http://localhost:8080`

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

## Comandos MariaDB Úteis

### Conectar ao MariaDB
```bash
mysql -u root -p
```

### Usar o banco de dados
```sql
USE productdb;
```

### Listar todos os produtos
```sql
SELECT * FROM products;
```

### Inserir produto manualmente
```sql
INSERT INTO products (name) VALUES ('Teclado Mecânico');
```

### Atualizar produto
```sql
UPDATE products SET name = 'Teclado RGB' WHERE id = 1;
```

### Deletar produto
```sql
DELETE FROM products WHERE id = 1;
```

### Contar produtos
```sql
SELECT COUNT(*) FROM products;
```

### Ver estrutura da tabela
```sql
DESCRIBE products;
```

### Deletar todos os dados (cuidado!)
```sql
TRUNCATE TABLE products;
```

## Vantagens do MariaDB
- ✅ Persistência permanente de dados
- ✅ Banco de dados robusto e confiável
- ✅ Suporte a transações ACID
- ✅ Código aberto e gratuito
- ✅ Compatível com MySQL
- ✅ Alto desempenho
- ✅ Adequado para produção
- ✅ Escalável
- ✅ Grande comunidade e documentação

## Desvantagens
- ❌ Requer instalação e configuração
- ❌ Maior consumo de recursos que H2
- ❌ Necessita gerenciamento (backup, manutenção)

## Quando Usar MariaDB
- Aplicações em produção
- Dados que precisam persistir permanentemente
- Aplicações que requerem transações complexas
- Sistemas com múltiplos usuários concorrentes
- Quando precisar de um banco relacional robusto
- Aplicações enterprise

## Comparação dos 4 Exemplos

| Aspecto | Exemplo 01 | Exemplo 02 | Exemplo 03 | Exemplo 04 |
|---------|-----------|-----------|-----------|-----------|
| **Tipo** | Lista | NoSQL | SQL em memória | SQL permanente |
| **Persistência** | ❌ Não | ✅ Sim | ❌ Não | ✅ Sim |
| **Banco** | Nenhum | MongoDB | H2 | MariaDB |
| **ID** | Long | String | Long | Long |
| **Repository** | ❌ | MongoRepository | JpaRepository | JpaRepository |
| **Service** | ❌ | ✅ | ✅ | ✅ |
| **SQL** | ❌ | ❌ | ✅ | ✅ |
| **Produção** | ❌ | ✅ | ❌ | ✅ |
| **Instalação** | Não requer | Requer | Não requer | Requer |
| **Complexidade** | Baixa | Média | Baixa | Média |

## Estrutura de Dados no MariaDB

### Tabela products
```sql
CREATE TABLE products (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(255)
);
```

### Exemplo de dados:
```
+----+-------------------+
| id | name              |
+----+-------------------+
|  1 | Notebook          |
|  2 | Mouse Gamer       |
|  3 | Teclado Mecânico  |
+----+-------------------+
```

## Segurança

### Boas Práticas:
1. **Não commite senhas no código**
   - Use variáveis de ambiente
   - Configure em application.properties local

2. **Use usuários específicos**
   ```sql
   CREATE USER 'appuser'@'localhost' IDENTIFIED BY 'senha_forte';
   GRANT ALL ON productdb.* TO 'appuser'@'localhost';
   ```

3. **Configuração com variáveis de ambiente**
   ```properties
   spring.datasource.username=${DB_USERNAME:root}
   spring.datasource.password=${DB_PASSWORD:root}
   ```

## Migração entre Bancos

Graças ao JPA, migrar entre bancos SQL é simples! Basta alterar o `application.properties`:

### Para MySQL:
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/productdb
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.jpa.database-platform=org.hibernate.dialect.MySQLDialect
```

### Para PostgreSQL:
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/productdb
spring.datasource.driver-class-name=org.postgresql.Driver
spring.jpa.database-platform=org.hibernate.dialect.PostgreSQLDialect
```

O código Java permanece inalterado!

## Backup e Restauração

### Fazer backup:
```bash
mysqldump -u root -p productdb > backup.sql
```

### Restaurar backup:
```bash
mysql -u root -p productdb < backup.sql
```

## Monitoramento

### Ver conexões ativas:
```sql
SHOW PROCESSLIST;
```

### Ver status do servidor:
```sql
SHOW STATUS;
```

### Ver variáveis de configuração:
```sql
SHOW VARIABLES;
```

## Troubleshooting

### Erro: "Access denied for user"
- Verifique usuário e senha no application.properties
- Certifique-se de que o usuário tem permissões no banco

### Erro: "Unknown database 'productdb'"
- Crie o banco: `CREATE DATABASE productdb;`

### Erro: "Communications link failure"
- Verifique se o MariaDB está rodando
- Verifique se a porta 3306 está correta
- Verifique firewall

### Erro: "Table doesn't exist"
- Verifique `spring.jpa.hibernate.ddl-auto=update`
- O Hibernate cria automaticamente as tabelas

## Próximos Passos
- Implementar validações com Bean Validation
- Adicionar paginação e ordenação
- Criar testes unitários e de integração
- Implementar segurança (Spring Security)
- Adicionar cache com Redis
- Implementar audit com @CreatedDate e @LastModifiedDate

## Recursos Adicionais
- [MariaDB Documentation](https://mariadb.org/documentation/)
- [Spring Data JPA](https://spring.io/projects/spring-data-jpa)
- [Hibernate ORM](https://hibernate.org/orm/)

## Autor
Curso de Arquitetura de Aplicação Web - Newton Paiva

## Licença
Este projeto é livre para uso educacional.
