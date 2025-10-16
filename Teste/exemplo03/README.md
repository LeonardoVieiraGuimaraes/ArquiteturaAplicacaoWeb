# Testes de Performance com JMeter - exemplo03

Este módulo demonstra testes de performance com **Apache JMeter** para sua API Spring Boot.

## 📚 Documentação Oficial

- **Apache JMeter:** https://jmeter.apache.org/
- **Guia do Usuário:** https://jmeter.apache.org/usermanual/index.html
- **Getting Started:** https://jmeter.apache.org/usermanual/get-started.html
- **Building a Web Test Plan:** https://jmeter.apache.org/usermanual/build-web-test-plan.html
- **Best Practices:** https://jmeter.apache.org/usermanual/best-practices.html

## 📥 Download e Instalação do JMeter (Windows)

### Pré-requisitos
- **Java JDK 8+** instalado
- Verificar: `java -version` no PowerShell

### Passo 1: Download
1. Acesse: https://jmeter.apache.org/download_jmeter.cgi
2. Baixe a versão **Binaries** (apache-jmeter-X.X.zip)
3. **Versão recomendada:** Apache JMeter 5.6.3 ou superior

### Passo 2: Instalação
```powershell
# 1. Extraia o arquivo ZIP para uma pasta (ex: C:\apache-jmeter-5.6.3)

# 2. Configure a variável de ambiente JMETER_HOME (opcional mas recomendado)
[Environment]::SetEnvironmentVariable("JMETER_HOME", "C:\apache-jmeter-5.6.3", "User")

# 3. Adicione o JMeter ao PATH (opcional)
$env:Path += ";C:\apache-jmeter-5.6.3\bin"
```

### Passo 3: Iniciar JMeter
```powershell
# Navegue até a pasta bin do JMeter
cd C:\apache-jmeter-5.6.3\bin

# Execute o JMeter (interface gráfica)
.\jmeter.bat

# Ou execute em modo console (sem interface)
.\jmeter.bat -n -t test-plan.jmx -l results.jtl
```

## 🎯 Estrutura do Projeto

Este projeto contém uma API Spring Boot com os seguintes endpoints:

- `GET /products` - Lista todos os produtos
- `GET /products/{id}` - Busca produto por ID
- `GET /products/name/{name}` - Busca produto por nome
- `POST /products` - Cria novo produto
- `GET /hello` - Endpoint de teste simples

## 📋 Criando Planos de Teste no JMeter

### 🔹 Teste 1: Teste de Carga Básico (equivalente ao Gatling BasicSimulation)

#### Configuração no JMeter:

1. **Abra o JMeter** (`jmeter.bat`)

2. **Adicione Thread Group:**
   - Clique com botão direito em `Test Plan` → `Add` → `Threads (Users)` → `Thread Group`
   - Configure:
     - **Number of Threads (users):** 50
     - **Ramp-up period (seconds):** 30
     - **Loop Count:** 1

3. **Adicione HTTP Request:**
   - Clique com botão direito em `Thread Group` → `Add` → `Sampler` → `HTTP Request`
   - Configure:
     - **Name:** GET Produtos
     - **Server Name or IP:** localhost
     - **Port Number:** 8080
     - **HTTP Request:** GET
     - **Path:** /products

4. **Adicione Assertion:**
   - Clique com botão direito em `HTTP Request` → `Add` → `Assertions` → `Response Assertion`
   - Configure:
     - **Response Code:** 200

5. **Adicione Listeners (Visualizadores de Resultado):**
   - Clique com botão direito em `Thread Group` → `Add` → `Listener`:
     - **View Results Tree** - Visualiza cada requisição
     - **Summary Report** - Resumo estatístico
     - **Graph Results** - Gráfico de performance
     - **Aggregate Report** - Relatório agregado

6. **Salve o Plano de Teste:**
   - `File` → `Save Test Plan as...` → `basic-load-test.jmx`

### 🔹 Teste 2: Teste de Stress (equivalente ao Gatling StressSimulation)

#### Configuração:
1. **Thread Group:**
   - **Number of Threads:** 200
   - **Ramp-up period:** 1 (todos os usuários de uma vez)
   - **Loop Count:** 1

2. Siga os mesmos passos do Teste 1 para HTTP Request e Listeners

3. **Salvar como:** `stress-test.jmx`

