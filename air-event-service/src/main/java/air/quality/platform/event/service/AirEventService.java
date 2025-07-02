package air.quality.platform.event.service;

import air.quality.platform.event.entity.AirQualityEventEntity;

public interface AirEventService {

    AirQualityEventEntity saveAlert(AirQualityEventEntity alert);
}
