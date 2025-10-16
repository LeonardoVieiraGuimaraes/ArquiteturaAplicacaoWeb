package computerdatabase;

import io.gatling.javaapi.core.*;
import io.gatling.javaapi.http.*;
import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;

// Simulação de teste de stress usando Gatling
public class StressSimulation extends Simulation {

    // Configuração do protocolo HTTP para as requisições
    HttpProtocolBuilder httpProtocol = http
        .baseUrl("http://localhost:8080") // URL base da API
        .acceptHeader("application/json"); // Aceita respostas em JSON

    // Definição do cenário de teste
    ScenarioBuilder scn = scenario("Teste de stress - Produtos")
        .exec(
            // Executa uma requisição GET para o endpoint de produtos
            http("Requisição GET Produtos")
                .get("/products") // Endpoint a ser testado (sem /api)
                .check(status().is(200)) // Verifica se o status da resposta é 200 (OK)
        );

    // Configuração da simulação de usuários
    {
        setUp(
            // atOnceUsers(200): Simula 200 usuários acessando ao mesmo tempo (pico)
            scn.injectOpen(
                atOnceUsers(200)
            )
        )
        // Aplica o protocolo HTTP configurado acima
        .protocols(httpProtocol);
    }
}
