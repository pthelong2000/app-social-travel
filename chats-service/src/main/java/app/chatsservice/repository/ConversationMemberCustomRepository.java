package app.chatsservice.repository;

public interface ConversationMemberCustomRepository {

    Long findConversationIdByTwoMemberId(Long firstMemberId, Long secondMemberId);

    void updateConversationMemberNickname(Long conversationId, Long memberId, String nickname);


}
