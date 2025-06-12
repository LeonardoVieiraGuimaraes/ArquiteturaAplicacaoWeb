FROM openjdk:17-jdk-alpine
VOLUME /config
COPY target/SpringCloudServer-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8888
ENTRYPOINT ["java","-jar","/app.jar"]
