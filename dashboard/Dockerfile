FROM eclipse-temurin:17

LABEL mentainer="marianaserban734@gmail.com"

WORKDIR /app

# Copy the specific JAR file from the build stage
COPY target/dashboard-0.0.1-SNAPSHOT.jar /app/dashboard.jar

ENV SPRING_PROFILES_ACTIVE=docker

ENTRYPOINT ["java", "-jar", "dashboard.jar"]