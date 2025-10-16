# FIPE (Spring Boot) — Integração com API externa

Guia didático para consumir a API pública FIPE (Parallelum) com Spring Boot, usando RestTemplate.

## Índice
- Visão geral
- Requisitos
- [Início Rápido](#-início-rápido)
- Endpoints da aplicação
- Exemplos (curl/Postman)
- Código por dentro (Controller e Service)
- Erros comuns e troubleshooting
- Ideias de testes
- Exercícios propostos
- Roteiro de aula
- Referências

## Visão geral
Este exemplo mostra uma API Spring Boot que atua como "proxy" para a API FIPE (carros). Os endpoints internos expõem:
- Lista de marcas
- Modelos por marca
- Anos por marca+modelo
- Valor por marca+modelo+ano

Estrutura principal:
- `FipeController` expõe endpoints REST
- `FipeService` chama a API FIPE usando `RestTemplate`

## Requisitos
- JDK 22
- Maven (use o wrapper `mvnw`/`mvnw.cmd`)

## 🚀 Início Rápido
1) Inicie a aplicação:
   - Windows PowerShell: `.\\mvnw.cmd spring-boot:run`
2) Acesse:
   - `http://localhost:8080/marcas`
   - `http://localhost:8080/modelos/59` (ex.: marca 59 = Honda)
   - `http://localhost:8080/anos/59/5940` (marca 59, modelo 5940)
   - `http://localhost:8080/valor/59/5940/2016-1` (marca 59, modelo 5940, ano 2016 gasolina)

Porta padrão: 8080.

## Endpoints da aplicação
- GET `/marcas` — lista de marcas
- GET `/modelos/{marca}` — modelos de uma marca (id da marca, inteiro)
- GET `/anos/{marca}/{modelo}` — anos para um modelo
- GET `/valor/{marca}/{modelo}/{ano}` — valor para marca+modelo+ano (ano no formato `YYYY-<combustivelIndex>` ex.: `2016-1`)

Observação: a API FIPE retorna JSON em formatos diferentes para cada endpoint. Este serviço retorna os JSONs brutos da FIPE (String).

## Exemplos (curl/Postman)
- Marcas:
  - `curl -s http://localhost:8080/marcas | jq`
- Modelos (marca 59):
  - `curl -s http://localhost:8080/modelos/59 | jq`
- Anos (marca 59, modelo 5940):
  - `curl -s http://localhost:8080/anos/59/5940 | jq`
- Valor (marca 59, modelo 5940, ano 2016-1):
  - `curl -s http://localhost:8080/valor/59/5940/2016-1 | jq`

## Código por dentro (Controller e Service)
- Controller: `com.api.fipe.controller.FipeController`
  - Injeta `FipeService` via construtor (boa prática de DI)
  - Mapeamentos:
    - `GET /marcas` -> `consultarMarcas()`
    - `GET /modelos/{marca}` -> `consultarModelos(marca)`
    - `GET /anos/{marca}/{modelo}` -> `consultarAnos(marca, modelo)`
    - `GET /valor/{marca}/{modelo}/{ano}` -> `consultarValor(marca, modelo, ano)`
- Service: `com.api.fipe.service.FipeService`
  - Anotado com `@Service` e com `@Value("${fipe.api.base-url:https://parallelum.com.br/fipe/api/v1}")` para configurar a base URL
  - Método utilitário `consultarURL(apiUrl)` cria `RestTemplate`, faz `GET` e retorna `String` do corpo quando 2xx
  - Endpoints FIPE construídos a partir de `${fipe.api.base-url}`:
    - `${fipe.api.base-url}/carros/marcas`
    - `${fipe.api.base-url}/carros/marcas/{id}/modelos`
    - `${fipe.api.base-url}/carros/marcas/{marca}/modelos/{modelo}/anos`
    - `${fipe.api.base-url}/carros/marcas/{marca}/modelos/{modelo}/anos/{ano}`

Sugestões de melhoria:
- Tratar erros e padronizar respostas (ex.: mapear 4xx/5xx em mensagens JSON)
- Usar `WebClient` para chamadas não-bloqueantes quando fizer sentido

## Erros comuns e troubleshooting
- 404: IDs de marca/modelo/ano inválidos
  - Verifique os IDs válidos consultando primeiro `/marcas` e `/modelos/{marca}`
- Falha de DNS ou rede ao chamar parallelum.com.br
  - Teste acesso externo via navegador ou `curl` direto na URL da FIPE
- Resposta inesperada da FIPE (mudança de schema)
  - Atualize o código para refletir novo formato

## Ideias de testes
- Testes unitários do `FipeService` com `MockRestServiceServer`
- Testes de integração dos endpoints (MockMvc)
- Casos de erro (HTTP não-2xx)

## Exercícios propostos
1) Refatorar para usar injeção de dependência: anotar `FipeService` com `@Service` e injetar no controller
2) Adicionar suporte a motos e caminhões (trocar `carros` por `motos` ou `caminhoes`)
3) Implementar cache para listas estáticas (`/marcas`)
4) Adicionar paginação/filtragem no retorno de modelos
5) Adotar `WebClient` e medir diferenças

## Roteiro de aula
1) Rodar a aplicação e explorar `/marcas` → `/modelos/{marca}` → `/anos/{marca}/{modelo}` → `/valor/{marca}/{modelo}/{ano}`
2) Abrir `FipeService` e inspecionar chamadas HTTP
3) Discutir melhorias (injeção, config externa, tratamento de erros)
4) Propor exercício 1 (injeção) e testar

## Referências
- API FIPE (Parallelum): https://deividfortuna.github.io/fipe/
- Spring Boot Web (RestTemplate/WebClient)
- Testes com MockMvc e MockRestServiceServer