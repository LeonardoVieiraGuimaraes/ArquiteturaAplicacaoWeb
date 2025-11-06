# Segurança — Guia rápido

Este README fornece um panorama prático sobre práticas de segurança para aplicações Java (ou outras plataformas) e instruções de integração com SonarQube e JaCoCo para análise estática e cobertura de testes.

## Objetivo

- Explicar conceitos essenciais de segurança (autenticação, autorização, criptografia, validação, gestão de segredos, logging). 
- Mostrar como instrumentar o projeto para análise estática com SonarQube e para medir cobertura com JaCoCo.

## Princípios de segurança (visão geral)

1. Autenticação
   - Use protocolos seguros (OAuth2 / OpenID Connect) quando possível.
   - Prefira provedores bem testados (Keycloak, Auth0, AzureAD) a implementações caseiras.

2. Autorização
   - Aplique o princípio do menor privilégio.
   - Use controle baseado em papéis (RBAC) ou políticas mais finas (ABAC) quando necessário.

3. Validação de entrada
   - Valide e sanitize todos os dados vindos do cliente.
   - Proteja contra injeção (SQL, XSS, comandos) usando prepared statements e frameworks que façam escape automático.

4. Criptografia
   - Use TLS (HTTPS) para transporte de dados.
   - Proteja dados sensíveis em repouso com algoritmos modernos e chaves gerenciadas.

5. Gestão de segredos
   - Não versionar segredos (API keys, senhas, certificados).
   - Use vaults ou serviços gerenciados (Vault, AWS Secrets Manager, Azure Key Vault).

6. Logging e monitoramento
   - Logar tentativas de autenticação, erros e eventos críticos, sem expor dados sensíveis.
   - Configure alertas para padrões anômalos.

7. Dependências e atualizações
   - Escaneie dependências por vulnerabilidades regularmente (Dependabot, Snyk, OWASP Dependency-Check).

8. OWASP Top 10
   - Familiarize-se com o OWASP Top 10 e aplique mitigations adequadas ao ciclo de desenvolvimento.

---

## SonarQube

SonarQube é uma plataforma de análise contínua de qualidade de código que detecta bugs, code smells, duplicações e problemas de segurança estática.

O essencial para integrar:

- Instalar/ter um servidor SonarQube (ou usar SonarCloud).
- Criar token de autorização para o projeto.
- Executar análise após build/test e publicar o relatório no servidor.

Exemplo (Maven):

- Instalar o plugin do Sonar no `pom.xml` não é necessário; basta usar o scanner do Maven:

Comando (PowerShell / Windows):

```
mvn clean verify sonar:sonar -Dsonar.host.url=https://seu-sonarqube -Dsonar.login=SEU_TOKEN
```

Exemplo mínimo `sonar-project.properties` (quando usar scanner standalone):

```
sonar.projectKey=meu-projeto
sonar.projectName=Meu Projeto
sonar.sources=src/main/java
sonar.tests=src/test/java
sonar.java.binaries=target/classes
sonar.host.url=https://seu-sonarqube
sonar.login=SEU_TOKEN
```

Recomendações SonarQube:
- Configure Quality Gates (p.ex. cobertura mínima, zero vulnerabilidades críticas).
- Integre a análise na pipeline CI (executar após testes e geração dos relatórios de cobertura).

---

## JaCoCo (Cobertura de testes)

JaCoCo é um plugin de cobertura para JVM que gera relatórios (HTML e XML). O SonarQube consome o relatório XML para calcular métricas de cobertura.

Maven (exemplo no `pom.xml` — snippet):

