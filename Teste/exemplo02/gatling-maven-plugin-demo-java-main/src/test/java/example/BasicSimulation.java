package computerdatabase;

import io.gatling.javaapi.core.*;
import io.gatling.javaapi.http.*;
import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;

// Simulação de teste de carga usando Gatling
public class BasicSimulation extends Simulation {

    // Configuração do protocolo HTTP para as requisições
    HttpProtocolBuilder httpProtocol = http
        .baseUrl("http://localhost:8080") // URL base da API a ser testada
        .acceptHeader("application/json"); // Aceita respostas em JSON

    // Definição do cenário de teste
    ScenarioBuilder scn = scenario("Teste de carga - Produtos")
        .exec(
            // Executa uma requisição GET para o endpoint de produtos
            http("Requisição GET Produtos")
                .get("/products") // Endpoint a ser testado (sem /api)
                .check(status().is(200)) // Verifica se o status da resposta é 200 (OK)
        );

    // Configuração da simulação de usuários
    {
        setUp(
            // rampUsers(50).during(30): Simula 50 usuários acessando em 30 segundos
            scn.injectOpen(
                rampUsers(50).during(30)
            )
        )
        // Aplica o protocolo HTTP configurado acima
        .protocols(httpProtocol);
    }
}
