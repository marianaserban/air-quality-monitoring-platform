package air.quality.platform.dashboard.kafka;

import air.quality.platform.dashboard.model.AirQualityInformation;
import air.quality.platform.dashboard.repository.AirQualityInformationRepository;
import air.quality.platform.data_processing.model.AirQualityEvent;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

@Component
public class AirQualityDashboardConsumer {

    private final AirQualityInformationRepository repository;

    public AirQualityDashboardConsumer(AirQualityInformationRepository repository) {
        this.repository=repository;
    }

    @KafkaListener(
            topics = "${spring.kafka.topic.name}"
            ,groupId = "${spring.kafka.consumer.group-id}"
    )
    public void consume(AirQualityEvent event) {
        AirQualityInformation information=new AirQualityInformation();
        information.setSensorId(event.getSensorId());
        information.setScore(event.getScore());
        information.setLocation(event.getLocation());
        information.setTimestamp(Instant.parse(event.getTimestamp()));
        information.setNotified(event.isShouldNotify());
        information.setScoreLevel(getAirQualityLabel(event.getScore()));
        repository.save(information);
    }
    public static String getAirQualityLabel(double score) {
        if (score >= 90) return "EXCELLENT";
        if (score >= 80) return "GOOD";
        if (score >= 60) return "MODERATE";
        if (score >= 40) return "POOR";
        if (score >= 20) return "VERY POOR";
        return "HAZARDOUS";
    }
}
