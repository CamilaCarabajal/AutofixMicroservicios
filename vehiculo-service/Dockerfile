FROM openjdk:17
ARG JAR_FILE=./target/*.jar
COPY ${JAR_FILE} vehiculo-service.jar
ENTRYPOINT ["java","-jar","/vehiculo-service.jar"]