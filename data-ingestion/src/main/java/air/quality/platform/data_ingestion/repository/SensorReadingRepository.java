package air.quality.platform.data_ingestion.repository;

import air.quality.platform.data_ingestion.model.SensorReadingDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SensorReadingRepository extends MongoRepository<SensorReadingDocument, String> {
}
