FROM openjdk:11-jre-slim
WORKDIR /sab
ARG JAR_FILE
ADD target/*.jar app.jar
ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "app.jar"]
