package air.quality.platform.event.kafka;

import air.quality.platform.event.entity.AirQualityEventEntity;
import air.quality.platform.event.service.AirEventService;
import air.quality.platform.data_processing.model.AirQualityEvent;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

@Component
public class AirEventConsumer {

    private final KafkaTemplate<String, AirQualityEvent> kafkaTemplate;
    private final AirEventService alertService;

    @Value("${spring.dlq.topic.name}")
    private String dlqTopic;

    @Value("${spring.kafka.producer.topic.dashboard.name}")
    private String dashboardTopic;

    public AirEventConsumer(KafkaTemplate<String, AirQualityEvent> kafkaTemplate, AirEventService alertService) {
        this.kafkaTemplate = kafkaTemplate;
        this.alertService=alertService;
    }

    @KafkaListener(
            topics = "${spring.kafka.consumer.topic.name}"
            ,groupId = "${spring.kafka.consumer.group-id}",
            containerFactory = "kafkaListenerContainerFactory"
    )
    public void listen(AirQualityEvent airQualityEvent, Acknowledgment ack) {
        try {

            // exception for test
            /*if (airQualityEvent.getScore() < 0) {
                throw new RuntimeException("Simulated processing error for alert: " + airQualityEvent.getId());
            }*/


            AirQualityEventEntity airQualityEventEntity=new AirQualityEventEntity();
            airQualityEventEntity.setLocation(airQualityEvent.getLocation());
            airQualityEventEntity.setTimestamp(airQualityEvent.getTimestamp());
            airQualityEventEntity.setScore(airQualityEvent.getScore());
            airQualityEventEntity.setSensorId(airQualityEvent.getSensorId());
            airQualityEventEntity.setShouldNotify(airQualityEvent.isShouldNotify());

            alertService.saveAlert(airQualityEventEntity);

            if(airQualityEvent.isShouldNotify()){
                //notifications
            }

            //dashboard
            Message<AirQualityEvent> message = MessageBuilder
                    .withPayload(airQualityEvent)
                    .setHeader(KafkaHeaders.TOPIC, dashboardTopic)
                    .build();
            kafkaTemplate.send(message);

            //at least once delivery semantic
            ack.acknowledge();

        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage() + " -> sending to DLQ");
            kafkaTemplate.send(dlqTopic, airQualityEvent.getId(), airQualityEvent);
            ack.acknowledge();
        }
    }
}
