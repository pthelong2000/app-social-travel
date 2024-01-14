package app.chatsservice.service;

import app.chatsservice.entity.Message;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
public class KafkaMessageListener {

    @KafkaListener(topics = "chat", groupId = "chats-service")
    public void listen(@Payload Message message) {

    }
}
