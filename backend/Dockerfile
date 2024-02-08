FROM eclipse-temurin:21-jdk-alpine
VOLUME /tmp
COPY target/*.jar app.jar
ENTRYPOINT ["java", "-Dspring.profiles.active=prod","-jar","/app.jar"]
EXPOSE 10000