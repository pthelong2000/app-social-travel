package app.commentservice.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ChildCommentResponse {

    @JsonProperty(namespace = "parent_id")
    private final String parentId;

    @JsonProperty(namespace = "user_id")
    private final String userId;

    @JsonProperty(namespace = "content")
    private final String content;

    @JsonProperty(namespace = "image")
    private final String image;

    @JsonProperty(namespace = "createdBy")
    private final String createdBy;

    @JsonProperty(namespace = "createdAt")
    private final String createdAt;

    @JsonProperty(namespace = "updatedBy")
    private final String updatedBy;

    @JsonProperty(namespace = "updatedAt")
    private final String updatedAt;
}
