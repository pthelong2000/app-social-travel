package app.chatsservice.service;

import app.chatsservice.dto.response.ConversationMemberNicknameResponse;

public interface ConversationMemberService {

    ConversationMemberNicknameResponse updateConversationMemberNickname(Long conversationId, Long memberId, String nickname);
}
