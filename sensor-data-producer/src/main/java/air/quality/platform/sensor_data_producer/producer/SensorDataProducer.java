package air.quality.platform.sensor_data_producer.producer;

import air.quality.platform.sensor_data_producer.model.SensorReading;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Component
public class SensorDataProducer {
    private NewTopic topic;
    private final KafkaTemplate<String, SensorReading> kafkaTemplate;
    private final Random random = new Random();
    private final List<String> locations = List.of("Sector 1", "Sector 2", "Sector 3","Sector 3","Sector 5","Sector 6");

    public SensorDataProducer(KafkaTemplate<String, SensorReading> kafkaTemplate,NewTopic topic) {
        this.kafkaTemplate = kafkaTemplate;
        this.topic = topic;
    }

    //30 secunde
    @Scheduled(fixedRate = 30 * 1000)
    public void sendData() {
        SensorReading reading = new SensorReading(
                "sensor-" + random.nextInt(100),
                Instant.now().toString(),
                random.nextDouble() * 100,
                350 + random.nextDouble() * 100,
                15 + random.nextDouble() * 15,
                locations.get(random.nextInt(locations.size()))
        );
        Message<SensorReading> message = MessageBuilder
                .withPayload(reading)
                .setHeader(KafkaHeaders.TOPIC, topic.name())
                .build();
        kafkaTemplate.send(message);
    }
}
