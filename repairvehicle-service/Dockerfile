FROM openjdk:17
ARG JAR_FILE=./target/*.jar
COPY ${JAR_FILE} repairvehicle-service.jar
ENTRYPOINT ["java","-jar","/repairvehicle-service.jar"]