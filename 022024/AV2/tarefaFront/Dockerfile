# Use a imagem base do OpenJDK
FROM openjdk:23-jdk

# Defina o diretório de trabalho dentro do contêiner
WORKDIR /app

# Copie o arquivo JAR da aplicação para o contêiner
COPY target/*.jar /app/my-spring-boot-app.jar

# Exponha a porta que a aplicação irá rodar
EXPOSE 8080

# Comando para executar a aplicação
ENTRYPOINT ["java", "-jar", "my-spring-boot-app.jar"]