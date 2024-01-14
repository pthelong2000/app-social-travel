package app.chatsservice.service;

import app.chatsservice.entity.Message;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KafkaMessageListener {

    private final SimpMessagingTemplate simpMessagingTemplate;

    @KafkaListener(topics = "chat", groupId = "chats-service")
    public void listen(@Payload Message message) {

        simpMessagingTemplate.convertAndSend("/message/" + message.getConversationId(), message);
    }
}
