# 🧪 Testes de Performance com Apache JMeter

## 📑 Índice

1. [🚀 Início Rápido](#-início-rápido)
2. [⚙️ Instalação](#️-instalação)
3. [🗂️ Estrutura do Projeto](#️-estrutura-do-projeto)
4. [🛠️ Criando Testes](#️-criando-testes)
   - [Teste 1: Carga Básica](#-teste-1-teste-de-carga-básica)
   - [Teste 2: Stress](#-teste-2-teste-de-stress)
   - [Teste 3: Spike/Pico](#-teste-3-teste-de-picopike)
   - [Teste 4: Endurance](#-teste-4-teste-de-endurance)
   - [Teste Completo CRUD](#-criando-um-teste-completo-passo-a-passo)
5. [▶️ Executando Testes](#️-executando-os-testes)
6. [📊 Visualizando Resultados](#-visualizando-resultados)
7. [🔧 Componentes do JMeter](#-principais-componentes-do-jmeter)
8. [⚔️ Comparação Gatling vs JMeter](#️-comparação-gatling-vs-jmeter)
9. [✅ Boas Práticas](#-boas-práticas)
10. [🆘 Troubleshooting](#-troubleshooting)
11. [🔗 Recursos Adicionais](#-recursos-adicionais)

---

## 🚀 Início Rápido

Se você já tem o JMeter instalado e quer começar imediatamente:

```powershell
# 1. Inicie a API
cd D:\GitHub\NewtonPaiva\ArquiteturaAplicacaoWeb\Teste\exemplo03\demo
.\mvnw.cmd spring-boot:run

# 2. Execute um teste (em outro terminal)
cd C:\apache-jmeter-5.6.3\bin
.\jmeter.bat -n -t "caminho\do\teste.jmx" -l results.jtl -e -o report

# 3. Visualize o relatório
start report\index.html
```

> 💡 **Primeira vez?** Continue lendo para entender cada etapa detalhadamente.

---

## 📥 Instalação

### ✅ Pré-requisitos
- Java 8 ou superior instalado
- Variável de ambiente JAVA_HOME configurada

### Passo 1: Baixar Apache JMeter

1. Acesse: https://jmeter.apache.org/download_jmeter.cgi
2. Baixe a versão binária ZIP: `apache-jmeter-5.6.3.zip`

### Passo 2: Extrair o arquivo

```powershell
# Extrair para C:\
Expand-Archive -Path "C:\Users\SeuUsuario\Downloads\apache-jmeter-5.6.3.zip" -DestinationPath "C:\"
```

### Passo 3: Adicionar ao PATH (Opcional)

```powershell
# Adicionar JMeter ao PATH permanentemente
[Environment]::SetEnvironmentVariable("PATH", "$env:PATH;C:\apache-jmeter-5.6.3\bin", "User")
```

### ✅ Verificar Instalação

```powershell
# Inicie o JMeter
cd C:\apache-jmeter-5.6.3\bin
.\jmeter.bat
```

---

## 🗂️ Estrutura do Projeto

```
Teste/exemplo03/
├── demo/                          # API Spring Boot
│   ├── src/
│   │   └── main/
│   │       └── java/
│   │           └── com/example/demo/
│   │               ├── ProductController.java
│   │               ├── ProductRepository.java
│   │               └── Product.java
│   ├── pom.xml
│   └── mvnw.cmd
│
├── basic-load-test.jmx           # Teste de carga básica
├── stress-test.jmx               # Teste de stress
├── spike-test.jmx                # Teste de spike
├── endurance-test.jmx            # Teste de endurance
├── crud-test.jmx                 # Teste CRUD completo
└── README.md                     # Este arquivo
```

### 🌐 Endpoints da API

A API `demo` expõe os seguintes endpoints em `http://localhost:8080`:

- `GET /products` - Listar todos os produtos
- `GET /products/{id}` - Buscar produto por ID
- `GET /products/name/{name}` - Buscar produto por nome
- `POST /products` - Criar novo produto
- `GET /hello` - Endpoint de health check

---

## 🛠️ Criando Testes

### 🔹 Teste 1: Teste de Carga Básica

**Equivalente ao Gatling BasicSimulation** - 50 usuários em 30 segundos

#### Passo a Passo:

**1. Abrir o JMeter:**
```powershell
cd C:\apache-jmeter-5.6.3\bin
.\jmeter.bat
```

**2. Criar Test Plan:**
- Clique com botão direito em `Test Plan` no painel esquerdo
- Renomeie para: `Basic Load Test - Products API`

**3. Adicionar Thread Group:**
- Clique com botão direito em `Test Plan`
- `Add` → `Threads (Users)` → `Thread Group`
- Configure:
  - **Name:** `Product Users`
  - **Number of Threads (users):** 50
  - **Ramp-up period (seconds):** 30
  - **Loop Count:** 1

**4. Adicionar HTTP Request:**
- Clique com botão direito em `Thread Group`
- `Add` → `Sampler` → `HTTP Request`
- Configure:
  - **Name:** `GET All Products`
  - **Protocol:** `http`
  - **Server Name or IP:** `localhost`
  - **Port Number:** `8080`
  - **HTTP Request:** `GET`apache-jmeter-5.6.3
  - **Path:** `/products`

**5. Adicionar Assertions (Validações):**
- Clique com botão direito em `HTTP Request`
- `Add` → `Assertions` → `Response Assertion`
- Configure:
  - **Apply to:** `Main sample only`
  - **Response Field:** `Response Code`
  - **Pattern Matching Rules:** `Equals`
  - **Patterns to Test:** Adicione `200`

**6. Adicionar Listeners (Visualizadores):**
- Clique com botão direito em `Thread Group`
- `Add` → `Listener` → `View Results Tree`
- `Add` → `Listener` → `Aggregate Report`
- `Add` → `Listener` → `Response Time Graph`

**7. Salvar:**
- `File` → `Save` → `basic-load-test.jmx`

---

### 🔹 Teste 2: Teste de Stress

**Equivalente ao Gatling StressSimulation** - 200 usuários de uma vez

> 🎯 **Objetivo:** Avaliar como o sistema se comporta sob carga extrema repentina

#### Passo a Passo Completo:

**1. Abrir o JMeter:**
```powershell
cd C:\apache-jmeter-5.6.3\bin
.\jmeter.bat
```

**2. Criar Novo Test Plan:**
- Se já tiver um teste aberto: `File` → `New` (Ctrl+N)
- Clique com botão direito em `Test Plan`
- Renomeie para: `Stress Test - Products API`

**3. Adicionar Thread Group:**
- Clique com botão direito em `Test Plan`
- `Add` → `Threads (Users)` → `Thread Group`
- No painel direito, configure:
  - **Name:** `Stress - 200 Users`
  - **Action to be taken after a Sampler error:** `Continue`
  - **Number of Threads (users):** `200`
  - **Ramp-up period (seconds):** `1`
  - **Loop Count:** `1`
  - ☑️ **Scheduler:** Desmarque (deixe desmarcado)

> 💡 **Dica:** Ramp-up de 1 segundo significa que todos os 200 usuários iniciarão quase simultaneamente!

**4. Adicionar HTTP Request Defaults (Configuração Global):**
- Clique com botão direito em `Thread Group`
- `Add` → `Config Element` → `HTTP Request Defaults`
- Configure:
  - **Protocol:** `http`
  - **Server Name or IP:** `localhost`
  - **Port Number:** `8080`

> 💡 **Por que usar Defaults?** Evita repetir servidor/porta em cada request!

**5. Adicionar HTTP Request - GET All Products:**
- Clique com botão direito em `Thread Group`
- `Add` → `Sampler` → `HTTP Request`
- Configure:
  - **Name:** `GET All Products`
  - **Protocol:** (deixe em branco - usará o default)
  - **Server Name or IP:** (deixe em branco - usará o default)
  - **Port Number:** (deixe em branco - usará o default)
  - **HTTP Request:** `GET`
  - **Path:** `/products`

**6. Adicionar Response Assertion:**
- Clique com botão direito em `GET All Products`
- `Add` → `Assertions` → `Response Assertion`
- Configure:
  - **Name:** `Assert Status 200`
  - **Apply to:** `Main sample only`
  - **Response Field to Test:** `Response Code`
  - **Pattern Matching Rules:** Selecione `Equals`
  - **Patterns to Test:** Clique em `Add` e digite `200`

**7. Adicionar Duration Assertion (Tempo Máximo):**
- Clique com botão direito em `GET All Products`
- `Add` → `Assertions` → `Duration Assertion`
- Configure:
  - **Name:** `Max Response Time 2000ms`
  - **Duration in milliseconds:** `2000`

> 💡 **Por que 2000ms?** Em teste de stress, esperamos respostas mais lentas!

**8. Adicionar Listeners:**

   **a) View Results Tree (Debug):**
   - Clique com botão direito em `Thread Group`
   - `Add` → `Listener` → `View Results Tree`

   **b) Summary Report:**
   - Clique com botão direito em `Thread Group`
   - `Add` → `Listener` → `Summary Report`

   **c) Aggregate Report:**
   - Clique com botão direito em `Thread Group`
   - `Add` → `Listener` → `Aggregate Report`

   **d) Response Time Graph:**
   - Clique com botão direito em `Thread Group`
   - `Add` → `Listener` → `Response Time Graph`

**9. Salvar o Teste:**
- `File` → `Save As...`
- Navegue até: `D:\GitHub\NewtonPaiva\ArquiteturaAplicacaoWeb\Teste\exemplo03`
- Nome do arquivo: `stress-test.jmx`
- Clique em `Save`

**10. Executar o Teste (Opcional - GUI Mode):**
```powershell
# Certifique-se que a API está rodando:
# cd D:\GitHub\NewtonPaiva\ArquiteturaAplicacaoWeb\Teste\exemplo03\demo
# .\mvnw.cmd spring-boot:run

# No JMeter GUI, clique no botão verde ▶️ (Start) ou Ctrl+R
```

**11. Executar via Linha de Comando (Recomendado):**
```powershell
cd C:\apache-jmeter-5.6.3\bin
.\jmeter.bat -n -t "D:\GitHub\NewtonPaiva\ArquiteturaAplicacaoWeb\Teste\exemplo03\stress-test.jmx" -l results-stress.jtl -e -o report-stress
```

**12. Visualizar Resultados:**
```powershell
start report-stress\index.html
```

### 📊 O que Esperar no Teste de Stress:

- ✅ **Taxa de Erro:** Pode aumentar (5-10%) devido à sobrecarga
- ✅ **Tempo de Resposta:** Provavelmente > 100ms (até 500ms ou mais)
- ✅ **Throughput:** Deve ser alto (100-200 req/s)
- ⚠️ **CPU/Memória:** Monitore o servidor - pode chegar a 80-100%

---

### 🔹 Teste 3: Teste de Pico/Spike

### 🔹 Teste 3: Teste de Pico/Spike

**Equivalente ao Gatling SpikeSimulation** - 100 usuários em pico repentino

> 🎯 **Objetivo:** Simular um pico repentino de tráfego (ex: promoção relâmpago, post viral)

#### Passo a Passo Completo:

**1. Abrir o JMeter:**
```powershell
cd C:\apache-jmeter-5.6.3\bin
.\jmeter.bat
```

**2. Criar Novo Test Plan:**
- `File` → `New` (ou Ctrl+N)
- Renomeie `Test Plan` para: `Spike Test - Products API`

**3. Adicionar Thread Group:**
- Clique com botão direito em `Test Plan`
- `Add` → `Threads (Users)` → `Thread Group`
- Configure:
  - **Name:** `Spike - 100 Users Sudden`
  - **Number of Threads (users):** `100`
  - **Ramp-up period (seconds):** `1`
  - **Loop Count:** `1`

> 💡 **Diferença entre Spike e Stress:** Spike tem menos usuários (100 vs 200), mas ainda é repentino!

**4. Adicionar HTTP Request Defaults:**
- Clique com botão direito em `Thread Group`
- `Add` → `Config Element` → `HTTP Request Defaults`
- Configure:
  - **Server Name or IP:** `localhost`
  - **Port Number:** `8080`

**5. Adicionar HTTP Request - GET Products:**
- Clique com botão direito em `Thread Group`
- `Add` → `Sampler` → `HTTP Request`
- Configure:
  - **Name:** `GET Products - Spike Load`
  - **Method:** `GET`
  - **Path:** `/products`

**6. Adicionar HTTP Request - GET Product by ID:**
- Clique com botão direito em `Thread Group`
- `Add` → `Sampler` → `HTTP Request`
- Configure:
  - **Name:** `GET Product by ID`
  - **Method:** `GET`
  - **Path:** `/products/1`

> 💡 **Por que 2 requests?** Para testar diferentes endpoints simultaneamente!

**7. Adicionar Assertions para o primeiro request:**
- Clique com botão direito em `GET Products - Spike Load`
- `Add` → `Assertions` → `Response Assertion`
- Configure:
  - **Pattern Matching Rules:** `Equals`
  - **Response Code:** `200`

**8. Adicionar Assertions para o segundo request:**
- Clique com botão direito em `GET Product by ID`
- `Add` → `Assertions` → `Response Assertion`
- Configure:
  - **Pattern Matching Rules:** `Equals`
  - **Response Code:** `200`

**9. Adicionar Duration Assertion (Global):**
- Clique com botão direito em `Thread Group`
- `Add` → `Assertions` → `Duration Assertion`
- Configure:
  - **Duration in milliseconds:** `1500`

> 💡 **Aplicado ao Thread Group:** Valida TODOS os requests!

**10. Adicionar Listeners Avançados:**

   **a) Aggregate Report:**
   - Clique com botão direito em `Thread Group`
   - `Add` → `Listener` → `Aggregate Report`

   **b) Response Time Graph:**
   - `Add` → `Listener` → `Response Time Graph`

   **c) Graph Results:**
   - `Add` → `Listener` → `Graph Results`

   **d) Backend Listener (Para visualização externa - Opcional):**
   - `Add` → `Listener` → `Backend Listener`
   - **Backend Listener implementation:** `GraphiteBackendListenerClient`
   - (Apenas se tiver Grafana/Graphite configurado)

**11. Adicionar Constant Timer (Opcional - Para controlar taxa):**
- Clique com botão direito em `Thread Group`
- `Add` → `Timer` → `Constant Timer`
- Configure:
  - **Thread Delay (in milliseconds):** `100`

> 💡 **Sem timer:** Requests são enviados o mais rápido possível!

**12. Salvar o Teste:**
- `File` → `Save As...`
- Caminho: `D:\GitHub\NewtonPaiva\ArquiteturaAplicacaoWeb\Teste\exemplo03`
- Nome: `spike-test.jmx`

**13. Executar via CLI:**
```powershell
cd C:\apache-jmeter-5.6.3\bin
.\jmeter.bat -n -t "D:\GitHub\NewtonPaiva\ArquiteturaAplicacaoWeb\Teste\exemplo03\spike-test.jmx" -l results-spike.jtl -e -o report-spike
```

**14. Analisar Gráficos:**
```powershell
start report-spike\index.html
```

### 📊 O que Esperar no Teste de Spike:

- ✅ **Início:** Pico súbito de requisições
- ✅ **Tempo de Resposta:** Pode ter picos > 200ms nos primeiros segundos
- ✅ **Recuperação:** Sistema deve se estabilizar rapidamente
- ⚠️ **Alertas:** Possíveis timeouts nos primeiros 2-3 segundos

---

### 🔹 Teste 4: Teste de Endurance

### 🔹 Teste 4: Teste de Endurance

**Equivalente ao Gatling EnduranceSimulation** - 10 usuários/seg por 10 minutos

> 🎯 **Objetivo:** Detectar memory leaks, degradação de performance ao longo do tempo

#### Passo a Passo Completo:

**1. Abrir o JMeter:**
```powershell
cd C:\apache-jmeter-5.6.3\bin
.\jmeter.bat
```

**2. Criar Novo Test Plan:**
- `File` → `New`
- Renomeie para: `Endurance Test - Products API`
- Clique com botão direito em `Test Plan`
- `Add` → `Config Element` → `User Defined Variables`
- Adicione variável:
  - **Name:** `TEST_DURATION`
  - **Value:** `600` (10 minutos em segundos)

**3. Adicionar Thread Group com Scheduler:**
- Clique com botão direito em `Test Plan`
- `Add` → `Threads (Users)` → `Thread Group`
- Configure:
  - **Name:** `Endurance - Constant Load`
  - **Number of Threads (users):** `10`
  - **Ramp-up period (seconds):** `10`
  - **Loop Count:** Selecione `Infinite` ☑️
  - **Scheduler:** ☑️ **Marque** esta opção!
  - **Duration (seconds):** `${TEST_DURATION}` (ou `600`)
  - **Startup delay (seconds):** `0`

> 💡 **Infinite Loop + Duration:** Thread continua até o tempo acabar!

**4. Adicionar HTTP Request Defaults:**
- Clique com botão direito em `Thread Group`
- `Add` → `Config Element` → `HTTP Request Defaults`
- Configure:
  - **Protocol:** `http`
  - **Server Name or IP:** `localhost`
  - **Port Number:** `8080`

**5. Adicionar Múltiplos HTTP Requests (Simular uso real):**

   **Request 1: GET All Products**
   - Clique com botão direito em `Thread Group`
   - `Add` → `Sampler` → `HTTP Request`
   - Configure:
     - **Name:** `GET All Products`
     - **Method:** `GET`
     - **Path:** `/products`

   **Request 2: GET Product by ID (Random)**
   - `Add` → `Sampler` → `HTTP Request`
   - Configure:
     - **Name:** `GET Product Random ID`
     - **Method:** `GET`
     - **Path:** `/products/${__Random(1,10)}`

   > 💡 **${__Random(1,10)}:** Gera ID aleatório entre 1 e 10!

   **Request 3: GET Product by Name**
   - `Add` → `Sampler` → `HTTP Request`
   - Configure:
     - **Name:** `GET Product by Name`
     - **Method:** `GET`
     - **Path:** `/products/name/Test`

   **Request 4: Health Check**
   - `Add` → `Sampler` → `HTTP Request`
   - Configure:
     - **Name:** `Health Check /hello`
     - **Method:** `GET`
     - **Path:** `/hello`

**6. Adicionar Constant Throughput Timer:**
- Clique com botão direito em `Thread Group` (acima dos requests)
- `Add` → `Timer` → `Constant Throughput Timer`
- Configure:
  - **Name:** `Constant Rate - 10 req/sec`
  - **Target throughput (in samples per minute):** `600`
  - **Calculate Throughput based on:** `all active threads in current thread group`

> 💡 **600 samples/min = 10 req/sec!**

**7. Adicionar Assertions (Para todos os requests):**
- Clique com botão direito em `Thread Group`
- `Add` → `Assertions` → `Response Assertion`
- Configure:
  - **Name:** `Assert HTTP 200`
  - **Apply to:** `Main sample and sub-samples`
  - **Response Field:** `Response Code`
  - **Pattern Matching Rules:** `Equals`
  - **Patterns to Test:** `200`

**8. Adicionar Duration Assertion:**
- Clique com botão direito em `Thread Group`
- `Add` → `Assertions` → `Duration Assertion`
- Configure:
  - **Duration in milliseconds:** `1000`

> 💡 **Importante:** Em teste de endurance, response time NÃO deve degradar!

**9. Adicionar Listeners para Monitoramento de Longo Prazo:**

   **a) Summary Report:**
   - Clique com botão direito em `Thread Group`
   - `Add` → `Listener` → `Summary Report`

   **b) Aggregate Report:**
   - `Add` → `Listener` → `Aggregate Report`

   **c) Response Time Graph:**
   - `Add` → `Listener` → `Response Time Graph`

   **d) Active Threads Over Time (Plugin - Opcional):**
   - Se tiver o plugin instalado:
   - `Add` → `Listener` → `jp@gc - Active Threads Over Time`

   **e) Transactions per Second (Plugin - Opcional):**
   - `Add` → `Listener` → `jp@gc - Transactions per Second`

**10. Adicionar Simple Data Writer (Salvar resultados):**
- Clique com botão direito em `Thread Group`
- `Add` → `Listener` → `Simple Data Writer`
- Configure:
  - **Filename:** `endurance-results.csv`

**11. Configurar Test Plan para Produção:**
- Clique em `Test Plan`
- ☑️ **Run Thread Groups consecutively** (desmarque)
- ☑️ **Run tearDown Thread Groups after shutdown** (marque)
- ☑️ **Functional Test Mode** (desmarque)

**12. Salvar o Teste:**
- `File` → `Save As...`
- Caminho: `D:\GitHub\NewtonPaiva\ArquiteturaAplicacaoWeb\Teste\exemplo03`
- Nome: `endurance-test.jmx`

**13. Preparar para Execução Longa:**
```powershell
# Aumentar memória JMeter para teste longo
cd C:\apache-jmeter-5.6.3\bin

# Editar jmeter.bat (opcional)
# Procure: set HEAP=-Xms1g -Xmx1g
# Altere para: set HEAP=-Xms2g -Xmx2g
```

**14. Executar via CLI (Recomendado para 10 minutos):**
```powershell
cd C:\apache-jmeter-5.6.3\bin

# Execute com log em arquivo
.\jmeter.bat -n -t "D:\GitHub\NewtonPaiva\ArquiteturaAplicacaoWeb\Teste\exemplo03\endurance-test.jmx" -l results-endurance.jtl -j endurance.log -e -o report-endurance
```

> ⏱️ **Atenção:** Este teste vai demorar **10 minutos**! Vá tomar um café ☕

**15. Monitorar em Tempo Real (Opcional):**
```powershell
# Em outro terminal, monitore o log
Get-Content endurance.log -Wait -Tail 50

# Ou monitore recursos do sistema
# Abra Task Manager e observe CPU/RAM durante os 10 minutos
```

**16. Após Conclusão, Visualizar Relatório:**
```powershell
start report-endurance\index.html
```

### 📊 O que Esperar no Teste de Endurance:

**✅ Métricas Ideais:**
- **Tempo de Resposta:** Deve permanecer **estável** (~15-30ms)
- **Taxa de Erro:** Deve ser **0.00%** do início ao fim
- **Throughput:** Constante em ~10 req/sec
- **Memória do Servidor:** Não deve crescer continuamente (memory leak)
- **CPU:** Deve estabilizar (~20-40%)

**⚠️ Sinais de Problema:**
- ❌ Tempo de resposta aumentando gradualmente (degradação)
- ❌ Taxa de erro crescendo ao longo do tempo
- ❌ Memória RAM do servidor crescendo sem parar (memory leak!)
- ❌ Conexões abertas aumentando (connection pool leak)

### 📈 Como Analisar o Relatório de Endurance:

**1. Verifique o gráfico "Response Times Over Time":**
   - Linha deve ser **horizontal** (não inclinada para cima)
   - Se subir gradualmente = **Degradação de Performance**

**2. Verifique "Active Threads Over Time":**
   - Deve ser constante em **10 threads**

**3. Verifique "Transactions per Second":**
   - Deve oscilar em torno de **10 tps**

**4. Compare métricas:**
   - Primeiro minuto vs Último minuto
   - Se divergirem muito = **Problema de performance**

### 🎓 Exemplo de Análise:

```
Minuto 1:
  Average: 15ms | Error: 0% | Throughput: 10.2 req/s

Minuto 10:
  Average: 16ms | Error: 0% | Throughput: 10.1 req/s

✅ RESULTADO: Sistema ESTÁVEL! (Variação < 10%)
```

```
Minuto 1:
  Average: 15ms | Error: 0% | Throughput: 10.2 req/s

Minuto 10:
  Average: 150ms | Error: 5% | Throughput: 8.5 req/s

❌ RESULTADO: DEGRADAÇÃO! Investigar memory leak ou connection pool!
```

---

### 📝 Criando um Teste Completo Passo a Passo

#### Exemplo: Teste de CRUD Completo

**1. Abra o JMeter** (`jmeter.bat`)

**2. Configure Thread Group:**
   - Nome: `CRUD Test - Products`
   - Threads: 10
   - Ramp-up: 10
   - Loop: 1

**3. Adicione HTTP Header Manager (Antes dos Requests):**
   - Clique com botão direito em `Thread Group`
   - `Add` → `Config Element` → `HTTP Header Manager`
   - Adicione headers:
     - `Content-Type: application/json`
     - `Accept: application/json`

**4. Adicione 4 HTTP Requests:**

   **Request 1: GET All Products**
   - Path: `/products`
   - Method: GET

   **Request 2: POST Create Product**
   - Path: `/products`
   - Method: POST
   - Body Data (JSON):
     ```json
     {
       "name": "Test Product JMeter",
       "price": 99.99,
       "description": "Produto criado via JMeter"
     }
     ```

   **Request 3: GET Product by ID**
   - Path: `/products/1`
   - Method: GET

   **Request 4: GET Product by Name**
   - Path: `/products/name/Test`
   - Method: GET

**5. Adicione Assertions em cada request:**
   - Response Code: 200
   - Response Time (ms): < 1000

**6. Adicione Listeners:**
   - View Results Tree
   - Aggregate Report
   - Response Time Graph

**7. Salve:** `crud-test.jmx`

---

## 🚀 Executando os Testes

### Método 1: Interface Gráfica (GUI Mode)

> ⚠️ **Importante:** Use GUI apenas para criar e depurar testes, não para executar testes de carga!

```powershell
# 1. Inicie o JMeter
cd C:\apache-jmeter-5.6.3\bin
.\jmeter.bat

# 2. Abra o plano de teste: File → Open → selecione o arquivo .jmx

# 3. Clique no botão verde "Start" (▶️) no topo da janela

# 4. Observe os resultados nos Listeners em tempo real
```

---

### Método 2: Modo Linha de Comando (Recomendado para Performance)

```powershell
cd C:\apache-jmeter-5.6.3\bin

# Teste Básico de Carga
.\jmeter.bat -n -t "D:\GitHub\NewtonPaiva\ArquiteturaAplicacaoWeb\Teste\exemplo03\basic-load-test.jmx" -l results-basic.jtl -e -o report-basic

# Teste de Stress
.\jmeter.bat -n -t "D:\GitHub\NewtonPaiva\ArquiteturaAplicacaoWeb\Teste\exemplo03\stress-test.jmx" -l results-stress.jtl -e -o report-stress

# Teste de Spike
.\jmeter.bat -n -t "D:\GitHub\NewtonPaiva\ArquiteturaAplicacaoWeb\Teste\exemplo03\spike-test.jmx" -l results-spike.jtl -e -o report-spike

# Teste de Endurance
.\jmeter.bat -n -t "D:\GitHub\NewtonPaiva\ArquiteturaAplicacaoWeb\Teste\exemplo03\endurance-test.jmx" -l results-endurance.jtl -e -o report-endurance
```

#### Parâmetros da Linha de Comando:
- `-n` : Modo não-GUI (non-GUI mode)
- `-t` : Caminho do arquivo de teste (.jmx)
- `-l` : Arquivo de log de resultados (.jtl)
- `-e` : Gera relatório HTML após o teste
- `-o` : Pasta de saída do relatório HTML

---

### 🔧 Ordem de Execução Completa

#### 1. Iniciar a API Spring Boot
```powershell
cd D:\GitHub\NewtonPaiva\ArquiteturaAplicacaoWeb\Teste\exemplo03\demo
.\mvnw.cmd spring-boot:run
```
✅ A API deve estar rodando em `http://localhost:8080`

#### 2. Verificar se a API está respondendo
```powershell
curl http://localhost:8080/products
# ou
Invoke-WebRequest -Uri "http://localhost:8080/products" -UseBasicParsing
```

#### 3. Executar os Testes JMeter

**Opção A - Interface Gráfica (Criação/Debug):**
```powershell
cd C:\apache-jmeter-5.6.3\bin
.\jmeter.bat
```

**Opção B - Linha de Comando (Performance Real):**
```powershell
cd C:\apache-jmeter-5.6.3\bin
.\jmeter.bat -n -t "caminho\do\teste.jmx" -l results.jtl -e -o report
```

#### 4. Analisar Resultados
```powershell
# Abra o relatório HTML no navegador
start report\index.html
```

---

## 📊 Visualizando Resultados

### Relatórios HTML Automáticos:
Após executar com `-e -o`, abra os relatórios:
```powershell
start report-basic\index.html
start report-stress\index.html
start report-spike\index.html
start report-endurance\index.html
```

### 📈 Principais Métricas:

| Métrica | Descrição |
|---------|-----------|
| **Throughput** | Requisições por segundo |
| **Average** | Tempo médio de resposta (ms) |
| **Min/Max** | Tempos mínimo e máximo (ms) |
| **Error %** | Taxa de erro (%) |
| **90th Percentile** | 90% das requisições abaixo desse tempo |
| **95th Percentile** | 95% das requisições abaixo desse tempo |
| **99th Percentile** | 99% das requisições abaixo desse tempo |

### 🎓 Exemplo de Saída Esperada

```
summary +     50 in 00:00:30 =    1.7/s Avg:    15 Min:     8 Max:   169 Err:     0 (0.00%)
summary =     50 in 00:00:30 =    1.7/s Avg:    15 Min:     8 Max:   169 Err:     0 (0.00%)

Throughput:   1.72 requests/sec
Average:      15 ms
Median:       11 ms
90% Line:     16 ms
95% Line:     16 ms
99% Line:     169 ms
Error %:      0.00%
```

---

## 🔍 Principais Componentes do JMeter

### 1️⃣ Thread Group (Grupo de Threads)
- Simula usuários virtuais
- Controla número de usuários, ramp-up, loops
- Equivalente ao `setUp()` do Gatling

### 2️⃣ Samplers (Amostradores)
- **HTTP Request** - Requisições HTTP/HTTPS
- **JDBC Request** - Testes de banco de dados
- **FTP Request** - Testes de FTP
- Equivalente ao `exec()` do Gatling

### 3️⃣ Listeners (Ouvintes)
- **View Results Tree** - Visualiza cada requisição/resposta
- **Aggregate Report** - Estatísticas agregadas
- **Graph Results** - Gráficos de performance
- **Summary Report** - Resumo rápido

### 4️⃣ Assertions (Afirmações)
- **Response Assertion** - Valida código de resposta, texto, etc.
- **Duration Assertion** - Valida tempo de resposta
- **Size Assertion** - Valida tamanho da resposta
- Equivalente ao `.check()` do Gatling

### 5️⃣ Timers (Temporizadores)
- **Constant Timer** - Pausa fixa entre requisições
- **Uniform Random Timer** - Pausa aleatória
- **Constant Throughput Timer** - Controla taxa de requisições
- Equivalente ao `pace()` do Gatling

### 6️⃣ Config Elements (Elementos de Configuração)
- **HTTP Header Manager** - Headers HTTP
- **HTTP Cookie Manager** - Gerencia cookies
- **CSV Data Set Config** - Carrega dados de CSV

---

## 🎯 Comparação: Gatling vs JMeter

| Característica | Gatling | JMeter |
|----------------|---------|--------|
| **Linguagem** | Scala/Java DSL | XML/GUI |
| **Interface** | Código | Gráfica + CLI |
| **Relatórios** | HTML elegante | HTML + vários listeners |
| **Curva de Aprendizado** | Média (código) | Fácil (GUI) |
| **Performance** | Muito alta | Alta |
| **Protocolos** | HTTP, WebSocket, SSE | HTTP, JDBC, FTP, SOAP, etc. |
| **Uso de Recursos** | Leve | Moderado |
| **Cenários Complexos** | Código DSL | GUI drag-and-drop |
| **Ideal Para** | DevOps, CI/CD | QA, testes exploratórios |

---

## 🎓 Dicas para Aula e Demonstração

### 📋 Ordem Recomendada de Ensino:

**1. Introdução Teórica (15 min):**
- Explicar tipos de teste: Load, Stress, Spike, Endurance
- Mostrar diferença entre GUI e CLI mode
- Explicar conceitos: Thread, Ramp-up, Loop Count, Assertions

**2. Demonstração Prática - Teste Básico (20 min):**
- Criar Test Plan do zero no JMeter GUI
- Adicionar Thread Group passo a passo
- Adicionar HTTP Request
- Adicionar Assertions
- Adicionar Listeners
- Executar em GUI mode (mostrar resultados em tempo real)

**3. Evolução - Testes Avançados (15 min):**
- Mostrar diferença entre Basic → Stress → Spike → Endurance
- Ajustar apenas Thread Group para cada tipo
- Explicar quando usar cada tipo

**4. Execução via CLI (10 min):**
- Demonstrar execução por linha de comando
- Mostrar relatórios HTML gerados
- Comparar performance GUI vs CLI

**5. Análise de Resultados (15 min):**
- Abrir relatório HTML no navegador
- Explicar métricas: Throughput, Response Time, Error %
- Interpretar gráficos

**6. Exercícios Práticos (30 min):**
- Alunos criam seus próprios testes
- Executam e comparam resultados

---

### 💡 Dicas de Demonstração em Aula:

#### ✅ DO (Faça):

1. **Prepare o ambiente ANTES da aula:**
   ```powershell
   # Teste que tudo funciona
   cd C:\apache-jmeter-5.6.3\bin
   .\jmeter.bat
   
   # Teste que a API sobe
   cd D:\GitHub\NewtonPaiva\ArquiteturaAplicacaoWeb\Teste\exemplo03\demo
   .\mvnw.cmd spring-boot:run
   ```

2. **Use projetor/compartilhamento de tela grande:**
   - Aumente zoom do JMeter: `Options` → `Zoom In`
   - Aumente fonte do PowerShell: `Ctrl + Scroll`

3. **Crie checkpoints:**
   - Salve `.jmx` em cada etapa
   - Ex: `basic-step1.jmx`, `basic-step2.jmx`, etc.
   - Se algo der errado, volte para checkpoint

4. **Mostre erros propositalmente:**
   - Execute teste SEM a API rodando (Connection Refused)
   - Coloque endpoint errado (`/api/products`) (HTTP 404)
   - Mostre como debugar no View Results Tree

5. **Compare resultados:**
   - Execute Basic (50 users) e Stress (200 users) lado a lado
   - Mostre diferença nos gráficos

#### ❌ DON'T (Não Faça):

1. ❌ Não execute teste de 10 minutos na aula (use 1 minuto para demo)
2. ❌ Não assuma que Java/JMeter está instalado nos PCs dos alunos
3. ❌ Não esqueça de fechar JMeter GUI antes de executar CLI
4. ❌ Não use valores muito altos (5000 threads) que travam o PC
5. ❌ Não pule a explicação de Thread Group (conceito fundamental!)

---

### 📝 Exercícios Práticos para Alunos

#### 🎯 Exercício 1: Teste Básico (Iniciante)

**Objetivo:** Criar um teste simples de carga

**Tarefas:**
1. Criar Test Plan chamado "Meu Primeiro Teste"
2. Adicionar Thread Group com 20 usuários, 10s ramp-up
3. Adicionar HTTP Request para `GET /hello`
4. Adicionar Response Assertion (status 200)
5. Adicionar View Results Tree
6. Executar e tirar print do resultado

**Entrega:** Screenshot do View Results Tree com status verde ✅

---

#### 🎯 Exercício 2: Teste com Múltiplos Endpoints (Intermediário)

**Objetivo:** Testar vários endpoints em sequência

**Tarefas:**
1. Criar Thread Group com 10 usuários
2. Adicionar 4 HTTP Requests:
   - `GET /products`
   - `GET /products/1`
   - `GET /products/name/Test`
   - `GET /hello`
3. Adicionar Assertions em cada um
4. Adicionar Aggregate Report
5. Executar via CLI e gerar relatório HTML

**Entrega:** 
- Arquivo `.jmx`
- Screenshot do Aggregate Report
- Link ou print do relatório HTML

---

#### 🎯 Exercício 3: Comparação de Testes (Avançado)

**Objetivo:** Comparar performance entre diferentes cargas

**Tarefas:**
1. Criar 3 testes separados:
   - Leve: 10 usuários, 10s ramp-up
   - Médio: 50 usuários, 30s ramp-up
   - Pesado: 100 usuários, 10s ramp-up
2. Executar os 3 via CLI
3. Gerar relatórios HTML para cada um
4. Comparar métricas em uma tabela:

| Teste | Threads | Throughput | Avg Time | Error % |
|-------|---------|------------|----------|---------|
| Leve  | 10      | ?          | ?        | ?       |
| Médio | 50      | ?          | ?        | ?       |
| Pesado| 100     | ?          | ?        | ?       |

**Entrega:** 
- 3 arquivos `.jmx`
- Tabela preenchida com métricas
- Análise: Qual teste teve melhor performance? Por quê?

---

#### 🎯 Exercício 4: Teste CRUD Completo (Desafio)

**Objetivo:** Criar teste completo com POST, GET, PUT, DELETE

**Tarefas:**
1. Criar teste com sequência:
   - `POST /products` (criar produto)
   - Extrair ID do produto criado (JSON Extractor)
   - `GET /products/${productId}` (buscar produto criado)
   - `PUT /products/${productId}` (atualizar produto)
   - `DELETE /products/${productId}` (deletar produto)
2. Adicionar assertions para cada operação
3. Usar CSV Data Set Config para nomes de produtos variados

**Entrega:**
- Arquivo `.jmx`
- Arquivo CSV com dados
- Relatório de execução

---

### 🎬 Script de Demonstração (Para Professores)

#### Cena 1: Instalação (5 min)

```
Professor: "Vamos instalar o JMeter. Abram o site oficial..."
[Mostrar download e instalação passo a passo]
[Executar jmeter.bat pela primeira vez]
"Essa é a interface do JMeter. Parece complexo, mas vamos desmistificar!"
```

#### Cena 2: Primeiro Teste (15 min)

```
Professor: "Vamos criar nosso primeiro teste de carga!"
[Seguir passo a passo do Teste 1]
"Reparem que Thread Group representa USUÁRIOS VIRTUAIS"
"50 usuários em 30 segundos = como se 50 pessoas acessassem seu site gradualmente"
[Executar teste]
"Vejam no View Results Tree - todas as requisições estão verdes! ✅"
```

#### Cena 3: Comparando Tipos de Teste (10 min)

```
Professor: "Agora vou mostrar a diferença entre Basic e Stress"
[Abrir basic-test.jmx e stress-test.jmx lado a lado]
"Reparem: Basic = 50 users em 30s (gradual)"
"Stress = 200 users em 1s (TODOS DE UMA VEZ!)"
[Executar stress test]
"Vejam que o tempo de resposta aumentou! Por quê?"
[Explicar sobrecarga do servidor]
```

#### Cena 4: CLI e Relatórios (10 min)

```
Professor: "GUI é legal para aprender, mas na vida real usamos CLI"
[Abrir PowerShell]
[Executar comando jmeter.bat -n -t ...]
"Vejam: muito mais rápido! Sem interface pesada"
[Esperar relatório ser gerado]
[Abrir index.html no navegador]
"Este é o relatório profissional que vocês vão gerar!"
[Mostrar gráficos e métricas]
```

#### Cena 5: Troubleshooting ao Vivo (5 min)

```
Professor: "Vou propositalmente causar um erro comum..."
[Para a API]
[Executa teste]
"Connection refused! O que aconteceu?"
[Mostrar erro no View Results Tree]
"Sempre verifiquem se a API está rodando ANTES do teste!"
[Reinicia API]
[Teste passa]
"Agora sim! ✅"
```

---

### 📊 Tabela Comparativa dos 4 Testes (Para Referência Rápida)

| Teste | Threads | Ramp-up | Duração | Objetivo | Quando Usar |
|-------|---------|---------|---------|----------|-------------|
| **Basic Load** | 50 | 30s | 30s | Performance normal | Validar comportamento típico |
| **Stress** | 200 | 1s | ~3min | Limite do sistema | Encontrar breaking point |
| **Spike** | 100 | 1s | ~2min | Pico repentino | Testar elasticidade |
| **Endurance** | 10 | 10s | 10min | Estabilidade longa | Detectar memory leaks |

---

### 🔬 Conceitos Técnicos Explicados (Glossário para Aula)

**Thread:** 
- = Usuário virtual
- 1 thread = 1 usuário fazendo requisições

**Ramp-up:**
- Tempo para subir todos os usuários
- Ex: 100 users, 10s ramp-up = 10 usuários/segundo

**Loop Count:**
- Quantas vezes cada thread repete as requisições
- Infinite = até dar timeout ou duração acabar

**Sampler:**
- = Requisição
- HTTP Request Sampler = fazer uma chamada HTTP

**Assertion:**
- = Validação
- Verificar se resposta está correta (status 200, tempo < 1s, etc.)

**Listener:**
- = Visualizador de resultados
- Mostra o que aconteceu durante o teste

**Throughput:**
- Requisições por segundo (req/s ou tps)
- Maior = melhor performance

**Latency:**
- Tempo entre enviar request e receber primeiro byte
- Menor = melhor

**Response Time:**
- Tempo total da requisição (latency + download)
- Menor = melhor

---

### 🎯 Checklist para Aula Bem-Sucedida

**Antes da Aula:**
- [ ] JMeter instalado e testado
- [ ] API demo rodando sem erros
- [ ] Arquivos .jmx de exemplo criados
- [ ] Relatórios HTML de exemplo gerados
- [ ] PowerShell configurado (zoom, cores)
- [ ] Slides/apresentação preparados

**Durante a Aula:**
- [ ] Explicar teoria antes da prática
- [ ] Mostrar conceitos com exemplos visuais
- [ ] Executar testes ao vivo (não só slides!)
- [ ] Provocar erros para ensinar troubleshooting
- [ ] Dar tempo para alunos praticarem
- [ ] Circular pela sala ajudando

**Depois da Aula:**
- [ ] Compartilhar arquivos .jmx de exemplo
- [ ] Disponibilizar este README
- [ ] Criar exercício para casa
- [ ] Abrir fórum para dúvidas

---

## 💡 Boas Práticas

### ✅ DO (Faça)

1. **NÃO execute testes de carga em GUI mode** - use apenas para criar/depurar
2. **Use modo CLI** (`-n`) para testes de performance reais
3. **Desative listeners desnecessários** durante execução
4. **Use CSV Data Set** para parametrizar dados
5. **Configure assertions** para validar respostas
6. **Monitore recursos do sistema** durante testes (CPU, RAM, rede)
7. **Comece pequeno** e aumente gradualmente a carga
8. **Execute warm-up** antes de medir resultados

### ❌ DON'T (Não Faça)

1. Não execute testes de carga na GUI (consome muitos recursos)
2. Não esqueça de limpar dados de teste após execução
3. Não ignore erros nos listeners
4. Não use valores hardcoded - parametrize com variáveis/CSV

---

## 🆘 Troubleshooting

### ❌ Problema: "Java not found"

**Solução:**
```powershell
# Verifique se Java está instalado
java -version

# Configure JAVA_HOME
[Environment]::SetEnvironmentVariable("JAVA_HOME", "C:\Program Files\Java\jdk-XX", "User")
```

---

### ❌ Problema: OutOfMemoryError

**Solução:**
```powershell
# Edite o arquivo jmeter.bat e aumente a memória heap
# Procure por: set HEAP=-Xms1g -Xmx1g
# Altere para: set HEAP=-Xms2g -Xmx4g
```

Ou crie um script customizado:
```powershell
# jmeter-custom.bat
set HEAP=-Xms2g -Xmx4g
call jmeter.bat %*
```

---

### ❌ Problema: Porta 8080 em uso

**Solução:**
```powershell
# Encontre o processo
netstat -ano | Select-String ":8080"

# Finalize o processo
Stop-Process -Id <PID> -Force
```

---

### ❌ Problema: Connection refused

**Verificações:**
1. A API está rodando? (`curl http://localhost:8080/products`)
2. O endpoint está correto? (sem `/api/` no path)
3. O firewall está bloqueando?

---

## 📚 Recursos Adicionais

### 📖 Documentação Oficial

- **Site Oficial:** https://jmeter.apache.org/
- **Download:** https://jmeter.apache.org/download_jmeter.cgi
- **User Manual:** https://jmeter.apache.org/usermanual/index.html
- **Best Practices:** https://jmeter.apache.org/usermanual/best-practices.html
- **Component Reference:** https://jmeter.apache.org/usermanual/component_reference.html

### 🎓 Tutoriais Recomendados

1. **[Getting Started](https://jmeter.apache.org/usermanual/get-started.html)** - Tutorial básico
2. **[Building a Web Test Plan](https://jmeter.apache.org/usermanual/build-web-test-plan.html)** - Criando testes web
3. **[Build a Database Test Plan](https://jmeter.apache.org/usermanual/build-db-test-plan.html)** - Testes de BD
4. **[Remote Testing](https://jmeter.apache.org/usermanual/remote-test.html)** - Testes distribuídos

### 🔌 Plugins Úteis

**JMeter Plugins Manager:** https://jmeter-plugins.org/

- Custom Thread Groups
- PerfMon (monitoramento de servidor)
- Parametrized Controllers
- Dummy Sampler

#### Instalação de Plugins:

```powershell
# 1. Baixe o Plugins Manager JAR
# https://jmeter-plugins.org/get/

# 2. Copie para C:\apache-jmeter-5.6.3\lib\ext\

# 3. Reinicie o JMeter

# 4. Acesse: Options → Plugins Manager
```

---

## 🚀 Próximos Passos

1. ✅ **Explore plugins do JMeter** para funcionalidades avançadas
2. ✅ **Configure testes distribuídos** para simular mais carga
3. ✅ **Integre com CI/CD** (Jenkins, GitLab CI, Azure DevOps)
4. ✅ **Use parametrização CSV** para cenários realistas
5. ✅ **Combine com monitoramento** (Prometheus, Grafana)
6. ✅ **Aprenda sobre correlação** para variáveis dinâmicas
7. ✅ **Estude testes de API REST** com JSON Path Assertions

---

**📝 Documentação criada com base em:**
- Apache JMeter 5.6.3
- Spring Boot 3.4.3
- Java 23
- Windows PowerShell

**🎯 Objetivo:** Guia completo para testes de performance com JMeter, equivalente aos testes Gatling criados anteriormente.

---

**Made with ❤️ for Performance Testing**
