package app.chatsservice.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class NewConversationRequest {

    private List<String> membersId;
}
