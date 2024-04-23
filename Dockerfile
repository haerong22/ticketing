FROM openjdk:17-alpine

ARG JAR_FILE=./build/libs/ticketing-1.0.0.jar

ENV PROFILE=dev

COPY ${JAR_FILE} /app.jar

ENTRYPOINT java -Dspring.profiles.active=${PROFILE} -jar /app.jar
