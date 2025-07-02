package air.quality.platform.dashboard.service;

import air.quality.platform.dashboard.model.AirQualityInformation;
import air.quality.platform.dashboard.repository.AirQualityInformationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AirQualityDashboardService {

    private final AirQualityInformationRepository repository;

    public List<AirQualityInformation> getAllSortedByTimestamp() {
        return repository.findAllByOrderByTimestampDesc();
    }

    public AirQualityInformation getLatestForSensor(String sensorId) {
        return repository.findTopBySensorIdOrderByTimestampDesc(sensorId);
    }

    public List<AirQualityInformation> getLowScores(double threshold) {
        return repository.findByScoreLessThan(threshold);
    }

    public List<AirQualityInformation> getByScoreLevel(String level) {
        return repository.findByScoreLevel(level);
    }

    public List<AirQualityInformation> getNotified() {
        return repository.findByNotifiedTrue();
    }

    public List<AirQualityInformation> getLast24Hours() {
        String since = OffsetDateTime.now().minusHours(24).toString();
        return repository.findByTimestampGreaterThan(since);
    }
/*
* [
  { "location": "Sector 2", "averageScore": 72.4 },
  { "location": "Sector 4", "averageScore": 48.9 }
]
*
* */
    public List<Map<String, Object>> getAverageScoreByLocation() {
        return repository.getAverageScorePerLocation();
    }

    /*
    * [
      { "scoreLevel": "GOOD", "count": 12 },
      { "scoreLevel": "BAD", "count": 3 }
    ]
*/
    public List<Map<String, Object>> getCountPerScoreLevel() {
        return repository.getCountPerScoreLevel();
    }
}
