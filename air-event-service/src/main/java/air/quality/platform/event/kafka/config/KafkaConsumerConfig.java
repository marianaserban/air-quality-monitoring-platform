package air.quality.platform.event.kafka.config;

import air.quality.platform.data_processing.model.AirQualityEvent;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;

@EnableKafka
@Configuration
public class KafkaConsumerConfig {

    @Value("${spring.kafka.bootstrap-servers}")
    private String BOOTSTRAP_SERVERS_CONFIG;

    @Value("${spring.kafka.consumer.group-id}")
    private String GROUP_ID_CONFIG;

    @Value("${spring.kafka.consumer.auto-offset-reset}")
    private String AUTO_OFFSET_RESET_CONFIG;


    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, AirQualityEvent> kafkaListenerContainerFactory() {

        ConcurrentKafkaListenerContainerFactory<String, AirQualityEvent> factory =
                new ConcurrentKafkaListenerContainerFactory<>();

        factory.setConsumerFactory(consumerFactory());
        factory.getContainerProperties().setAckMode(ContainerProperties.AckMode.MANUAL);
        return factory;
    }

    @Bean
    public ConsumerFactory<String, AirQualityEvent> consumerFactory() {
        JsonDeserializer<AirQualityEvent> deserializer = new JsonDeserializer<>(AirQualityEvent.class);
        deserializer.addTrustedPackages("*");

        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS_CONFIG);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, GROUP_ID_CONFIG);
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, AUTO_OFFSET_RESET_CONFIG);
        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false);

        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,deserializer);

        return new DefaultKafkaConsumerFactory<>(props, new StringDeserializer(), deserializer);
    }

}
