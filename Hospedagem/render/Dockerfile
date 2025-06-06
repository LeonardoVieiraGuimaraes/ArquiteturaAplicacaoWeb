FROM ubuntu:latest AS build
LABEL authors="Leonardo Vieira Guimaraes"
# Atualize o repositório e instale o OpenJDK 17 e Maven
RUN apt-get update && \
    apt-get install -y openjdk-17-jdk maven

# Defina o diretório de trabalho
WORKDIR /app

# Copie o código-fonte para o contêiner
COPY . .

# Compile o projeto
RUN mvn clean install

# Use a imagem oficial do OpenJDK 17 para rodar a aplicação
FROM openjdk:17-jdk-slim

# Defina o diretório de trabalho
WORKDIR /app

# Copie o arquivo JAR gerado para o contêiner
COPY --from=build /app/target/*.jar app.jar

# Exponha a porta que a aplicação irá rodar
EXPOSE 8080

# Comando para iniciar a aplicação
ENTRYPOINT ["java", "-jar", "app.jar"]