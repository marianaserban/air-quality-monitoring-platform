FROM eclipse-temurin:17

LABEL mentainer="marianaserban734@gmail.com"

WORKDIR /app

COPY target/data-ingestion-0.0.1-SNAPSHOT.jar /app/data-ingestion.jar

ENV SPRING_PROFILES_ACTIVE=docker

ENTRYPOINT ["java", "-jar", "data-ingestion.jar"]