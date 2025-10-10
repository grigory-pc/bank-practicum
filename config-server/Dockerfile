FROM eclipse-temurin:21-jdk-alpine
WORKDIR /app
COPY build/libs/*.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]

#FROM eclipse-temurin:21-jdk-alpine
#WORKDIR /app
#COPY target/config-server-*.jar app.jar
#EXPOSE 8888
#CMD ["java", "-jar", "app.jar"]
