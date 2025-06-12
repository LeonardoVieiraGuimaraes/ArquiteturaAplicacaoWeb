# Monitoramento com Spring Boot Actuator, Prometheus e Grafana

Este projeto demonstra como monitorar aplicações Spring Boot utilizando o **Spring Boot Actuator**, **Prometheus** e **Grafana**.

---

## Spring Boot Actuator

O **Spring Boot Actuator** adiciona endpoints prontos para monitoramento e gerenciamento da aplicação, como métricas, informações de saúde, entre outros.

### Como usar

1. Adicione a dependência no `pom.xml`:
   ```xml
   <!-- Adiciona o Actuator para expor endpoints de monitoramento -->
   <dependency>
     <groupId>org.springframework.boot</groupId>
     <artifactId>spring-boot-starter-actuator</artifactId>
   </dependency>
   ```
2. Para expor métricas para o Prometheus, adicione também:
   ```xml
   <!-- Adiciona o Micrometer Prometheus para exportar métricas no formato Prometheus -->
   <dependency>
     <groupId>io.micrometer</groupId>
     <artifactId>micrometer-registry-prometheus</artifactId>
   </dependency>
   ```
3. No `application.properties`:
   ```
   # Expõe todos os endpoints do actuator, incluindo o prometheus
   management.endpoints.web.exposure.include=*
   management.endpoint.prometheus.enabled=true
   ```
4. Acesse as métricas em: `http://localhost:8080/actuator/prometheus`

