package app.commentservice.dto.request;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ChildCommentRequest {
    private final long parentCommentId;
    private final long userId;
    private final String content;
}
