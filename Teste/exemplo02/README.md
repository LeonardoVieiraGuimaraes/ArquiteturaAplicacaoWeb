# 🧪 Testes de Performance com Gatling (Java DSL)

## 📑 Índice

1. [🚀 Início Rápido](#-início-rápido)
2. [⚙️ Instalação](#️-instalação)
3. [🗂️ Estrutura do Projeto](#️-estrutura-do-projeto)
4. [🎯 Conceitos Fundamentais](#-conceitos-fundamentais)
5. [🛠️ Criando Simulações Passo a Passo](#️-criando-simulações-passo-a-passo)
   - [Simulação 1: Carga Básica (BasicSimulation)](#-simulação-1-carga-básica-basicsimulation)
   - [Simulação 2: Stress (StressSimulation)](#-simulação-2-stress-stresssimulation)
   - [Simulação 3: Spike (SpikeSimulation)](#-simulação-3-spike-spikesimulation)
   - [Simulação 4: Endurance (EnduranceSimulation)](#-simulação-4-endurance-endurancesimulation)
6. [▶️ Executando os Testes](#️-executando-os-testes)
7. [📊 Analisando Relatórios](#-analisando-relatórios)
8. [📝 Gatling DSL Explicado](#-gatling-dsl-explicado)
9. [⚔️ Comparação: Gatling vs JMeter](#️-comparação-gatling-vs-jmeter)
10. [🎓 Dicas para Aula](#-dicas-para-aula-e-demonstração)
11. [📚 Exercícios Práticos](#-exercícios-práticos-para-alunos)
12. [🆘 Troubleshooting](#-troubleshooting)
13. [🔗 Recursos Adicionais](#-recursos-adicionais)

---

## 🚀 Início Rápido

Se você já tem o Gatling configurado e quer executar imediatamente:

### Opção A: Maven (Recomendado)

```powershell
# 1. Inicie a API
cd D:\GitHub\NewtonPaiva\ArquiteturaAplicacaoWeb\Teste\exemplo02\demo
.\mvnw.cmd spring-boot:run

# 2. Execute o teste (em outro terminal)
cd D:\GitHub\NewtonPaiva\ArquiteturaAplicacaoWeb\Teste\exemplo02\gatling-maven-plugin-demo-java-main
.\mvnw.cmd gatling:test

# 3. Abra o relatório
start target\gatling\basicsimulation-[timestamp]\index.html
```

### Opção B: Standalone

```powershell
# 1. Execute o Gatling
cd C:\gatling-charts-highcharts-bundle-3.14.6\bin
.\gatling.bat

# 2. Selecione a simulação
# Digite: computerdatabase.BasicSimulation
```

> 💡 **Primeira vez?** Continue lendo para entender cada conceito detalhadamente.

---

## 📥 Instalação

### Opção 1: Maven (Recomendado para Projetos)

**Pré-requisitos:**
- Java 11+ instalado
- Maven 3.6+ instalado

**Passo 1: Verificar Java**

```powershell
java -version
# Deve mostrar: java version "11" ou superior
```

**Passo 2: Projeto Maven já está configurado**

O projeto `gatling-maven-plugin-demo-java-main` já contém:

```xml
<!-- pom.xml -->
<plugin>
    <groupId>io.gatling</groupId>
    <artifactId>gatling-maven-plugin</artifactId>
    <version>4.10.2</version>
</plugin>
```

**Passo 3: Executar**

```powershell
cd gatling-maven-plugin-demo-java-main
.\mvnw.cmd gatling:test
```

---

### Opção 2: Standalone (Recomendado para Iniciantes)

**Passo 1: Baixar Gatling**

1. Acesse: https://gatling.io/open-source/
2. Baixe: `gatling-charts-highcharts-bundle-3.14.6.zip`

**Passo 2: Extrair**

```powershell
Expand-Archive -Path "C:\Users\SeuUsuario\Downloads\gatling-*.zip" -DestinationPath "C:\"
```

**Passo 3: Copiar Simulações**

```powershell
# Copie as simulações para a pasta do Gatling
Copy-Item "D:\GitHub\NewtonPaiva\ArquiteturaAplicacaoWeb\Teste\exemplo02\gatling\*.java" `
    -Destination "C:\gatling-charts-highcharts-bundle-3.14.6\user-files\simulations\computerdatabase\"
```

**Passo 4: Executar**

```powershell
cd C:\gatling-charts-highcharts-bundle-3.14.6\bin
.\gatling.bat
```

---

## 🗂️ Estrutura do Projeto

```
Teste/exemplo02/
├── demo/                                    # API Spring Boot
│   ├── src/
│   │   └── main/
│   │       └── java/
│   │           └── com/example/demo/
│   │               └── ProductController.java
│   ├── pom.xml
│   └── mvnw.cmd
│
├── gatling-maven-plugin-demo-java-main/    # Projeto Gatling Maven
│   ├── src/
│   │   └── test/
│   │       └── java/
│   │           └── example/                 # ⚠️ Nota: deveria ser computerdatabase
│   │               ├── BasicSimulation.java      # ✅ Carga progressiva
│   │               ├── StressSimulation.java     # ✅ Teste de stress
│   │               ├── SpikeSimulation.java      # ✅ Teste de pico
│   │               └── EnduranceSimulation.java  # ✅ Teste de endurance
│   ├── pom.xml                              # Maven com gatling-maven-plugin
│   └── mvnw.cmd
│
├── gatling/                                 # Simulações originais
│   ├── BasicSimulation.java
│   ├── StressSimulation.java
│   ├── SpikeSimulation.java
│   └── EnduranceSimulation.java
│
└── README.md                                # Este arquivo
```

### 🌐 Endpoints Testados

A API `demo` expõe em `http://localhost:8080`:

- `GET /products` - Listar todos os produtos
- `GET /products/{id}` - Buscar produto por ID
- `GET /products/name/{name}` - Buscar produto por nome
- `POST /products` - Criar novo produto
- `GET /hello` - Health check

---

## 🎯 Conceitos Fundamentais

### O que é Gatling?

> **Gatling** = Framework de testes de carga/performance baseado em **código** (não GUI)

**Características:**
- ✅ **Código em Java/Scala** - Simulações são código, não cliques
- ✅ **Alta Performance** - Usa programação assíncrona (Akka)
- ✅ **Relatórios Lindos** - HTML com gráficos interativos
- ✅ **CI/CD Friendly** - Fácil integrar em pipelines

### Gatling vs JMeter: Quando usar?

| Aspecto | Gatling | JMeter |
|---------|---------|--------|
| **Interface** | Código (DSL) | GUI + XML |
| **Curva de Aprendizado** | Média | Fácil |
| **Performance** | ⚡ Muito alta | Alta |
| **Relatórios** | 🎨 Lindos | Funcionais |
| **Ideal para** | DevOps, CI/CD | QA, testes exploratórios |

### Estrutura de uma Simulação Gatling

```java
package example;

import io.gatling.javaapi.core.*;
import io.gatling.javaapi.http.*;
import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;

public class MinhaSimulacao extends Simulation {
    
    // 1️⃣ Configuração HTTP (base URL)
    HttpProtocolBuilder httpProtocol = http
        .baseUrl("http://localhost:8080");
    
    // 2️⃣ Cenário (o que o usuário faz)
    ScenarioBuilder cenario = scenario("Meu Cenário")
        .exec(http("request_1")
            .get("/products")
            .check(status().is(200)));
    
    // 3️⃣ Setup (quantos usuários, como injetar)
    {
        setUp(
            cenario.injectOpen(rampUsers(50).during(30))
        ).protocols(httpProtocol);
    }
}
```

**Componentes:**
1. **HttpProtocolBuilder** = Configuração base (URL, headers)
2. **ScenarioBuilder** = O que o usuário virtual faz
3. **setUp()** = Quantos usuários e como injetar

---

## 🛠️ Criando Simulações Passo a Passo

### 🔹 Simulação 1: Carga Básica (BasicSimulation)

> 🎯 **Objetivo:** Testar comportamento normal com carga progressiva (50 usuários em 30s)

#### Arquivo Completo: `BasicSimulation.java`

```java
package example;

import io.gatling.javaapi.core.*;
import io.gatling.javaapi.http.*;
import java.time.Duration;
import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;

public class BasicSimulation extends Simulation {
    
    // ==================================================
    // 1️⃣ CONFIGURAÇÃO HTTP
    // ==================================================
    
    HttpProtocolBuilder httpProtocol = http
        .baseUrl("http://localhost:8080")              // Base URL da API
        .acceptHeader("application/json")              // Accept JSON
        .contentTypeHeader("application/json")         // Content-Type JSON
        .userAgentHeader("Gatling/BasicSimulation");   // User-Agent customizado
    
    // ==================================================
    // 2️⃣ CENÁRIO - O QUE O USUÁRIO FAZ
    // ==================================================
    
    ScenarioBuilder scn = scenario("Basic Load Test")
        .exec(
            http("GET All Products")                   // Nome da requisição
                .get("/products")                      // Endpoint
                .check(status().is(200))               // Validação: status = 200
                .check(responseTimeInMillis().lte(1000)) // Tempo < 1000ms
        );
    
    // ==================================================
    // 3️⃣ SETUP - INJEÇÃO DE USUÁRIOS
    // ==================================================
    
    {
        setUp(
            scn.injectOpen(
                rampUsers(50).during(Duration.ofSeconds(30))
                // 50 usuários distribuídos em 30 segundos
                // ~1.67 usuários/segundo
            )
        ).protocols(httpProtocol);
    }
}
```

#### Explicação Linha por Linha:

**Seção 1: Configuração HTTP**

| Código | O que faz |
|--------|-----------|
| `.baseUrl("http://localhost:8080")` | URL base para todas as requisições |
| `.acceptHeader("application/json")` | Header Accept: application/json |
| `.contentTypeHeader("application/json")` | Header Content-Type: application/json |
| `.userAgentHeader("Gatling/...")` | Identifica o cliente (opcional) |

**Seção 2: Cenário**

| Código | O que faz |
|--------|-----------|
| `scenario("Basic Load Test")` | Nome do cenário (aparece no relatório) |
| `http("GET All Products")` | Nome da requisição específica |
| `.get("/products")` | Método HTTP GET, path /products |
| `.check(status().is(200))` | Valida que status HTTP = 200 |
| `.check(responseTimeInMillis().lte(1000))` | Tempo de resposta ≤ 1000ms |

**Seção 3: Injeção de Usuários**

| Código | O que faz |
|--------|-----------|
| `rampUsers(50)` | 50 usuários no total |
| `.during(Duration.ofSeconds(30))` | Distribuídos em 30 segundos |
| `protocols(httpProtocol)` | Usa a configuração HTTP definida |

#### Como Executar:

**Maven:**
```powershell
cd gatling-maven-plugin-demo-java-main
.\mvnw.cmd gatling:test -Dgatling.simulationClass=example.BasicSimulation
```

**Standalone:**
```powershell
cd C:\gatling-charts-highcharts-bundle-3.14.6\bin
.\gatling.bat
# Digite: computerdatabase.BasicSimulation
```

#### O que Esperar:

```
================================================================================
---- Global Information --------------------------------------------------------
> request count                                         50 (OK=50     KO=0     )
> min response time                                      8 (OK=8      KO=-     )
> max response time                                    169 (OK=169    KO=-     )
> mean response time                                    15 (OK=15     KO=-     )
> std deviation                                         23 (OK=23     KO=-     )
> response time 50th percentile                         11 (OK=11     KO=-     )
> response time 75th percentile                         16 (OK=16     KO=-     )
> response time 95th percentile                         16 (OK=16     KO=-     )
> response time 99th percentile                        169 (OK=169    KO=-     )
> mean requests/sec                                  1.724 (OK=1.724  KO=-     )
---- Response Time Distribution ------------------------------------------------
> t < 800 ms                                            50 (100%)
> 800 ms <= t < 1200 ms                                  0 (  0%)
> t >= 1200 ms                                           0 (  0%)
> failed                                                 0 (  0%)
================================================================================
```

✅ **Resultado Esperado:**
- 50 requisições, 0 falhas
- Tempo médio: ~15ms
- Throughput: ~1.7 req/s
- 100% abaixo de 800ms

---

### 🔹 Simulação 2: Stress (StressSimulation)

> 🎯 **Objetivo:** Encontrar o ponto de quebra do sistema (200 usuários de uma vez)

#### Arquivo Completo: `StressSimulation.java`

```java
package example;

import io.gatling.javaapi.core.*;
import io.gatling.javaapi.http.*;
import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;

public class StressSimulation extends Simulation {
    
    HttpProtocolBuilder httpProtocol = http
        .baseUrl("http://localhost:8080")
        .acceptHeader("application/json");
    
    ScenarioBuilder scn = scenario("Stress Test - 200 Users")
        .exec(
            http("GET Products - Stress")
                .get("/products")
                .check(status().in(200, 503))  // ⚠️ 503 = Sobrecarga OK
        );
    
    {
        setUp(
            scn.injectOpen(
                atOnceUsers(200)  // 200 usuários AO MESMO TEMPO!
            )
        ).protocols(httpProtocol)
         .assertions(
             global().responseTime().max().lt(5000),  // Máximo 5s
             global().failedRequests().percent().lte(10.0)  // Máx 10% erro
         );
    }
}
```

#### Diferenças Importantes:

| BasicSimulation | StressSimulation |
|-----------------|------------------|
| `rampUsers(50).during(30)` | `atOnceUsers(200)` |
| Carga gradual | Carga instantânea |
| `.check(status().is(200))` | `.check(status().in(200, 503))` |
| Só aceita 200 | Aceita 200 ou 503 (sobrecarga) |
| Sem assertions | `.assertions()` para critérios |

#### Executar:

```powershell
.\mvnw.cmd gatling:test -Dgatling.simulationClass=example.StressSimulation
```

#### O que Esperar:

- ⚠️ **Taxa de Erro:** Pode chegar a 5-10%
- ⚠️ **Tempo de Resposta:** Pode ultrapassar 500ms
- ⚠️ **CPU do Servidor:** 80-100%

---

### 🔹 Simulação 3: Spike (SpikeSimulation)

> 🎯 **Objetivo:** Simular pico repentino de tráfego (100 usuários instantâneos)

#### Arquivo Completo: `SpikeSimulation.java`

```java
package example;

import io.gatling.javaapi.core.*;
import io.gatling.javaapi.http.*;
import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;

public class SpikeSimulation extends Simulation {
    
    HttpProtocolBuilder httpProtocol = http
        .baseUrl("http://localhost:8080");
    
    // Cenário com MÚLTIPLAS requisições
    ScenarioBuilder scn = scenario("Spike Test")
        .exec(
            http("GET Products")
                .get("/products")
                .check(status().is(200))
        )
        .pause(1)  // Pausa de 1 segundo
        .exec(
            http("GET Product by ID")
                .get("/products/1")
                .check(status().is(200))
        );
    
    {
        setUp(
            scn.injectOpen(
                nothingFor(5),           // Espera 5s (baseline)
                atOnceUsers(100),        // SPIKE! 100 users
                nothingFor(10),          // Espera 10s (recuperação)
                rampUsers(20).during(10) // Volta ao normal
            )
        ).protocols(httpProtocol);
    }
}
```

#### Conceitos Novos:

| Método | O que faz |
|--------|-----------|
| `pause(1)` | Pausa de 1 segundo entre requisições |
| `nothingFor(5)` | Não injeta usuários por 5 segundos |
| `atOnceUsers(100)` | Pico repentino de 100 usuários |
| Múltiplos `.exec()` | Sequência de ações do usuário |

#### Executar:

```powershell
.\mvnw.cmd gatling:test -Dgatling.simulationClass=example.SpikeSimulation
```

---

### 🔹 Simulação 4: Endurance (EnduranceSimulation)

> 🎯 **Objetivo:** Detectar memory leaks e degradação (10 users/sec por 10 min)

#### Arquivo Completo: `EnduranceSimulation.java`

```java
package example;

import io.gatling.javaapi.core.*;
import io.gatling.javaapi.http.*;
import java.time.Duration;
import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;

public class EnduranceSimulation extends Simulation {
    
    HttpProtocolBuilder httpProtocol = http
        .baseUrl("http://localhost:8080");
    
    // Cenário variado (simula uso real)
    ScenarioBuilder scn = scenario("Endurance Test - 10 min")
        .exec(
            http("GET All Products")
                .get("/products")
                .check(status().is(200))
        )
        .pause(Duration.ofSeconds(2))  // Think time
        .exec(
            http("GET Product by ID")
                .get("/products/#{randomId}")  // ID dinâmico
                .check(status().in(200, 404))
        )
        .pause(Duration.ofSeconds(1))
        .exec(
            http("GET Product by Name")
                .get("/products/name/Test")
                .check(status().is(200))
        );
    
    {
        setUp(
            scn.injectOpen(
                constantUsersPerSec(10).during(Duration.ofMinutes(10))
                // 10 usuários/segundo por 10 minutos
                // Total: ~6000 requisições
            )
        ).protocols(httpProtocol)
         .assertions(
             global().responseTime().mean().lt(100),  // Média < 100ms
             global().successfulRequests().percent().gt(99.0)  // 99% sucesso
         );
    }
}
```

#### Conceitos de Endurance:

| Conceito | Implementação |
|----------|---------------|
| **Taxa Constante** | `constantUsersPerSec(10)` |
| **Longa Duração** | `.during(Duration.ofMinutes(10))` |
| **Think Time** | `pause(Duration.ofSeconds(2))` |
| **IDs Dinâmicos** | `"/products/#{randomId}"` (requer feeder) |
| **Assertions** | Garante performance estável |

#### Feeder para IDs Dinâmicos (Opcional):

```java
// Adicione antes do cenário
Iterator<Map<String, Object>> feeder = Stream.generate(() -> {
    Map<String, Object> map = new HashMap<>();
    map.put("randomId", ThreadLocalRandom.current().nextInt(1, 10));
    return map;
}).iterator();

// Use no cenário
ScenarioBuilder scn = scenario("...")
    .feed(feeder)  // Alimenta dados
    .exec(...);
```

#### Executar:

```powershell
.\mvnw.cmd gatling:test -Dgatling.simulationClass=example.EnduranceSimulation
```

> ⏱️ **Atenção:** Este teste demora **10 minutos**!

---

## 🚀 Executando os Testes

### Método 1: Maven - Uma Simulação Específica

```powershell
cd gatling-maven-plugin-demo-java-main

# BasicSimulation
.\mvnw.cmd gatling:test -Dgatling.simulationClass=example.BasicSimulation

# StressSimulation
.\mvnw.cmd gatling:test -Dgatling.simulationClass=example.StressSimulation

# SpikeSimulation
.\mvnw.cmd gatling:test -Dgatling.simulationClass=example.SpikeSimulation

# EnduranceSimulation (10 min)
.\mvnw.cmd gatling:test -Dgatling.simulationClass=example.EnduranceSimulation
```

### Método 2: Maven - Todas as Simulações

```powershell
# Executa TODAS as simulações em sequência
.\mvnw.cmd clean gatling:test
```

⚠️ **Cuidado:** Vai executar TUDO, incluindo o teste de 10 minutos!

### Método 3: Standalone Gatling

```powershell
# 1. Navegue até a pasta bin
cd C:\gatling-charts-highcharts-bundle-3.14.6\bin

# 2. Execute
.\gatling.bat

# 3. Escolha a simulação quando solicitado
# Digite o número ou nome completo:
# example.BasicSimulation
```

### Método 4: Modo No-Reports (Mais Rápido)

```powershell
# Executa sem gerar relatório HTML (mais rápido)
.\mvnw.cmd gatling:test -Dgatling.simulationClass=example.BasicSimulation -Dgatling.noReports=true
```

---

## 📊 Analisando Relatórios

### Localização dos Relatórios:

**Maven:**
```
target/
└── gatling/
    └── basicsimulation-20251016193807248/
        ├── index.html          # ← Abra este!
        ├── js/
        ├── style/
        └── simulation.log
```

**Standalone:**
```
results/
└── basicsimulation-20251016193807248/
    └── index.html
```

### Abrir Relatório:

```powershell
# Maven
start target\gatling\basicsimulation-*\index.html

# Standalone
start C:\gatling-*\results\basicsimulation-*\index.html
```

### Seções do Relatório:

#### 1️⃣ Global Information

```
request count                     50 (OK=50     KO=0     )
min response time                  8 (OK=8      KO=-     )
max response time                169 (OK=169    KO=-     )
mean response time                15 (OK=15     KO=-     )
std deviation                     23 (OK=23     KO=-     )
response time 50th percentile     11 (OK=11     KO=-     )
response time 95th percentile     16 (OK=16     KO=-     )
response time 99th percentile    169 (OK=169    KO=-     )
mean requests/sec               1.72 (OK=1.72   KO=-     )
```

**Métricas Importantes:**

| Métrica | Significado | Ideal |
|---------|-------------|-------|
| **request count** | Total de requisições | OK = Total |
| **mean response time** | Tempo médio | < 100ms |
| **95th percentile** | 95% abaixo desse tempo | < 200ms |
| **99th percentile** | 99% abaixo desse tempo | < 500ms |
| **mean requests/sec** | Throughput médio | Quanto maior, melhor |

#### 2️⃣ Response Time Distribution

```
t < 800 ms                        50 (100%)
800 ms <= t < 1200 ms              0 (  0%)
t >= 1200 ms                       0 (  0%)
failed                             0 (  0%)
```

✅ **Ideal:** 100% abaixo de 800ms

#### 3️⃣ Gráficos Interativos

- **Active Users Over Time** - Usuários ativos ao longo do tempo
- **Response Time Distribution** - Distribuição dos tempos
- **Response Time Percentiles** - Percentis (50%, 75%, 95%, 99%)
- **Requests per Second** - Taxa de requisições
- **Responses per Second** - Taxa de respostas

---

## 🔍 Gatling DSL Explicado

### Tabela Completa de Métodos:

| Método | Finalidade | Exemplo |
|--------|------------|---------|
| **Injeção de Usuários** |||
| `atOnceUsers(n)` | N usuários ao mesmo tempo | `atOnceUsers(100)` |
| `rampUsers(n).during(d)` | N usuários em D segundos | `rampUsers(50).during(30)` |
| `constantUsersPerSec(n).during(d)` | N users/seg por D segundos | `constantUsersPerSec(10).during(600)` |
| `nothingFor(d)` | Pausa de D segundos | `nothingFor(5)` |
| **Requests HTTP** |||
| `.get(path)` | HTTP GET | `.get("/products")` |
| `.post(path)` | HTTP POST | `.post("/products")` |
| `.put(path)` | HTTP PUT | `.put("/products/1")` |
| `.delete(path)` | HTTP DELETE | `.delete("/products/1")` |
| **Checks (Validações)** |||
| `.check(status().is(200))` | Status exato | `status().is(200)` |
| `.check(status().in(200, 201))` | Status em lista | `status().in(200, 201)` |
| `.check(responseTimeInMillis().lte(1000))` | Tempo ≤ 1000ms | `responseTimeInMillis().lte(1000)` |
| `.check(jsonPath("$.name").is("Test"))` | JSON Path | `jsonPath("$.name")` |
| **Pausas** |||
| `pause(n)` | Pausa de N segundos | `pause(2)` |
| `pause(Duration.ofSeconds(n))` | Pausa com Duration | `pause(Duration.ofSeconds(5))` |
| **Assertions (Critérios)** |||
| `global().responseTime().mean().lt(100)` | Média < 100ms | Global |
| `global().successfulRequests().percent().gt(95)` | 95% sucesso | Global |
| `forAll().failedRequests().count().is(0)` | 0 falhas | Todas requests |

### Exemplos Práticos:

**POST com Body JSON:**

```java
.exec(
    http("Create Product")
        .post("/products")
        .header("Content-Type", "application/json")
        .body(StringBody("""
            {
                "name": "Notebook",
                "price": 2500.00
            }
        """))
        .check(status().is(201))
        .check(jsonPath("$.id").saveAs("productId"))  // Salva ID
)
```

**Usar ID Salvo:**

```java
.exec(
    http("Get Created Product")
        .get("/products/#{productId}")  // Usa ID salvo
        .check(status().is(200))
)
```

**Feeders (Dados Dinâmicos):**

```java
// CSV Feeder
Iterator<Map<String, Object>> feeder = csv("data.csv").circular();

scenario("Test")
    .feed(feeder)
    .exec(
        http("Request")
            .get("/products/#{productId}")  // Lê do CSV
    );
```

---

## 🎯 Comparação: Gatling vs JMeter

| Aspecto | Gatling | JMeter |
|---------|---------|--------|
| **Abordagem** | Código (DSL) | GUI + XML |
| **Linguagem** | Java/Scala | Java |
| **Curva de Aprendizado** | Média | Fácil |
| **Performance** | ⚡⚡⚡ Muito Alta | ⚡⚡ Alta |
| **Uso de Memória** | Leve (~200MB) | Moderado (~500MB) |
| **Relatórios** | 🎨 Bonitos, interativos | Funcionais |
| **Protocolos** | HTTP, WebSocket, SSE, JMS | HTTP, JDBC, FTP, SOAP, LDAP, etc. |
| **CI/CD** | ✅ Excelente | ✅ Bom |
| **Testes Distribuídos** | ✅ Sim (Enterprise) | ✅ Sim (Grátis) |
| **Ideal Para** | DevOps, Desenvolvedores | QA, Testadores |
| **Versionamento** | ✅ Git-friendly (código) | ⚠️ XML grande |
| **Code Review** | ✅ Fácil | ❌ Difícil |

**Quando usar Gatling:**
- ✅ Time trabalha com código
- ✅ Integração CI/CD
- ✅ Testes HTTP/WebSocket
- ✅ Versionamento Git

**Quando usar JMeter:**
- ✅ Time sem experiência em código
- ✅ Testes exploratórios na GUI
- ✅ Protocolos variados (JDBC, FTP, SOAP)
- ✅ Testes distribuídos gratuitos

---

## 🎓 Dicas para Aula e Demonstração

### 📋 Ordem Recomendada de Ensino (60 min):

**1. Introdução Teórica (10 min):**
- Por que testes de performance?
- Gatling vs JMeter
- Conceitos: Scenario, Injection, Protocol

**2. Demonstração - BasicSimulation (15 min):**
- Abrir código no VS Code
- Explicar cada seção (HTTP, Scenario, Setup)
- Executar ao vivo
- Mostrar relatório HTML

**3. Análise de Relatórios (10 min):**
- Abrir index.html
- Explicar métricas
- Gráficos interativos
- Percentis (50%, 95%, 99%)

**4. Tipos de Teste (15 min):**
- Diferença entre Basic, Stress, Spike, Endurance
- Quando usar cada um
- Executar StressSimulation

**5. Código ao Vivo (10 min):**
- Criar simulação simples do zero
- Adicionar checks
- Adicionar assertions

**6. Exercícios (tempo restante)**

---

### 💡 Dicas de Demonstração:

#### ✅ DO (Faça):

1. **Mostre o código ANTES de executar:**
```java
// Explique linha por linha
rampUsers(50).during(30)  // ← "50 usuários em 30 segundos"
```

2. **Execute com API parada (erro proposital):**
```
java.net.ConnectException: Connection refused
```
"Vejam: sempre verifiquem se a API está rodando!"

3. **Compare resultados:**
- Execute Basic (50 users)
- Execute Stress (200 users)
- Mostre diferença nos gráficos

4. **Use assertions para demonstrar falhas:**
```java
.assertions(
    global().responseTime().mean().lt(10)  // Impossível!
)
```
"Vejam: assertion falhou! Tempo médio foi 15ms, não < 10ms"

#### ❌ DON'T (Não Faça):

1. ❌ Não execute Endurance (10 min) na aula
2. ❌ Não assuma que alunos sabem Java
3. ❌ Não pule a explicação de `injectOpen()`
4. ❌ Não use Scala (use Java DSL)

---

### 🎬 Script de Demonstração (5 Cenas):

#### Cena 1: Por que Gatling? (5 min)

```
Professor: "JMeter é ótimo, mas vejam isto..."
[Mostrar arquivo .jmx do JMeter - XML gigante]

"Agora vejam o Gatling:"
[Mostrar BasicSimulation.java - código limpo]

"Qual é mais fácil de versionar no Git?"
"Qual é mais fácil de fazer code review?"
```

#### Cena 2: Primeiro Teste (10 min)

```
Professor: "Vamos executar nossa primeira simulação!"
[Abrir terminal]

cd gatling-maven-plugin-demo-java-main
.\mvnw.cmd gatling:test

[Mostrar saída em tempo real]
"Vejam: 50 requisições, 0 falhas!"
"Relatório gerado em target/gatling/..."
```

#### Cena 3: Relatório Interativo (10 min)

```
Professor: "Agora o melhor - o relatório!"
[Abrir index.html no navegador]

"Vejam esse gráfico: Response Time Percentiles"
[Passar mouse sobre gráfico]
"95% das requisições em 16ms!"

"Agora Active Users Over Time"
"Vejam a rampa: 0 → 50 users em 30 segundos"
```

#### Cena 4: Código ao Vivo (15 min)

```
Professor: "Vamos criar uma simulação do zero!"
[Criar arquivo MinhaSimulacao.java]

package example;
import io.gatling.javaapi.core.*;
...

[Escrever código passo a passo]
"Primeiro: protocolo HTTP"
"Segundo: cenário"
"Terceiro: injeção"

[Executar]
"Funcionou! Primeira simulação criada!"
```

#### Cena 5: Stress vs Basic (10 min)

```
Professor: "Qual a diferença entre Basic e Stress?"
[Mostrar código lado a lado]

Basic:  rampUsers(50).during(30)   // Gradual
Stress: atOnceUsers(200)           // BOOM!

[Executar Stress]
"Vejam: taxa de erro subiu!"
"Tempo de resposta aumentou!"
"É isso que queremos: encontrar o limite!"
```

---

## 📝 Exercícios Práticos para Alunos

### 🎯 Exercício 1: Primeira Simulação (Iniciante)

**Objetivo:** Criar uma simulação básica

**Tarefas:**
1. Crie `MeuTesteSimples.java` em `src/test/java/example/`
2. Teste o endpoint `/hello` com 10 usuários em 5 segundos:

```java
package example;

import io.gatling.javaapi.core.*;
import io.gatling.javaapi.http.*;
import java.time.Duration;
import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;

public class MeuTesteSimples extends Simulation {
    
    HttpProtocolBuilder httpProtocol = http
        .baseUrl("http://localhost:8080");
    
    ScenarioBuilder scn = scenario("Meu Primeiro Teste")
        .exec(http("Hello")
            .get("/hello")
            .check(status().is(200)));
    
    {
        setUp(
            scn.injectOpen(rampUsers(10).during(Duration.ofSeconds(5)))
        ).protocols(httpProtocol);
    }
}
```

3. Execute: `.\mvnw.cmd gatling:test -Dgatling.simulationClass=example.MeuTesteSimples`

**Entrega:** Screenshot do relatório HTML

---

### 🎯 Exercício 2: Múltiplas Requisições (Intermediário)

**Objetivo:** Simular jornada do usuário

**Tarefas:**
1. Crie simulação com sequência de ações:
   - GET /products (listar)
   - Pausa de 2 segundos
   - GET /products/1 (detalhe)
   - Pausa de 1 segundo
   - GET /products/name/Test (busca)

```java
ScenarioBuilder scn = scenario("Jornada do Usuário")
    .exec(http("Listar").get("/products").check(status().is(200)))
    .pause(Duration.ofSeconds(2))
    .exec(http("Detalhe").get("/products/1").check(status().is(200)))
    .pause(Duration.ofSeconds(1))
    .exec(http("Buscar").get("/products/name/Test").check(status().is(200)));
```

**Entrega:**
- Código completo
- Relatório mostrando 3 requisições por usuário

---

### 🎯 Exercício 3: Assertions (Avançado)

**Objetivo:** Definir critérios de sucesso

**Tarefas:**
1. Adicione assertions à simulação:

```java
setUp(...)
    .protocols(httpProtocol)
    .assertions(
        global().responseTime().mean().lt(100),        // Média < 100ms
        global().responseTime().percentile3().lt(200), // 95% < 200ms
        global().successfulRequests().percent().gt(95.0), // 95% sucesso
        forAll().failedRequests().count().is(0L)       // 0 falhas
    );
```

2. Execute e veja se passa todas as assertions

**Entrega:**
- Print do terminal mostrando assertions PASSED
- Ou print mostrando FAILED e explicação

---

### 🎯 Exercício 4: Feeder com CSV (Desafio)

**Objetivo:** Usar dados dinâmicos de arquivo CSV

**Tarefas:**
1. Crie arquivo `products.csv`:

```csv
productId,productName
1,Notebook
2,Mouse
3,Teclado
4,Monitor
5,Webcam
```

2. Use feeder na simulação:

```java
Iterator<Map<String, Object>> feeder = csv("products.csv").circular();

ScenarioBuilder scn = scenario("Teste com CSV")
    .feed(feeder)
    .exec(http("GET Product")
        .get("/products/#{productId}")
        .check(status().is(200))
        .check(jsonPath("$.name").is("#{productName}")));
```

**Entrega:**
- Arquivo CSV
- Código da simulação
- Relatório mostrando uso de dados variados

---

### 🎯 Exercício 5: POST Request (Expert)

**Objetivo:** Testar criação de recursos

**Tarefas:**
1. Crie simulação que:
   - Cria produto via POST
   - Salva o ID retornado
   - Busca o produto criado via GET

```java
ScenarioBuilder scn = scenario("CRUD Completo")
    .exec(http("Criar Produto")
        .post("/products")
        .header("Content-Type", "application/json")
        .body(StringBody("""
            {
                "name": "Produto Teste",
                "price": 99.99
            }
        """))
        .check(status().is(201))
        .check(jsonPath("$.id").saveAs("novoId")))
    .exec(http("Buscar Criado")
        .get("/products/#{novoId}")
        .check(status().is(200)));
```

**Entrega:**
- Código completo
- Print mostrando POST 201 e GET 200

---

## 🆘 Troubleshooting

### ❌ Problema: Package does not exist

**Erro:**
```
[ERROR] package io.gatling.javaapi.core does not exist
```

**Solução:**
```powershell
# Recompilar o projeto
.\mvnw.cmd clean compile

# Verificar pom.xml - deve ter:
<dependency>
    <groupId>io.gatling</groupId>
    <artifactId>gatling-core-java</artifactId>
</dependency>
```

---

### ❌ Problema: Connection refused

**Erro:**
```
j.n.ConnectException: Connection refused: localhost/127.0.0.1:8080
```

**Solução:**
```powershell
# 1. Verificar se API está rodando
curl http://localhost:8080/products

# 2. Iniciar API
cd demo
.\mvnw.cmd spring-boot:run

# 3. Executar teste novamente
```

---

### ❌ Problema: No simulation found

**Erro:**
```
[ERROR] There is no simulation script. Please check that your scripts are in src/test/java
```

**Solução:**
```
# Verificar localização do arquivo:
src/
└── test/
    └── java/           ← DEVE estar aqui!
        └── example/
            └── MinhaSimulacao.java

# NÃO pode estar em src/main/java!
```

---

### ❌ Problema: Assertions failed

**Erro:**
```
Global: mean of response time is 150.0, expected 100.0
Simulation failed.
```

**Solução:**
```java
// Ajuste a expectativa para valor realista
.assertions(
    global().responseTime().mean().lt(200)  // Era 100, agora 200
);

// Ou investigue por que está lento:
// - Banco de dados lento?
// - Muitos usuários simultâneos?
// - CPU alta?
```

---

### ❌ Problema: OutOfMemoryError

**Erro:**
```
java.lang.OutOfMemoryError: Java heap space
```

**Solução:**
```powershell
# Aumentar memória do Maven
set MAVEN_OPTS=-Xmx2g
.\mvnw.cmd gatling:test

# Ou no pom.xml:
<plugin>
    <groupId>io.gatling</groupId>
    <artifactId>gatling-maven-plugin</artifactId>
    <configuration>
        <jvmArgs>
            <jvmArg>-Xmx2g</jvmArg>
        </jvmArgs>
    </configuration>
</plugin>
```

---

### ❌ Problema: Relatório não abre

**Solução:**
```powershell
# Procurar relatório manualmente
dir target\gatling /s /b | Select-String "index.html"

# Abrir diretamente
start target\gatling\[nome-da-pasta]\index.html

# Se não existir, executar com -e:
.\mvnw.cmd gatling:test -Dgatling.simulationClass=example.BasicSimulation
```

---

## 📚 Recursos Adicionais

### 📖 Documentação Oficial

- **Site Oficial:** https://gatling.io/
- **Documentação:** https://docs.gatling.io/
- **Java DSL Reference:** https://docs.gatling.io/reference/script/core/
- **Maven Plugin:** https://docs.gatling.io/reference/integrations/build-tools/maven-plugin/
- **GitHub:** https://github.com/gatling/gatling

### 🎓 Tutoriais Recomendados

1. **[Quickstart](https://docs.gatling.io/tutorials/quickstart/)** - Início rápido
2. **[Scripting Introduction](https://docs.gatling.io/tutorials/scripting-intro/)** - Introdução ao DSL
3. **[Advanced Tutorial](https://docs.gatling.io/tutorials/advanced/)** - Recursos avançados
4. **[HTTP Protocol](https://docs.gatling.io/reference/script/protocols/http/protocol/)** - Configuração HTTP

### 🔧 Ferramentas Úteis

**Gatling Enterprise (Pago):**
- Testes distribuídos em nuvem
- Dashboards em tempo real
- Integração com CI/CD
- https://gatling.io/enterprise/

**Extensões:**
- **gatling-sbt-plugin** - Para projetos Scala/SBT
- **gatling-gradle-plugin** - Para Gradle
- **gatling-charts-highcharts** - Gráficos (incluído no bundle)

---

## 📊 Tabela Comparativa de Injeção

| Método | Descrição | Uso | Gráfico de Usuários |
|--------|-----------|-----|---------------------|
| `atOnceUsers(100)` | 100 users instantâneo | Spike/Stress | ```│█████``` |
| `rampUsers(100).during(60)` | 100 users em 60s | Load normal | ```│    ╱``` |
| `constantUsersPerSec(10).during(60)` | 10 users/s por 60s | Taxa constante | ```│████``` |
| `nothingFor(10)` | Pausa de 10s | Warm-up/Cool-down | ```│____``` |

---

## 🎯 Checklist para Aula Bem-Sucedida

### Antes da Aula:
- [ ] Java 11+ instalado e testado
- [ ] Maven funcionando (`.\mvnw.cmd --version`)
- [ ] API demo compilando e rodando
- [ ] Testes Gatling executando sem erros
- [ ] Relatórios HTML abrindo no navegador
- [ ] Slides/apresentação preparados

### Durante a Aula:
- [ ] Explicar "por quê" antes de "como"
- [ ] Mostrar código ANTES de executar
- [ ] Executar testes ao vivo (não só slides!)
- [ ] Abrir relatórios e explorar gráficos
- [ ] Provocar erros (API parada) para ensinar troubleshooting
- [ ] Comparar diferentes tipos de teste

### Depois da Aula:
- [ ] Compartilhar código das simulações
- [ ] Disponibilizar este README
- [ ] Criar exercícios para casa
- [ ] Abrir fórum/chat para dúvidas

---

## 🚀 Próximos Passos

1. ✅ **Explore Feeders** - Dados dinâmicos (CSV, JSON)
2. ✅ **Aprenda Assertions** - Critérios de pass/fail
3. ✅ **Use Session Variables** - Compartilhar dados entre requests
4. ✅ **Testes Distribuídos** - Gatling Enterprise ou cluster
5. ✅ **Integre CI/CD** - Jenkins, GitLab CI, GitHub Actions
6. ✅ **Monitore o Backend** - Combine com Prometheus/Grafana
7. ✅ **Estude WebSocket** - Testes de conexões persistentes

---

**📝 Documentação criada com base em:**
- Gatling 3.14.6
- Java 11+
- Maven 3.9.x
- Spring Boot 3.4.3

**🎯 Objetivo:** Guia completo de testes de performance com Gatling para aulas práticas, do básico ao avançado.

---

**Made with ❤️ for Performance Testing Excellence**

## Troubleshooting

- Porta errada/serviço fora do ar: ajuste `.baseUrl(...)` e suba a API
- TLS/HTTPS: configure `.inferHtmlResources()` e certificados, se necessário
- Erros 429/5xx sob carga: reduza usuários/ritmo e avalie gargalos
