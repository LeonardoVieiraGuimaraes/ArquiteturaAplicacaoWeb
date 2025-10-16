# 🏗️ Arquitetura de Microsserviços

Este diretório contém exemplos e materiais didáticos sobre arquitetura de microsserviços e fundamentos de Java, organizados de forma progressiva para facilitar o aprendizado.

## 📑 Índice

- [🎯 Visão Geral](#-visão-geral)
- [📁 Estrutura do Repositório](#-estrutura-do-repositório)
- [🚀 Exemplo01: Hello World Spring Boot](#-exemplo01-hello-world-spring-boot)
- [📚 IntroducaoJava: Fundamentos da Linguagem](#-introducaojava-fundamentos-da-linguagem)
- [📋 Pré-requisitos](#-pré-requisitos)
- [💡 Como Navegar](#-como-navegar)
- [🎓 Roteiro de Aprendizagem](#-roteiro-de-aprendizagem)
- [📖 Referências](#-referências)

## 🎯 Visão Geral

Este módulo abrange desde os fundamentos da linguagem Java até a construção de microsserviços com Spring Boot, incluindo:

- **Fundamentos de Java**: Sintaxe, tipos de dados, estruturas de controle, POO
- **Conceitos Avançados**: Concorrência, programação funcional, streams
- **Spring Boot**: Criação de APIs REST, injeção de dependências
- **Arquitetura de Microsserviços**: Conceitos e implementação prática

## 📁 Estrutura do Repositório

```
ArquiteturaMicrosservico/
├── exemplo01/              # Spring Boot "Hello World" API
│   └── hello-wold/        # Projeto Maven completo
│       ├── src/           # Código-fonte
│       ├── pom.xml        # Configuração Maven
│       └── README.md      # Documentação específica
│
└── IntroducaoJava/        # Fundamentos de Java
    ├── FundamentosSintaxe/
    ├── EstruturaDados/
    ├── ProgramacaoOrientadoObjetos/
    ├── ProgramacaoFuncional/
    ├── ConcorrrenciaMutithreading/
    ├── ManipulacaoArquivos/
    ├── TratamentoExcecoes/
    ├── MetodoFuncao/
    ├── TopicosIntermediarios/
    ├── HelloWorld.java
    └── Readme.md
```

## 🚀 Exemplo01: Hello World Spring Boot

**Localização**: `exemplo01/hello-wold/`

Uma aplicação Spring Boot minimalista que demonstra:
- ✅ Configuração básica de projeto Maven
- ✅ Endpoint REST simples (`/hello`)
- ✅ Parâmetros de requisição (`@RequestParam`)
- ✅ Resposta JSON

**Tecnologias**:
- Java 23
- Spring Boot 3.4.3
- Spring Web
- Spring DevTools

**Como executar**:
```powershell
cd exemplo01\hello-wold
.\mvnw.cmd spring-boot:run
```

**Testar**:
```powershell
curl http://localhost:8080/hello?name=Estudante
```

📖 **Documentação completa**: [exemplo01/hello-wold/README.md](exemplo01/hello-wold/README.md)

## 📚 IntroducaoJava: Fundamentos da Linguagem

**Localização**: `IntroducaoJava/`

Coleção organizada de exemplos Java cobrindo todos os conceitos fundamentais:

### 1️⃣ **FundamentosSintaxe**
- Variáveis e tipos primitivos
- Operadores (aritméticos, relacionais, lógicos)
- Estruturas de controle (if/else, switch, loops)
- Manipulação de strings

### 2️⃣ **EstruturaDados**
- Arrays e operações
- Collections (List, Set, Map)
- Optional para tratamento de nulos

### 3️⃣ **ProgramacaoOrientadoObjetos**
- Classes e objetos (Pessoa, Aluno, Professor)
- Herança e polimorfismo
- Interfaces e implementações
- Calculadora com operações básicas

### 4️⃣ **ProgramacaoFuncional**
- Expressões lambda
- Interfaces funcionais
- Streams API e operações
- Method references

### 5️⃣ **ConcorrrenciaMutithreading**
- Threads e execução paralela
- Sincronização
- Executors e thread pools

### 6️⃣ **Outros Tópicos**
- **ManipulacaoArquivos**: Leitura/escrita de arquivos
- **TratamentoExcecoes**: Try/catch, exceções customizadas
- **MetodoFuncao**: Definição e uso de métodos
- **TopicosIntermediarios**: Conceitos avançados

📖 **Documentação completa**: [IntroducaoJava/Readme.md](IntroducaoJava/Readme.md)

## 📋 Pré-requisitos

### Para exemplo01 (Spring Boot):
- **JDK 23** ou superior
- **Maven 3.9.x** (wrapper incluído)
- IDE recomendada: IntelliJ IDEA ou VS Code com Extension Pack for Java

### Para IntroducaoJava:
- **JDK 11** ou superior
- Editor de texto ou IDE
- Terminal/Prompt de comando

### Instalação do JDK:
```powershell
# Verificar instalação
java -version
javac -version
```

Se não estiver instalado, baixe em: [Oracle JDK](https://www.oracle.com/java/technologies/javase-downloads.html)

## 💡 Como Navegar

### 🎯 Para iniciantes em Java:
1. Comece por `IntroducaoJava/FundamentosSintaxe/`
2. Avance para `EstruturaDados/`
3. Estude `ProgramacaoOrientadoObjetos/`
4. Explore `ProgramacaoFuncional/`
5. Finalize com `ConcorrrenciaMutithreading/`

### 🚀 Para aprender Spring Boot:
1. Revise os fundamentos em `IntroducaoJava/`
2. Estude `exemplo01/hello-wold/`
3. Avance para exemplos em outros módulos (PersistenciaDados, Seguranca, etc.)

### 📚 Para referência rápida:
- Cada subdiretório contém `Readme.md` com exemplos de código
- Arquivos `.java` estão comentados para facilitar compreensão
- READMEs incluem exercícios práticos e troubleshooting

## 🎓 Roteiro de Aprendizagem

### Semana 1-2: Fundamentos Java
- [ ] Sintaxe básica e tipos primitivos
- [ ] Estruturas de controle
- [ ] Arrays e Collections
- [ ] POO: classes, herança, interfaces

### Semana 3: Conceitos Avançados
- [ ] Programação funcional e lambdas
- [ ] Streams API
- [ ] Manipulação de arquivos
- [ ] Tratamento de exceções

### Semana 4: Spring Boot
- [ ] Configuração de projeto Maven
- [ ] Criação de REST Controllers
- [ ] Injeção de dependências
- [ ] Testes com curl/Postman

### Próximos Passos
- Persistência de dados (módulo PersistenciaDados)
- Segurança com Spring Security (módulo Seguranca)
- Testes automatizados (módulo Teste)
- Documentação de APIs (módulo Documentacao)

## 📖 Referências

### Documentação Oficial
- [Java SE Documentation](https://docs.oracle.com/en/java/javase/)
- [Spring Boot Reference](https://docs.spring.io/spring-boot/docs/current/reference/html/)
- [Spring Framework](https://spring.io/projects/spring-framework)
- [Maven Documentation](https://maven.apache.org/guides/)

### Tutoriais e Guias
- [Oracle Java Tutorials](https://docs.oracle.com/javase/tutorial/)
- [Spring Guides](https://spring.io/guides)
- [Baeldung Java Tutorials](https://www.baeldung.com/)
- [JournalDev Spring Boot](https://www.journaldev.com/spring-boot-tutorial)

### Livros Recomendados
- "Effective Java" por Joshua Bloch
- "Java: The Complete Reference" por Herbert Schildt
- "Spring in Action" por Craig Walls
- "Clean Code" por Robert C. Martin

---

📝 **Nota**: Este repositório é parte do curso de Arquitetura de Aplicações Web e está em constante atualização com novos exemplos e materiais didáticos.
