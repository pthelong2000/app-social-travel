package app.chatsservice.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ConversationNameRequest {

    @JsonProperty("conversation_name")
    private String conversationName;
}