```xml
<build>
  <plugins>
    <plugin>
      <groupId>org.jacoco</groupId>
      <artifactId>jacoco-maven-plugin</artifactId>
      <version>0.8.8</version>
      <executions>
        <execution>
          <goals>
            <goal>prepare-agent</goal>
          </goals>
        </execution>
        <execution>
          <id>report</id>
          <phase>verify</phase>
          <goals>
            <goal>report</goal>
          </goals>
        </execution>
        <!-- Gera o XML para SonarQube -->
        <execution>
          <id>report-xml</id>
          <phase>verify</phase>
          <goals>
            <goal>report</goal>
          </goals>
          <configuration>
            <reports>
              <report>xml</report>
            </reports>
          </configuration>
        </execution>
      </executions>
    </plugin>
  </plugins>
</build>
```

Comando (Windows PowerShell):

```
mvn clean verify
```

O relatório XML normalmente fica em `target/site/jacoco/jacoco.xml` ou `target/jacoco.exec` dependendo da configuração; configure o Sonar para apontar para o arquivo XML.

Gradle (exemplo `build.gradle`):

```groovy
plugins {
  id 'java'
  id 'jacoco'
}

jacoco {

  toolVersion = '0.8.8'

---

## Testes de carga — Gatling

Gatling é uma ferramenta popular para testes de carga e performance em JVM. Use Gatling para validar comportamento sob alto tráfego, medir tempos de resposta e detectar gargalos.

Pontos principais:
- Crie simulações que representem cenários reais (autenticação, caminhos críticos, picos).
- Execute Gatling em um job separado na pipeline de CI para não atrasar o build principal.
- Armazene os relatórios HTML/CSV/JSON como artefatos do job para investigação.

Exemplo (Maven) — plugin mínimo no `pom.xml`:

```xml
<plugin>
  <groupId>io.gatling</groupId>
  <artifactId>gatling-maven-plugin</artifactId>
  <version>3.9.6</version>
  <executions>
    <execution>
      <goals>
        <goal>test</goal>
      </goals>
    </execution>
  </executions>
</plugin>
```

Comando (Windows PowerShell):

```
mvn -Dgatling.simulationClass=com.meuprojeto.simulations.BasicSimulation gatling:test
```

Os relatórios normalmente ficam em `target/gatling/<simulation-name>-<timestamp>`.

Gradle (plugin):

```groovy
plugins {
  id 'io.gatling.gradle' version '3.9.6'
}

// Exemplo de execução
// ./gradlew gatlingRun -DgatlingSimulation=com.meuprojeto.simulations.BasicSimulation
```

Integração com Sonar/JaCoCo:
- Gatling não gera métricas de cobertura de código; use-o junto com testes unitários para cobertura. Use Gatling para performance e JaCoCo para coverage.
- Execute Gatling após a fase de testes unitários/integrados; guarde relatórios para análise de performance.

---

## Testes unitários e mocks — JUnit + Mockito

JUnit (preferencialmente JUnit 5 - Jupiter) e Mockito são a base para testes unitários em projetos Java.

Pontos principais:
- Escreva testes unitários rápidos e determinísticos com JUnit.
- Use Mockito para mocks e stubs (serviços externos, repositórios, clientes HTTP).
- Execute os testes em cada build; JaCoCo capturará a cobertura.

Exemplo (Maven) — dependências mínimas no `pom.xml`:

```xml
<dependencies>
  <!-- JUnit 5 -->
  <dependency>
    <groupId>org.junit.jupiter</groupId>
    <artifactId>junit-jupiter</artifactId>
    <version>5.9.3</version>
    <scope>test</scope>
  </dependency>
  <!-- Mockito -->
  <dependency>
    <groupId>org.mockito</groupId>
    <artifactId>mockito-core</artifactId>
    <version>5.2.0</version>
    <scope>test</scope>
  </dependency>
</dependencies>
```

Exemplo simples de teste com Mockito (JUnit 5):

```java
@ExtendWith(MockitoExtension.class)
class UsuarioServiceTest {

  @Mock
  private UsuarioRepository repo;

  @InjectMocks
  private UsuarioService service;

