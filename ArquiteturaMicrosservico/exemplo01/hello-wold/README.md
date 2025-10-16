# 🚀 Spring Boot Hello World - Exemplo01

Aplicação Spring Boot minimalista que demonstra a criação de um endpoint REST simples, ideal para introduzir conceitos de microsserviços e desenvolvimento de APIs.

## 📑 Índice

- [🎯 Visão Geral](#-visão-geral)
- [📋 Requisitos](#-requisitos)
- [🚀 Início Rápido](#-início-rápido)
- [⚙️ Configuração do Projeto](#️-configuração-do-projeto)
- [🗂️ Estrutura do Código](#️-estrutura-do-código)
- [🌐 Endpoints da API](#-endpoints-da-api)
- [🧪 Testando a Aplicação](#-testando-a-aplicação)
- [🔍 Entendendo o Código](#-entendendo-o-código)
- [🆘 Troubleshooting](#-troubleshooting)
- [💡 Ideias de Extensão](#-ideias-de-extensão)
- [📝 Exercícios Práticos](#-exercícios-práticos)
- [🎓 Roteiro de Aula](#-roteiro-de-aula)
- [📚 Referências](#-referências)

## 🎯 Visão Geral

Este projeto é um exemplo didático de aplicação Spring Boot que implementa:
- ✅ Um endpoint REST básico (`GET /hello`)
- ✅ Recebimento de parâmetros de query
- ✅ Resposta em formato texto/JSON
- ✅ Configuração mínima de dependências
- ✅ Uso do Maven Wrapper

**Objetivo Educacional**: Demonstrar como criar uma API REST funcional com o mínimo de código possível.

## 📋 Requisitos

- **JDK 23** ou superior
- **Maven 3.9.x** (ou use o wrapper incluído `mvnw.cmd`)
- **IDE** (opcional): IntelliJ IDEA, Eclipse, ou VS Code com Extension Pack for Java
- **Cliente HTTP** (opcional): curl, Postman, Insomnia ou navegador

### Verificar Instalação

```powershell
# Verificar Java
java -version
# Esperado: openjdk version "23" ou superior

# Verificar Maven (se não usar wrapper)
mvn -version
```

## 🚀 Início Rápido

### 1. Compilar e Executar

```powershell
# Navegar até o diretório do projeto
cd exemplo01\hello-wold

# Executar com Maven Wrapper (recomendado)
.\mvnw.cmd spring-boot:run

# OU executar com Maven instalado
mvn spring-boot:run
```

### 2. Testar no Navegador

Abra o navegador e acesse:
```
http://localhost:8080/hello
```

Resposta esperada:
```
Hello World!
```

### 3. Testar com Parâmetro

```
http://localhost:8080/hello?name=Estudante
```

Resposta esperada:
```
Hello Estudante!
```

### 4. Parar a Aplicação

No terminal onde a aplicação está rodando, pressione: **`Ctrl + C`**

## ⚙️ Configuração do Projeto

### Estrutura Maven (pom.xml)

```xml
<parent>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-parent</artifactId>
    <version>3.4.3</version>
</parent>

<groupId>com.example</groupId>
<artifactId>hello-wold</artifactId>
<version>0.0.1-SNAPSHOT</version>

<properties>
    <java.version>23</java.version>
</properties>

<dependencies>
    <!-- Spring Web: REST Controllers, MVC -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
    </dependency>
    
    <!-- DevTools: Hot reload durante desenvolvimento -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-devtools</artifactId>
        <scope>runtime</scope>
        <optional>true</optional>
    </dependency>
    
    <!-- Spring Boot Test: JUnit, Mockito, etc. -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-test</artifactId>
        <scope>test</scope>
    </dependency>
</dependencies>
```

### Configuração da Aplicação (application.properties)

```properties
# Porta do servidor (padrão: 8080)
server.port=8080

# Logging
logging.level.root=INFO
logging.level.com.example=DEBUG
```

## 🗂️ Estrutura do Código

```
hello-wold/
├── .mvn/                          # Maven Wrapper
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/example/hello_wold/
│   │   │       └── HelloWoldApplication.java  # Classe principal
│   │   └── resources/
│   │       └── application.properties         # Configuração
│   └── test/                                   # Testes unitários
├── target/                                     # Binários compilados
├── pom.xml                                     # Configuração Maven
├── mvnw.cmd                                    # Maven Wrapper (Windows)
└── README.md                                   # Esta documentação
```

## 🌐 Endpoints da API

### `GET /hello`

Retorna uma mensagem de saudação personalizada.

**URL**: `http://localhost:8080/hello`

**Método HTTP**: `GET`

**Parâmetros de Query** (opcionais):

| Parâmetro | Tipo   | Obrigatório | Padrão  | Descrição                          |
|-----------|--------|-------------|---------|------------------------------------|
| `name`    | String | Não         | "World" | Nome para incluir na saudação      |

**Exemplo 1**: Saudação padrão
```powershell
curl http://localhost:8080/hello
```
**Resposta**:
```
Hello World!
```

**Exemplo 2**: Saudação personalizada
```powershell
curl http://localhost:8080/hello?name=Maria
```
**Resposta**:
```
Hello Maria!
```

**Exemplo 3**: Saudação com nome composto
```powershell
curl "http://localhost:8080/hello?name=João Silva"
```
**Resposta**:
```
Hello João Silva!
```

## 🧪 Testando a Aplicação

### Com curl (Linha de Comando)

```powershell
# Teste básico
curl http://localhost:8080/hello

# Com parâmetro
curl "http://localhost:8080/hello?name=Professor"

# Verbose (mostra headers)
curl -v http://localhost:8080/hello

# Salvar resposta em arquivo
curl http://localhost:8080/hello -o resposta.txt
```

### Com Postman

1. Criar nova requisição `GET`
2. URL: `http://localhost:8080/hello`
3. Aba **Params**:
   - Key: `name`
   - Value: `SeuNome`
4. Clicar em **Send**

### Com Navegador

Simplesmente abra: `http://localhost:8080/hello?name=Teste`

### Com PowerShell (Invoke-WebRequest)

```powershell
# Requisição básica
Invoke-WebRequest -Uri "http://localhost:8080/hello" | Select-Object -ExpandProperty Content

# Com parâmetro
Invoke-WebRequest -Uri "http://localhost:8080/hello?name=PowerShell" | Select-Object -ExpandProperty Content
```

## 🔍 Entendendo o Código

### Classe Principal: HelloWoldApplication.java

```java
package com.example.hello_wold;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication  // 1️⃣ Configuração Spring Boot
@RestController         // 2️⃣ Indica que é um REST Controller
public class HelloWoldApplication {

    public static void main(String[] args) {
        // 3️⃣ Inicializa a aplicação Spring Boot
        SpringApplication.run(HelloWoldApplication.class, args);
    }

    // 4️⃣ Endpoint GET /hello
    @GetMapping("/hello")
    public String hello(
        // 5️⃣ Parâmetro opcional com valor padrão
        @RequestParam(value = "name", defaultValue = "World") String name
    ) {
        // 6️⃣ Retorna string formatada
        return String.format("Hello %s!", name);
    }
}
```

### Anotações Explicadas

#### 1️⃣ `@SpringBootApplication`
Combina 3 anotações:
- `@Configuration`: Classe de configuração Spring
- `@EnableAutoConfiguration`: Auto-configuração do Spring Boot
- `@ComponentScan`: Escaneia componentes no pacote

#### 2️⃣ `@RestController`
- Combina `@Controller` + `@ResponseBody`
- Converte automaticamente retorno em JSON/texto
- Indica que todos os métodos são endpoints REST

#### 3️⃣ `SpringApplication.run(...)`
- Inicializa contexto Spring
- Configura servidor embutido (Tomcat)
- Inicia aplicação na porta 8080

#### 4️⃣ `@GetMapping("/hello")`
- Mapeia método para requisições HTTP GET
- Endpoint: `/hello`
- Equivale a `@RequestMapping(method = RequestMethod.GET, path = "/hello")`

#### 5️⃣ `@RequestParam`
- Extrai parâmetro de query da URL
- `value = "name"`: nome do parâmetro
- `defaultValue = "World"`: valor se não informado
- Opcional: `required = false`

#### 6️⃣ `String.format(...)`
- Formata string com placeholder `%s`
- Substitui `%s` pelo valor de `name`

## 🆘 Troubleshooting

### Erro: "Port 8080 was already in use"

**Causa**: Outra aplicação está usando a porta 8080.

**Solução 1**: Mudar a porta no `application.properties`
```properties
server.port=8081
```

**Solução 2**: Matar o processo na porta 8080
```powershell
# Encontrar processo
netstat -ano | findstr :8080

# Matar processo (substitua <PID> pelo número encontrado)
taskkill /PID <PID> /F
```

### Erro: "javac is not recognized"

**Causa**: JDK não está no PATH.

**Solução**: Configurar variável de ambiente
```powershell
# Adicionar ao PATH (substitua pelo caminho do seu JDK)
$env:JAVA_HOME = "C:\Program Files\Java\jdk-23"
$env:Path += ";$env:JAVA_HOME\bin"
```

### Erro: "No compiler is provided in this environment"

**Causa**: Usando JRE em vez de JDK.

**Solução**: Instalar JDK completo do site Oracle.

### Aplicação Iniciou mas Não Responde

**Verificar**:
1. Aplicação realmente iniciou? (ver logs)
2. Porta correta? (verificar `application.properties`)
3. Firewall bloqueando? (testar com `localhost` vs `127.0.0.1`)

```powershell
# Testar conectividade
Test-NetConnection -ComputerName localhost -Port 8080
```

### Caracteres Especiais na URL

Use codificação URL:
```powershell
# ERRADO
curl http://localhost:8080/hello?name=João Silva

# CORRETO
curl "http://localhost:8080/hello?name=João%20Silva"
```

## 💡 Ideias de Extensão

### Nível Iniciante
1. **Adicionar novo endpoint** `/goodbye` que retorna "Goodbye {name}!"
2. **Criar endpoint** `/time` que retorna hora atual
3. **Endpoint de status** `/health` que retorna "UP"
4. **Contador de visitas** usando variável estática

### Nível Intermediário
5. **Múltiplos parâmetros**: `/greet?name=João&language=pt`
6. **Endpoint POST**: Receber JSON no corpo da requisição
7. **Path Variable**: `/hello/{name}` em vez de query param
8. **Headers customizados**: Ler header `User-Agent`

### Nível Avançado
9. **Serviço separado**: Criar `GreetingService` e injetar no controller
10. **Exception handling**: Tratar erros com `@ControllerAdvice`
11. **Validação**: Usar `@Valid` e Bean Validation
12. **Logging**: Adicionar logs com SLF4J/Logback

## 📝 Exercícios Práticos

### Exercício 1: Novo Endpoint

**Tarefa**: Criar endpoint `/age` que retorna a idade baseada no ano de nascimento.

```java
@GetMapping("/age")
public String calculateAge(@RequestParam int birthYear) {
    int currentYear = java.time.Year.now().getValue();
    int age = currentYear - birthYear;
    return String.format("Você tem %d anos!", age);
}
```

**Teste**:
```powershell
curl "http://localhost:8080/age?birthYear=2000"
```

### Exercício 2: Resposta JSON

**Tarefa**: Criar endpoint que retorna objeto JSON.

```java
@GetMapping("/person")
public Map<String, Object> getPerson(@RequestParam String name) {
    Map<String, Object> response = new HashMap<>();
    response.put("name", name);
    response.put("timestamp", LocalDateTime.now());
    response.put("message", "Hello from Spring Boot!");
    return response;
}
```

**Teste**:
```powershell
curl http://localhost:8080/person?name=Maria
```

### Exercício 3: Múltiplos Parâmetros

**Tarefa**: Criar endpoint de saudação personalizada.

```java
@GetMapping("/custom-greeting")
public String customGreeting(
    @RequestParam(defaultValue = "Hello") String greeting,
    @RequestParam(defaultValue = "World") String name
) {
    return String.format("%s, %s!", greeting, name);
}
```

**Teste**:
```powershell
curl "http://localhost:8080/custom-greeting?greeting=Olá&name=Estudante"
```

### Exercício 4: Path Variable

**Tarefa**: Usar Path Variable em vez de Query Param.

```java
@GetMapping("/hello/{name}")
public String helloPath(@PathVariable String name) {
    return String.format("Hello %s from path!", name);
}
```

**Teste**:
```powershell
curl http://localhost:8080/hello/Maria
```

## 🎓 Roteiro de Aula

### Aula 1: Introdução (30 min)

**Objetivos**:
- Compreender o que é Spring Boot
- Entender o conceito de REST API
- Conhecer a estrutura de um projeto Maven

**Atividades**:
1. Apresentar slides sobre Spring Boot (10 min)
2. Mostrar o código do projeto (10 min)
3. Executar a aplicação pela primeira vez (10 min)

### Aula 2: Explorando o Código (45 min)

**Objetivos**:
- Entender anotações Spring
- Aprender sobre request/response HTTP
- Dominar parâmetros de query

**Atividades**:
1. Explicar cada anotação (15 min)
2. Demonstrar requisições com curl (15 min)
3. Alunos testam endpoints (15 min)

### Aula 3: Mãos na Massa (60 min)

**Objetivos**:
- Criar novos endpoints
- Trabalhar com diferentes tipos de parâmetros
- Retornar diferentes formatos de resposta

**Atividades**:
1. Exercício 1: Novo endpoint (15 min)
2. Exercício 2: Resposta JSON (20 min)
3. Exercício 3: Múltiplos parâmetros (15 min)
4. Desafio: Path variable (10 min)

### Aula 4: Boas Práticas (45 min)

**Objetivos**:
- Separar responsabilidades (Controller vs Service)
- Adicionar logging
- Tratar erros adequadamente

**Atividades**:
1. Refatorar para usar Service (20 min)
2. Adicionar logs estratégicos (15 min)
3. Criar GlobalExceptionHandler (10 min)

### Dicas para o Professor

**Antes da Aula**:
- Verificar que todos têm JDK 23 instalado
- Distribuir projeto via GitHub ou ZIP
- Preparar slides de apoio

**Durante a Aula**:
- Mostrar sempre primeiro, depois pedir para replicarem
- Circular pela sala ajudando individualmente
- Incentivar perguntas e experimentação

**Após a Aula**:
- Disponibilizar código final no GitHub
- Sugerir exercícios extras
- Coletar feedback dos alunos

## 📚 Referências

### Documentação Oficial
- [Spring Boot Reference Documentation](https://docs.spring.io/spring-boot/docs/current/reference/html/)
- [Spring Web MVC](https://docs.spring.io/spring-framework/docs/current/reference/html/web.html)
- [Spring Boot Guides](https://spring.io/guides)

### Tutoriais
- [Building a RESTful Web Service](https://spring.io/guides/gs/rest-service/)
- [Building an Application with Spring Boot](https://spring.io/guides/gs/spring-boot/)
- [Baeldung Spring Boot Tutorials](https://www.baeldung.com/spring-boot)

### Anotações Spring
- [@SpringBootApplication](https://docs.spring.io/spring-boot/docs/current/api/org/springframework/boot/autoconfigure/SpringBootApplication.html)
- [@RestController](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/bind/annotation/RestController.html)
- [@GetMapping](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/bind/annotation/GetMapping.html)
- [@RequestParam](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/bind/annotation/RequestParam.html)

### Vídeos Recomendados
- [Spring Boot Tutorial for Beginners](https://www.youtube.com/results?search_query=spring+boot+tutorial+beginners)
- [REST API Tutorial](https://www.youtube.com/results?search_query=rest+api+tutorial)

### Livros
- "Spring in Action" por Craig Walls
- "Spring Boot: Up and Running" por Mark Heckler
- "Pro Spring Boot 2" por Felipe Gutierrez

---

📝 **Nota**: Este exemplo é parte do módulo de Arquitetura de Microsserviços e serve como introdução ao desenvolvimento de APIs REST com Spring Boot.
