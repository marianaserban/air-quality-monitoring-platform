package air.quality.platform.data_ingestion.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "sensor_readings")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class SensorReadingDocument {
    @Id
    private String id;
    private String sensorId;
    private String timestamp;
    private double pm25;
    private double co2;
    private double temperature;
    private String location;
}