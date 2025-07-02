FROM eclipse-temurin:17

LABEL mentainer="marianaserban734@gmail.com"

WORKDIR /app

COPY target/api-gateway-0.0.1-SNAPSHOT.jar /app/api-gateway.jar

ENV SPRING_PROFILES_ACTIVE=docker

ENTRYPOINT ["java", "-jar", "api-gateway.jar"]