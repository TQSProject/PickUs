FROM eclipse-temurin:17-jdk-alpine
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar", "-Dspring.datasource.url=jdbc:mysql://${DATABASE_HOST}:${DATABASE_PORT}/${DATABASE_NAME}", "/app.jar"]