### 🔹 Teste 3: Teste de Pico/Spike (equivalente ao Gatling SpikeSimulation)

#### Configuração:
1. **Thread Group:**
   - **Number of Threads:** 100
   - **Ramp-up period:** 1 (spike repentino)
   - **Loop Count:** 1

2. Siga os mesmos passos anteriores

3. **Salvar como:** `spike-test.jmx`

### 🔹 Teste 4: Teste de Endurance (equivalente ao Gatling EnduranceSimulation)

#### Configuração:
1. **Thread Group:**
   - **Number of Threads:** 10
   - **Ramp-up period:** 10
   - **Loop Count:** Infinite
   - **Duration (seconds):** 600 (10 minutos)

2. Ou use **Constant Throughput Timer:**
   - Clique com botão direito em `Thread Group` → `Add` → `Timer` → `Constant Throughput Timer`
   - Configure: **Target throughput:** 600 (10 req/sec)

3. **Salvar como:** `endurance-test.jmx`

## 🚀 Executando os Testes

### Método 1: Interface Gráfica (GUI Mode)

```powershell
# 1. Inicie o JMeter
cd C:\apache-jmeter-5.6.3\bin
.\jmeter.bat

# 2. Abra o plano de teste: File → Open → selecione o arquivo .jmx

# 3. Clique no botão verde "Start" (▶️) no topo da janela

# 4. Observe os resultados nos Listeners em tempo real
```

⚠️ **Importante:** A GUI consome muitos recursos. Use apenas para criar/depurar testes!

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

### Parâmetros da Linha de Comando:
- `-n` : Modo não-GUI (non-GUI mode)
- `-t` : Caminho do arquivo de teste (.jmx)
- `-l` : Arquivo de log de resultados (.jtl)
- `-e` : Gera relatório HTML após o teste
- `-o` : Pasta de saída do relatório HTML

## 📊 Visualizando Resultados

### Relatórios HTML Automáticos:
Após executar com `-e -o`, abra o arquivo:
```
report-basic\index.html
report-stress\index.html
report-spike\index.html
report-endurance\index.html
```

### Principais Métricas:
- **Throughput** - Requisições por segundo
- **Average** - Tempo médio de resposta
- **Min/Max** - Tempos mínimo e máximo
- **Error %** - Taxa de erro
- **90th/95th/99th Percentile** - Percentis de tempo de resposta

## 🔧 Ordem de Execução Completa

### 1. Iniciar a API Spring Boot
```powershell
cd D:\GitHub\NewtonPaiva\ArquiteturaAplicacaoWeb\Teste\exemplo03\demo
.\mvnw.cmd spring-boot:run
```
✅ A API deve estar rodando em `http://localhost:8080`

### 2. Verificar se a API está respondendo
```powershell
curl http://localhost:8080/products
# ou
Invoke-WebRequest -Uri "http://localhost:8080/products" -UseBasicParsing
```

### 3. Executar os Testes JMeter

**Opção A - Interface Gráfica (Criação/Debug):**
```powershell
cd C:\apache-jmeter-5.6.3\bin
.\jmeter.bat
```

**Opção B - Linha de Comando (Performance Real):**
```powershell
# Navegue até a pasta bin do JMeter
cd C:\apache-jmeter-5.6.3\bin

# Execute o teste desejado
.\jmeter.bat -n -t "caminho\do\teste.jmx" -l results.jtl -e -o report
```

### 4. Analisar Resultados
```powershell
# Abra o relatório HTML no navegador
start report\index.html
```

## 📝 Criando um Teste Completo Passo a Passo

### Exemplo: Teste de CRUD Completo

1. **Abra o JMeter** (`jmeter.bat`)

2. **Configure Thread Group:**
   - Nome: `CRUD Test - Products`
   - Threads: 10
   - Ramp-up: 10
   - Loop: 1

3. **Adicione 4 HTTP Requests:**

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
   - Headers:
     - `Content-Type: application/json`

   **Request 3: GET Product by ID**
   - Path: `/products/1`
   - Method: GET

   **Request 4: GET Product by Name**
   - Path: `/products/name/Test`
   - Method: GET

4. **Adicione HTTP Header Manager:**
   - Clique com botão direito em `Thread Group` → `Add` → `Config Element` → `HTTP Header Manager`
   - Adicione: `Accept: application/json`