  @Test
  void deveRetornarUsuarioQuandoExistir() {
    when(repo.findById(1L)).thenReturn(Optional.of(new Usuario(1L, "Leo")));
    var u = service.findById(1L);
    assertNotNull(u);
    assertEquals("Leo", u.getNome());
  }
}
```

Comando (Windows PowerShell) para rodar testes e gerar cobertura:

```
mvn clean verify
```

JaCoCo e Sonar
- JaCoCo instrumenta testes e gera o XML que o Sonar consome para métricas de cobertura.
- Configure o Sonar para apontar ao `jacoco.xml` conforme mostrado na seção do JaCoCo.

---

## Como combinar tudo numa pipeline (sugestão)

Job 1 — Build & Unit Tests
- mvn clean verify (executa testes JUnit/Mockito e gera relatório JaCoCo)

Job 2 — Análise estática
- Executa SonarQube scanner apontando para o relatório JaCoCo XML.

Job 3 — Testes de carga (opcional)
- Executa Gatling em uma máquina dedicada ou runner; armazene relatórios como artefatos.

Exemplo resumido de execução Sonar após cobertura (Maven):

```
mvn clean verify sonar:sonar -Dsonar.host.url=https://seu-sonarqube -Dsonar.login=SEU_TOKEN -Dsonar.coverage.jacoco.xmlReportPaths=target/site/jacoco/jacoco.xml
```

---

Se quiser, eu posso:
- Gerar um `pom.xml` exemplo que junte JaCoCo, dependências de teste (JUnit/Mockito) e o plugin do Gatling.
- Criar um workflow GitHub Actions que execute os 3 jobs (build/test -> sonar -> gatling) e publique artefatos.

---

Fim do documento.

````
}

tasks.test {
  finalizedBy tasks.jacocoTestReport
}

jacocoTestReport {
  reports {
    xml.required = true
    html.required = true
  }
}
```

Comando (Windows PowerShell):

```
./gradlew clean test jacocoTestReport
```

Integração JaCoCo → SonarQube

- Configure Sonar para ler o relatório XML gerado pelo JaCoCo. Propriedade comum:

```
-Dsonar.coverage.jacoco.xmlReportPaths=target/site/jacoco/jacoco.xml
```

ou, para Gradle, o caminho para `build/reports/jacoco/test/jacocoTestReport.xml`.

Exemplo de execução Sonar após gerar cobertura (Maven):

```
mvn clean verify sonar:sonar -Dsonar.host.url=https://seu-sonarqube -Dsonar.login=SEU_TOKEN -Dsonar.coverage.jacoco.xmlReportPaths=target/site/jacoco/jacoco.xml
```

Observação: em versões mais novas do SonarQube, a propriedade pode ser `sonar.coverage.jacoco.xmlReportPaths` ou `sonar.java.coveragePlugin=jacoco`; confira a documentação da versão que você usa.

---

## Exemplo de pipeline CI mínima (conceito)

1. Checkout do código
2. Build + testes (mvn clean verify ou ./gradlew test)
3. Gerar relatório JaCoCo (XML)
4. Executar SonarQube Scanner apontando para o relatório XML
5. Falhar o pipeline se o Quality Gate do Sonar falhar

## Boas práticas de integração

- Execute a análise Sonar com um token de CI (não commitado). Use variáveis de ambiente no CI.
- Defina limites razoáveis de cobertura (ex.: 80%) e vá melhorando gradualmente.
- Bloqueie merges com Quality Gate falhando.
- Priorize correções de vulnerabilidades e bugs críticos.

---

## Recursos e leitura adicional

- OWASP Top 10: https://owasp.org/www-project-top-ten/
- SonarQube: https://www.sonarqube.org/
- JaCoCo: https://www.jacoco.org/jacoco/

---

## Observações finais

Este documento é um guia rápido. Para cada projeto, ajuste as versões dos plugins, caminhos dos relatórios e políticas de qualidade conforme a necessidade. Se quiser, eu adapto este README para incluir exemplos específicos do seu build (pom.xml ou build.gradle) e para gerar um exemplo de pipeline (GitHub Actions / Azure DevOps / GitLab CI).
