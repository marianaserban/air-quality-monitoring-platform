FROM eclipse-temurin:17

LABEL mentainer="marianaserban734@gmail.com"

WORKDIR /app

COPY target/data-processing-0.0.1-SNAPSHOT.jar /app/data-processing.jar

ENV SPRING_PROFILES_ACTIVE=docker

ENTRYPOINT ["java", "-jar", "data-processing.jar"]