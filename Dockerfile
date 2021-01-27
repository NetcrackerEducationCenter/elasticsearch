FROM openjdk:8-jdk-alpine
RUN addgroup -S elasticsearch && adduser -S elasticsearch -G elasticsearch
USER elasticsearch:elasticsearch
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]