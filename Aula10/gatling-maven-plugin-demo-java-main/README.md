Gatling plugin for Maven - Java demo project
============================================

A simple showcase of a Maven project using the Gatling plugin for Maven. Refer to the plugin documentation
[on the Gatling website](https://docs.gatling.io/reference/integrations/build-tools/maven-plugin/) for usage.

This project is written in Java, others are available for [Kotlin](https://github.com/gatling/gatling-maven-plugin-demo-kotlin)
and [Scala](https://github.com/gatling/gatling-maven-plugin-demo-scala).

It includes:

* [Maven Wrapper](https://maven.apache.org/wrapper/), so that you can immediately run Maven with `./mvnw` without having
  to install it on your computer
* minimal `pom.xml`
* latest version of `io.gatling:gatling-maven-plugin` applied
* sample [Simulation](https://docs.gatling.io/reference/glossary/#simulation) class,
  demonstrating sufficient Gatling functionality
* proper source file layout



Pacote e Importações

package computerdatabase;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;

import io.gatling.javaapi.core.*;
import io.gatling.javaapi.http.*;

package computerdatabase;: Define o pacote onde a classe UsuarioSimulation está localizada.
import static io.gatling.javaapi.core.CoreDsl.*;: Importa todas as classes e métodos estáticos do pacote CoreDsl do Gatling.
import static io.gatling.javaapi.http.HttpDsl.*;: Importa todas as classes e métodos estáticos do pacote HttpDsl do Gatling.
import io.gatling.javaapi.core.*;: Importa todas as classes do pacote core do Gatling.
import io.gatling.javaapi.http.*;: Importa todas as classes do pacote http do Gatling.

Classe Principal
public class UsuarioSimulation extends Simulation {

public class UsuarioSimulation extends Simulation: Define uma classe pública chamada UsuarioSimulation que estende a classe Simulation do Gatling. A classe Simulation é a classe base para todas as simulações de carga no Gatling.

Configuração do Protocolo HTTP

HttpProtocolBuilder httpProtocol = http
    .baseUrl("http://localhost:8080") // URL base do seu serviço
    .acceptHeader("application/json");

HttpProtocolBuilder httpProtocol: Declara uma variável httpProtocol do tipo HttpProtocolBuilder. Esta variável será usada para configurar o protocolo HTTP para a simulação.
.baseUrl("http://localhost:8080"): Define a URL base para todas as requisições HTTP na simulação. Neste caso, a URL base é http://localhost:8080, que é onde o serviço está hospedado.
.acceptHeader("application/json"): Define o cabeçalho Accept para todas as requisições HTTP. Neste caso, o cabeçalho Accept é configurado para application/json, indicando que a simulação espera respostas no formato JSON.
Cadeias de Ações (ChainBuilder)

Listar Todos os Usuários

ChainBuilder listarTodos = exec(
    http("Listar Todos os Usuários")
        .get("/usuarios")
        .check(status().is(200))
).pause(1);

ChainBuilder listarTodos: Declara uma variável listarTodos do tipo ChainBuilder. Esta variável define uma cadeia de ações para listar todos os usuários.
http("Listar Todos os Usuários"): Cria uma requisição HTTP com o nome "Listar Todos os Usuários".
.get("/usuarios"): Define o método HTTP como GET e o endpoint como /usuarios.
.check(status().is(200)): Verifica se o status da resposta é 200 (OK).
.pause(1): Pausa de 1 segundo entre as requisições.

Buscar Usuário por ID

ChainBuilder buscarPorId = exec(
    http("Buscar Usuário por ID")
        .get("/usuarios/1")
        .check(status().is(200))
).pause(1);

ChainBuilder buscarPorId: Declara uma variável buscarPorId do tipo ChainBuilder. Esta variável define uma cadeia de ações para buscar um usuário por ID.
http("Buscar Usuário por ID"): Cria uma requisição HTTP com o nome "Buscar Usuário por ID".
.get("/usuarios/1"): Define o método HTTP como GET e o endpoint como /usuarios/1.
.check(status().is(200)): Verifica se o status da resposta é 200 (OK).
.pause(1): Pausa de 1 segundo entre as requisições.

Salvar Usuário

ChainBuilder salvar = exec(
    http("Salvar Usuário")
        .post("/usuarios")
        .body(StringBody("{\"nome\": \"Teste\", \"email\": \"teste@exemplo.com\"}")).asJson()
        .check(status().is(201))
).pause(1);

ChainBuilder salvar: Declara uma variável salvar do tipo ChainBuilder. Esta variável define uma cadeia de ações para salvar um novo usuário.
http("Salvar Usuário"): Cria uma requisição HTTP com o nome "Salvar Usuário".
.post("/usuarios"): Define o método HTTP como POST e o endpoint como /usuarios.
.body(StringBody("{"nome": "Teste", "email": "teste@exemplo.com"}")).asJson(): Define o corpo da requisição como um JSON com os dados do usuário.
.check(status().is(201)): Verifica se o status da resposta é 201 (Criado).
.pause(1): Pausa de 1 segundo entre as requisições.

Atualizar Usuário

ChainBuilder atualizar = exec(
    http("Atualizar Usuário")
        .put("/usuarios/1")
        .body(StringBody("{\"nome\": \"Teste Atualizado\", \"email\": \"teste.atualizado@exemplo.com\"}")).asJson()
        .check(status().is(200))
).pause(1);

ChainBuilder atualizar: Declara uma variável atualizar do tipo ChainBuilder. Esta variável define uma cadeia de ações para atualizar um usuário existente.
http("Atualizar Usuário"): Cria uma requisição HTTP com o nome "Atualizar Usuário".
.put("/usuarios/1"): Define o método HTTP como PUT e o endpoint como /usuarios/1.
.body(StringBody("{"nome": "Teste Atualizado", "email": "teste.atualizado@exemplo.com"}")).asJson(): Define o corpo da requisição como um JSON com os dados atualizados do usuário.
.check(status().is(200)): Verifica se o status da resposta é 200 (OK).
.pause(1): Pausa de 1 segundo entre as requisições.

Deletar Usuário

ChainBuilder deletar = exec(
    http("Deletar Usuário")
        .delete("/usuarios/1")
        .check(status().is(204))
).pause(1);

ChainBuilder deletar: Declara uma variável deletar do tipo ChainBuilder. Esta variável define uma cadeia de ações para deletar um usuário.
http("Deletar Usuário"): Cria uma requisição HTTP com o nome "Deletar Usuário".
.delete("/usuarios/1"): Define o método HTTP como DELETE e o endpoint como /usuarios/1.
.check(status().is(204)): Verifica se o status da resposta é 204 (Sem Conteúdo).
.pause(1): Pausa de 1 segundo entre as requisições.

Cenário de Teste (ScenarioBuilder)

ScenarioBuilder usuarios = scenario("Usuarios")
    .exec(listarTodos, buscarPorId, salvar, atualizar, deletar);

ScenarioBuilder usuarios: Declara uma variável usuarios do tipo ScenarioBuilder. Esta variável define o cenário de teste.
scenario("Usuarios"): Cria um novo cenário de teste chamado "Usuarios".
.exec(listarTodos, buscarPorId, salvar, atualizar, deletar): Define a sequência de ações que serão executadas no cenário de teste. Neste caso, o cenário executa as cadeias de ações listarTodos, buscarPorId, salvar, atualizar e deletar em sequência.

Configuração do Cenário de Teste

{
    setUp(
        usuarios.injectOpen(rampUsers(10).during(10)) // Configuração de carga
    ).protocols(httpProtocol);
}


setUp: Configura o cenário de teste para execução.
usuarios.injectOpen(rampUsers(10).during(10)): Injeta 10 usuários ao longo de 10 segundos no cenário de teste. Isso simula uma carga crescente de usuários acessando o serviço.
.protocols(httpProtocol): Aplica a configuração do protocolo HTTP (httpProtocol) ao cenário de teste.

Conclusão

Este código define um teste de carga usando Gatling para um CRUD de usuários, incluindo operações de listar, buscar por ID, salvar, atualizar e deletar usuários. O cenário de teste injeta 10 usuários ao longo de 10 segundos para simular a carga.