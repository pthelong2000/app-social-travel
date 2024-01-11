package app.chatsservice.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class ConversationResponse {

    @JsonProperty("conversation_id")
    private String id;

    @JsonProperty("conversation_name")
    private String conversationName;

    @JsonProperty("avatar")
    private String avatar;

    @JsonProperty("created_at")
    private String createdAt;

    @JsonProperty("member_count")
    private String memberCount;

    @JsonProperty("members")
    private List<Member> members;

    @JsonProperty("is_group_chat")
    private Boolean isGroupChat;

    @Getter
    @Builder
    public static class Member {

        @JsonProperty("id")
        private String id;

        @JsonProperty("name")
        private String name;

        @JsonProperty("nickname")
        private String nickname;

        @JsonProperty("avatar")
        private String avatar;
    }
}
