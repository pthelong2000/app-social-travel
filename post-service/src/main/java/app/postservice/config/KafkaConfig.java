package app.postservice.config;

import app.postservice.entity.PostArticle;
import app.postservice.service.PostArticleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.*;
import org.apache.kafka.streams.state.KeyValueBytesStoreSupplier;
import org.apache.kafka.streams.state.Stores;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.support.serializer.JsonSerde;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.time.Duration;

import static app.postservice.utils.kafka.Topics.*;


@Slf4j
@Configuration
@RequiredArgsConstructor
public class KafkaConfig {

    private PostArticleService postArticleService;


    @Autowired
    public KafkaConfig(PostArticleService postArticleService) {
        this.postArticleService = postArticleService;
    }

    @Bean
    public NewTopic orders() {
        return TopicBuilder.name(POST_ARTICLE)
                .partitions(3)
                .compact()
                .build();
    }


    //this is the one that will get results from other topics to check
    @Bean
    public KStream<Long, PostArticle> stream(StreamsBuilder builder) {



        Serde<Long> keySerde =  Serdes.Long();
        JsonSerde<PostArticle> valueSerde = new JsonSerde<>(PostArticle.class);


        KStream<Long, PostArticle> commentStream = builder
                .stream(COMMENT, Consumed.with(keySerde, valueSerde));

        KStream<Long, PostArticle> stockStream = builder
                .stream(NEW_FEED,Consumed.with(keySerde, valueSerde));

        //join records from both tables
        commentStream.join(
                        stockStream,
                        postArticleService::savePostContent,
                        JoinWindows.of(Duration.ofSeconds(10)), // timestamps of matched records must fall within this window of time
                        StreamJoined.with(keySerde, valueSerde, valueSerde)//the key must be the same, 1st stream serde, 2nd stream serde
                )
                .peek((k,v)->log.info("Kafka stream match: key[{}],value[{}]",k,v))
                .to(POST_ARTICLE);

        return commentStream;
    }
    /**
     * To build a persistent key-value store
     * This KTable will be used to store all the Orders
     ***/

    @Bean
    public KTable<Long, PostArticle> table(StreamsBuilder builder) {

        KeyValueBytesStoreSupplier store = Stores.persistentKeyValueStore(POST_ARTICLE);

        Serde<Long> keySerde =  Serdes.Long();
        JsonSerde<PostArticle> valueSerde = new JsonSerde<>(PostArticle.class);

        KStream<Long, PostArticle> stream = builder
                .stream(POST_ARTICLE, Consumed.with(keySerde, valueSerde))
                .peek((k,v)->log.info("Kafka persistence table: key[{}],value[{}]",k,v));

        return stream.toTable(Materialized.<Long, PostArticle>as(store)
                .withKeySerde(keySerde)
                .withValueSerde(valueSerde));
    }

    @Bean
    public TaskExecutor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(5);
        executor.setMaxPoolSize(5);
        executor.setThreadNamePrefix("kafkaSender-");
        executor.initialize();
        return executor;
    }


}
