package app.chatsservice.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class ConversationResponse {

    private String id;
    private String conversationName;
    private String avatar;
    private String createdAt;
    private String memberCount;
    private List<Member> members;
    private Boolean isGroupChat;

    @Getter
    @Builder
    public static class Member {
        private String id;
        private String name;
        private String nickname;
        private String avatar;
    }
}
