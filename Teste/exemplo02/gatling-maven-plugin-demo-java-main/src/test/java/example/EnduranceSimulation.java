package computerdatabase;

import io.gatling.javaapi.core.*;
import io.gatling.javaapi.http.*;
import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;

// Simulação de teste de endurance (soak test) usando Gatling
public class EnduranceSimulation extends Simulation {

    // Configuração do protocolo HTTP para as requisições
    HttpProtocolBuilder httpProtocol = http
        .baseUrl("http://localhost:8080") // URL base da API
        .acceptHeader("application/json"); // Aceita respostas em JSON

    // Definição do cenário de teste
    ScenarioBuilder scn = scenario("Teste de endurance - Produtos")
        .exec(
            // Executa uma requisição GET para o endpoint de produtos
            http("Requisição GET Produtos")
                .get("/api/products") // Endpoint a ser testado
                .check(status().is(200)) // Verifica se o status da resposta é 200 (OK)
        );

    // Configuração da simulação de usuários
    {
        setUp(
            // constantUsersPerSec(10).during(600): Simula 10 usuários por segundo durante 10 minutos
            scn.injectOpen(
                constantUsersPerSec(10).during(600)
            )
        )
        // Aplica o protocolo HTTP configurado acima
        .protocols(httpProtocol);
    }
}
