# Monitoramento Completo: Spring Boot + Micrometer + Prometheus + Grafana

Este módulo demonstra um setup completo de monitoramento técnico e de negócio para uma API de pedidos.

## O que foi implementado
- Actuator com endpoint `/actuator/prometheus`
- Métricas técnicas automáticas (JVM, HTTP) e métricas de negócio:
  - `business_pedidos_criados_total`
  - `business_pedidos_valor` (DistributionSummary)
  - `business_pedidos_processamento` (Timer)
- Logs estruturados em JSON com MDC (`pedidoId`, `usuario`)
- Docker Compose com Prometheus e Grafana (datasource provisionado)
- Regras de alerta no Prometheus

## Como rodar
1) Inicie a aplicação Spring Boot (porta 8080):

```powershell
# Na pasta demo
./mvnw.cmd spring-boot:run
```

2) Suba Prometheus e Grafana via Docker:

```powershell
# Em outra janela na pasta demo
docker compose up -d
```

3) Gere tráfego

```powershell
curl -X POST http://localhost:8080/pedidos ^
  -H "Content-Type: application/json" ^
  -d '{"valor": 123.45, "usuario": "alice"}'
```

4) Acesse as ferramentas
- Prometheus: http://localhost:9090
- Grafana: http://localhost:3000 (admin/admin)
  - Data source Prometheus já provisionado (http://prometheus:9090)
  - Você pode importar dashboards prontos (IDs 4701 ou 6756)

## Prometheus
Arquivo: `prometheus/prometheus.yml`
- Scrape do app em `host.docker.internal:8080/actuator/prometheus` (Windows/Mac com Docker Desktop)
- Regras de alerta em `prometheus/alert.rules.yml`:
  - Latência alta no `/pedidos`
  - >5% de erros 5xx
  - Queda na criação de pedidos

Exemplos de queries:
- Taxa de 5xx: `sum(rate(http_server_requests_seconds_count{status=~"5.."}[5m]))`
- Latência média de `/pedidos`: `sum(rate(http_server_requests_seconds_sum{uri="/pedidos"}[5m])) / sum(rate(http_server_requests_seconds_count{uri="/pedidos"}[5m]))`
- Pedidos por segundo: `rate(business_pedidos_criados_total[5m])`

## Logs estruturados
- Configuração em `src/main/resources/logback-spring.xml`
- Envia JSON para stdout, incluindo MDC com `pedidoId` e `usuario`
- Integração com Loki/ELK pode ser feita apontando o coletor para os logs do container da app

## Endpoints úteis
- POST `/pedidos` { valor, usuario }
- GET `/pedidos`
- GET `/pedidos/{id}`
- GET `/actuator/health`
- GET `/actuator/prometheus`

## Notas
- Ajuste os thresholds de alerta conforme sua realidade
- Em Linux sem `host.docker.internal`, substitua o target do Prometheus pelo IP da sua máquina
