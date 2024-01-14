package app.chatsservice.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class ConversationAllMemberResponse {

    @JsonProperty("conversation_id")
    private String conversationId;

    @JsonProperty("members")
    private List<Member> members;

    @Getter
    @Builder
    public static class Member {

        @JsonProperty("id")
        private String id;

        @JsonProperty("name")
        private String name;

        @JsonProperty("nickname")
        private String nickname;
    }
}
