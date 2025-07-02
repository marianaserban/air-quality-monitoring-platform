package air.quality.platform.data_ingestion.kafka;


import air.quality.platform.data_ingestion.model.SensorReadingDocument;
import air.quality.platform.data_ingestion.repository.SensorReadingRepository;
import air.quality.platform.sensor_data_producer.model.SensorReading;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class SensorDataConsumer {

    private final SensorReadingRepository repository;

    public SensorDataConsumer(SensorReadingRepository repository) {
        this.repository = repository;
    }

    @KafkaListener(
            topics = "${spring.kafka.topic.name}"
            ,groupId = "${spring.kafka.consumer.group-id}"
    )
    public void consume(SensorReading reading) {
        if (isValid(reading)) {
            SensorReadingDocument sensorReadingDocument=new SensorReadingDocument();
            sensorReadingDocument.setSensorId(reading.getSensorId());
            sensorReadingDocument.setTimestamp(reading.getTimestamp());
            sensorReadingDocument.setPm25(reading.getPm25());
            sensorReadingDocument.setCo2(reading.getCo2());
            sensorReadingDocument.setTemperature(reading.getTemperature());
            sensorReadingDocument.setLocation(reading.getLocation());
            repository.save(sensorReadingDocument);
        } else {
            System.out.println("Invalid data: " + reading);
        }
    }

    private boolean isValid(SensorReading reading) {
        return reading.getPm25() >= 0 && reading.getCo2() >= 0 && reading.getTemperature() >= -50;
    }
}
