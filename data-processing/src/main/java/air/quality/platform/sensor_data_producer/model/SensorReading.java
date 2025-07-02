package air.quality.platform.sensor_data_producer.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SensorReading {
    private String sensorId;
    private String timestamp;
    private double pm25;
    private double co2;
    private double temperature;
    private String location;
}