5. **Adicione Assertions em cada request:**
   - Response Code: 200
   - Response Time (ms): < 1000

6. **Adicione Listeners:**
   - View Results Tree
   - Aggregate Report
   - Response Time Graph

7. **Salve:** `crud-test.jmx`

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

## 🔍 Principais Componentes do JMeter

### Thread Group (Grupo de Threads)
- Simula usuários virtuais
- Controla número de usuários, ramp-up, loops

### Samplers (Amostradores)
- **HTTP Request** - Requisições HTTP/HTTPS
- **JDBC Request** - Testes de banco de dados
- **FTP Request** - Testes de FTP

### Listeners (Ouvintes)
- **View Results Tree** - Visualiza cada requisição/resposta
- **Aggregate Report** - Estatísticas agregadas
- **Graph Results** - Gráficos de performance
- **Summary Report** - Resumo rápido

### Assertions (Afirmações)
- **Response Assertion** - Valida código de resposta, texto, etc.
- **Duration Assertion** - Valida tempo de resposta
- **Size Assertion** - Valida tamanho da resposta

### Timers (Temporizadores)
- **Constant Timer** - Pausa fixa entre requisições
- **Uniform Random Timer** - Pausa aleatória
- **Constant Throughput Timer** - Controla taxa de requisições

### Config Elements (Elementos de Configuração)
- **HTTP Header Manager** - Headers HTTP
- **HTTP Cookie Manager** - Gerencia cookies
- **CSV Data Set Config** - Carrega dados de CSV

## 💡 Boas Práticas

1. **NÃO execute testes de carga em GUI mode** - use apenas para criar/depurar
2. **Use modo CLI** (`-n`) para testes de performance reais
3. **Desative listeners desnecessários** durante execução
4. **Use CSV Data Set** para parametrizar dados
5. **Configure assertions** para validar respostas
6. **Monitore recursos do sistema** durante testes (CPU, RAM, rede)
7. **Comece pequeno** e aumente gradualmente a carga
8. **Execute warm-up** antes de medir resultados

## 📚 Recursos Adicionais

### Tutoriais Recomendados:
1. **[Getting Started](https://jmeter.apache.org/usermanual/get-started.html)** - Tutorial básico
2. **[Building a Web Test Plan](https://jmeter.apache.org/usermanual/build-web-test-plan.html)** - Criando testes web
3. **[Build a Database Test Plan](https://jmeter.apache.org/usermanual/build-db-test-plan.html)** - Testes de BD
4. **[Remote Testing](https://jmeter.apache.org/usermanual/remote-test.html)** - Testes distribuídos

### Plugins Úteis:
- **JMeter Plugins Manager:** https://jmeter-plugins.org/
  - Custom Thread Groups
  - PerfMon (monitoramento de servidor)
  - Parametrized Controllers

### Instalação de Plugins:
```powershell
# 1. Baixe o Plugins Manager JAR
# https://jmeter-plugins.org/get/

# 2. Copie para C:\apache-jmeter-5.6.3\lib\ext\

# 3. Reinicie o JMeter

# 4. Acesse: Options → Plugins Manager
```

## 🆘 Troubleshooting

### Problema: "Java not found"
```powershell
# Verifique se Java está instalado
java -version

# Configure JAVA_HOME
[Environment]::SetEnvironmentVariable("JAVA_HOME", "C:\Program Files\Java\jdk-XX", "User")
```

### Problema: OutOfMemoryError
```powershell
# Edite o arquivo jmeter.bat e aumente a memória heap
# Procure por: set HEAP=-Xms1g -Xmx1g
# Altere para: set HEAP=-Xms2g -Xmx4g
```

### Problema: Porta 8080 em uso
```powershell
# Encontre o processo
netstat -ano | Select-String ":8080"

# Finalize o processo
Stop-Process -Id <PID> -Force
```

## 🎓 Exemplo de Saída Esperada

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

## 🚀 Próximos Passos

1. **Explore plugins do JMeter** para funcionalidades avançadas
2. **Configure testes distribuídos** para simular mais carga
3. **Integre com CI/CD** (Jenkins, GitLab CI, etc.)
4. **Use parametrização CSV** para cenários realistas
5. **Combine com monitoramento** (Prometheus, Grafana)

---

**Documentação criada com base em:**
- Apache JMeter 5.6.3
- Spring Boot 3.4.3
- Java 23
- Windows PowerShell
