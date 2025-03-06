package computerdatabase;

// Importações estáticas do Gatling Core DSL
import java.util.concurrent.ThreadLocalRandom;

import io.gatling.javaapi.core.ChainBuilder;
import static io.gatling.javaapi.core.CoreDsl.css; // Importa todas as classes do pacote core do Gatling
import static io.gatling.javaapi.core.CoreDsl.csv; // Importa todas as classes do pacote HTTP do Gatling
import static io.gatling.javaapi.core.CoreDsl.exec; // Importa a classe ThreadLocalRandom para gerar números aleatórios de forma eficiente em um ambiente multi-thread
import static io.gatling.javaapi.core.CoreDsl.feed;
import static io.gatling.javaapi.core.CoreDsl.pause;
import static io.gatling.javaapi.core.CoreDsl.rampUsers;
import static io.gatling.javaapi.core.CoreDsl.repeat;
import static io.gatling.javaapi.core.CoreDsl.scenario;
import static io.gatling.javaapi.core.CoreDsl.tryMax;
import io.gatling.javaapi.core.FeederBuilder;
import io.gatling.javaapi.core.ScenarioBuilder;
import io.gatling.javaapi.core.Simulation;
import static io.gatling.javaapi.http.HttpDsl.http;
import static io.gatling.javaapi.http.HttpDsl.status;
import io.gatling.javaapi.http.HttpProtocolBuilder;

/**
 * Explicação das importações:
 * 
 * 1. `import static io.gatling.javaapi.core.CoreDsl.*;`
 *    - Importa todas as classes e métodos estáticos do pacote `io.gatling.javaapi.core.CoreDsl`.
 *    - `CoreDsl` contém as construções principais do DSL (Domain Specific Language) do Gatling, como `scenario`, `exec`, `pause`, `repeat`, etc.
 *    - Exemplo de uso: `scenario("MyScenario").exec(...)`.
 * 
 * 2. `import static io.gatling.javaapi.http.HttpDsl.*;`
 *    - Importa todas as classes e métodos estáticos do pacote `io.gatling.javaapi.http.HttpDsl`.
 *    - `HttpDsl` contém as construções específicas para testes HTTP no Gatling, como `http`, `get`, `post`, `check`, etc.
 *    - Exemplo de uso: `http("MyRequest").get("/path").check(status().is(200))`.
 * 
 * 3. `import io.gatling.javaapi.core.*;`
 *    - Importa todas as classes do pacote `io.gatling.javaapi.core`.
 *    - Inclui classes essenciais para a definição de simulações no Gatling, como `Simulation`, `ScenarioBuilder`, `ChainBuilder`, `FeederBuilder`, etc.
 *    - Exemplo de uso: `ScenarioBuilder scn = scenario("MyScenario");`.
 * 
 * 4. `import io.gatling.javaapi.http.*;`
 *    - Importa todas as classes do pacote `io.gatling.javaapi.http`.
 *    - Inclui classes específicas para a configuração e execução de requisições HTTP no Gatling, como `HttpProtocolBuilder`, `HttpRequestActionBuilder`, etc.
 *    - Exemplo de uso: `HttpProtocolBuilder httpProtocol = http.baseUrl("http://example.com");`.
 * 
 * 5. `import java.util.concurrent.ThreadLocalRandom;`
 *    - Importa a classe `ThreadLocalRandom` do pacote `java.util.concurrent`.
 *    - `ThreadLocalRandom` é uma classe que fornece uma maneira eficiente de gerar números aleatórios em um ambiente multi-thread.
 *    - Exemplo de uso: `int randomNum = ThreadLocalRandom.current().nextInt(1, 100);`.
 */

/**
 * Este exemplo é baseado em nossos tutoriais oficiais:
 * <ul>
 *   <li><a href="https://docs.gatling.io/tutorials/recorder/">Tutorial rápido do Gatling</a>
 *   <li><a href="https://docs.gatling.io/tutorials/advanced/">Tutorial avançado do Gatling</a>
 * </ul>
 */
public class ComputerDatabaseSimulation extends Simulation {