- [Documentação oficial](https://docs.spring.io/spring-boot/docs/current/reference/html/actuator.html)
- [Guia de início rápido](https://spring.io/guides/gs/actuator-service/)

---

## Prometheus

O **Prometheus** é uma ferramenta de monitoramento e alerta que coleta e armazena métricas em tempo real.

### Como usar

1. Configure o Prometheus para coletar métricas do endpoint `/actuator/prometheus` da sua aplicação Spring Boot.
2. Exemplo de configuração (`prometheus.yml`):

   ```yaml
   # filepath: d:\GitHub\NewtonPaiva\ArquiteturaAplicacaoWeb\Monitoramento\Monitoring\prometheus.yml
   global:
     scrape_interval: 15s         # Intervalo padrão de coleta de métricas
     evaluation_interval: 15s     # Intervalo de avaliação de regras

   scrape_configs:
     - job_name: prometheus
       static_configs:
         - targets: ['localhost:9090']  # Monitora o próprio Prometheus

     - job_name: 'spring-actuator'
       metrics_path: '/actuator/prometheus' # Caminho das métricas expostas pelo Spring Boot
       scrape_interval: 5s                  # Intervalo de coleta para o Spring Boot
       static_configs:
         - targets: ['host.docker.internal:8080'] # Endereço da aplicação Spring Boot
   ```

   - `scrape_interval`: define de quanto em quanto tempo o Prometheus coleta as métricas.
   - `job_name`: nome do job de monitoramento.
   - `metrics_path`: endpoint onde as métricas estão expostas.
   - `targets`: endereço(s) dos serviços a serem monitorados.

   > **Observação:** O endpoint `/actuator/prometheus` retorna as métricas no formato `openmetrics-text`, que é suportado nativamente pelo Prometheus. No log da aplicação, você pode ver mensagens como:
   > ```
   > DEBUG ... Found 'Content-Type:application/openmetrics-text;version=1.0.0;charset=utf-8' in response
   > DEBUG ... Writing ["# TYPE jvm_compilation_time_ms counter<EOL># HELP jvm_compilation_time_ms ..."]
   > ```
   > Isso indica que as métricas estão sendo expostas corretamente para coleta.

3. Inicie o Prometheus (veja o `docker-compose.yml` deste projeto).

- [Documentação oficial](https://prometheus.io/docs/introduction/overview/)
- [Guia de início rápido](https://prometheus.io/docs/prometheus/latest/getting_started/)

---

## Grafana

O **Grafana** é uma plataforma de visualização de métricas, permitindo criar dashboards a partir dos dados coletados pelo Prometheus.

### Como usar

1. Acesse o Grafana em `http://localhost:3000` (usuário e senha padrão: `admin`).
2. Adicione o Prometheus como fonte de dados (`http://prometheus:9090`).
3. Crie dashboards para visualizar as métricas da aplicação.

- [Documentação oficial](https://grafana.com/docs/grafana/latest/)
- [Guia de início rápido](https://grafana.com/docs/grafana/latest/getting-started/getting-started-prometheus/)

---

## Subindo Prometheus e Grafana com Docker Compose

Utilize o arquivo `docker-compose.yml` abaixo para subir rapidamente o Prometheus e o Grafana:

```yaml
# filepath: d:\GitHub\NewtonPaiva\ArquiteturaAplicacaoWeb\Monitoramento\Monitoring\docker-compose.yml
version: "3.7"

services:
  prometheus:
    image: prom/prometheus:latest
    container_name: prometheus
    volumes:
      - ./prometheus.yml:/etc/prometheus/prometheus.yml # Monta o arquivo de configuração do Prometheus
    ports:
      - "9090:9090" # Expõe a porta do Prometheus

  grafana:
    image: grafana/grafana:latest
    container_name: grafana
    ports:
      - "3000:3000" # Expõe a porta do Grafana
    environment:
      - GF_SECURITY_ADMIN_PASSWORD=admin # Define a senha do admin
    volumes:
      - grafana-storage:/var/lib/grafana # Volume para persistência dos dados do Grafana

volumes:
  grafana-storage:
```

- O serviço `prometheus` sobe o Prometheus já configurado para coletar métricas do Spring Boot.
- O serviço `grafana` sobe o Grafana pronto para ser acessado e configurado.
- O volume `grafana-storage` garante que as configurações e dashboards do Grafana não sejam perdidos ao reiniciar o container.

Após executar `docker-compose up -d`, acesse:
- Prometheus: [http://localhost:9090](http://localhost:9090)
- Grafana: [http://localhost:3000](http://localhost:3000)  
  **Usuário:** `admin`  
  **Senha:** `admin`

Para acessar as métricas do Spring Boot expostas pelo Actuator:
- [http://localhost:8080/actuator/prometheus](http://localhost:8080/actuator/prometheus)
(Certifique-se que sua aplicação Spring Boot está rodando na porta 8080)

### Como acessar e configurar o Grafana

1. Acesse [http://localhost:3000](http://localhost:3000) no navegador.
2. Faça login com usuário/senha: **admin**.
3. No menu lateral, clique em **Configurações** (ícone de engrenagem) > **Data Sources**.
4. Clique em **Add data source**, escolha **Prometheus**.
5. Em **URL**, coloque: `http://prometheus:9090` e clique em **Save & Test**.
6. Agora você pode criar dashboards clicando em **+** > **Dashboard** e adicionando painéis com as métricas desejadas.

---

## Resumo do fluxo

1. **Spring Boot Actuator** expõe métricas.
2. **Prometheus** coleta essas métricas.
3. **Grafana** visualiza os dados coletados.

---

## Exemplo de uso

Suponha que você tenha uma aplicação Spring Boot rodando na porta 8080.  
Após configurar tudo conforme as instruções acima:

- Acesse [http://localhost:8080/actuator/prometheus](http://localhost:8080/actuator/prometheus) para ver as métricas expostas.
- No Prometheus ([http://localhost:9090](http://localhost:9090)), pesquise por métricas como `jvm_memory_used_bytes` ou `http_server_requests_seconds_count`.
- No Grafana ([http://localhost:3000](http://localhost:3000)), crie um painel e adicione, por exemplo, o gráfico da métrica `process_cpu_usage` para visualizar o uso de CPU da sua aplicação em tempo real.

Assim, você terá um monitoramento completo do seu serviço Spring Boot utilizando Actuator, Prometheus e Grafana!

---

## Dashboards prontos do Grafana

O Grafana possui uma grande variedade de dashboards prontos, criados pela comunidade, que podem ser facilmente importados para o seu ambiente.

### Como obter e importar dashboards prontos

1. Acesse o site oficial de dashboards do Grafana:  
   [https://grafana.com/grafana/dashboards](https://grafana.com/grafana/dashboards)
2. Pesquise por dashboards relacionados a "Spring Boot", "Micrometer", "JVM", "Prometheus", etc.
3. Copie o **ID** do dashboard desejado (por exemplo, `4701` para o dashboard "Spring Boot Statistics").
4. No Grafana, clique no menu lateral em **+** > **Import**.
5. Cole o ID do dashboard ou faça upload do arquivo `.json` baixado.
6. Selecione a fonte de dados Prometheus e clique em **Import**.

### Exemplos de dashboards úteis

- [Spring Boot Statistics (ID: 4701)](https://grafana.com/grafana/dashboards/4701-spring-boot-statistics/)
- [JVM Micrometer (ID: 4701)](https://grafana.com/grafana/dashboards/4701)
- [Prometheus 2.0 Stats (ID: 3662)](https://grafana.com/grafana/dashboards/3662)

Esses dashboards já vêm prontos para uso e exibem diversas métricas importantes da sua aplicação Spring Boot.

---

## Explicação dos arquivos de configuração

### application.properties

```properties
# filepath: d:\GitHub\NewtonPaiva\ArquiteturaAplicacaoWeb\Monitoramento\Monitoring\src\main\resources\application.properties
# Expõe todos os endpoints do actuator, incluindo prometheus, info, health, metrics, loggers e mappings
management.endpoints.web.exposure.include=*
# Habilita o endpoint prometheus
management.endpoint.prometheus.enabled=true
# Habilita outros endpoints úteis para monitoramento e troubleshooting
management.endpoint.info.enabled=true
management.endpoint.health.enabled=true
management.endpoint.metrics.enabled=true
management.endpoint.loggers.enabled=true
management.endpoint.mappings.enabled=true
```
- Este arquivo garante que todos os endpoints do actuator estejam disponíveis para consulta, especialmente o `/actuator/prometheus`, que será consumido pelo Prometheus.

---

### prometheus.yml

```yaml
# filepath: d:\GitHub\NewtonPaiva\ArquiteturaAplicacaoWeb\Monitoramento\Monitoring\prometheus.yml
global:
  scrape_interval: 15s         # Intervalo padrão de coleta de métricas
  evaluation_interval: 15s     # Intervalo de avaliação de regras

scrape_configs:
  - job_name: prometheus
    static_configs:
      - targets: ['localhost:9090']  # Monitora o próprio Prometheus

  - job_name: 'spring-actuator'
    metrics_path: '/actuator/prometheus' # Caminho das métricas expostas pelo Spring Boot
    scrape_interval: 5s                  # Intervalo de coleta para o Spring Boot
    static_configs:
      - targets: ['host.docker.internal:8080'] # Endereço da aplicação Spring Boot
```
- O Prometheus coleta métricas do próprio serviço e da aplicação Spring Boot.
- O endpoint `/actuator/prometheus` é consultado a cada 5 segundos.
- `host.docker.internal:8080` permite que o Prometheus (em container) acesse a aplicação rodando no host.

---

### docker-compose.yml

```yaml
# filepath: d:\GitHub\NewtonPaiva\ArquiteturaAplicacaoWeb\Monitoramento\Monitoring\docker-compose.yml
version: "3.7"

services:
  prometheus:
    image: prom/prometheus:latest
    container_name: prometheus
    volumes:
      - ./prometheus.yml:/etc/prometheus/prometheus.yml # Monta o arquivo de configuração do Prometheus
    ports:
      - "9090:9090" # Expõe a porta do Prometheus

  grafana:
    image: grafana/grafana:latest
    container_name: grafana
    ports:
      - "3000:3000" # Expõe a porta do Grafana
    environment:
      - GF_SECURITY_ADMIN_PASSWORD=admin # Define a senha do admin
    volumes:
      - grafana-storage:/var/lib/grafana # Volume para persistência dos dados do Grafana

volumes:
  grafana-storage:
```
- Sobe dois containers: um para o Prometheus e outro para o Grafana.
- O Prometheus já está configurado para buscar métricas do Spring Boot.
- O Grafana armazena dashboards e configurações em um volume persistente.

---

## Como tudo funciona junto

1. **Spring Boot Actuator** expõe métricas detalhadas da aplicação em `/actuator/prometheus`.
2. **Prometheus** acessa esse endpoint periodicamente e armazena as métricas.
3. **Grafana** se conecta ao Prometheus como fonte de dados e permite criar dashboards para visualização dessas métricas.

---

## Como criar e visualizar alertas no Prometheus e Grafana

### 1. Criando alertas no Prometheus

- Os alertas do Prometheus são definidos em arquivos de regras (`alert.rules.yml`) e aparecem apenas na interface do Prometheus.

**Passos:**
1. Crie o arquivo `alert.rules.yml` com suas regras de alerta, por exemplo:
   ```yaml
   groups:
     - name: exemplo-alertas
       rules:
         - alert: AlertaSempreAtivo
           expr: vector(1) == 1
           for: 10s
           labels:
             severity: info
           annotations:
             summary: "Alerta de teste sempre ativo"
             description: "Este alerta é apenas para teste e estará sempre ativo."
   ```
2. No seu `prometheus.yml`, adicione ou garanta que existe:
   ```yaml
   rule_files:
     - "alert.rules.yml"
   ```
3. No `docker-compose.yml`, monte o arquivo de regras no container:
   ```yaml
   volumes:
     - ./prometheus.yml:/etc/prometheus/prometheus.yml
     - ./alert.rules.yml:/etc/prometheus/alert.rules.yml
   ```
4. Reinicie o Prometheus:
   ```
   docker-compose down
   docker-compose up -d
   ```
5. Acesse [http://localhost:9090/alerts](http://localhost:9090/alerts) para visualizar os alertas ativos.

---

### 2. Visualizando e criando alertas no Grafana

- O Grafana **não lê automaticamente** os alertas do Prometheus. Para visualizar alertas no Grafana, você deve criar alertas visuais nos painéis dos dashboards.

**Passos:**
1. Acesse o Grafana em [http://localhost:3000](http://localhost:3000) (usuário/senha: admin).
2. Certifique-se de que o Prometheus está configurado como fonte de dados.
3. Crie um novo dashboard e adicione um painel.
4. No painel, insira a consulta PromQL usada no seu alerta, por exemplo: `vector(1) == 1` ou `process_cpu_usage`.
5. Clique na aba **Alert** (ou "Alertas") e depois em **Create Alert**.
6. Defina a condição do alerta (ex: "is above 0" para o alerta de teste).
7. Configure notificações (Slack, e-mail, etc.) se desejar.
8. Salve o painel e o dashboard.
9. Os alertas criados aparecerão em **Alerting > Alert rules** no menu do Grafana.

---

### 3. Resumo das diferenças

- **Alertas do Prometheus:**  
  - Definidos em arquivos de regras.
  - Visualizados apenas na interface do Prometheus (`/alerts`).
  - Não aparecem automaticamente no Grafana.

- **Alertas do Grafana:**  
  - Criados diretamente nos painéis dos dashboards.
  - Visualizados e gerenciados no próprio Grafana.
  - Permitem notificações e integrações com outros sistemas.

---

> **Dica:** Use o Prometheus para alertas automáticos e o Grafana para visualização centralizada e alertas visuais/notificações customizadas.

---

## Como visualizar logs no Spring Boot

O Spring Boot já gera logs automaticamente. Para visualizar logs de requisições, erros e métricas:

- Os logs aparecem no console onde a aplicação está rodando.
- Você pode configurar o nível de log em `application.properties`:

```properties
# ...existing code...
logging.level.root=INFO
logging.level.org.springframework.web=DEBUG
logging.level.org.springframework.boot.actuate=DEBUG
# ...existing code...
```

- Para logs customizados, use o logger no seu código Java:

```java
// Exemplo de log em um controller Spring Boot
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
public class ExemploController {
    private static final Logger logger = LoggerFactory.getLogger(ExemploController.class);

    @GetMapping("/exemplo")
    public String exemplo() {
        logger.info("Endpoint /exemplo foi acessado");
        return "ok";
    }
}
```

---

## Observação importante sobre alertas no Grafana

- **Alertas criados no Prometheus** (via `alert.rules.yml`) aparecem apenas na interface do Prometheus ([http://localhost:9090/alerts](http://localhost:9090/alerts)).
- **Alertas do Grafana** são criados e gerenciados dentro do próprio Grafana, em dashboards ou via Alerting > Alert rules.

### Se você não vê regras em "Grafana-managed" ou "Data source-managed":

- Isso é esperado se você ainda **não criou alertas no próprio Grafana**.
- Os alertas do Prometheus não aparecem automaticamente na lista de alertas do Grafana.
- Para criar alertas no Grafana:
  1. Crie um painel em um dashboard.
  2. Clique em **Alert** (ou "Alertas") e depois em **Create Alert**.
  3. Defina a condição, a métrica (ex: `vector(1) == 1`), e configure notificações se desejar.
  4. Salve o painel e o dashboard.

- Após criar um alerta no painel, ele aparecerá na seção "Grafana-managed" ou "Data source-managed" do menu de alertas do Grafana.

> **Resumo:**  
> - Alertas do Prometheus são visualizados no Prometheus.  
> - Alertas do Grafana são criados e visualizados no próprio Grafana, a partir dos dashboards e painéis.

