FROM openjdk:17-alpine

ARG JAR_FILE=/build/libs/ticketing-1.0.0.jar

COPY ${JAR_FILE} /app.jar

ENTRYPOINT ["java", "-jar" , "/app.jar"]
