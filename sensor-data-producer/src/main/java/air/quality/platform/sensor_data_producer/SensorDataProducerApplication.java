package air.quality.platform.sensor_data_producer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class SensorDataProducerApplication {

	public static void main(String[] args) {
		SpringApplication.run(SensorDataProducerApplication.class, args);
	}

}
