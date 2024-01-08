package app.commentservice.dto.request;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ParentCommentRequest {
    private final long postId;
    private final long userId;
    private final String content;
    private final long imageId;
}
