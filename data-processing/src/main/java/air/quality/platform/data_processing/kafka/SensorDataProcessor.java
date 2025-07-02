package air.quality.platform.data_processing.kafka;

import air.quality.platform.data_processing.model.AirQualityEvent;
import air.quality.platform.sensor_data_producer.model.SensorReading;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.UUID;

@Component
public class SensorDataProcessor {

    private final KafkaTemplate<String, AirQualityEvent> kafkaTemplate;

    @Value("${spring.kafka.producer.topic.name}")
    private String topicName;

    public SensorDataProcessor(KafkaTemplate<String, AirQualityEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @KafkaListener(
            topics = "${spring.kafka.consumer.topic.name}"
            ,groupId = "${spring.kafka.consumer.group-id}"
    )
    public void process(SensorReading reading) {

        double score = computeAirQualityScore(reading);
        AirQualityEvent airQualityEvent=new AirQualityEvent(UUID.randomUUID().toString(),
                reading.getSensorId(), score, reading.getLocation(), Instant.now().toString(),false);
        if(score<50)airQualityEvent.setShouldNotify(true);

        Message<AirQualityEvent> message = MessageBuilder
                    .withPayload(airQualityEvent)
                    .setHeader(KafkaHeaders.TOPIC, topicName)
                    .build();

        kafkaTemplate.send(message)
                    .thenAccept(result ->
                            System.out.println("Event sent: " + airQualityEvent.getId()))
                    .exceptionally(ex -> {
                        System.err.println("Failed to send event: " + ex.getMessage());
                        return null;
                    });

    }

    private double computeAirQualityScore(SensorReading r) {
        // Normalize PM2.5 (max 100 µg/m³)
        double pmPenalty = Math.min(r.getPm25(), 100) / 100.0;

        // Normalize CO2 (400–2000 ppm)
        double co2Penalty = Math.min(Math.max(r.getCo2() - 400, 0), 1600) / 1600.0;

        // Normalize temperature (ideal 21°C)
        double tempDelta = Math.abs(r.getTemperature() - 21);
        double tempPenalty = Math.min(tempDelta, 10) / 10.0;

        double penalty = pmPenalty * 0.4 + co2Penalty * 0.4 + tempPenalty * 0.2;

        double score = 100 - (penalty * 100);
        return Math.round(score * 100.0) / 100.0;
    }
}
