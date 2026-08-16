# Arquitetura de Aplicações Web

Material didático de Arquitetura de Aplicações Web — o conteúdo que separa "a aplicação
funciona" de "a aplicação está pronta para produção".

## O percurso completo, em oito etapas

A pasta `022024` segue a ordem da disciplina, e cada número acrescenta uma preocupação
real de sistema em produção:

| # | Etapa | O que entra |
| :-- | :--- | :--- |
| 01 | `hello` | Primeiro projeto |
| 02 | `api` | Exposição de recursos via REST |
| 03 | `crud` | Persistência |
| 04 | `trs` | Camada de transporte e serviços |
| 05 | **`Seguranca`** | Autenticação e autorização |
| 06 | **`Documentacao`** | OpenAPI/Swagger e JavaDoc |
| 07 | **`Monitoramento`** | Observabilidade da aplicação |
| 08 | **`Hospedagem`** | Publicação |

E ainda:

- **`Tests_JUnit_Mockito`** — testes de unidade com mock
- **`gatling-maven-plugin-demo-java-main`** — **teste de carga com Gatling**
- `ArquiteturaMicrosservico` — decomposição em serviços
- `ConstrucaoServicoWeb` — construção de serviços passo a passo
- `Documentacao/Documentation_JWT_RestAPI` — API REST autenticada, documentada

## Por que este material é diferente

A maior parte do ensino de web para por volta da etapa 03: o CRUD funciona, o semestre
acaba. Segurança, documentação, monitoramento, testes e teste de carga ficam para "quando
você trabalhar".

Aqui as cinco entram como conteúdo. Teste de carga com Gatling em disciplina de graduação
é incomum — e é justamente o que o aluno vai encontrar na primeira semana de trabalho.

## Como rodar

Java 17+, Maven via wrapper:

```bash
cd 022024/03\ -\ crud
./mvnw spring-boot:run
```

Para o teste de carga:

```bash
cd 022024/gatling-maven-plugin-demo-java-main
./mvnw gatling:test
```

---

## Autor

**Leonardo Vieira Guimarães** — desenvolvedor backend e Product Owner no IMA.
Mestre em Modelagem Computacional e Sistemas (UNIMONTES), doutorando em Modelagem
Matemática e Computacional (CEFET-MG).

[![Portfólio](https://img.shields.io/badge/Portf%C3%B3lio-leoproti.com.br-0A0A0A?style=flat)](https://leoproti.com.br)
[![ORCID](https://img.shields.io/badge/ORCID-0009--0000--3118--4664-A6CE39?style=flat&logo=orcid&logoColor=white)](https://orcid.org/0009-0000-3118-4664)
[![LinkedIn](https://img.shields.io/badge/LinkedIn-perfil-0A66C2?style=flat&logo=linkedin&logoColor=white)](https://www.linkedin.com/in/leonardo-vieira-guimaraes/)
