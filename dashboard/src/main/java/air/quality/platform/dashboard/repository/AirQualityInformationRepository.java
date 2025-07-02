package air.quality.platform.dashboard.repository;

import air.quality.platform.dashboard.model.AirQualityInformation;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface AirQualityInformationRepository extends MongoRepository<AirQualityInformation, String> {

    List<AirQualityInformation> findAllByOrderByTimestampDesc();

    AirQualityInformation findTopBySensorIdOrderByTimestampDesc(String sensorId);

    List<AirQualityInformation> findByScoreLessThan(double scoreThreshold);

    List<AirQualityInformation> findByScoreLevel(String scoreLevel);

    List<AirQualityInformation> findByNotifiedTrue();

    List<AirQualityInformation> findByTimestampGreaterThan(String timestamp);

    @Aggregation(pipeline = {
            "{ $group: { _id: \"$location\", averageScore: { $avg: \"$score\" } } }",
            "{ $project: { location: \"$_id\", averageScore: 1, _id: 0 } }"
    })
    List<Map<String, Object>> getAverageScorePerLocation();

    @Aggregation(pipeline = {
            "{ $group: { _id: \"$scoreLevel\", count: { $sum: 1 } } }",
            "{ $project: { scoreLevel: \"$_id\", count: 1, _id: 0 } }"
    })
    List<Map<String, Object>> getCountPerScoreLevel();
}

