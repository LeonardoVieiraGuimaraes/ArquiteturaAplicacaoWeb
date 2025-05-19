# Como executar o Gatling com Spring Boot

1. Inicie sua aplicação Spring Boot:
   ```sh
   ./mvnw spring-boot:run
   ```
   ou
   ```sh
   java -jar target/seu-app.jar
   ```

2. Em outro terminal, execute o Gatling:
   ```sh
   mvn gatling:test
   ```

3. Veja os relatórios em `target/gatling/`.

> Certifique-se que o endpoint da sua API no Gatling (`http://localhost:8080`) corresponde ao endereço da sua aplicação Spring Boot.
