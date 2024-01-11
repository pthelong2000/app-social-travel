package app.commentservice.dto.request;

import lombok.Builder;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Builder
public class ChildCommentRequest {
    private final long parentCommentId;
    private final long userId;
    private final String content;
    private final MultipartFile image;
}
