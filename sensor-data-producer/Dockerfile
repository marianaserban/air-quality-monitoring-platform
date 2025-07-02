FROM eclipse-temurin:17

LABEL mentainer="marianaserban734@gmail.com"

WORKDIR /app

COPY target/sensor-data-producer-0.0.1-SNAPSHOT.jar /app/sensor-data-producer.jar
#COPY wait-for-config.sh /app/wait-for-config.sh

#RUN chmod +x wait-for-config.sh

ENV SPRING_PROFILES_ACTIVE=docker

ENTRYPOINT ["java", "-jar", "sensor-data-producer.jar"]