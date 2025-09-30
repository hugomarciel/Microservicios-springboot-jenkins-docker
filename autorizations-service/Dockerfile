FROM openjdk:17
ARG JAR_FILE=target/autorizations.jar
COPY ${JAR_FILE} autorizations.jar
ENTRYPOINT ["java","-jar","/autorizations.jar"]