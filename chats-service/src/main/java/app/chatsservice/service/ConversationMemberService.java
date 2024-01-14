package app.chatsservice.service;

import app.chatsservice.dto.response.ConversationMemberNicknameResponse;
import app.chatsservice.dto.response.ConversationMemberResponse;

public interface ConversationMemberService {

    ConversationMemberNicknameResponse updateConversationMemberNickname(Long conversationId, Long memberId, String nickname);

    ConversationMemberResponse addConversationMember(Long conversationId, Long memberId);

    ConversationMemberResponse removeConversationMember(Long conversationId, Long memberId);
}
