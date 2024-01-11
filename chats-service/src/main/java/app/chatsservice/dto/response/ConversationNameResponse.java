package app.chatsservice.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ConversationNameResponse {

    @JsonProperty("conversation_id")
    private String conversationId;

    @JsonProperty("conversation_name")
    private String conversationName;
}
