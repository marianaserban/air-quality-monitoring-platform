FROM eclipse-temurin:17

LABEL mentainer="marianaserban734@gmail.com"

WORKDIR /app

COPY target/air-event-service-0.0.1-SNAPSHOT.jar /app/air-event-service.jar

ENV SPRING_PROFILES_ACTIVE=docker

ENTRYPOINT ["java", "-jar", "air-event-service.jar"]