# Testes de Performance - exemplo02

Este módulo demonstra testes de performance com Gatling (Java DSL) para sua API.

## 📚 Documentação Oficial

- **Gatling Documentation:** https://docs.gatling.io/
- **Scripting Introduction Tutorial:** https://docs.gatling.io/tutorials/scripting-intro/
- **Java DSL Reference:** https://docs.gatling.io/reference/script/core/
- **Maven Plugin:** https://docs.gatling.io/reference/integrations/build-tools/maven-plugin/

## Pré-requisitos

- Java 11+ instalado
- API em execução (ex.: `http://localhost:8080`)

## Onde ficam as simulações

Os arquivos Java estão em `Teste/exemplo02/gatling/`:

- `BasicSimulation.java` — carga progressiva (rampUsers)
- `StressSimulation.java` — stress (muitos usuários de uma vez)
- `SpikeSimulation.java` — pico repentino (spike)
- `EnduranceSimulation.java` — endurance/soak (constante por tempo longo)

Cada arquivo está comentado explicando o cenário e a configuração.

> Dica: ajuste o `.baseUrl("http://localhost:8080")` e o endpoint (ex.: `/api/products`) para a sua API.

## Executando (Standalone Gatling)

Se você usa o Gatling Standalone:

1) Baixe e extraia o Gatling: https://gatling.io/open-source/
2) Copie as simulações para `user-files/simulations/computerdatabase/` dentro da pasta do Gatling.
3) Rode o Gatling e selecione a simulação.

PowerShell (exemplo):

```powershell
# Dentro da pasta do Gatling (bin)
./gatling.bat
# Se pedir classe, informe, por exemplo:
# computerdatabase.BasicSimulation
```

## Executando (via Maven) — opcional

Caso prefira Maven, crie um projeto Maven com o plugin do Gatling e mova estas simulações para `src/test/java`.
Depois execute:

```powershell
mvn -Dgatling.simulationClass=computerdatabase.BasicSimulation gatling:test
```

Troque `computerdatabase.BasicSimulation` por `StressSimulation`, `SpikeSimulation` ou `EnduranceSimulation` para outros tipos de teste.

## Relatórios

Após a execução, o Gatling gera um relatório HTML com gráficos (tempo de resposta, throughput, percentis, erros).

- Standalone: `results/<nome-execução>/index.html`
- Maven: `target/gatling/<nome-execução>/index.html`

Abra o `index.html` no navegador.

## Boas práticas rápidas

- Mantenha dados de entrada variáveis com feeders quando necessário
- Valide respostas com `.check(status().is(200))`
- Execute cenários menores primeiro (sanidade) e aumente gradualmente
- Meça também o ambiente (CPU/RAM/DB) durante os testes

## 📖 Recursos de Aprendizado

### Tutoriais Recomendados:
1. **[Scripting Introduction](https://docs.gatling.io/tutorials/scripting-intro/)** - Tutorial essencial para começar
2. **[Advanced Tutorial](https://docs.gatling.io/tutorials/advanced/)** - Recursos avançados
3. **[Quick Start](https://docs.gatling.io/tutorials/quickstart/)** - Início rápido

### Conceitos Importantes:
- **Scenarios:** Define o comportamento dos usuários virtuais
- **Feeders:** Injeta dados dinâmicos nas requisições
- **Checks:** Valida respostas HTTP
- **Assertions:** Define critérios de sucesso/falha do teste
- **Injection Profiles:** Controla como usuários são injetados (rampUsers, atOnceUsers, constantUsersPerSec, etc.)

### Exemplos Práticos:
```java
// Ramp up gradual - 50 usuários em 30 segundos
rampUsers(50).during(30)

// Spike test - 200 usuários ao mesmo tempo
atOnceUsers(200)

// Taxa constante - 10 usuários/segundo por 10 minutos
constantUsersPerSec(10).during(600)

// Endurance - carga constante por tempo prolongado
constantUsersPerSec(5).during(3600) // 1 hora
```

## Troubleshooting

- Porta errada/serviço fora do ar: ajuste `.baseUrl(...)` e suba a API
- TLS/HTTPS: configure `.inferHtmlResources()` e certificados, se necessário
- Erros 429/5xx sob carga: reduza usuários/ritmo e avalie gargalos
