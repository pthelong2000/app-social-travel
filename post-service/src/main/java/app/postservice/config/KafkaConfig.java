package app.postservice.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;

import java.util.HashMap;
import java.util.Map;

public class KafkaConfig {

//    @Autowired
//    private KafkaProperties kafkaProperties;
//
//    @Value("${tpd.topic-name}")
//    private String topicName;
//
//    // Producer configuration
//
//    @Bean
//    public Map<String, Object> producerConfigs() {
//        Map<String, Object> props =
//                new HashMap<>(kafkaProperties.buildProducerProperties());
//        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,
//                StringSerializer.class);
//        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
//                JsonSerializer.class);
//        return props;
//    }
//
//    @Bean
//    public ProducerFactory<String, Object> producerFactory() {
//        return new DefaultKafkaProducerFactory<>(producerConfigs());
//    }
//
//    @Bean
//    public KafkaTemplate<String, Object> kafkaTemplate() {
//        return new KafkaTemplate<>(producerFactory());
//    }
//
//    @Bean
//    public NewTopic adviceTopic() {
//        return new NewTopic(topicName, 3, (short) 1);
//    }
}
