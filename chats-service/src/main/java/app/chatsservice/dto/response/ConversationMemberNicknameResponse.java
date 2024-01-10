package app.chatsservice.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ConversationMemberNicknameResponse {

        private String conversationId;
        private String memberId;
        private String nickname;
}
