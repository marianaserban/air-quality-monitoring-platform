package air.quality.platform.event.repository;

import air.quality.platform.event.entity.AirQualityEventEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AirEventRepository extends JpaRepository<AirQualityEventEntity, Long> {
}
