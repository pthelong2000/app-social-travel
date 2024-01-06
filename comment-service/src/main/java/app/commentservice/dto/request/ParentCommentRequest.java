package app.commentservice.dto.request;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ParentCommentRequest {
    private final long post_id;
    private final long user_id;
    private final String content;
}
