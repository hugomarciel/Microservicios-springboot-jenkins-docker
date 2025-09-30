FROM openjdk:17
ARG JAR_FILE=target/inandoutreg.jar
COPY ${JAR_FILE} inandoutreg.jar
ENTRYPOINT ["java","-jar","/inandoutreg.jar"]