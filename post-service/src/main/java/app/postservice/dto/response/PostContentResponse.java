package app.postservice.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PostContentResponse {

    @JsonProperty(namespace = "title")
    private final String title;

    @JsonProperty(namespace = "content")
    private final String content;

    @JsonProperty(namespace = "image_url")
    private final String imageUrl;

    @JsonProperty(namespace = "created_at")
    private final String createdAt;

    @JsonProperty(namespace = "created_by")
    private final String createdBy;

    @JsonProperty(namespace = "updated_at")
    private final String updatedAt;

    @JsonProperty(namespace = "updated_by")
    private final String updatedBy;
}
