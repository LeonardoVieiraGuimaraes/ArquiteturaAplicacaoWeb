package computerdatabase;

// Importa todas as classes e métodos estáticos do CoreDsl e HttpDsl do Gatling
import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;

import io.gatling.javaapi.core.*;
import io.gatling.javaapi.http.*;

public class UsuarioSimulation extends Simulation {

    // Configuração do protocolo HTTP
    HttpProtocolBuilder httpProtocol = http
        .baseUrl("http://localhost:8080") // URL base do seu serviço
        .acceptHeader("application/json"); // Cabeçalho de aceitação para JSON

    // Definição da cadeia de ações para listar todos os usuários
    ChainBuilder listarTodos = exec(
        http("Listar Todos os Usuários") // Nome da requisição
            .get("/usuarios") // Método GET para o endpoint /usuarios
            .check(status().is(200)) // Verifica se o status da resposta é 200 (OK)
    ).pause(1); // Pausa de 1 segundo entre as requisições

    // Definição da cadeia de ações para buscar um usuário por ID
    ChainBuilder buscarPorId = exec(
        http("Buscar Usuário por ID") // Nome da requisição
            .get("/usuarios/1") // Método GET para o endpoint /usuarios/1
            .check(status().is(200)) // Verifica se o status da resposta é 200 (OK)
    ).pause(1); // Pausa de 1 segundo entre as requisições

    // Definição da cadeia de ações para salvar um novo usuário
    ChainBuilder salvar = exec(
        http("Salvar Usuário") // Nome da requisição
            .post("/usuarios") // Método POST para o endpoint /usuarios
            .body(StringBody("{\"nome\": \"Teste\", \"email\": \"teste@exemplo.com\"}")).asJson() // Corpo da requisição em JSON
            .check(status().is(201)) // Verifica se o status da resposta é 201 (Criado)
    ).pause(1); // Pausa de 1 segundo entre as requisições

    // Definição da cadeia de ações para atualizar um usuário existente
    ChainBuilder atualizar = exec(
        http("Atualizar Usuário") // Nome da requisição
            .put("/usuarios/1") // Método PUT para o endpoint /usuarios/1
            .body(StringBody("{\"nome\": \"Teste Atualizado\", \"email\": \"teste.atualizado@exemplo.com\"}")).asJson() // Corpo da requisição em JSON
            .check(status().is(200)) // Verifica se o status da resposta é 200 (OK)
    ).pause(1); // Pausa de 1 segundo entre as requisições

    // Definição da cadeia de ações para deletar um usuário
    ChainBuilder deletar = exec(
        http("Deletar Usuário") // Nome da requisição
            .delete("/usuarios/1") // Método DELETE para o endpoint /usuarios/1
            .check(status().is(204)) // Verifica se o status da resposta é 204 (Sem Conteúdo)
    ).pause(1); // Pausa de 1 segundo entre as requisições

    // Definição do cenário de teste que executa todas as ações CRUD
    ScenarioBuilder usuarios = scenario("Usuarios")
        .exec(listarTodos, buscarPorId, salvar, atualizar, deletar);

    {
        // Configuração do cenário de teste com injeção de usuários
        setUp(
            usuarios.injectOpen(rampUsers(10).during(10)) // Injeção de 10 usuários ao longo de 10 segundos
        ).protocols(httpProtocol); // Aplicação do protocolo HTTP configurado
    }
}

/*
Explicação:

Este código define um teste de carga usando Gatling para um CRUD de usuários. Ele inclui operações de listar, buscar por ID, salvar, atualizar e deletar usuários. O cenário de teste injeta 10 usuários ao longo de 10 segundos para simular uma carga crescente de usuários acessando o serviço.

- usuarios.injectOpen(rampUsers(10).during(10)): Injeta 10 usuários ao longo de 10 segundos no cenário de teste. Isso simula uma carga crescente de usuários acessando o serviço.
- .protocols(httpProtocol): Aplica a configuração do protocolo HTTP (httpProtocol) ao cenário de teste.

Conclusão:

Este código define um teste de carga usando Gatling para um CRUD de usuários, incluindo operações de listar, buscar por ID, salvar, atualizar e deletar usuários. O cenário de teste injeta 10 usuários ao longo de 10 segundos para simular a carga.
*/