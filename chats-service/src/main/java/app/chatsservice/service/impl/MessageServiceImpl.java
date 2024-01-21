package app.chatsservice.service.impl;

import app.chatsservice.dto.request.MessageRequest;
import app.chatsservice.entity.Message;
import app.chatsservice.repository.ConversationMemberRepository;
import app.chatsservice.repository.ConversationRepository;
import app.chatsservice.repository.MessageRepository;
import app.chatsservice.service.MessageService;
import app.chatsservice.utils.SystemDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {

    private final KafkaTemplate<String, Message> kafkaTemplate;
    private final ConversationRepository conversationRepository;
    private final ConversationMemberRepository conversationMemberRepository;
    private final MessageRepository messageRepository;
    private final SystemDateTime systemDateTime;
    private final RestTemplate restTemplate;

    @Override
    public void sendMessage(MessageRequest request, Long conversationId) {
        // User id of the authenticated user
        Long authUserId = 1L;

        conversationRepository.findById(conversationId)
                .orElseThrow(() -> new RuntimeException("Conversation not found"));

        if (!conversationMemberRepository.existsByConversationIdAndMemberId(conversationId, authUserId)) {
            throw new RuntimeException("Conversation member not found");
        }

        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl("http://FILE-SERVICE/api/v1/file/upload")
                .queryParam("file", request.getFileData());

        ResponseEntity<String> filePath = restTemplate.exchange(uriBuilder.toUriString(),
                HttpMethod.POST, null, String.class);

        if (!filePath.getStatusCode().is2xxSuccessful()) {
            throw new RuntimeException("File upload failed");
        }

        Message message = Message.builder()
                .conversationId(conversationId)
                .senderId(authUserId)
                .content(request.getContent())
                .filePath(filePath.getBody())
                .createAt(systemDateTime.now())
                .createdBy(authUserId)
                .build();

        messageRepository.save(message);

        kafkaTemplate.send("chat", message);
    }
}
