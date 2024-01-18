package app.commentservice.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaConfig {
    @Bean
    public NewTopic commentTopic(){
        return TopicBuilder.name("comment")
                .partitions(3)
                .replicas(1)
                .build();
    }
}
