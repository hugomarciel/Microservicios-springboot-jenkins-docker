FROM openjdk:17
ARG JAR_FILE=target/config-service.jar
#WORKDIR /app
COPY ${JAR_FILE} config-service.jar
ENTRYPOINT ["java", "-jar", "/config-service.jar"]

