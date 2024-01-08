package app.chatsservice.repository;

public interface ConversationMemberCustomRepository {

    Long findConversationIdByTwoMemberId(Long firstMemberId, Long secondMemberId);
}
