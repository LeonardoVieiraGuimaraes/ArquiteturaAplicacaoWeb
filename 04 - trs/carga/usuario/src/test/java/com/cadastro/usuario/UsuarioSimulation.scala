import io.gatling.core.Predef._
import io.gatling.http.Predef._
import scala.concurrent.duration._

class UsuarioSimulation extends Simulation {

  val httpProtocol = http
    .baseUrl("http://localhost:8080") // URL do seu servidor Spring Boot
    .acceptHeader("application/json")

  val scn = scenario("Teste de Carga de Usuário")
    .exec(http("Requisição para listar usuários")
      .get("/usuarios")
      .check(status.is(200)))

  setUp(
    scn.inject(
      atOnceUsers(10), // Número de usuários simultâneos
      rampUsers(100) during (30 seconds) // Aumenta para 100 usuários ao longo de 30 segundos
    ).protocols(httpProtocol)
  )
}