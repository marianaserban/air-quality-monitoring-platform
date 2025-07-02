package air.quality.platform.data_processing.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AirQualityEvent {
    private String id;
    private String sensorId;
    private double score;
    private String location;
    private String timestamp;
    boolean shouldNotify;

}