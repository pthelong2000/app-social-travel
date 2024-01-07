package app.chatsservice.service;

import app.chatsservice.dto.response.ConversationResponse;

public interface ConversationService {

    ConversationResponse getConversationById(Long conversationId);
}
