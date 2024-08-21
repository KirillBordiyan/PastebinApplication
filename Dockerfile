FROM openjdk:17
COPY target/*.jar application.jar
ENTRYPOINT ["java", "-jar", "application.jar"]