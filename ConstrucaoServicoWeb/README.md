# ConstrucaoServicoWeb — Integração com APIs externas (Guia Geral)

Este diretório reúne exemplos de construção de serviços Web em Spring Boot consumindo APIs externas. Cada exemplo possui um README didático próprio seguindo a mesma estrutura: visão geral, início rápido, endpoints, exemplos de uso, explicação do código, exercícios e troubleshooting.

## Projetos

- exemplo01/climatempo
  - Integração com a API do Climatempo usando RestTemplate e variável de ambiente para token.
  - README: `exemplo01/climatempo/README.md`
  - Quick Start (Windows PowerShell):
    - Criar `.env` com `CLIMATEMPO_API_KEY`
    - Executar:
      ```pwsh
      .\mvnw.cmd spring-boot:run -f .\exemplo01\climatempo\pom.xml
      ```
    - Acessar:
      - `http://localhost:8080/climate/BR`
      - `http://localhost:8080/climate/rain/6879`

- exemplo02/fipe
  - Proxy para API FIPE (Parallelum) com endpoints para marcas, modelos, anos e valor.
  - README: `exemplo02/fipe/README.md`
  - Quick Start (Windows PowerShell):
    ```pwsh
    .\mvnw.cmd spring-boot:run -f .\exemplo02\fipe\pom.xml
    ```
    - Acessar:
      - `http://localhost:8080/marcas`
      - `http://localhost:8080/modelos/59`
      - `http://localhost:8080/anos/59/5940`
      - `http://localhost:8080/valor/59/5940/2016-1`

## Padrão adotado nos READMEs
- Visão geral do problema e da solução
- Requisitos e início rápido
- Endpoints expostos com exemplos (curl/Postman)
- Explicação do código (Controller/Service)
- Erros comuns e troubleshooting
- Ideias de testes e exercícios práticos
- Roteiro sugerido para aula/demonstração

## Dicas gerais
- Use `.env.example` quando houver variáveis sensíveis (ex.: `CLIMATEMPO_API_KEY`)
- Prefira injeção de dependência (`@Service`, construtores) em vez de instanciar serviços manualmente
- Considere `WebClient` para chamadas não-bloqueantes
- Padronize tratamento de erros (2xx/4xx/5xx) e respostas JSON

## Próximos passos
- Adicionar testes automatizados por módulo (MockMvc, MockRestServiceServer)
- Externalizar URLs-base das APIs em `application.properties`
- Implementar cache para reduzir chamadas repetidas quando fizer sentido
