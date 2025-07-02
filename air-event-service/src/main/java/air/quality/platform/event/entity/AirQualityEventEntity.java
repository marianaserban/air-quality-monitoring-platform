package air.quality.platform.event.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "air_quality_events")
public class AirQualityEventEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String location;
    @Column
    private String timestamp;
    @Column
    private double score;
    @Column
    private String sensorId;
    @Column
    boolean shouldNotify;

}
