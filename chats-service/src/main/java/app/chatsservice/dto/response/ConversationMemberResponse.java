package app.chatsservice.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ConversationMemberResponse {

    @JsonProperty("conversation_id")
    private String conversationId;

    @JsonProperty("member_id")
    private String memberId;
}
