# Aplicação Spring Boot - Aula de Hoje

## Descrição

Esta é uma aplicação simples desenvolvida durante a aula de hoje para demonstrar o uso do Spring Boot. A aplicação expõe uma API REST que responde a requisições HTTP.

## Funcionalidades

- Exposição de um endpoint `/hello` que retorna uma mensagem de saudação.

## Tecnologias Utilizadas

- Java
- Spring Boot
- Maven

## Pré-requisitos

- JDK 11 ou superior
- Maven 3.6.0 ou superior

## Como Executar

1. Clone o repositório:
    ```sh
    git clone <URL_DO_REPOSITORIO>
    ```
2. Navegue até o diretório do projeto:
    ```sh
    cd Aula1/hello-wold
    ```
3. Compile e execute a aplicação:
    ```sh
    mvn spring-boot:run
    ```

## Endpoints

### GET /hello

Retorna uma mensagem de saudação.

- **URL:** `/hello`
- **Método:** `GET`
- **Parâmetros de Consulta:**
  -  (opcional): Nome a ser incluído na saudação. Valor padrão é "World".
- **Exemplo de Requisição:**
    ```sh
    curl http://localhost:8080/hello?name=Estudante
    ```
- **Exemplo de Resposta:**
    ```json
    {
      "message": "Hello Estudante!"
    }
    ```

## Estrutura do Projeto

- : Classe principal da aplicação.
- : Arquivo de configuração da aplicação.

## Autor

- Nome do Autor: [Seu Nome]
- Contato: [Seu Email]

## Licença

Este projeto está licenciado sob a Licença MIT - veja o arquivo LICENSE para mais detalhes.