# Configuração do Projeto

1. Crie um novo projeto Spring Boot usando [Spring Initializr](https://start.spring.io/).
2. Adicione as dependências mencionadas acima.
3. Crie a estrutura de diretórios conforme necessário.

## Código de Exemplo

Crie um controlador para responder com "Hello World":

```java
package com.exemplo.helloworld;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorldController {

    @GetMapping("/hello")
    public String hello() {
        return "Hello World";
    }
}


```
# Executando o Projeto
1. Compile e execute o projeto usando seu IDE ou linha de comando:
```bash
./mvnw spring-boot:run
```
2. Acesse http://localhost:8080/hello em seu navegador para ver a mensagem "Hello World".

