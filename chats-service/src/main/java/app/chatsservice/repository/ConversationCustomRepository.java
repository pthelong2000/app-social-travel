package app.chatsservice.repository;

public interface ConversationCustomRepository {

    void updateConversationName(Long conversationId, String conversationName);
}
