FROM openjdk:17
ARG JAR_FILE=target/categorys.jar
COPY ${JAR_FILE} categorys.jar
ENTRYPOINT ["java","-jar","/categorys.jar"]