    // Alimentador que lê dados de um arquivo CSV de forma aleatória
    FeederBuilder<String> feeder = csv("search.csv").random();

    // Cadeia de ações para a busca de computadores
    ChainBuilder search = exec(
        http("Home").get("/"), // Requisição HTTP GET para a página inicial
        pause(1), // Pausa de 1 segundo
        feed(feeder), // Alimenta a simulação com dados do CSV
        http("Search")
            .get("/computers?f=#{searchCriterion}") // Requisição HTTP GET para buscar computadores
            .check(
                css("a:contains('#{searchComputerName}')", "href").saveAs("computerUrl") // Verifica se o link do computador está presente e salva a URL
            ),
        pause(1), // Pausa de 1 segundo
        http("Select")
            .get("#{computerUrl}") // Requisição HTTP GET para selecionar o computador
            .check(status().is(200)), // Verifica se o status da resposta é 200 (OK)
        pause(1) // Pausa de 1 segundo
    );

    // Cadeia de ações para navegar pelas páginas de computadores
    ChainBuilder browse =
        // Note como forçamos o nome do contador, para que possamos reutilizá-lo
        repeat(4, "i").on(
            http("Page #{i}").get("/computers?p=#{i}"), // Requisição HTTP GET para navegar pelas páginas
            pause(1) // Pausa de 1 segundo
        );

    // Cadeia de ações para editar um computador
    // Vamos demonstrar como podemos tentar novamente: vamos fazer a requisição falhar aleatoriamente e tentar novamente um determinado número de vezes
    ChainBuilder edit =
        // Vamos tentar no máximo 2 vezes
        tryMax(2)
            .on(
                http("Form").get("/computers/new"), // Requisição HTTP GET para o formulário de novo computador
                pause(1), // Pausa de 1 segundo
                http("Post")
                    .post("/computers") // Requisição HTTP POST para criar um novo computador
                    .formParam("name", "Beautiful Computer") // Parâmetro do formulário: nome
                    .formParam("introduced", "2012-05-30") // Parâmetro do formulário: data de introdução
                    .formParam("discontinued", "") // Parâmetro do formulário: data de descontinuação
                    .formParam("company", "37") // Parâmetro do formulário: empresa
                    .check(
                        status().is(
                            // Fazemos uma verificação em uma condição que foi personalizada com
                            // uma lambda. Ela será avaliada toda vez que um usuário executar
                            // a requisição
                            session -> 200 + ThreadLocalRandom.current().nextInt(2) // Verifica se o status da resposta é 200 ou 201
                        )
                    )
            )
            // Se a cadeia não tiver sucesso, o usuário sai do cenário
            .exitHereIfFailed();

    // Configuração do protocolo HTTP
    HttpProtocolBuilder httpProtocol =
        http.baseUrl("https://computer-database.gatling.io") // URL base da aplicação
            .acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8") // Cabeçalho de aceitação
            .acceptLanguageHeader("en-US,en;q=0.5") // Cabeçalho de aceitação de idioma
            .acceptEncodingHeader("gzip, deflate") // Cabeçalho de aceitação de codificação
            .userAgentHeader(
                "Mozilla/5.0 (Macintosh; Intel Mac OS X 10.15; rv:109.0) Gecko/20100101 Firefox/119.0" // Cabeçalho de agente de usuário
            );

    // Definição dos cenários de usuários e administradores
    ScenarioBuilder users = scenario("Users").exec(search, browse); // Cenário de usuários que executam busca e navegação
    ScenarioBuilder admins = scenario("Admins").exec(search, browse, edit); // Cenário de administradores que executam busca, navegação e edição

    {
        // Configuração da injeção de usuários nos cenários
        setUp(
            users.injectOpen(rampUsers(10).during(10)), // Injeção de 10 usuários ao longo de 10 segundos
            admins.injectOpen(rampUsers(2).during(10)) // Injeção de 2 administradores ao longo de 10 segundos
        ).protocols(httpProtocol); // Aplicação do protocolo HTTP configurado
    }
}