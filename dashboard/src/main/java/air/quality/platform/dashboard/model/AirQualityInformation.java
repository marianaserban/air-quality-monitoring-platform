package air.quality.platform.dashboard.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Document(collection = "air_quality_information")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class AirQualityInformation {
    @Id
    private String id;
    private String sensorId;
    private double score;
    private String location;
    private Instant timestamp;
    boolean notified;
    private String scoreLevel;
}
