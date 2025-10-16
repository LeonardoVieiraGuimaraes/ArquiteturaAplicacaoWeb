# Climatempo (Spring Boot) — Integração com API externa

Guia didático para consumir a API do Climatempo com Spring Boot, usando RestTemplate e variáveis de ambiente.

## Índice
- Visão geral
- Requisitos
- [Início Rápido](#-início-rápido)
- Configuração de variáveis (.env)
- Endpoints da aplicação
- Exemplos de uso (curl/Postman)
- Código por dentro (Controller e Service)
- Erros comuns e troubleshooting
- Ideias de testes
- Exercícios propostos
- Roteiro de aula (demo script)
- Referências

## Visão geral
Este exemplo mostra como integrar um serviço Spring Boot com a API pública do Climatempo:
- Busca de análise sinóptica por país (ex.: BR)
- Busca de dados climáticos por ID de localidade

Estrutura principal:
- `ClimateController` expõe endpoints REST sob `/climate`
- `ClimateService` monta as URLs e chama a API externa usando `RestTemplate`
- Chave de API lida via variável de ambiente `CLIMATEMPO_API_KEY` (carregada por `.env` via spring-dotenv)

## Requisitos
- JDK 22 (projeto usa `<java.version>22</java.version>`)
- Maven (use o wrapper fornecido: `mvnw`/`mvnw.cmd`)
- Chave de API Climatempo válida (token)

## 🚀 Início Rápido
1) Crie um arquivo `.env` na raiz do projeto com sua chave de API (detalhes abaixo).
2) Inicie a aplicação:
   - Windows PowerShell: `.\\mvnw.cmd spring-boot:run`
3) Acesse no navegador ou via curl:
   - `http://localhost:8080/climate/BR`
   - `http://localhost:8080/climate/rain/6879`

Porta padrão: 8080.

## Configuração de variáveis (.env)
O projeto usa a dependência `spring-dotenv` para carregar automaticamente variáveis definidas em um arquivo `.env`.

Crie o arquivo `.env` na pasta do projeto `climatempo` com o conteúdo:
```
CLIMATEMPO_API_KEY=SEU_TOKEN_AQUI
```
A propriedade `application.properties` referencia: `climatempo.api.key=${CLIMATEMPO_API_KEY}`.

Dicas:
- Após criar ou alterar o `.env`, reinicie a aplicação.
- Evite versionar `.env` com tokens reais.

## Endpoints da aplicação
- GET `/climate/{country}`
  - Ex.: `/climate/BR` — retorna análise sinóptica do Brasil
- GET `/climate/rain/{id}`
  - Ex.: `/climate/rain/6879` — retorna dados de clima/temperatura da localidade com ID 6879

Observação: os dados retornados são JSON bruto da API externa (via Jackson `JsonNode`). O formato pode variar conforme a API do Climatempo.

## Exemplos de uso (curl/Postman)
- Sinóptica por país (BR):
  - `curl -s http://localhost:8080/climate/BR | jq`
- Clima/temperatura por localidade (ex.: 6879):
  - `curl -s http://localhost:8080/climate/rain/6879 | jq`

Se preferir Postman/Insomnia, importe duas requisições GET para as URLs acima.

## Código por dentro (Controller e Service)
- Controller: `com.api.climatempo.controller.ClimateController`
  - Base path: `/climate`
  - Métodos:
    - `GET /{country}` -> `weatherService.getClimate(country)`
    - `GET /rain/{id}` -> `weatherService.getClimateRain(id)`
- Service: `com.api.climatempo.service.ClimateService`
  - Usa `RestTemplate` para chamadas HTTP e `ObjectMapper` para parse JSON
  - Monta URLs com base em:
    - `apiUrl` (via `@Value("${climatempo.api.url}")`, default configurado em `application.properties`)
    - `apiKey` (via `@Value("${climatempo.api.key}")`)
  - Métodos principais:
    - `getClimate(country)`: `GET {apiUrl}/anl/synoptic/locale/{country}?token={apiKey}`
    - `getClimateRain(id)`: `GET {apiUrl}/climate/temperature/locale/{id}?token={apiKey}`

Sugestões de melhoria no código:
- Tratar exceções e status não-2xx com `ResponseEntity` e `@ControllerAdvice`
- Adotar `WebClient` (Reator) para IO não bloqueante quando apropriado

## Erros comuns e troubleshooting
- 401/403: token ausente/ inválido
  - Verifique `.env` e reinicie a aplicação
  - Cheque se o token expirou
- 404/Resposta vazia: country/ID inválido ou recurso indisponível
  - Teste diretamente a URL da API externa (com `token`) para validar
- 429: rate limit atingido
  - Reduza frequência de chamadas e implemente retry/backoff
- `.env` não lido
  - Confirme que o arquivo está na raiz do módulo (`climatempo/.env`)
  - Reinicie a aplicação após alterações

## Ideias de testes
- Unitários de Service com `MockRestServiceServer` (simula respostas da API externa)
- Testes de integração do Controller com `@SpringBootTest` + `TestRestTemplate`
- Casos de erro: status 4xx/5xx, JSON inválido

## Exercícios propostos
1) Extrair `apiUrl` para `application.properties` e ler com `@Value`
2) Criar um endpoint que liste cidades por nome e retorne IDs de localidade (usar endpoints públicos do Climatempo)
3) Migrar de `RestTemplate` para `WebClient` e comparar desempenho
4) Implementar cache simples (ex.: `@Cacheable`) para reduzir chamadas repetidas
5) Padronizar respostas de erro com `@ControllerAdvice`

## Roteiro de aula (demo script)
1) Mostrar `.env` e iniciar a aplicação
2) Chamar `/climate/BR` e `/climate/rain/{id}` mostrando JSON
3) Abrir `ClimateService` e explicar como a URL é montada
4) Introduzir melhorias (config externa, tratamento de erros)
5) Propor e acompanhar um dos exercícios (ex.: WebClient)

## Referências
- API Climatempo (documentação pública)
- Spring Boot Web (RestTemplate/WebClient)
- Jackson (JsonNode/ObjectMapper)
- spring-dotenv (carregar `.env`)