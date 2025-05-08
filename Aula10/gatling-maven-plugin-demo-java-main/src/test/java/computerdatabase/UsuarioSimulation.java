package computerdatabase;

// Importa a classe ChainBuilder do Gatling, usada para construir cadeias de ações
// Importa a classe ChainBuilder do Gatling, usada para construir cadeias de ações
import io.gatling.javaapi.core.ChainBuilder;
import static io.gatling.javaapi.core.CoreDsl.StringBody;
import static io.gatling.javaapi.core.CoreDsl.exec;
import static io.gatling.javaapi.core.CoreDsl.jsonPath;
import static io.gatling.javaapi.core.CoreDsl.rampUsers;
import static io.gatling.javaapi.core.CoreDsl.scenario;
import io.gatling.javaapi.core.ScenarioBuilder;
import io.gatling.javaapi.core.Simulation;
import static io.gatling.javaapi.http.HttpDsl.http;
import static io.gatling.javaapi.http.HttpDsl.status;
import io.gatling.javaapi.http.HttpProtocolBuilder;

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

    // Definição da cadeia de ações para salvar um novo usuário
    ChainBuilder salvar = exec(
            http("Salvar Usuário") // Nome da requisição
                    .post("/usuarios") // Método POST para o endpoint /usuarios
                    .body(StringBody("{\"nome\": \"Teste\", \"email\": \"teste@exemplo.com\"}")).asJson() // Corpo da requisição em JSON
                    .check(status().is(200)) // Verifica se o status da resposta é 200 (OK)
                    .check(jsonPath("$.id").saveAs("userId")) // Salva o ID do usuário criado
    ).pause(1); // Pausa de 1 segundo entre as requisições

    // Definição da cadeia de ações para buscar um usuário por ID
    ChainBuilder buscarPorId = exec(
            http("Buscar Usuário por ID") // Nome da requisição
                    .get("/usuarios/#{userId}") // Método GET para o endpoint /usuarios/{userId}
                    .check(status().is(200)) // Verifica se o status da resposta é 200 (OK)
    ).pause(1); // Pausa de 1 segundo entre as requisições

    // Definição da cadeia de ações para atualizar um usuário existente
    ChainBuilder atualizar = exec(
            http("Atualizar Usuário") // Nome da requisição
                    .put("/usuarios/#{userId}") // Método PUT para o endpoint /usuarios/{userId}
                    .body(StringBody("{\"nome\": \"Teste Atualizado\", \"email\": \"teste.atualizado@exemplo.com\"}")).asJson() // Corpo da requisição em JSON
                    .check(status().is(200)) // Verifica se o status da resposta é 200 (OK)
    ).pause(1); // Pausa de 1 segundo entre as requisições

    // Definição da cadeia de ações para deletar um usuário
    ChainBuilder deletar = exec(
            http("Deletar Usuário") // Nome da requisição
                    .delete("/usuarios/#{userId}") // Método DELETE para o endpoint /usuarios/{userId}
                    .check(status().is(204)) // Verifica se o status da resposta é 204 (Sem Conteúdo)
    ).pause(1); // Pausa de 1 segundo entre as requisições

    // Definição do cenário de teste que executa todas as ações CRUD
    ScenarioBuilder usuarios = scenario("Usuarios")
            .exec(salvar, listarTodos, buscarPorId, atualizar, deletar);

    {
        // Configuração do cenário de teste com injeção de usuários
        setUp(
                // Teste de Carga
                usuarios.injectOpen(rampUsers(5).during(10)) // Injeção de 5 usuários ao longo de 10 segundos

        // //  Teste de Estresse
        // usuarios.injectOpen(
        //         incrementUsersPerSec(5) // Incrementa 5 usuários por segundo
        //                 .times(5) // Por 5 vezes
        //                 .eachLevelLasting(10) // Cada nível dura 10 segundos
        //                 .startingFrom(10) // Começando com 10 usuários por segundo
        // )
        ).protocols(httpProtocol); // Aplicação do protocolo HTTP configurado
    }
}

// setUp(
//         usuarios.injectOpen(
//                 constantUsersPerSec(10).during(60)
//         ).protocols(httpProtocol)
// ).name("Teste de Desempenho");
// setUp(
//         usuarios.injectOpen(
//                 rampUsers(50).during(60)
//         ).protocols(httpProtocol)
// ).name("Teste de Carga");
// setUp(
//         usuarios.injectOpen(
//                 incrementUsersPerSec(5)
//                         .times(5)
//                         .eachLevelLasting(10)
//                         .startingFrom(10)
//         ).protocols(httpProtocol)
// ).name("Teste de Estresse");
