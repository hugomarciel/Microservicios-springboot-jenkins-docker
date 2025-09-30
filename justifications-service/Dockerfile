FROM openjdk:17
ARG JAR_FILE=target/justifications.jar
COPY ${JAR_FILE} justifications.jar
ENTRYPOINT ["java","-jar","/justifications.jar"]