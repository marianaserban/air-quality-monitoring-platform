package air.quality.platform.data_processing.kafka.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfig {

    @Value("${spring.kafka.producer.topic.name}")
    private String topicName;

    // spring bean for kafka topic
    @Bean
    public NewTopic topicName(){
        return TopicBuilder.name(topicName)
                .build();
    }
}
