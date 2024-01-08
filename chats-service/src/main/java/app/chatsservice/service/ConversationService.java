package app.chatsservice.service;

import app.chatsservice.dto.response.ConversationNameResponse;
import app.chatsservice.dto.response.ConversationResponse;

import java.util.List;

public interface ConversationService {

    ConversationResponse getConversationById(Long conversationId);

    ConversationNameResponse updateConversationName(Long conversationId, String conversationName);

    ConversationResponse createConversation(List<String> membersId);
}
