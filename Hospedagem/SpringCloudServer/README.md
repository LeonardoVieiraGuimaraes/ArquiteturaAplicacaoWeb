# Spring Cloud Config Server - Docker

## Passos realizados

1. **Configuração do application.properties**
   - Ajustado para buscar arquivos de configuração em `/config` dentro do container:
     ```
     spring.cloud.config.server.native.search-locations=file:/config
     ```

2. **Criação do Dockerfile**
   - Utiliza a imagem `openjdk:17-jdk-alpine`.
   - Copia o JAR gerado pelo Maven para dentro do container.
   - Expõe a porta 8888.
   - Exemplo:
     ```dockerfile
     FROM openjdk:17-jdk-alpine
     VOLUME /config
     COPY target/SpringCloudServer-0.0.1-SNAPSHOT.jar app.jar
     EXPOSE 8888
     ENTRYPOINT ["java","-jar","/app.jar"]
     ```

3. **Criação do docker-compose.yml**
   - Orquestra o serviço, mapeando a porta 8888 e o diretório `./config` local para `/config` no container.
   - Exemplo:
     ```yaml
     version: '3.8'
     services:
       config-server:
         build: .
         ports:
           - "8888:8888"
         volumes:
           - ./config:/config
         restart: unless-stopped
     ```

4. **Build do JAR**
   - Execute na raiz do projeto:
     ```
     mvn clean package -DskipTests
     ```

5. **Subir o serviço**
   - Execute:
     ```
     docker-compose up -d --build
     ```

6. **Observações**
   - O diretório `config` deve conter os arquivos `.properties` ou `.yml` de configuração dos clientes.
   - Para acessar: `http://localhost:8888/{nome-do-app}/default`

---
