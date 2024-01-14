package app.chatsservice.service;

import app.chatsservice.dto.request.MessageRequest;

public interface MessageService {
    void sendMessage(MessageRequest request, Long conversationId);
}
