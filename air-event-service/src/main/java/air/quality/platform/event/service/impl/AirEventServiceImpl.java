package air.quality.platform.event.service.impl;

import air.quality.platform.event.entity.AirQualityEventEntity;
import air.quality.platform.event.repository.AirEventRepository;
import air.quality.platform.event.service.AirEventService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AirEventServiceImpl implements AirEventService {

    private AirEventRepository alertRepository;
    @Override
    public AirQualityEventEntity saveAlert(AirQualityEventEntity alert) {
        return alertRepository.save(alert);
    }
}
