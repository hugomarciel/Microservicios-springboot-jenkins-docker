FROM openjdk:17
ARG JAR_FILE=target/eureka-service.jar
#WORKDIR /app
COPY ${JAR_FILE} eureka-service.jar
ENTRYPOINT ["java", "-jar", "/eureka-service.jar"]

