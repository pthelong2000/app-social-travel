package app.postservice.config;

import app.postservice.dto.request.CreatePostArticleRequest;
import app.postservice.service.PostArticleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Produced;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafkaStreams;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.support.serializer.JsonSerde;

import static app.postservice.utils.kafka.Topics.POST_ARTICLE;

@Slf4j
@Configuration
@EnableKafkaStreams
@RequiredArgsConstructor
public class KafkaConfig {

    private final PostArticleService postArticleService;

    @Bean
    public NewTopic postsTopic() {
        return TopicBuilder.name(POST_ARTICLE)
                .partitions(3)
                .replicas(1)
                .build();
    }

    @Bean
    public KStream<Long, CreatePostArticleRequest> postStream(StreamsBuilder builder) {
        Serde<Long> keySerde = Serdes.Long();
        JsonSerde<CreatePostArticleRequest> valueSerde = new JsonSerde<>(CreatePostArticleRequest.class);

        KStream<Long, CreatePostArticleRequest> stream = builder.stream(POST_ARTICLE, Consumed.with(keySerde, valueSerde));

        stream.foreach((key, post) -> {
            log.info("Received new post: key={}, post={}", key, post);
            postArticleService.savePostContent(post);
        });

        stream.to("Add post article: ", Produced.with(keySerde, valueSerde));

        return stream;
    }

}
