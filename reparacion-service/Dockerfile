FROM openjdk:17
ARG JAR_FILE=./target/*.jar
COPY ${JAR_FILE} reparacion-service.jar
ENTRYPOINT ["java","-jar","/reparacion-service.jar"]