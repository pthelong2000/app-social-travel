package app.chatsservice.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ConversationNameResponse {

    private String conversationId;
    private String conversationName;
}
